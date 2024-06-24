package com.fangche.service1.utils.authority;

import com.fangche.service1.entity.Response;
import com.fangche.service1.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;


//声明一个类为切面类
@Aspect
//将该类注入到IOC容器中，交给Spring管理
@Component
public class AuthorityAspectConfig {
    @Autowired
    private AuthenticationService authenticationService;
    /**
     * 声明一个切入点
     * 切入点为：所有标注了Authority注解的方法
     */
    @Pointcut("@annotation(com.fangche.service1.utils.authority.Authority)")
    public void authority(){

    }

    /**
     * 具体验证业务数据
     */
    @Around("authority()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
        }

        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Authority authorityAnnotation = method.getAnnotation(Authority.class);
        byte permissionType = 0;
        if (authorityAnnotation != null) {
            // 获取注解参数
            permissionType = authorityAnnotation.value();
            System.out.println("Authority value: " + permissionType);
        }

        if (permissionType == Permission.NORMAL){
            // 执行真正的业务处理逻辑
            if (request != null && authenticationService.baseAuthenticate(request)) {
                return joinPoint.proceed();
            } else {
                return new Response(300, "鉴权不通过", null);
            }
        }
        if (permissionType == Permission.AUTHOR){
            if (request != null && authenticationService.isAuthorAuthenticate(request)) {
                return joinPoint.proceed();
            }
            return new Response(301, "权限拒绝", null);
        }
        if (permissionType == Permission.ADMIN){
            if (request != null && authenticationService.isAdminAuthenticate(request)) {
                return joinPoint.proceed();
            }
            return new Response(302, "权限拒绝", null);
        }
        return new Response(300, "鉴权不通过", null);
    }
}