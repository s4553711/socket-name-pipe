package com.CK.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class TCPSocketServer {
    private ServerSocket server;
    private byte[] receiveData = new byte[8192];
    private MemQueue mem;
    //private FileBasedPipe fmem;
    Thread t;
    
    public TCPSocketServer(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //fmem = new FileBasedPipe("/tmp/testpipe");
        mem = new MemQueue(new LinkedBlockingQueue<byte[]>());
        t = new Thread(mem);
        t.start();          
    }
    
    public void receive() {
        //System.out.println("start receive");
        Socket s1;
        while(true) {
            try {
                s1 = server.accept();
                //System.out.println("accept");
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
                        receive = new String(realPack).trim();
                        //System.out.println("read .. "+bytesRead+", really size:"+receiveData.length);
                        //System.out.println("put .. \n"+receive); 
                        if (!receive.equals("stopSignal")) {
                            mem.put(realPack);
                            //fmem.put(realPack);
                        }
                    }
                }
                if (receive.equals("stopSignal")) {
                    //System.out.println("Receive stop signal ...");
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
    }
    
    private void close() {
        try {
            server.close();
            mem.terminate();
            //fmem.terminate();
            t.join();
        } catch (InterruptedException e) {
            //System.out.println("close thread error");
            t.interrupt();
        } catch (IOException e) {
            //System.out.println("TCP server close error");
        }
    }
}
