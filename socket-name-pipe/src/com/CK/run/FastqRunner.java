package com.CK.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.CK.util.TCPNamedPipe;
import com.CK.util.fastq.FastqReader;

public class FastqRunner {
    public static void main(String[] args) {
        TCPNamedPipe[] pipes = new TCPNamedPipe[args.length];
        for(int j = 0; j < args.length; j++) {
			String[] argPart = args[j].split(":");
			if (argPart.length < 2) {
				System.out.println("Illegal argument.");
				return;
			}
            pipes[j] = new TCPNamedPipe(argPart[0], Integer.valueOf(argPart[1]));
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
			String tmp = "";
            int fastqNum = 0;
			int i = 1;
            while((line = br.readLine()) != null) {
				if ((i % 4) == 0) {
					tmp = tmp.concat(line+"\n");
					int pipesIndex = fastqNum % pipes.length;
					byte[] data = tmp.getBytes();
					pipes[pipesIndex].push(data, data.length);
					//System.out.println("Send fastqNum: "+fastqNum+" to "+pipesIndex);
					fastqNum++;
					tmp = "";
				} else {
					tmp = tmp.concat(line+"\n");
				}
				i++;

            }
        } catch (IOException e) {
            System.out.println("Error");
        }
        for(TCPNamedPipe pipe : pipes) {
            pipe.closePusher();
        }
    }
}
