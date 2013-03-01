
package com.ppsea.ds.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Compilable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.exception.PpseaException;

/**
 * ClassName:Util 通用工具类
 *
 * @author   Daniel.Hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2008	Nov 29, 2008		8:08:56 PM
 *
 * @see 	 
 */
public class Util {
	private static final Logger log = Logger.getLogger(Util.class);
	private static final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("javascript");
//	public static String PROJECT_CONFIG_FILE = "config.properties";
	public static String DIRTY_WORD_FILE = "dirty_word.properties";	
	
	public static Properties getProjectProperties(String propFile){
		return loadProperties(propFile);
	}
	
    /**
     * Load properties
     * 
     * @param  @param fileName
     * @param  @return
     * @return Properties    
     * @throws
     */
    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();

        InputStream inputStream = null;
        try {
            inputStream = Util.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(inputStream);
        }catch(Exception e){
        	log.error("exception", e);
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                	log.error("exception", e);
                }
        }
        
        return properties;
    }
    
    public static ScriptEngine getScriptEngine(){
    	return scriptEngine;
    }
    
    public static Compilable getCompilabe(){
    	if (scriptEngine instanceof Compilable) return (Compilable)scriptEngine;
    	else return null;    	
    }
    
	public static Map<String, String> decodeMessage(Object message) throws PpseaException{
		if (!(message instanceof String)) {
			throw new PpseaException("message not String!");
		}
		
		String s = (String)message;
		if(s == null || s.length() == 0){
			throw new PpseaException("message == null || message.length() == 0 ");
		}

		Map<String, String> map = new HashMap<String, String>();
		String[] temp = null;
		if(s.indexOf("&amp;") > 0) temp = s.split("&amp;");
		else temp = s.split("&");

		for (String param : temp) {
			String[] params = param.split("=");

			if (params.length < 2)
				continue;

			map.put(params[0], params[1]);
		}
		return map;
	}    

    public static String defaultString(String str, String defaults)
    {
        return (str == null ? defaults : str);
    }   

    public static int defaultInt(String str, int defaults)
    {
        if(str == null) return defaults;
        try {
            return Integer.parseInt(str);
        }
        catch(Exception e) {
            return defaults;
        }
    }
    
    /**
     * 通用翻页函数
     * */
	public static List<Object> page(List<?> objs, int pageId, CommandResult result){
		if(objs == null){
			objs = new LinkedList<Object>();
		}
		int start = pageId*Constants.PAGE_SIZE;
		int end = start + Constants.PAGE_SIZE;
		if( start<0) start = 0;
		if( start> objs.size()) start = objs.size();
		if( end < start) end = start;
		if( end> objs.size()) end = objs.size();
		
		List<Object> list = new LinkedList<Object>();
		list.addAll(objs.subList(start, end));
		
		result.setVO(Command.TAG_TOTAL_PAGE, totalPage(objs.size()));
		result.setVO(Command.TAG_PAGE, pageId);
		result.setVO(Command.TAG_PAGE_OBJS,list);
		return list;
	}
	
	/**
	 * 数据库分页
	 * @param objs
	 * @param pageId
	 * @param result
	 * @param count
	 * @return
	 */
	public static List<Object> page(List<?> objs, int pageId, CommandResult result,int count){
		if(objs == null){
			objs = new LinkedList<Object>();
		}
		
		List<Object> list = new LinkedList<Object>(objs);
		result.setVO(Command.TAG_TOTAL_PAGE, totalPage(count));
		result.setVO(Command.TAG_PAGE, pageId);
		result.setVO(Command.TAG_PAGE_OBJS,list);
		return list;
	}
	
	public static int totalPage(int total){
		int n = total/Constants.PAGE_SIZE;
		if(total%Constants.PAGE_SIZE >0 ){
			n += 1;
		}
		return n;
		//return (total+Constants.PAGE_SIZE-1)/Constants.PAGE_SIZE;
	}
	
	/**
	 * 检测字符是否满足正则表达式
	 * 
	 * @param regex 正则
	 * @param source 源字符
	 * @return
	 */
	public static boolean test(String regex,String source){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		boolean b= matcher.matches();
		return b;
	}	
	
	/**
	 * 根据key，value获取相应key
	 * key：需要获得的关键字，
	 * value：关键字所占的百分比
	 * @param keyChanceMap
	 * @return
	 */
	public static String getPreKey(Map<String, Integer> keyChanceMap) {
		if (keyChanceMap == null || keyChanceMap.size() == 0){
			return null;
		}

		Integer sum = 0;
		for (Integer value : keyChanceMap.values()) {
			sum += value;
		}
		// 从1开始
		Integer rand = new Random().nextInt(sum) + 1;

		for (Map.Entry<String, Integer> entry : keyChanceMap.entrySet()) {
			rand -= entry.getValue();
			// 选中
			if (rand <= 0) {
				return entry.getKey();
			}
		}

		return null;
	}
}
