package com.CK.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class MemQueue implements Runnable {
    private BlockingQueue<byte[]> queue;
    private boolean isRunning = true;
    
    public MemQueue(BlockingQueue<byte[]> queue) {
        this.queue = queue;
    }
    
    public void terminate() {
        this.isRunning = false;
    }
    
    public void run() {
        try {
            System.out.println("runnable");
            PrintWriter writer = new PrintWriter("./a.txt", "UTF-8");
            while(true) {
                while (!queue.isEmpty()) {
                    String takeout = new String(this.queue.take());
                    //System.out.println(">>>");
                    //System.out.println(takeout);
                    //System.out.println("<<<");
                    //System.out.println("size> "+queue.size());
                    //System.out.println("isEmpty> "+queue.isEmpty());
                    //writer.print(takeout);
                }
                System.out.println("empty");
                if (!isRunning) {
                    System.out.println("clear and leave the thread");
                    break;
                }
                Thread.sleep(1000);
            }
            writer.close();
            System.out.println("thread begin to done");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("get exception");
            try {
                if (!this.queue.isEmpty()) {
                    System.out.println(new String(this.queue.take()));
                }
            } catch (InterruptedException e2) {
                System.out.println("Error while take out contetn from blocking queue inside");
            }
            System.out.println("Error while take out contetn from blocking queue");
        }
        System.out.println("Thread end");
    }
}
