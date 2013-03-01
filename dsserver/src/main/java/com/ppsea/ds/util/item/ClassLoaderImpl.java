package com.ppsea.ds.util.item;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.tools.JavaFileObject;
/** 
 * ClassLoader的一个实现,它map类名和JavaFileObjectImpl的实例。
 * 本类在SimpleCompiler和FileManagerImpl中被使用。 
 **/  
final class ClassLoaderImpl extends ClassLoader { 
	
	/**
	 * 存储要加载的类文件 
	 **/
    private final Map<String, JavaFileObjectImpl> classes = new HashMap<String, JavaFileObjectImpl>();   
    
    /**
     * 默认构造方法 
     **/
    ClassLoaderImpl(final ClassLoader parentClassLoader) {   
        super(parentClassLoader);   
    }   
    
    /**
     * 加载指定名称的类 
     **/  
    public InputStream getResourceAsStream(final String name) {   
        if (name.endsWith(".class")) {   
            String qualifiedClassName = name.substring(0, name.length() - ".class".length()).replace('/', '.');   
            JavaFileObjectImpl file = classes.get(qualifiedClassName);   
            if (file != null) {   
                try {   
                    return new ByteArrayInputStream(Utils.toByteArray(file.openInputStream()));   
                } catch (IOException ex) {   
                }   
            }   
        }   
        return super.getResourceAsStream(name);   
    }   
    
    /**
     * 添加要进行加载的类 
     **/
    protected void add(final String qualifiedClassName, final JavaFileObjectImpl javaFile) {   
        classes.put(qualifiedClassName, javaFile);   
    }   
    
    /** 
     * @return 返回不可变的Collection,含有所有持有的JavaFileObject对象 
     **/  
    protected Collection<JavaFileObjectImpl> files() {   
        return Collections.unmodifiableCollection(classes.values());   
    }   
    
    /**
     * 查找类
     **/
    protected Class<?> findClass(final String qualifiedClassName) throws ClassNotFoundException {   
        JavaFileObject file = classes.get(qualifiedClassName);   
        if (file != null) {   
            try {   
                byte[] bytes = Utils.toByteArray(file.openInputStream());   
                return defineClass(qualifiedClassName, bytes, 0, bytes.length);   
            } catch (IOException ex) {   
            }   
        }   
        try {   
            Class<?> c = Class.forName(qualifiedClassName);   
            return c;   
        } catch (ClassNotFoundException nf) {   
        }   
        return super.findClass(qualifiedClassName);   
    }   
    
    /**
     * 加载类 
     **/
    protected synchronized Class<?> loadClass(final String name, final boolean resolve)throws ClassNotFoundException {   
        return super.loadClass(name, resolve);   
    }   
} 