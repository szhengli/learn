package com.urs.devops.aop;


import com.urs.devops.entity.Student;
import com.urs.devops.impl.SchoolServiceImpl;
import com.urs.devops.utils.Auditable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerAspect {

    @Pointcut("execution( * com.urs.devops.impl.SchoolServiceImpl.haveClass(String)) && args(name) ")
    private void checktime(String name){}

    @Pointcut("execution( * com.urs.devops.interfaces.*.*(..))  ")
    private void checktime2(){}


    @Before("checktime(name) && this(usageTracked)")
    public void beforelog( String name, UsageTracked usageTracked){
        System.out.println("------------------- this from AOP in place -------------------");
        System.out.println(name);
        usageTracked.increaseUseCount();
        System.out.println(usageTracked);
        System.out.println("------------------- this from AOP in place -------------------");
    }

    @DeclareParents(value = "com.urs.devops.impl.SchoolServiceImpl" , defaultImpl = DefaultUsageTracked.class)
    public static UsageTracked mixin;





    @Around(value = "checktime2() && @annotation(auditable) && args(name) && target(bean) ", argNames = "pjp,auditable,name,bean")
    public Student AfterShow(ProceedingJoinPoint pjp , Auditable auditable, String name, SchoolServiceImpl bean) throws Throwable {
        Long start = System.currentTimeMillis();
        System.out.println("$$$$$$$$$$$$$$ this from AOP start time: " + start +"  ################" );
        System.out.println(auditable.code());
        System.out.println(name);

        Student student = (Student) pjp.proceed();
        System.out.println(student);
        Long end = System.currentTimeMillis();
        System.out.println("++++++++++++++++++++++++++");

        System.out.println("++++++++++++++++++++++++++");
        System.out.println("#######!!!!!! execution time:" + (end - start)+" ################");
        return student;
    }

}
