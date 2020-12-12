package com.luo.mb;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class MyMonitorTransformer implements ClassFileTransformer {

    private static final Set<String> classNameSet = new HashSet<>();

    static {
        classNameSet.add("org.apache.ibatis.session.Configuration");
//        classNameSet.add("com.luo.mb.MyCachingExecutor");
    }

    //这个方法被遍历，每获取一个jvm要加载的类就会执行一次
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        try {
            if(className == null){
                return null;
            }
            String currentClassName = className.replaceAll("/", ".");//遍历jvm加载的类
            if (!classNameSet.contains(currentClassName)) { // 找到我们要找的类
                return null;
            }
            System.out.println("transform: [" + currentClassName + "]");

            ClassPool pool = new ClassPool(true);
            pool.appendClassPath(new LoaderClassPath(loader));
            CtClass ctClass = pool.getDefault().get(currentClassName);
            CtBehavior[] methods = ctClass.getDeclaredBehaviors();
            for (CtBehavior method : methods) {
                enhanceMethod(method);
            }
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;

    }

    //修改方法字节码
    private void enhanceMethod(CtBehavior method) throws Exception {
        if (method.isEmpty()) {
            return;
        }
        String methodName = method.getName();
        if (!"newExecutor".equalsIgnoreCase(methodName)) {// 不是newExecutor的方法直接返回不修改。
            return;
        }

        final StringBuilder source = new StringBuilder();
        // 前置增强: 打入时间戳
        // 保留原有的代码处理逻辑
        source.append("{")
//                .append("long start = System.nanoTime();\n") //前置增强: 打入时间戳
                .append("$_ = $proceed($$);\n")              //调用原有代码，类似于method();($$)表示所有的参数
                .append("System.out.println(\"method:[")
                .append(methodName).append("]\");").append("\n")//执行原有方法
                .append("org.apache.ibatis.executor.Executor executor = new com.luo.mb.MyCachingExecutor($_);")
                .append("return executor;")
//                .append("System.out.println(\" cost:[\" +(System.nanoTime() - start)+ \"ns]\");") // 后置增强，计算输出方法执行耗时
                .append("}");

        ExprEditor editor = new ExprEditor() {
            @Override
            public void edit(MethodCall methodCall) throws CannotCompileException {
                methodCall.replace(source.toString());
            }
        };
        method.instrument(editor);
    }

}
