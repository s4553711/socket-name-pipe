package com.CK.util.fastq;

import java.io.BufferedReader;
import java.io.IOException;

public class FastqReader {
    private BufferedReader reader;
    
    public FastqReader(BufferedReader bufferedReader) {
        reader = bufferedReader;
    }

    public String readLine() throws IOException {
        String result = null;
        for(int i = 1;i <=4; i++ ) {
            String line = reader.readLine();
            if (line == null) {
                break;
            } else {
                if (i == 1){
                    result = line + "\n";
                } else {
                    result += line + "\n";
                }
            }
        }
        return result;
    }
}
