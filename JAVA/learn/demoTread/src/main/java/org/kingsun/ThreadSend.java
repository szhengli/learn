package org.kingsun;

public class ThreadSend extends Thread{
    private String msg;
    private Sender sender;

    ThreadSend(String m , Sender obj){
        msg = m ;
        sender = obj;
    }

    public void run(){
        System.out.println("beginning " + Thread.currentThread().getName()+ "!!!!!!!!!!!!");
        synchronized (sender){
            System.out.println(Thread.currentThread().getName()+" get the lock#######");
            sender.send(msg);

        }

        System.out.println("end " + Thread.currentThread().getName()+ "!!!!****!!!!!!!!");
    }
}
