package com.ppsea.ds.util.item;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.tools.SimpleJavaFileObject;
/**  
 * FileObject和JavaFileObject的一个实现,它能持有java源代码或编译后的class。
 * 这个类可以用于：  
 * 
 * 存放需要传递给编译器的源码,这时使用的是JavaFileObjectImpl(String, CharSequence)}构造器。  
 * 存放编译器编译完的byte code,这是使用的是JavaFileObjectImpl(String, JavaFileObject.Kind)}  
 *  
 */  
final class JavaFileObjectImpl extends SimpleJavaFileObject { 
	
    /** 
     * 如果kind == CLASS, 存储byte code,可以通过openInputStream()得到 
     **/  
    private ByteArrayOutputStream byteCode; 
    
    /** 
     * 如果kind == SOURCE, 存储源码 
     * */  
    private final CharSequence source;  
    
    /**  
     * 创建持有源码的实例   
     */  
    JavaFileObjectImpl(final String baseName, final CharSequence source) {   
        super(Utils.toURI(baseName + ".java"), Kind.SOURCE);   
        this.source = source;   
    }   
    /**  
     * 创建持有二进制byte code的实例  
     */  
    JavaFileObjectImpl(final String name, final Kind kind) {   
        super(Utils.toURI(name), kind);   
        source = null;   
    }  
      
    /**
     * 返回源代码 
     **/
    public CharSequence getCharContent(final boolean ignoreEncodingErrors) throws UnsupportedOperationException {   
        if (source == null) {   
            throw new UnsupportedOperationException("getCharContent()");   
        }   
        return source;   
    }   
    
    /**
     * 返回字节码 
     **/
    public InputStream openInputStream() {   
        return new ByteArrayInputStream(byteCode.toByteArray());   
    }   
    
    /**
     * 返回字节码输出流对象,提供写入字节码
     **/
    public OutputStream openOutputStream() {   
        return (byteCode = new ByteArrayOutputStream());   
    }   
    
    /**
     * 返回字节码输出流对象,提供写入字节码
     **/
    public Writer openWriter() throws IOException {   
        return new OutputStreamWriter(openOutputStream(), Utils.ENCODING);   
    }   
}  