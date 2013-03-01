package com.ppsea.ds.util;

/**
 * 字符工具类
 * 
 * @author sky 
 */

public final class StringUtil {
	
	public static String unix_simple_clazz(String className) {
		String simpleClassName = className;
		int p = className.lastIndexOf('.');
		if (p != -1) {
			simpleClassName = className.substring(p+1);
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < simpleClassName.length(); ++i) {
			char c = simpleClassName.charAt(i);
			if (Character.isUpperCase(c)) {
				if (i != 0) sb.append("_");
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	/**
	 * 格式化成XML识别的字符
	 * 
	 * @param value 字符串
	 * @return string 过滤后的字符串
	 */
    public static String formatStringToXML(final String value) {
    	if(value==null) return "";
        char[] content = new char[value.length()];
        value.getChars(0, value.length(), content, 0);
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
	            
	        	case '&':
	                result.append("＆");
	                break;
	            case '<':
	                result.append("＜");
	                break;
	            case '>':
	                result.append("＞");
	                break;
	            case '"':
	                result.append("＄");
	                break;
	            case '\'':
	                result.append("＇");
	                break;
	            case '$':
	                result.append("");
	                break;	            
	            
            	/*
            	case '&':
	                result.append("&amp;");
	                break;
	            case '<':
	                result.append("&lt;");
	                break;
	            case '>':
	                result.append("&gt;");
	                break;
	            case '"':
	                result.append("&quot;");
	                break;
	            case '\'':
	                result.append("&#39;");
	                break;
	            */
	            default:
	                result.append(content[i]);
            }
        }
        return result.toString();
    }	
    
    public static String formatStringToXML(final String value, int length) {
    	String s = formatStringToXML(value);
    	if(s.length() > length) s = s.substring(0, length);
    	
    	return s;
    }
    
    /**
     * 把字符串数组转换为斜线分隔的字符串 
     **/
    public static String splitSlash(String[] strArray){
    	if(strArray == null || strArray.length == 0)return "";
    	if(strArray.length == 1)return strArray[0];
    	StringBuffer sb = new StringBuffer();
    	for(int i=0;i<strArray.length;i++){
    		sb.append(strArray[i]).append("/");
    	}
    	return sb.toString();
    }
    
}
