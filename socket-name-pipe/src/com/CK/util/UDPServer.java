package com.CK.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String args[]) throws Exception {
        DatagramSocket server = new DatagramSocket(45678);
        byte[] receiveData = new byte[1024];
        while(true) {
            DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
            server.receive(packet);
            String str = new String(packet.getData());
            System.out.println("str > "+str);
            if (str.equals("stopSignal")) {
                server.close();
            }
        }
    }
}
