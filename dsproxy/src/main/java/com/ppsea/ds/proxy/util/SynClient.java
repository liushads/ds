package com.ppsea.ds.proxy.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.apache.mina.common.ByteBuffer;

public class SynClient {
	public static int CODEC_SERIALIZATION = 1;
	public static int CODEC_TEXT = 2;
	
	Socket client = null;
	private static int maxObjectSize = 1024*1024; // 1M
	private static final Logger log = Logger.getLogger(SynClient.class);
	private String host;
	private int port;
	private byte[] recvbuf = new byte[maxObjectSize];
//	private byte[] sendbuf = new byte[maxObjectSize+4];
	private int timeout;
	
	public SynClient(String host, int port, int timeout){
		this.host = host;
		this.port = port;
		this.timeout = timeout;
	}
	
	public boolean connect(){
		try{
			client = new Socket();
			client.setSoTimeout(timeout);
			client.setSendBufferSize(maxObjectSize);
			client.setReceiveBufferSize(maxObjectSize);
			client.connect(new InetSocketAddress(host, port), 1000);
			if(!client.isConnected()){
				log.info("connect timeout:" + getPeer() );
				return false;
			}
		}catch(Exception e){
			log.error("exception", e);
			close();
			return false;
		}
		return true;
	}
	
//	private static int bytesToInt(byte[] bytes) { 
//		int num = bytes[0] & 0xFF; 
//		num |= ((bytes[1] << 8) & 0xFF00); 
//		num |= ((bytes[2] << 16) & 0xFF0000); 
//		num |= ((bytes[3] << 24) & 0xFF000000); 
//		return num; 
//	}
	
	public Object sendAndRecv(Object message){
		int objectSize = 0;
		try{
			encode(message, client.getOutputStream());	
			InputStream in = client.getInputStream();			
			int len = in.read(recvbuf);
			if(len < 4 ){
				log.error("package too small: "+ len);
				close();
				return null;
			}
			
			len = len - 4 ;
			//object size
			int len2 = ByteBuffer.wrap(recvbuf, 0, 4).getInt();
			objectSize = len2;
			if( len2<=4 || len2 >= recvbuf.length ){
				log.error("obj too large: "+ len2);
				close();
				return null;
			}
			
			while(len < len2){
				int n = in.read(recvbuf, len+4, len2-len);
				if(n == -1) {
					log.error("n=-1");
					break;
				}
				len += n;
				//log.info("package: "+ len +","+len2);
			}
			//log.error("package len:"+len+"-"+len2+"-"+recvbuf.length);
			//byte[] real_buf = new byte[len];
			// for debug
			//System.arraycopy(recvbuf, 0, real_buf, 0, len);
			//记录超大包(1K)和对应的指令
//			if(objectSize > 1000 && message instanceof CommandRequest){
//				CommandRequest req = (CommandRequest) message;
//				log.error("large|"+req.getCmd()+"|"+req.getPid()+"|"+objectSize);
//			}
			ByteBuffer bf = ByteBuffer.wrap(recvbuf,0,len2+4);
			return bf.getObject();
		}catch(Exception e){
			log.error("exception: objsize =" + objectSize, e);
			close();
			return null;
		}
		
	}
	
	private void encode(Object message, OutputStream out ) throws Exception {
        if (!(message instanceof Serializable)) {
            throw new NotSerializableException();
        }

        ByteBuffer buf = ByteBuffer.allocate(64);
        buf.setAutoExpand(true);
        buf.putObject(message);

        int objectSize = buf.position() - 4;
        if (objectSize > maxObjectSize-4) {
            buf.release();
            throw new IllegalArgumentException(
                    "The encoded object is too big: " + objectSize + " (> "
                            + maxObjectSize + ')');
        }
        buf.flip();
        int len = buf.remaining();
        buf.get(recvbuf,0, len);

        out.write(recvbuf, 0, len); 
        out.flush();
	}
	
	public void close(){
		if(client != null){
			try {
				client.getInputStream().close();
			} catch (IOException e) {
			}
			try {
				client.getOutputStream().close();
			} catch (IOException e) {
			}
			try {
				client.close();
			} catch (IOException e) {
			}
		}		
		client = null;
	}
	
	public String getPeer() {
		return "[" + host + ":" + port + "]";
	}
	
	public boolean isConnected(){
		return (client != null && client.isConnected());
	}

}
