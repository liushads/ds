package com.ppsea.ds;

import java.io.File;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.filter.LoggingFilter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import com.ppsea.ds.command.CommandSet;
import com.ppsea.ds.command.admin.AdminCmdChain;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.EventMG;
import com.ppsea.ds.manager.GoodsMG;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.manager.MissionMG;
import com.ppsea.ds.manager.MonsterMG;
import com.ppsea.ds.manager.MoveMG;
import com.ppsea.ds.manager.NpcMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.manager.ResellPriceMG;
import com.ppsea.ds.manager.SectMG;
import com.ppsea.ds.service.DBEngine;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.task.CleanTask;
import com.ppsea.ds.util.GlobalConfig;
import com.ppsea.ds.util.SqlMapConfig;
import com.ppsea.ds.util.SqlMapConfigData;
import com.ppsea.ds.util.WordFilter;


public class GameServer {    
    private static int SERVER_PORT;
    private static int ADMIN_PORT;
    private static String SERVER_IP;
    public static String SERVER_MD5KEY;
    public static String SERVER_GLOBL_MD5KEY;
    
    private static final Logger log = Logger.getLogger(GameServer.class);
    public static String zoneId = "NULL";

    public static boolean terminated = false;
    
    public GameServer(String id){
    	zoneId = id;
    }
    
    public static void doTerminate() {
    	log.warn("****************DBService start doTerminate ...");
    	DBService.doTerminate();
        log.warn("****************DBService end doTerminate.");
    }
    
    public static void startup() throws Throwable{
    	initResource();
		initMina();
		initAdmin();
		initTask();
		log.warn( "The server Listening on server port " + SERVER_PORT ); 
		log.warn( "The server Listening on admin port " + ADMIN_PORT ); 
    }
    
    public static void initResource() throws Exception {
    	//加载配置文件
    	log.warn("start gserver, zoneid="+zoneId);
    	GlobalConfig.initForGserver(GlobalConfig.GLOBAL_CONFIG_FILE, zoneId);
    	SERVER_IP = GlobalConfig.zone.attributeValue("host");
    	SERVER_PORT = Integer.parseInt(GlobalConfig.zone.attributeValue("port"));
    	ADMIN_PORT = Integer.parseInt(GlobalConfig.zone.attributeValue("adminPort"));
    	SERVER_MD5KEY = GlobalConfig.zone.element("proxy").attributeValue("md5Key");
    	SERVER_GLOBL_MD5KEY = GlobalConfig.global.attributeValue("md5Key");
    	
    	log.warn("gserver config,ip=" + SERVER_IP + ",port=" + SERVER_PORT
    			+ ",adminport:" + ADMIN_PORT + ",md5key:" + SERVER_MD5KEY + "global_md5kye:"+SERVER_GLOBL_MD5KEY);
    	
    	//产生db属性文件
    	log.warn("generate db properties!");
    	GlobalConfig.generateDBConfig(zoneId);
    	File f = new File(GlobalConfig.DB_CONFIG_FILE);
    	if( !f.exists()){
    		throw new PpseaException("don't find "+GlobalConfig.DB_CONFIG_FILE);
    	}
    	
    	//初始化db
    	log.warn("init ibatis....");
    	SqlMapConfig.init();
    	SqlMapConfigData.init();
    	
    	//初始化DBEngine
    	log.warn("init DBEngine");
    	DBEngine.initialize();
    	
    	//启动DB缓写线程
    	log.warn("start DBService Thread...");
    	DBService.initialize();    	
    	
    	//初始化command
		log.warn("init CommandSet......");
		CommandSet commandSet = CommandSet.getInstance();
		commandSet.loadCommand();
		log.warn("CommandSet load finish...... \n" + commandSet.printCommandSet());CommandSet.getInstance().loadCommand();
    	
		log.warn("init word filter......");
		WordFilter.init();
		log.warn("init word filter......end");

		log.warn("init GlobalGenerator......");
		GlobalGenerator.init();
		log.warn("init GlobalGenerator......end");
		
		log.warn("init Constants.....");
		Constants.init();
		log.warn("init Constants...... end");
		
		log.warn("start load items....");
		ItemMG.instance.init();
		log.warn("load items finish...");
		
		log.warn("init EventMG.....");
		EventMG.instance.init();
		log.warn("init EventMG...... end");

		log.warn("init MapMG.....");
		MapMG.instance.init();
		log.warn("init MapMG...... end");
		
		log.warn("init SectMG.....");
		SectMG.instance.init();
		log.warn("init SectMG...... end");
		
		log.warn("init MissionMG.....");
		MissionMG.instance.init();
		log.warn("init MissionMG...... end");

		log.warn("init NpcMG.....");
		NpcMG.instance.init();
		log.warn("init NpcMG...... end");

		log.warn("init MonsterMG...");
		MonsterMG.instance.init();
		log.warn("init MonsterMG...... end");
		
		log.warn("init GoodsMG.....");
		GoodsMG.instance.init();
		log.warn("init GoodsMG...... end");    
		
		log.warn("init PlayerMG.....");
		PlayerMG.instance.init();
		log.warn("init PlayerMG...... end");
		
		
		log.warn("start load chuan song configuration...");
		MoveMG.instance.init();
		log.warn("start load chuan song configuration...end");
		
		log.info("start load spec buff ...");
		ResourceCache.instance.loadSpecBuff();
		log.info("start load spec buff ...end");
		
		log.info("start load ItemType ...");
		ResourceCache.instance.loadItemType();
		log.info("start load ItemType ...end");
		
		log.info("start load BuffRand ...");
		ResourceCache.instance.loadBuffRand();
		log.info("start load BuffRand ...end");
		
		log.info("start load ComposeItem ...");
		ResourceCache.instance.loadComposeItem();
		log.info("start load ComposeItem ...end");
		
		log.info("start load ItemSuit ...");
		ResourceCache.instance.loadItemSuit();
		log.info("start load ItemSuit ...end");
		
		log.info("start load ItemDecompose ...");
		ResourceCache.instance.loadItemDecompose();
		log.info("start load ItemDecompose ...end");
		
		log.info("start load ResellPriceMG ...");
		ResellPriceMG.instance.init();
		log.info("start load ResellPriceMG ...end");
		log.info("start load ItemImprove ...");
		ResourceCache.instance.loadItemImprove();
		log.info("start load ItemImprove ...end");
		
		log.info("start load Darts ...");
		ResourceCache.instance.loadDarts();
		log.info("start load Darts ...end");
		
		log.info("start load skills ...");
		ResourceCache.instance.loadSkills();
		log.info("start load skills ...end");
		
		log.info("start load FarmPre ...");
		ResourceCache.instance.loadFarmPre();
		log.info("start load FarmPre ...end");
    }
    
    private static void initMina() throws Throwable{
    	log.warn("start initMina...");
    	IoAcceptor acceptor = new SocketAcceptor();    
        SocketAcceptorConfig cfg = new SocketAcceptorConfig();    
        cfg.setReuseAddress( true );  
        /*
        cfg.setThreadModel(ThreadModel.MANUAL);
        Executor threadPool = Executors.newCachedThreadPool(); 
		cfg.getFilterChain().addLast("threadPool", new ExecutorFilter(threadPool));
		*/
        cfg.getFilterChain().addLast("codec", new ProtocolCodecFilter( new ObjectSerializationCodecFactory() ) );    
        //cfg.getFilterChain().addLast( "logger", new LoggingFilter() );    
		acceptor.bind(new InetSocketAddress(SERVER_IP,SERVER_PORT),new ServerHandler(), cfg );  	
			log.warn("start initMina...end");
    }
    
    private static void initAdmin() throws Throwable{
    	IoAcceptor acceptor = new SocketAcceptor();    
        SocketAcceptorConfig cfg = new SocketAcceptorConfig();    
        cfg.setReuseAddress( true );    
        cfg.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new ObjectSerializationCodecFactory() ));
        cfg.getFilterChain().addLast( "logger", new LoggingFilter() );    
		acceptor.bind(new InetSocketAddress(SERVER_IP,ADMIN_PORT),new AdminHandler(), cfg ); 
		
		//注册管理命令
		AdminCmdChain.registerAll();
    }
   
    // 启动定时器
    private static void initTask() throws Throwable {
    	//回收资源
    	//启动离线用户扫描线程
    	log.warn("start player scan:  sys.scan.interval="+ Constants.SCAN_INTERVAL
    			+ ", idle.timeout="+ Constants.IDLE_TIME);
    	
    	CleanTask cleanTask = new CleanTask();
    	cleanTask.start();
    }
    
    /**
     * 
     * @param args
     * @throws Throwable
     */
    public static void main( String[] args ) throws Throwable{
    	if(args.length < 1){
    		System.out.println("config file and zoneId is need!");
    		return;
    	}
    	String zoneId = args[0];
    	new GameServer(zoneId);
    	long u1 = System.currentTimeMillis();
    	//启动server
    	startup();

    	long u2 = System.currentTimeMillis();
    	log.warn("start game use:"+(u2-u1)+"ms");
    }

	public static boolean isTerminated() {
		return terminated;
	}
}   

 

