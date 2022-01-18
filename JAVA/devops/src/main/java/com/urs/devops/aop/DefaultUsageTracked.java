package com.urs.devops.aop;

public class DefaultUsageTracked implements UsageTracked{
    public static int count;
    public void increaseUseCount(){
        count = count + 1;
        System.out.println("((((((((((((( do somthing ((((((((((((");
    }
}
