package com.CK.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Runner {
    private static Socket socket;
    public static void main(String[] args) {
        System.out.println("GO");
        SocketNamePipe pipe = new SocketNamePipe();
        pipe.attach();

        initTcpClient();
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(System.in));
                String line;
                while ((line = in.readLine()) != null) {
                    //System.out.println("get > " + line);
                    out.writeBytes(line+"\n");
                }
                out.writeBytes("stopSignal");
                System.out.println("stopSignal");
            } catch (IOException e) {
                System.out.println("Error");
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void initTcpClient() {
        try {
            socket = new Socket("localhost", 45678);
        } catch (Exception e) {
            System.out.println("Initate tcp client falied! "+e);
        }
    }
}
