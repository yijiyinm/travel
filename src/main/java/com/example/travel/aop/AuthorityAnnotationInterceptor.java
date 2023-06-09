package com.example.travel.aop;

import com.alibaba.fastjson.JSON;
import com.example.travel.cache.CacheManager;
import com.example.travel.dao.entity.UserDO;
import com.example.travel.service.impl.UserService;
import com.example.travel.util.CommenUtils;
import com.example.travel.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author yijiyin
 */
@Slf4j
@Aspect
@Component
public class AuthorityAnnotationInterceptor implements HandlerInterceptor {

    /**
     *    用户业务层接口
     */
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;

            Class<?> clazz = hm.getBeanType () ;
            Method m = hm.getMethod();

            try {
                if (clazz != null && m != null) {
                    boolean isClzAnnotation=clazz.isAnnotationPresent(Authority.class);
                    boolean isMethondAnnotation = m.isAnnotationPresent (Authority.class);
                    log.info("isClzAnnotation:{}", isClzAnnotation);
                    log.info("isMethondAnnotation:{}", isMethondAnnotation);


                    Authority authority = null;
                    //如果方法和类声明中同时存在这个注解，那么方法中的会覆盖类中的设定。
                    //方法上的@Authority注解优先级比类高
                    if (isMethondAnnotation) {
                        authority = m.getAnnotation (Authority.class);
                    } else if (isClzAnnotation) {
                        authority = clazz.getAnnotation(Authority.class);
                    }
                    log.info("切面类:{}",authority);

                    response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json; charset=utf-8");

                        if (authority != null&& authority.authoritytype()== AuthorityType.CHECK_LOGIN){
                            log.info("进入：{}",authority);
                            // 验证登录
                            String tokeninfo = request.getHeader("tokeninfo");

                            if(StringUtils.isBlank(tokeninfo)){
                                response.getWriter().write(JSON.toJSONString( ResultCode.of(40001,"请登录")));
                                return false;
                            }
                            log.info("tokeninfo：{}",tokeninfo);
                            String openId  = CacheManager.get(tokeninfo);

                            if(StringUtils.isBlank(openId)) {
                                response.getWriter().write(JSON.toJSONString(ResultCode.of(40000,"token过期，请重新登录")));
                                return false;
                            }
                            log.info("userService：{}",userService);

                            UserDO userDO = userService.getUserInfoByOpenId(openId);
                            if(Objects.isNull(userDO)){
                                response.getWriter().write(JSON.toJSONString(ResultCode.of(40000,"用户不存在")));
                                return false;

                            }

                            //loginCache.put(openId, token);
                            //userCache.put(Thread.currentThread().getName(), userDO);
                            return true;
                        }

                }
            } catch (Exception e){
                log.error("切面异常",e);
                e.printStackTrace();
            }
        }
        return true;
    }
}
