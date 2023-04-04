//package com.example.travel.user.interceptor;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author yijiyin
// */
//@Configuration
//public class WebMvcConfiguration implements WebMvcConfigurer {
//    @Autowired
//    AuthorityAnnotationInterceptor authorityAnnotationInterceptor;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(authorityAnnotationInterceptor)
//                //要拦截的请求
//                .addPathPatterns("/**")
//                //排除请求
//                .excludePathPatterns("/user");
//    }
//}
