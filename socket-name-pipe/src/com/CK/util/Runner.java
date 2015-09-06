package com.CK.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Runner {
    public static void main(String[] args) {
        System.out.println("GO");
        SocketNamePipe pipe = new SocketNamePipe();
        pipe.attach();

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("get > " + line);
            }
        } catch (IOException e) {
            System.out.println("Error");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
