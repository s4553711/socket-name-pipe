package com.CK.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketServer {
    private DatagramSocket server;
    private RandomAccessFile pipe;
    private byte[] receiveData = new byte[16384];
    private MemQueue out;
    Thread t;
    private BlockingQueue<byte[]> queue;
    
    public SocketServer(int port) {
        try {
            server = new DatagramSocket(port);
            //createNamedPipe();
            queue = new LinkedBlockingQueue<>();
            out = new MemQueue(queue);
            t = new Thread(out);
            System.out.println("go");
            //out.run();
            t.start();
            System.out.println("finish");
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    
    public void receive() {
        System.out.println("start receive");
        while(true) {
            DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
            try {
                System.out.println("wait to receive ... ");
                server.receive(packet);
                System.out.println("after receive ...");
                //pipe.write(receiveData);
                queue.put(receiveData);
                System.out.println("finish write ...");
                String receive = new String(receiveData, 0, packet.getLength());
                System.out.println("get > "+receive+" <<");
                if (receive.equals("stopSignal")) {
                    System.out.println("Receive stop signal ...");
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.close();
    }
    
    private void close() {
        //try {
            server.close();
            //out.terminate();
            out.terminate();
            //pipe.close();
            //new File("/tmp/test-pipe").delete();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }
    
    private void createNamedPipe() {
        try {
            new File("/tmp/test-pipe").delete();
            Runtime.getRuntime().exec("mkfifo /tmp/test-pipe");
            pipe = new RandomAccessFile("/tmp/test-pipe", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
