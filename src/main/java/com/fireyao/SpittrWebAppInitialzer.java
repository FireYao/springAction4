package com.fireyao;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by lly on 2017/8/31
 */
public class SpittrWebAppInitialzer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * 加载spring相关配置
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * 加载springmvc相关配置
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * 将DispatcherServlet映射到 "/"
     *
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * 这里注册的所有过滤器,都会映射到DispatcherServlet
     * 就是说这里的过滤器过滤规则是 /*
     * 所有的请求都会先到这里注册的过滤器中
     *
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new CharacterEncodingFilter("UTF-8", true)
        };
    }


}
