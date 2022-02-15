package org.kingsun;

import javafx.application.Application;

public class ThreadGroupDemo {

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group = new ThreadGroup("originGroup");

        NewThread t1 = new NewThread("one", group);
        System.out.println("Starting one");
        NewThread t2 = new NewThread("two", group);
        System.out.println("Starting two");

        System.out.println("number of active thread: " + group.activeCount());

       Thread[] threads = new Thread[group.activeCount()];

       group.enumerate(threads);

        for (Thread thread : threads) {
            System.out.println(thread.getState());
        }

        Application

        t1.join();
        t2.join();

        for (Thread thread : threads) {
            System.out.println(thread.getState());
        }

    }

}
