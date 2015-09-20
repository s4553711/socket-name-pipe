package com.CK.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TCPSocketServer {
    private ServerSocket server;
    private byte[] receiveData = new byte[8192];
    private MemQueue out;
    Thread t;
    private BlockingQueue<byte[]> queue;
    
    public TCPSocketServer(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        queue = new LinkedBlockingQueue<>();
        out = new MemQueue(queue);
        t = new Thread(out);
        t.start();          
    }
    
    public void receive() {
        System.out.println("start receive");
        Socket s1;
        while(true) {
            try {
                s1 = server.accept();
                DataInputStream in = new DataInputStream(s1.getInputStream());
                int bytesRead = 0;
                boolean end = false;
                String receive = "";
                while(!end) {
                    bytesRead = in.read(receiveData);
                    if (bytesRead == -1) {
                        end = true;
                    } else {
                        byte[] realPack = Arrays.copyOfRange(receiveData, 0, bytesRead);
                        receive = new String(realPack);
                        //System.out.println("read .. "+bytesRead+", really size:"+receiveData.length);
                        //System.out.println("put .. \n"+receive);
                        queue.put(realPack);                        
                    }
                }
                if (receive.trim().equals("stopSignal")) {
                    System.out.println("Receive stop signal ...");
                    break;
                }
                receive = "";
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.close();
        try {
            System.out.println("Wait the thread to join");
            t.join();
            System.out.println("thread is now join");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void close() {
        try {
            server.close();
            out.terminate();    
        } catch (IOException e) {
            System.out.println("TCP server close error");
        }
    }
}
