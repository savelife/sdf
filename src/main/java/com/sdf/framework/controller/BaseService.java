package com.sdf.framework.controller;

import com.sdf.common.IReq;
import com.sdf.common.Resp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @author barry
 * @date 2021/8/27 3:25 下午
 * @description
 */
public class BaseService<T> {
    Function<IReq, Resp<T>> function;

    Method method;
    Object obj;

    public BaseService(Function<IReq, Resp<T>> function) {
        this.function = function;
    }

    public BaseService(Object obj,Method method) {
        this.obj = obj;
        this.method = method;
    }

    public void setFunction(Function<IReq, Resp<T>> function) {
        this.function = function;
    }

    public Resp<T> handle(Object req) {
        Object result = null;
        try {
            if(method.getParameterTypes().length > 0) {
                result = method.invoke(obj, req);
            } else {
                result = method.invoke(obj);
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
           e.printStackTrace();
        }
        return (Resp<T>)result;
    }
}
