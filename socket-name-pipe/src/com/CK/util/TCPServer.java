package com.CK.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TCPServer {

    public static void readHeader() {
    	BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("header.sam"));
			String line;
			while((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
	
    public static void main(String args[]) {
    	readHeader();
        TCPSocketServer server = new TCPSocketServer(Integer.valueOf(args[0]));
        server.receive();
    }
}
