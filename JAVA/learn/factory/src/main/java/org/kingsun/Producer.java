package org.kingsun;

import java.util.List;

public class Producer implements Runnable{
    private final List<Integer>  taskQueue;
    private final int MAX_CAPACITY;

    public Producer(List<Integer> taskQueue, int MAX_CAPACITY) {
        this.taskQueue = taskQueue;
        this.MAX_CAPACITY = MAX_CAPACITY;
    }

    private void produce (int i) throws InterruptedException {

        synchronized (taskQueue){
            System.out.println("++" + Thread.currentThread().getName() + "  get the lock");
            while (taskQueue.size() == MAX_CAPACITY){
                System.out.println("+++ Queue is full, have to stop produce and  and release lock-----" + Thread.currentThread().getName() + " is waiting , size: " + taskQueue.size());
                taskQueue.wait();

            }
            Thread.sleep(500);
            taskQueue.add(i);
            System.out.println("+++++ produced a product, ++++ " + i + taskQueue);
            taskQueue.notifyAll();
            System.out.println("+++++++ can start consume++++");
        }
    }

    @Override
    public void run() {
        int counter = 0 ;
        System.out.println("++" + Thread.currentThread().getName() +" begins" );

        while (true){
            try {
                produce(counter++);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
