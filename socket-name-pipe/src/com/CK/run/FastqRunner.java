package com.CK.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.CK.util.TCPNamedPipe;
import com.CK.util.fastq.FastqReader;

public class FastqRunner {
    public static void main(String[] args) {
        System.out.println("Number of args "+args.length);
        TCPNamedPipe[] pipes = new TCPNamedPipe[args.length];
        for(int j = 0; j < args.length; j++) {
            pipes[j] = new TCPNamedPipe("localhost", Integer.valueOf(args[j]));
            System.out.println("Create "+j+" for "+args[j]);
        }
        try {
            FastqReader br = new FastqReader(new BufferedReader(new InputStreamReader(System.in)));
            String line;
            int fastqNum = 0;
            while((line = br.readLine()) != null) {
                  int pipesIndex = fastqNum % pipes.length;
                  byte[] data = line.getBytes();
                  pipes[pipesIndex].push(data, data.length);
                  System.out.println("Send fastqNum: "+fastqNum+" to "+pipesIndex);
                  fastqNum++;
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
        for(TCPNamedPipe pipe : pipes) {
            pipe.closePusher();
        }
    }
}
