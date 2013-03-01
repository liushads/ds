package com.ppsea.ds.util.item;
import java.nio.charset.Charset;   
import java.util.ArrayList;   
import java.util.Collection;   
import java.util.HashMap;   
import java.util.List;   
import java.util.Map;   
import java.util.Map.Entry;   
import javax.tools.DiagnosticCollector;   
import javax.tools.JavaCompiler;   
import javax.tools.JavaFileObject;   
import javax.tools.StandardLocation;   
import javax.tools.ToolProvider;   
import javax.tools.JavaCompiler.CompilationTask;  
/**
 * Java动态编译器简单实现 
 **/
public class SimpleCompiler<T> {
	
	/** 
	 * 系统编译器 
	 **/  
    private final JavaCompiler compiler;   
    
    /**
     * 类加载器 
     **/
    private final ClassLoaderImpl classLoader;   
    
    /** 
     * 保存编译器编译中的诊断信息 
     **/   
    private DiagnosticCollector<JavaFileObject> diagnostics;   
    
    /**
     * Java字节码和源代码文件管理器 
     **/
    private final FileManagerImpl javaFileManager;   
    
    /** 
     * 编译参数 
     **/  
    private final List<String> options;   
   
    /**  
     * 构造一个新的实例,该实例持有指定的ClassLoader  
     * @param loader 应用的ClassLoader  
     * @param options 编译器的编译参数，具体可参考JavaC编译参数  
     * @throws IllegalStateException 如果java编译器不能正确载入则抛出异常  
     */  
    public SimpleCompiler(ClassLoader loader, Collection<String> options) {   
        compiler = ToolProvider.getSystemJavaCompiler();   
        if (compiler == null) {   
            throw new IllegalStateException("系统编译器无法找到,请确认类路径中已经包含tools.jar（注：JDK 6中默认自带，JRE 6中默认不带）。");   
        }   
        if (loader == null) {   
            classLoader = new ClassLoaderImpl(this.getClass().getClassLoader());   
        } else {   
            classLoader = new ClassLoaderImpl(loader);   
        }   
        this.options = new ArrayList<String>();   
        if (options != null) {   
            this.options.addAll(options);   
        }   
        diagnostics = new DiagnosticCollector<JavaFileObject>();   
        javaFileManager = new FileManagerImpl(compiler.getStandardFileManager(diagnostics, null, Charset.forName(Utils.ENCODING)), classLoader);   
    }   
    
    /**  
     * 编译多个Java类的源码  
     *   
     * @param classes key为类的完全限定名，value为对应的源码。  
     * @return 编译后的类  
     * @throws SimpleCompilerException  
     */  
    public synchronized Map<String, Class<T>> compile(Map<String, CharSequence> classes)throws PpseaCompilerException {   
        //准备待编译文件   
        List<JavaFileObject> sources = new ArrayList<JavaFileObject>();   
        for (Entry<String, CharSequence> entry : classes.entrySet()) {   
            String qualifiedClassName = entry.getKey();   
            CharSequence javaSource = entry.getValue();   
            if (javaSource != null) {   
                int dotPos = qualifiedClassName.lastIndexOf('.');   
                String className = dotPos == -1 ? qualifiedClassName : qualifiedClassName.substring(dotPos + 1);   
                String packageName = dotPos == -1 ? "" : qualifiedClassName.substring(0, dotPos);   
                JavaFileObjectImpl source = new JavaFileObjectImpl(className, javaSource);   
                sources.add(source);   
                javaFileManager.putFileForInput(StandardLocation.SOURCE_PATH, packageName, className + ".java", source);   
            }   
        }   
        //编译代码   
        CompilationTask task = compiler.getTask(null, javaFileManager, diagnostics, options, null, sources);   
        Boolean result = task.call();   
        //返回编译结果   
        if ((result == null) || !result.booleanValue()) {   
            throw new PpseaCompilerException("Compilation failed.", classes.keySet(), diagnostics);   
        }   
        try {   
            Map<String, Class<T>> compiled = new HashMap<String, Class<T>>();   
            for (String qualifiedClassName : classes.keySet()) {   
                compiled.put(qualifiedClassName, loadClass(qualifiedClassName));   
            }   
            return compiled;   
        } catch (ClassNotFoundException ex) {   
            throw new PpseaCompilerException(classes.keySet(), ex, diagnostics);   
        } catch (IllegalArgumentException ex) {   
            throw new PpseaCompilerException(classes.keySet(), ex, diagnostics);   
        } catch (SecurityException ex) {   
            throw new PpseaCompilerException(classes.keySet(), ex, diagnostics);   
        }   
    }   
    /**  
     * 编译一个Java类。  
     *   
     * @param qualifiedClassName 类的完全限定名。  
     * @param javaSource 编译的java类完整的源码。  
     * @param types  0或多个类,用以检验被编译的类能否转换成这些类中任何一个。  
     * @return 编译后的类  
     * @throws PpseaCompilerException 如果类无法被编译则抛出异常。  
     * @throws ClassCastException 如果编译后的类无法转换成types中的任何一种类型,则抛出异常。  
     */  
    public synchronized Class<T> compile(String qualifiedClassName, CharSequence javaSource,   
            Class<?>... types) throws PpseaCompilerException, ClassCastException {   
        diagnostics = new DiagnosticCollector<JavaFileObject>();   
        Map<String, CharSequence> classes = new HashMap<String, CharSequence>(1);   
        classes.put(qualifiedClassName, javaSource);   
        Map<String, Class<T>> compiled = compile(classes);   
        Class<T> newClass = compiled.get(qualifiedClassName);   
        for (Class<?> type : types) {   
            if (!type.isAssignableFrom(newClass)) {   
                throw new ClassCastException(type.getName());   
            }   
        }   
        return newClass;   
    }   
    
    /** 
     * 载入Java类。 
     **/  
    @SuppressWarnings("unchecked")   
    private Class<T> loadClass(final String qualifiedClassName) throws ClassNotFoundException {   
        return (Class<T>) classLoader.loadClass(qualifiedClassName);   
    }   



}
