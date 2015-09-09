package com.CK.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketNamePipe {
    private Socket socket;
    private DataOutputStream out;
    private int port = 45678;
    
    public SocketNamePipe() {
        initTcpClient("localhost", port);
    }
    
	public void push(String line) {
	    try {
            out.writeBytes(line+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void closePusher() {
	    try {
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    private void initTcpClient(String host, int port) {
        try {
            socket = new Socket(host, port);
            out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("TCP client inicate falied! "+e);
        }
    }
}
