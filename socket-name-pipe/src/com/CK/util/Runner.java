package com.CK.util;

import java.io.IOException;
import java.io.InputStream;

public class Runner {
    public static void main(String[] args) {
        TCPNamedPipe pipe = new TCPNamedPipe("localhost", Integer.valueOf(args[0]));
        try {
            int nRead;
            byte[] data = new byte[8192];
            InputStream is = System.in;
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                //pipe.write(data, nRead);
                pipe.push(data, nRead);
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
        pipe.closePusher();
    }
}
