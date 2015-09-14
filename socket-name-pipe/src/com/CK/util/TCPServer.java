package com.CK.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static int port = 45678;
    private static ServerSocket serv;
    
    public static void main(String args[]) throws Exception {
        try {
            serv = new ServerSocket(port, 4);
            serv.setReceiveBufferSize(8192);
        } catch (Exception e) {
            System.out.println("Init ServerSocket Error"+e);
        }
        Socket s1 = null;
        try {
            System.out.println("Starting server...");
            while (true) {
                System.out.println("wait accept");
                s1 = serv.accept();
                System.out.println("get accept");
                boolean shouldStop = false;
                BufferedReader fromClient = new BufferedReader(
                        new BufferedReader(new InputStreamReader(s1.getInputStream()))
                );
                System.out.println("Wait for the text");
                String str;
                while((str = fromClient.readLine()) != null) {
                    //System.out.println("get from client > "+str);
                    if (str.trim().equals("stopSignal")){
                        shouldStop = true;
                        break;
                    }
                }
                System.out.println("end of the text");
                fromClient.close();
                if (shouldStop) {
                    System.out.println("Server down");
                    break;
                }
            }
        } catch (Exception e) {    
            System.out.println("Server setup error"+e);
        } finally {            
            serv.close();
        }
    }
}
