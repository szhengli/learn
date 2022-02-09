package org.kingsun;

public class Driver {
    public static void main(String[] args) throws InterruptedException {
        Sender snd = new Sender();
        ThreadSend s1 = new ThreadSend("Hi", snd);

        ThreadSend s2 = new ThreadSend("Bye", snd);

        s1.start();
        s2.start();

        s1.join();
        s2.join();
    }
}
