package com.CK.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String args[]) throws Exception {
        DatagramSocket server = new DatagramSocket(45678);
        byte[] receiveData = new byte[16384];
        while(true) {
            DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
            server.receive(packet);
            String str = new String(receiveData, 0, packet.getLength());
            //System.out.println(">"+str+"<");
            if (str.equals("stopSignal")) {
                break;
            }
        }
        server.close();
    }
}
