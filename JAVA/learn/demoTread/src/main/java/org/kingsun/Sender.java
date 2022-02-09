package org.kingsun;

public class Sender {
    public void send(String msg){
        System.out.println("Sending \t" + msg + " "+Thread.currentThread().getName());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n" + msg + " Sent" +  " "+Thread.currentThread().getName());
    }
}
