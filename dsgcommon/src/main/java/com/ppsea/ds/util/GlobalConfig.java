
package com.ppsea.ds.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ClassName:配置文件处理类
 *
 * @author   Daniel.Hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2008	Nov 29, 2008		8:08:56 PM
 *
 * @see 	 
 */
public class GlobalConfig {
	private static final Logger log = Logger.getLogger(GlobalConfig.class);
	public static final String GLOBAL_CONFIG_FILE = "global_config.xml";	//全局配置文件
	public static String DB_CONFIG_FILE = "db.properties";			//程序产生的分区db属性文件
	
	public static Element root = null;
	public static Element global = null;
	public static Element zone = null;
	
	private static Document document = null;
	
	public static String DB_URL;
	/**
	 *找到对应分区节点
	 **/
	@SuppressWarnings("unchecked")
	private static Element getZoneNode(String zoneId){
        List<Element> nodes = root.elements("zone");
        for (Iterator<Element> it = nodes.iterator(); it.hasNext();) {
           Element elm = it.next();
           if(elm != null && elm.attributeValue("id").equals(zoneId)){
        	   return elm;
           }
        }
        return null;
	}
	
	public static void init(String file) throws Exception{
		SAXReader reader = new SAXReader();
        document = reader.read(GlobalConfig.class.getClassLoader().getResourceAsStream(file));
        root = document.getRootElement();
        if(root == null ){
        	throw new Exception("no root node in config");
        }
        global = root.element("global");
        if(global == null ){
        	throw new Exception("no global node in config");
        }
	}
	
	public static void initForGserver(String file, String zoneId) throws Exception{
		init(file);        
        //找到对应分区节点
        zone = getZoneNode(zoneId);
        
        if(zone == null){
        	throw new Exception("no zone node in config: zoneId="+zoneId);
        }
	} 
	
	public static void initForProxy(String file) throws Exception{
		init(file);
        Element targetZone = root.element("targetZone");
        String zoneId = targetZone.attributeValue("id");
        zone = getZoneNode(zoneId);
        if(zone == null){
        	throw new Exception("no zone node in config: zoneId="+zoneId);
        }
	}

	/**
	 * 非法用户名正则列表
	 * @return
	 * @throws Exception
	 */
	public static String[] getInvalidNames() {
		String regex = global.element("invalidNames").attributeValue("regex");
		return regex.split("[|]");
	}
	
	/**
	 * 产生db属性文件
	 * */
	public static void generateDBConfig(String zoneId) throws Exception{
        String driver = global.element("driver").attributeValue("name"); 
        
        String zoneUserName = zone.element("db").attributeValue("username"); 
        String zonePassword = zone.element("db").attributeValue("password"); 
 
        String dbHost = zone.element("db").attributeValue("host");
        String dbPort = zone.element("db").attributeValue("port");
        String dbName = zone.element("db").attributeValue("dbname") + "_" + zoneId;
        
        String resUserName = zone.element("res").attributeValue("username"); 
        String resPassword = zone.element("res").attributeValue("password"); 
        String resHost = zone.element("res").attributeValue("host");
        String resPort = zone.element("res").attributeValue("port");
        String resName = zone.element("res").attributeValue("dbname");

        DB_URL  = "jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName; 
        
        StringBuffer sb = new StringBuffer(); 
        sb.append("driver=").append(driver).append("\n");
        sb.append("zone_username=").append(zoneUserName).append("\n")
    	.append("zone_password=").append(zonePassword).append("\n")
    	.append("zone_url=").append(DB_URL).append("\n")
    	.append("res_username=").append(resUserName).append("\n")
    	.append("res_password=").append(resPassword).append("\n")
    	.append("res_url=").append("jdbc:mysql://"+resHost+":"+resPort+"/"+resName).append("\n\n");
    
        File f = new File(DB_CONFIG_FILE);
        if(!f.exists()){
        	f.createNewFile();
        }
        OutputStream os = new FileOutputStream(f);	
        PrintStream ps = new PrintStream(os);  
        ps.print(sb.toString());
	}
	
//	/**
//	 * 产生db属性文件
//	 * */
//	public static void generateDBConfig() throws Exception{
//        String driver = global.element("driver").attributeValue("name"); 
//        
//        String globalUserName = global.element("db").attributeValue("username"); 
//        String globalPassword = global.element("db").attributeValue("password"); 
//        String globalUrl = global.element("db").attributeValue("url"); 
//
//        StringBuffer sb = new StringBuffer(); 
//        sb.append("driver=").append(driver).append("\n")
//        	.append("global_username=").append(globalUserName).append("\n")
//        	.append("global_password=").append(globalPassword).append("\n")
//        	.append("global_url=").append(globalUrl).append("\n\n");
//        
//        File f = new File(DB_CONFIG_FILE);
//        if(!f.exists()){
//        	f.createNewFile();
//        }
//        OutputStream os = new FileOutputStream(f);	
//        PrintStream ps = new PrintStream(os);  
//        ps.print(sb.toString());
//	}
}
