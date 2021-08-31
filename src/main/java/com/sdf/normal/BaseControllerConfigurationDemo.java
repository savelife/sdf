package com.sdf.normal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdf.common.SpringBeanFactory;
import com.sdf.framework.controller.BaseController;
import com.sdf.framework.controller.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author barry
 * @date 2021/8/27 2:53 下午
 * @description
 */
@Component
public class BaseControllerConfigurationDemo extends AbstractBaseController implements ApplicationRunner {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired

    private AutowireCapableBeanFactory beanFactory;

    @Autowired

    DefaultListableBeanFactory defaultListableBeanFactory;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RequestMapping requestMapping = new RequestMapping(){
            @Override
            public Class<? extends Annotation> annotationType() {
                return RequestMapping.class;
            }

            @Override
            public String name() {
                return null;
            }

            @Override
            public String[] value() {
                return new String[]{"add"};
            }

            @Override
            public String[] path() {
                return new String[]{"/user/addd1","/user/addd2"};
            }

            @Override
            public RequestMethod[] method() {
                return new RequestMethod[]{RequestMethod.valueOf("POST")};
            }

            @Override
            public String[] params() {
                return new String[0];
            }

            @Override
            public String[] headers() {
                return new String[0];
            }

            @Override
            public String[] consumes() {
                return new String[0];
            }

            @Override
            public String[] produces() {
                return new String[0];
            }

        };

        Field field= RequestMappingHandlerMapping.class.getDeclaredField("config");
        field.setAccessible(true);
        //解析注解
        RequestMappingInfo.BuilderConfiguration configuration=(RequestMappingInfo.BuilderConfiguration)field.get(requestMappingHandlerMapping);


        RequestMethod requestMethod = RequestMethod.valueOf("POST");
        //RequestMappingHandlerMapping
        RequestMappingInfo.Builder builder;
        builder = RequestMappingInfo.paths(requestMapping.path())
            .methods(requestMapping.method())
            .params(requestMapping.params())
            .headers(requestMapping.headers())
            .consumes(requestMapping.consumes())
            .produces(requestMapping.produces())
            .mappingName(requestMapping.name());

        builder.options(configuration);

        Object serviceBean = SpringBeanFactory.getBean(Class.forName("com.sdf.normal.IUserService"));
        //Object reqBean = com.demo.SpringBeanFactory.getBean(Class.forName("com.demo.AddUserReq"));
        Class<?> aClass = Class.forName("com.sdf.normal.AddUserReq");
        Method method = serviceBean.getClass().getDeclaredMethod("add", aClass);



        //Object respBean = com.demo.SpringBeanFactory.getBean(Class.forName("responseBean"));
        BaseService baseService = new BaseService(serviceBean,method);
        BaseController baseController = new BaseController(baseService,aClass){

            @Override
            public Object addUser(Object o) {
                return  baseService.handle(JSONObject.parseObject(JSON.toJSONString(o),aClass));
            }

            @Override
            public String getName() {
                return aClass.getName() + method.getName();
            }
        };
        Method method2 = baseController.getClass().getDeclaredMethod("addUser",Object.class);

        defaultListableBeanFactory.registerSingleton("b1",baseController);
        defaultListableBeanFactory.registerSingleton("b2",baseController);
        beanFactory.autowireBean(baseController);
        requestMappingHandlerMapping.registerMapping(builder.build(),baseController,method2);




        RequestMapping requestMapping2 = new RequestMapping(){
            @Override
            public Class<? extends Annotation> annotationType() {
                return RequestMapping.class;
            }

            @Override
            public String name() {
                return null;
            }

            @Override
            public String[] value() {
                return new String[]{"qq"};
            }

            @Override
            public String[] path() {
                return new String[]{"/user/q1","/user/q2"};
            }

            @Override
            public RequestMethod[] method() {
                return new RequestMethod[]{RequestMethod.valueOf("POST")};
            }

            @Override
            public String[] params() {
                return new String[0];
            }

            @Override
            public String[] headers() {
                return new String[0];
            }

            @Override
            public String[] consumes() {
                return new String[0];
            }

            @Override
            public String[] produces() {
                return new String[0];
            }

        };

        RequestMappingInfo.Builder builder2;
        builder2 = RequestMappingInfo.paths(requestMapping2.path())
                .methods(requestMapping2.method())
                .params(requestMapping2.params())
                .headers(requestMapping2.headers())
                .consumes(requestMapping2.consumes())
                .produces(requestMapping2.produces())
                .mappingName(requestMapping2.name());

        builder2.options(configuration);


        Object serviceBean2 = SpringBeanFactory.getBean(Class.forName("com.sdf.normal.IUserService"));
        //Object reqBean = com.demo.SpringBeanFactory.getBean(Class.forName("com.demo.AddUserReq"));
        Class<?> aClass2 = Class.forName("com.sdf.normal.QueryUserReq");
        Method method3 = serviceBean2.getClass().getDeclaredMethod("find", aClass2);

        BaseService baseService2 = new BaseService(serviceBean2,method3);
        BaseController baseController2 = new BaseController(baseService2,aClass2){
            @Override
            public Object addUser(Object o) {
                return  baseService2.handle(JSONObject.parseObject(JSON.toJSONString(o),aClass2));
            }

            @Override
            public String getName() {
                return aClass.getName() + method.getName();
            }
        };

        Method method5 = baseController2.getClass().getDeclaredMethod("addUser",Object.class);

        defaultListableBeanFactory.registerSingleton("c1",baseController2);
        beanFactory.autowireBean(baseController2);
        requestMappingHandlerMapping.registerMapping(builder2.build(),baseController2,method5);
    }
}
