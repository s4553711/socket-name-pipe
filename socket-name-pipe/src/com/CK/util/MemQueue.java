package com.CK.util;

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
            int i = 0;
            while(true) {
                while (!queue.isEmpty()) {
                    i++;
                    String takeout = new String(this.queue.take());
                    System.out.print(takeout);
                }
                if (!isRunning) {
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            try {
                if (!this.queue.isEmpty()) {
                    System.out.print(new String(this.queue.take()));
                }
            } catch (InterruptedException e2) {
                //System.out.println("Error while take out contetn from blocking queue inside");
            }
            //System.out.println("Error while take out contetn from blocking queue");
        }
    }

    public void put(byte[] realPack) throws InterruptedException {
        this.queue.put(realPack);
    }
}
