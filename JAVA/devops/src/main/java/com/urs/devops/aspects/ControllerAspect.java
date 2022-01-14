package com.urs.devops.aspects;


import com.urs.devops.entity.Student;
import com.urs.devops.utils.Auditable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerAspect {

    @Pointcut("execution( * com.urs.devops.impl.SchoolServiceImpl.haveClass(String)) && args(name) ")
    private void checktime(String name){}

    @Pointcut("execution( * com.urs.devops.interfaces.*.*(..)) ")
    private void checktime2(){}


    @Before("checktime(name)")
    public void beforelog( String name){
        System.out.println("------------------- this from AOP in place -------------------");
        System.out.println(name);
        System.out.println("------------------- this from AOP in place -------------------");
    }


    @Around("checktime2() && @annotation(auditable)")
    public Student AfterShow(ProceedingJoinPoint pjp , Auditable auditable) throws Throwable {
        Long start = System.currentTimeMillis();
        System.out.println("$$$$$$$$$$$$$$ this from AOP start time: " + start +"  ################" );
        System.out.println(auditable.code());
        Student student = (Student) pjp.proceed();
        System.out.println(student);
        Long end = System.currentTimeMillis();
        System.out.println("#######!!!!!! execution time:" + (end - start)+" ################");
        return student;
    }

}
