package com.urs.devops.aspects;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerAspect {

    @Pointcut("within(com.urs.devops.controllers..*)")
    private  void log(){}


    @Before("log()")
    public void beforelog(){
        System.out.println("!!!!!!!!!!this from AOP!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
