package com.CK.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.CK.util.fastq.FastqReader;

public class FastqRunner {
    public static void main(String[] args) {
        try {
            FastqReader br = new FastqReader(new BufferedReader(new InputStreamReader(System.in)));
            String line;
            int i = 1;
            while((line = br.readLine()) != null) {
                  System.out.print("> "+line);
                  if (i == 5) break;
                  i++;
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
