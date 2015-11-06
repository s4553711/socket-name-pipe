package com.CK.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.LinkedBlockingQueue;

public class TCPSocketServer {
    private ServerSocket server;
    private byte[] receiveData = new byte[8192];
    private MemQueue mem;
    Thread t;
    private PrintWriter writer;
    
    public TCPSocketServer(int port) {
        try {
            server = new ServerSocket(port);
            writer = new PrintWriter("socketServer-"+port+".log", "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mem = new MemQueue(new LinkedBlockingQueue<byte[]>());
        t = new Thread(mem);
        t.start();
    }
    
    public void receive() {
        Socket s1;
        boolean firstSignal = true;
        while(true) {
            try {
                s1 = server.accept();
                if (firstSignal) {
                    writer.println("start > "+new SimpleDateFormat("yyyyMMdd_HHmmss.SSS").format(Calendar.getInstance().getTime()));
                    firstSignal = false;
                }
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
                        if (!receive.equals("stopSignal")) {
                            mem.put(realPack);
                        }
                    }
                }
                if (receive.equals("stopSignal")) {
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
            writer.flush();
            writer.println("end > "+new SimpleDateFormat("yyyyMMdd_HHmmss.SSS").format(Calendar.getInstance().getTime()));
            writer.close();
            server.close();
            mem.terminate();
            t.join();
        } catch (InterruptedException e) {
            t.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
