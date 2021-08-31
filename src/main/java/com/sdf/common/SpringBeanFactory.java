package com.sdf.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author barry
 * @date 2020/11/17 4:29 下午
 * @description
 */
@Component
public class SpringBeanFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanFactory.applicationContext = applicationContext;
    }

    /**
     * 通过类型获取
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public final static <T> T getBean(Class<T> clazz) {
        return SpringBeanFactory.applicationContext.getBean(clazz);
    }

    /**
     * 通过名称和类型获取
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public final static <T> T getBean(String name, Class<T> clazz) {
        return SpringBeanFactory.applicationContext.getBean(name, clazz);
    }

    /**
     * 获取当前的运行环境信息
     *
     * @return
     */
    public final static Environment getEnvironment() {
        return SpringBeanFactory.applicationContext.getEnvironment();
    }

}