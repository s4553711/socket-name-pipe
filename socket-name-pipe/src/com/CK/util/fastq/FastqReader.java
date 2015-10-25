package com.CK.util.fastq;

import java.io.BufferedReader;
import java.io.IOException;

public class FastqReader {
    private BufferedReader reader;
    
    public FastqReader(BufferedReader bufferedReader) {
        reader = bufferedReader;
    }

    public String readLine() throws IOException {
        StringBuilder result = new StringBuilder();
        for(int i = 1;i <=4; i++ ) {
            String line = reader.readLine();
            if (line == null) {
                break;
            } else {
                result.append(line+"\n");
            }
        }     
        return result.length() == 0 ? null : result.toString();
    }
    
    public void close() throws IOException {
        reader.close();
    }
}
