package com.urs.devops.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Scheduler {

 //   @Scheduled(cron = "*/2 * * * * ?")
    public void cronJob(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date();

        System.out.println("&&&&&&&&&&&&&& " + sdf.format(date));
    }
}
