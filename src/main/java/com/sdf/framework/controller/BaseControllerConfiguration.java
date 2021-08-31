package com.sdf.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdf.common.SpringBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author barry
 * @date 2021/8/27 2:53 下午
 * @description
 */
@Component
public class BaseControllerConfiguration extends AbstractBaseController
    implements ApplicationRunner {

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

        // 读取同包下所有controller接口
        List<Class> classList = getAllClassByPackage();

        Field field = RequestMappingHandlerMapping.class.getDeclaredField("config");
        field.setAccessible(true);
        //解析注解
        RequestMappingInfo.BuilderConfiguration configuration =
            (RequestMappingInfo.BuilderConfiguration)field.get(requestMappingHandlerMapping);

        classList.forEach(aClass -> {
            // 获取接口类的所有方法
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                try {
                    RequestMapping requestMapping =
                        AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);

                    RequestMappingInfo.Builder builder =
                        RequestMappingInfo.paths(requestMapping.path())
                            .methods(requestMapping.method()).params(requestMapping.params())
                            .headers(requestMapping.headers()).consumes(requestMapping.consumes())
                            .produces(requestMapping.produces()).mappingName(requestMapping.name());
                    builder.options(configuration);
                    Object serviceBean = SpringBeanFactory.getBean(aClass);
                    BaseService baseService = new BaseService(serviceBean, method);

                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Class aClass2 = Object.class;
                    if(parameterTypes.length > 0) {
                        aClass2 = parameterTypes[0];
                    }

                    Class finalAClass = aClass2;
                    BaseController baseController = new BaseController(baseService, finalAClass) {
                        @Override
                        public Object addUser(Object o) {
                            return baseService
                                .handle(JSONObject.parseObject(JSON.toJSONString(o), finalAClass));
                        }

                        @Override
                        public String getName() {
                            return aClass.getName() + method.getName();
                        }
                    };
                    Method method2 =
                        baseController.getClass().getDeclaredMethod("addUser", Object.class);
                    defaultListableBeanFactory.registerSingleton(baseController.getName(),
                        baseController);
                    beanFactory.autowireBean(baseController);
                    requestMappingHandlerMapping.registerMapping(builder.build(), baseController, method2);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        //todo 配置 扫包读取配置包

    }

    private List<Class> getAllClassByPackage() {
        ArrayList<Class> returnClassList = new ArrayList<Class>();
        try {
            String packageName = this.getClass().getPackage().getName();

            // 获取当前包下以及子包下所以的类
            List<Class<?>> allClass = getClasses(packageName);
            // 判断是否是一个接口
            allClass.forEach(clazz -> {
                if(clazz.isInterface()) {
                    returnClassList.add(clazz);
                }
            });
        } catch (Exception e) {
        }
        return returnClassList;
    }

    /**
     * 从包package中获取所有的Class
     * @param packageName
     * @return
     */
    public static List<Class<?>> getClasses(String packageName){

        //第一个class类的集合
        List<Class<?>> classes = new ArrayList<Class<?>>();
        //是否循环迭代
        boolean recursive = true;
        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //循环迭代下去
            while (dirs.hasMoreElements()){
                //获取下一个元素
                URL url = dirs.nextElement();
                //得到协议的名称
                String protocol = url.getProtocol();
                //如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)){
                    //如果是jar包文件
                    //定义一个JarFile
                    JarFile jar;
                    try {
                        //获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        //从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        //同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            //如果是以/开头的
                            if (name.charAt(0) == '/') {
                                //获取后面的字符串
                                name = name.substring(1);
                            }
                            //如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                //如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    //获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                //如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive){
                                    //如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        //去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            //添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes){
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
        File[] dirfiles = dir.listFiles(
            file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                    file.getAbsolutePath(),
                    recursive,
                    classes);
            }
            else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
