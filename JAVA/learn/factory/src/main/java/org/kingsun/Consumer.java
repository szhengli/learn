package org.kingsun;

import java.util.List;

public class Consumer implements Runnable{
    private List<Integer> taskQueue;

    public Consumer(List<Integer> taskQueue) {
        this.taskQueue = taskQueue;
    }

    private void consume() throws InterruptedException {

        synchronized (taskQueue){
            System.out.println("--"+ Thread.currentThread().getName() + "  get the lock^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
           while (taskQueue.isEmpty()){
                System.out.println("---- Queue is empty, stop consume, and release lock *************" + Thread.currentThread().getName() + " is waiting , size: " + taskQueue.size());
                taskQueue.wait();

            }
            Thread.sleep(1000);
            int i = (Integer) taskQueue.remove(0);
            System.out.println("----- Consumed: " + i + "################# by" + Thread.currentThread().getName());
            taskQueue.notifyAll();
            System.out.println("------------- can start produce");
        }
    }


    @Override
    public void run() {
        System.out.println("---!!--"+Thread.currentThread().getName() +" begins" + "^^^^^^^^^^^^");
        while (true){
            try {
                Thread.sleep(10);
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
