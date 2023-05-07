package com.example.travel.aop;

import com.example.travel.aop.AuthorityAnnotationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yijiyin
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    AuthorityAnnotationInterceptor authorityAnnotationInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authorityAnnotationInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/**/user","/**/getProductAllTableWX","/**/getProductListWX","/**/getProductDetail","/**/getProductListWXTab");

    }
}
