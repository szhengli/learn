package org.kingsun;

public class NewThread extends Thread{
    NewThread(String name, ThreadGroup threadGroup){
        super(threadGroup, name);
        start();
    }

    public void run(){
        for (int i=0 ; i<1000; i++){

            try {
                System.out.println("------------- " + Thread.currentThread().getState());
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
