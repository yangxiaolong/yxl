package com.lego.yxl.smart;

import com.lego.yxl.annotation.Action;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Integer.MIN_VALUE)
public class ActionAspect {
    @Pointcut("@annotation(com.lego.yxl.annotation.Action)")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object action(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Action annotation = methodSignature.getMethod().getAnnotation(Action.class);
        ActionContext.set(annotation.type());
        try {
            return joinPoint.proceed();
        } finally {
            ActionContext.clear();
        }
    }
}
