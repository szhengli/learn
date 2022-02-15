package org.kingsun;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class Factory {
    public static void main(String[] args) {
        List<Integer> taskQueue = new ArrayList<>();

        int MAX_CAPACITY = 5 ;

        Thread thread = new Thread();

        Thread tProducer = new Thread(new Producer(taskQueue, MAX_CAPACITY), "Producer");
        Thread tConsumer = new Thread(new Consumer(taskQueue), "Consumer1");


        new Thread(new Producer(taskQueue, MAX_CAPACITY), "Producer2").start();
        tProducer.start();
        new Thread(new Consumer(taskQueue), "Consumer2").start();
        tConsumer.start();


    }
}
