package com.lxx.aspect;

import com.lxx.dao.LogDao;
import com.lxx.entity.Admin;
import com.lxx.entity.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Component
@Aspect
@Slf4j
public class Myaspect {
    @Autowired
    LogDao logDao;
    @Autowired
    HttpServletRequest request;

    @Around(value = "pt()")
    public Object MyAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proeceed ;
        System.out.println("环绕通知开始");
        //获取执行操作的用户
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //获取执行的时间
        Date time=new Date();
        //获取方法对象
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        //获取注解对象
        Mylog mylog = method.getAnnotation(Mylog.class);
        String thing=mylog.name();
        //事情执行的结果
        String res="false";

        try {
            //调用目标方法
            proeceed=proceedingJoinPoint.proceed();
            res="true";
            return proeceed;
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            if(null==admin){
                admin=new Admin();
                admin.setUsername("暂未登录");
            }
            log.info("admin--->{},date--->{},thing--->{},flag--->{}",admin.getUsername(),time,thing,res);
            Log log = new Log(UUID.randomUUID().toString(),thing,admin.getUsername(),time,res);
            logDao.insert(log);
        }
    }


    @Pointcut(value = "@annotation(com.lxx.aspect.Mylog)")
    public void pt() {}
}
