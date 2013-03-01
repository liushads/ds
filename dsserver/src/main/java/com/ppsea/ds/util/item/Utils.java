package com.ppsea.ds.util.item;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
/**
 * 辅助工具类 
 **/
public abstract class Utils { 
	
	/**
	 * 默认编码方式
	 **/
    public static final String ENCODING = Charset.defaultCharset().name();   
    
    /** 
     * 把String转换成URI,如果转换异常不抛出URISyntaxException.
     * 而直接抛出RuntimeException。 
     **/  
    static URI toURI(String name) {   
        try {   
            return new URI(name);   
        } catch (URISyntaxException e) {   
            throw new RuntimeException(e);   
        }   
    }   
    
    /**
     * 将输入流转换为字节数组
     **/
    static byte[] toByteArray(InputStream in) throws IOException {
    	if(in == null || in.available() == 0) throw new IOException("No data can be read");
    	byte[] bytes = new byte[in.available()];
    	in.read(bytes, 0, bytes.length);
    	return bytes;
    }
} 