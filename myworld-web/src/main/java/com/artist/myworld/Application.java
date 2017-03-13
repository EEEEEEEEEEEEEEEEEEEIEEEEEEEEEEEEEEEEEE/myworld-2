package com.artist.myworld;

import com.alibaba.fastjson.JSON;
import com.artist.myworld.rpc.UserRpc;
import com.dili.utils.boot.retrofitful.RestfulFactoryBean;
import com.dili.utils.boot.retrofitful.annotation.RestfulServiceScan;
import com.dili.utils.domain.BaseOutput;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.beans.Introspector;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by asiamastor on 2016/12/21.
 */

@SpringBootApplication
@MapperScan("com.artist.myworld.dao")
@EnableAutoConfiguration(exclude = {ThymeleafAutoConfiguration.class, VelocityAutoConfiguration.class})
@ImportResource(locations = "classpath:applicationContext.xml")
@ComponentScan(basePackages = {"com.dili.utils", "com.artist.myworld"})
@RestfulServiceScan("com.artist.myworld.rpc")
public class Application extends SpringBootServletInitializer {

    public static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SpringApplication.run(Application.class, args);

//        String basePackage = "com.artist.myworld.rpc";
//
//        Resource rootResource = getRootResource(basePackage);
//        Resource[] resources = getResources(basePackage);
//        ApplicationContext applicationContext =new ClassPathXmlApplicationContext("applicationContext.xml");
//
//        for (Resource resource : resources) {
//            ClassPathResource classPathResource = new ClassPathResource(resource.getURL().getPath());
//            String classFullName = getClassNameByResource(resource, rootResource.getURL(), basePackage);
//            Class clazz = Class.forName(classFullName);
//            if(!clazz.isInterface()) continue;
//            System.out.println("classFullName:"+classFullName+", defaultBeanName:"+buildDefaultBeanName(classFullName));
//            //类的全路径
//            DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//            BeanDefinitionBuilder dataSourceBuider = BeanDefinitionBuilder.genericBeanDefinition(RestfulFactoryBean.class);
//            //向里面的属性注入值，提供get set方法
//            dataSourceBuider.addPropertyValue("intfClass", clazz);
////            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
////            beanDefinition.setBeanClass(RestfulFactoryBean.class);
////            beanDefinition.getPropertyValues().add("intfClass", Class.forName(classFullName));
//////            beanDefinition.setAttribute("intfClass", Class.forName(classFullName));
////            beanDefinition.setSynthetic(true);
//            //注册Bean
//            fty.registerBeanDefinition(buildDefaultBeanName(classFullName), dataSourceBuider.getBeanDefinition());
//            applicationContext.getBean(UserRpc.class);
//            //以后便可以取出Bean了
//            UserRpc rpc = (UserRpc)applicationContext.getBean(buildDefaultBeanName(classFullName));
//            Map<String, String> map = new HashMap<>();
//            map.put("phoneNumber","18108087570");
//            map.put("password","123456");
//            BaseOutput output = rpc.updateSellerPasswordByPhoneNumber(JSON.toJSONString(map));
//            System.out.println(output.getData());
////            rpc.hello("world");
//        }
//        System.out.println("======================");
//        //测试调用路径,
////        这是直接引用spring的源码部分的内容，如果这里可以获取到，且路径是正确的，一般情况下，都是可以加载到类的
//        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
//        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(basePackage);
//        for (BeanDefinition beanDefinition : beanDefinitions) {
//            System.out.println(beanDefinition.getBeanClassName()
//                    + "\t" + beanDefinition.getResourceDescription()
//                    + "\t" + beanDefinition.getClass());
//        }
    }

    /**
     * 获取根资源
     * 找不到返回空
     */
    public static Resource getRootResource(String basePackage){
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String basePackagePath = ClassUtils.convertClassNameToResourcePath(basePackage);
        try {
            Resource[] resources = resourcePatternResolver.getResources("classpath*:" + basePackagePath + "/");
            return resources.length>0?resources[0]:null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 扫描所有资源
     * 找不到返回空
     */
    public static Resource[] getResources(String basePackage){
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String basePackagePath = ClassUtils.convertClassNameToResourcePath(basePackage);
        String resourcePattern = "**/*.class";
        String ex = "classpath*:" + basePackagePath + '/' + resourcePattern;
        try {
            return resourcePatternResolver.getResources(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据资源、根URL和扫描包获取类全名
     */
    public static String getClassNameByResource(Resource resource, URL rootDirURL, String basePackage) {
        JarFile jarFile = null;
        boolean closeJarFile = false;
        JarEntry entry;
        try {
            URLConnection con = resource.getURL().openConnection();
            if (con instanceof JarURLConnection) {
                JarURLConnection entries = (JarURLConnection) con;
                ResourceUtils.useCachesIfNecessary(entries);
                jarFile = entries.getJarFile();
                String jarFileUrl = entries.getJarFileURL().toExternalForm();
                entry = entries.getJarEntry();
                String rootEntryPath = entry != null ? entry.getName() : "";
                String classFullPath = rootEntryPath.substring(0, rootEntryPath.length() - ".class".length());
                String classFullName = ClassUtils.convertResourcePathToClassName(classFullPath);
//                System.out.println("jar包里面的类:"+classFullName);
                closeJarFile = !entries.getUseCaches();
                return classFullName;
            } else {
                String resourcePath = resource.getURL().getPath();
                String rootDirPath = rootDirURL.getPath();
                String path = resourcePath.substring(resourcePath.lastIndexOf(rootDirPath) + rootDirPath.length() - basePackage.length() - 1);
                String classFullPath = path.substring(0, path.length() - ".class".length());
                String classFullName = ClassUtils.convertResourcePathToClassName(classFullPath);
//                System.out.println("当前项目的类:"+classFullName);
                return classFullName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (closeJarFile) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                }
            }
        }
        return "";
    }


    /**
     * 构建spring默认beanName
     * @param className
     * @return
     */
    public static String buildDefaultBeanName(String className) {
        String shortClassName = ClassUtils.getShortName(className);
        return Introspector.decapitalize(shortClassName);
    }


}
