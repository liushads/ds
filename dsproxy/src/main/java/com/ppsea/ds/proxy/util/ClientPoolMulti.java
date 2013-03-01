package com.ppsea.ds.proxy.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * client的连接池
 * */
public class ClientPoolMulti {
	private static final Logger log = Logger.getLogger(ClientPoolMulti.class);
	private String host;
	private int port;
	private int size;
	private int type;
	private final int DEFAULT_TIMEOUT = 1000;
	private final int DEFAULT_FAIL_LIMIT_NUMS = 10;
	private final int DEFAULT_SUSPEND_SECONDS = 1 * 60;
	private int FAIL_LIMIT_NUMS = DEFAULT_FAIL_LIMIT_NUMS;
	private int SUSPEND_SECONDS = DEFAULT_SUSPEND_SECONDS;
	private LinkedBlockingQueue<SynClient> idleQ;
	private int failNums = 0;
	private long lastSuspendTime = 0;
	private boolean suspendFlag = false;


	/**
	 * 
	 * @param h
	 * @param p
	 * @param poolSize
	 * @param codecType
	 * @param timeout
	 */
	public void initClientPool(String h, int p, int poolSize, int codecType, int timeout, int failLimitNum, int suspendSeconds) {
		host = h;
		port = p;
		size = poolSize;
		type = codecType;
		this.FAIL_LIMIT_NUMS = failLimitNum;
		this.SUSPEND_SECONDS = suspendSeconds;

		idleQ = new LinkedBlockingQueue<SynClient>(size);

		int succ = 0;
		int fail = 0;
		for (int i = 0; i < size; i++) {
			SynClient client = new SynClient(host, port, timeout);
			if (client.connect())
				++succ;
			else
				++fail;
			idleQ.add(client);
		}
		log.info("client pool connect to:" + host + ":" + port + ",succ:" + succ + ",fail:" + fail);
	}

	/**
	 * 初始化连接池
	 * @param h host
	 * @param p port
	 * @param size pool size
	 * @param t abandonedTime
	 * */
	public ClientPoolMulti(String h, int p, int poolSize, int codecType) {
		initClientPool(h, p, poolSize, codecType, this.DEFAULT_TIMEOUT, this.DEFAULT_FAIL_LIMIT_NUMS, this.DEFAULT_SUSPEND_SECONDS);
	}

	/**
	 * 初始化连接池
	 * @param h
	 * @param p
	 * @param poolSize
	 * @param codecType
	 * @param timeout
	 */
	public ClientPoolMulti(String h, int p, int poolSize, int codecType, int timeout) {
		initClientPool(h, p, poolSize, codecType, timeout, this.DEFAULT_FAIL_LIMIT_NUMS, this.DEFAULT_SUSPEND_SECONDS);
	}

	/**
	 * 
	 * @param h
	 * @param p
	 * @param poolSize
	 * @param codecType
	 * @param timeout
	 */
	public ClientPoolMulti(String h, int p, int poolSize, int codecType, int timeout, int failLimitNum, int suspendSeconds) {
		initClientPool(h, p, poolSize, codecType, timeout, failLimitNum, suspendSeconds);
	}

	/**
	 * 利用空闲连接发,发送后等待应答，收到应答归还连
	 * 
	 * */
	public Object sendAndRecv(Object msg) {
		long now = System.currentTimeMillis();
		long inter = (now - this.lastSuspendTime) / 1000;
		if (suspendFlag == true && inter > 0 && inter < this.SUSPEND_SECONDS) {
			log.error("client pool has suspended," + host + ":" + port + "," + inter + "(s)->" + this.SUSPEND_SECONDS + "(s)");
			return null;
		}
		
		suspendFlag = false;
		SynClient client = null;
		try {
			client = idleQ.poll(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			log.error("retrieval socket client connection failed.idleQ.size="+idleQ.size(), e);
		}

		if (client == null) {
			log.error(host+":"+port+", idleQ is empty!!!, pool full");
			return null;
		}

		log.info("use "+client.getPeer()+",connected:"+client.isConnected());
		//是否要重新连接
		if (!client.isConnected()) {
			if (!client.connect()) {
				log.error("reconnect " + client.getPeer() + " fail");
			} else {
				log.info("reconnect " + client.getPeer() + " succ");
			}
		}

		Object ret = null;
		if (client.isConnected()) {
			ret = client.sendAndRecv(msg);
		}

		//如果对方关闭连接，要尝试重发
		if(ret == null && !client.isConnected()){
			log.error("server close reconnect:" + client.getPeer());
			if(client.connect()){
				ret = client.sendAndRecv(msg);
			}
			else{
				log.error("server close reconnect fail:" + client.getPeer());
			}
		}
		
//		if(debug) {
//			log.error("FarmGetUser to"+client.getPeer()+",seq="+seq+",t1="+(time_poll-now)
//						+",t2="+(time_connect-now)
//						+",t3="+(time_sendrecv-now)
//						+",t4="+(time_resendrecv-now));
//		}
		if (ret == null) {
			++failNums;
			log.error("client pool return null:" + host + ":" + port + ",failNums:" + failNums + "->" + this.FAIL_LIMIT_NUMS);
			if (failNums >= this.FAIL_LIMIT_NUMS) {
				suspendFlag = true;
				lastSuspendTime = System.currentTimeMillis();
				log.error("client pool begin suspend:" + host + ":" + port + ",failNums:" + failNums + ",for " + this.SUSPEND_SECONDS + "(s)");
			}
		} else {
			failNums = 0;
		}
		//还回连接池
		try {
			idleQ.add(client);
			log.info("unuse "+client.getPeer()+",connected:"+client.isConnected() + ", time="+(System.currentTimeMillis()-now));
		} catch (Exception e) {
			log.error("exception", e);
		}
		log.info(host+":"+port+" idleQ.size=" + idleQ.size());
		return ret;
	}

	public int getIdleNum() {
		return idleQ.size();
	}
}
