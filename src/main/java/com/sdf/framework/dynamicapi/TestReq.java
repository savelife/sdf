package com.sdf.framework.dynamicapi;

import com.alibaba.fastjson.JSON;
import com.sdf.common.IReq;
import javassist.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author barry
 * @date 2021/9/3 5:54 下午
 * @description
 */
public class TestReq {




    public static void main(String[] args) throws Exception{


        IReq req = new IReq() {

        };
        Map<String,Class> propertiesMap = new HashMap<String,Class>();
        propertiesMap.put("age", int.class);

        Object obj = ReflectUtil.getObject(req, propertiesMap);

        System.out.println("动态为User添加age之后，req："+ JSON.toJSONString(obj));
        System.out.println("动态为User添加age之后，req："+ JSON.toJSONString(obj.getClass().getDeclaredMethods()));

        ClassPool pool = ClassPool.getDefault();
        CtClass cc  = pool.get(req.getClass().getName());
        CtMethod mthd = CtNewMethod
            .make("public String test() { return \"test() is called \"+ toString();  }", cc);
        cc.addMethod(mthd);
        //测试反射调用添加的方法
        System.out.println(req.getClass().getDeclaredMethod("test").invoke(req));
    }
}
