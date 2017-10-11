package com.fireyao;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/**
 * @author liuliyuan
 * @date 2017/9/29 14:46
 * @Description:动态注册bean,加载IOC容器时,这里的的bean就会被注册到springIoc中
 */
@Component
public class MyBeanRegister implements BeanDefinitionRegistryPostProcessor {

    String classPath = "com.fireyao.mq.RabbitProcesse";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        try {
            Class clazz = Class.forName(classPath);
            BeanDefinition beanDefinition = new RootBeanDefinition(clazz);
            //获取驼峰类名
            registry.registerBeanDefinition(ClassUtils.getShortNameAsProperty(clazz), beanDefinition);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
