package com.sdf.framework.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author barry
 * @date 2021/8/27 3:22 下午
 * @description
 */
public abstract class BaseController{

    private BaseService baseService;

    private Class req;

    private String name;

    public BaseController(BaseService baseService, Class c) {
        this.baseService = baseService;
        this.req = c;
    }

    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    public BaseController() {
    }

    @ResponseBody
    public abstract Object addUser(@RequestBody Object o);

    public abstract String getName();

}
