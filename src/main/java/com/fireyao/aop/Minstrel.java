package com.fireyao.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


/**
 * Created by lly on 2017/8/29
 */
@Aspect
public class Minstrel {

    @Pointcut("execution(* com.fireyao.bean.Knights.*(..))")
    public void pointCutMethod() {
    }

    @Before("pointCutMethod()")
    public void singBeforeQuest(JoinPoint joinPoint) {
        System.out.println("♂ FA ♂ FA ♂ FA ♂ FA ♂, 乖乖站好");
    }

    @After("pointCutMethod()")
    public void singAfterQuest() {
        System.out.println("♂ A ♂ A ♂ A ♂ A ♂,到河北省来");
    }

    @Pointcut("execution(* com.fireyao.bean.Knights.saySomething(StringBuffer))&&args(sth)")
    public void somethingPointCut(StringBuffer sth) {
    }

    @Before("somethingPointCut(sth)")
    public void beforeSay(StringBuffer sth) {
        sth.append(",来河北省之前");
    }

}
