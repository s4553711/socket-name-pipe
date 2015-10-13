package com.CK.util;

import java.io.IOException;
import java.io.InputStream;

public class Dispatcher {
    public static void main(String[] args) {
        System.out.println(args.length);
        TCPNamedPipe[] pipes = new TCPNamedPipe[args.length];
        int i = 0;
        for(String port : args) {
            pipes[i] = new TCPNamedPipe("localhost", Integer.valueOf(args[i]));
            System.out.println("Create "+i+" for "+args[i]);
            i++;
        }
        try {
            int line = 0;
            int nRead;
            byte[] data = new byte[8192];
            InputStream is = System.in;
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                int fastqNum = (line / 4)+1;
                int pipesIndex = fastqNum % pipes.length;
                pipes[pipesIndex].push(data, nRead);
                System.out.println("Send to fastqNum : "+fastqNum+" / Line : "+line);
                line++;
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
        for(TCPNamedPipe pipe : pipes) {
            pipe.closePusher();
        }
    }
}
