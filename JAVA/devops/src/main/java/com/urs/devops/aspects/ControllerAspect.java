package com.urs.devops.aspects;


import com.urs.devops.entity.Student;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerAspect {

    @Pointcut("execution( * com.urs.devops.impl.SchoolServiceImpl.haveClass(String)) && args(name) ")
    private void checktime(String name){}


    @Before("execution( * com.urs.devops.impl.SchoolServiceImpl.haveClass(String)) && args(name)")
    public void beforelog( String name){
        System.out.println("------------------- this from AOP in place -------------------");
        System.out.println(name);
        System.out.println("------------------- this from AOP in place -------------------");
    }


    @Around("checktime(name)")
    public Student AfterShow(ProceedingJoinPoint pjp, String name) throws Throwable {
        Long start = System.currentTimeMillis();
        System.out.println("$$$$$$$$$$$$$$ this from AOP start time: " + start +"  ################" );
        System.out.println(name);
        Student student = (Student) pjp.proceed();
        System.out.println(student);
        Long end = System.currentTimeMillis();
        System.out.println("#######!!!!!! execution time:" + (end - start)+" ################");
        return student;
    }

}
