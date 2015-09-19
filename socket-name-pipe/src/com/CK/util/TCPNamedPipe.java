package com.CK.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPNamedPipe {
    private Socket socket;
    private DataOutputStream out;
    
    public TCPNamedPipe(String host, int port) {
        initTcpClient(host, port);
    }
    
	public void push(byte[] line, int nRead) {
	    try {
            out.write(line, 0, nRead);
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