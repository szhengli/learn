package org.kingsun;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Runnable{
    private String name;

    public Task(String name) {
        this.name = name;
    }


    @Override
    public void run() {
        try {
            for (int i=0 ; i <= 5 ; i++){
                if (i == 0 ){
                    Date d = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
                    System.out.println("Initilize Time for task name: " + name + ft.format(d) );
                }else {
                    Date d = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
                    System.out.println("====Executing Time  for task name: " + name + ft.format(d) );
                }
                Thread.sleep(1000);
            }
            System.out.println(name+" complete");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
