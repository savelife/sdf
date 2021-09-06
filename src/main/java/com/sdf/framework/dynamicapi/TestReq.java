package com.sdf.framework.dynamicapi;

import com.alibaba.fastjson.JSON;
import com.sdf.common.IReq;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author barry
 * @date 2021/9/3 5:54 下午
 * @description
 */
public class TestReq {

    public static void main(String[] args) throws Exception {

        IReq req = new IReq() {

        };
        Map<String, Class> propertiesMap = new HashMap<String, Class>();
        propertiesMap.put("age", int.class);

        Object obj = ReflectUtil.getObject(req, propertiesMap);

        System.out.println("动态为User添加age之后，req：" + JSON.toJSONString(obj));
        System.out.println(
            "动态为User添加age之后，req：" + JSON.toJSONString(obj.getClass().getDeclaredMethods()));

        // todo 添加方法类需要是 public
        // http://blog.itpub.net/69940844/viewspace-2692551/
        /**
         * javassist被用于struts2和hibernate中，都用来做动态字节码修改使用。
         * 一般开发中不会用到，但在封装框架时比 较有用。
         * 虽然javassist提供了一套简单易用的API，但如果用于平常的开发，会有如下几点不好的地方:
         * 1. 所引用的类型，必须通过ClassPool获取后才可以使用
         * 2. 代码块中所用到的引用类型，使用时必须写全量类名
         * 3. 即使代码块内容写错了，它也不会像eclipse等开发工具一样有提示，它只有在运行时才报错
         * 4. 动态修改的类，必须在修改之前，jvm中不存在这个类的实例对象。修改方法的实现必须在修改的类加载之前进行
         */

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(req.getClass().getName());
        CtMethod mthd = CtNewMethod
            .make("public String test() { return \"test() is called \"+ toString();  }", cc);
        cc.addMethod(mthd);

        AppClassLoader appClassLoader = AppClassLoader.getInstance();
        Class<?> clazz = appClassLoader.findClassByBytes(req.getClass().getName(), cc.toBytecode());
        //            clazz.getDeclaredConstructor().newInstance();
        Object obj2 = appClassLoader.getObj(clazz, req);
        System.out.println("after:" + obj2);
        //测试反射调用添加的方法
        System.out.println(obj2.getClass().getDeclaredMethod("test").invoke(obj2));

    }
}
