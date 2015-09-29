package com.CK.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MemQueueTest {

    private ByteArrayOutputStream outContent;
    private MemQueue mem;
    private Thread t;
    
    @Before
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        mem = new MemQueue(new LinkedBlockingQueue<byte[]>());
        t = new Thread(mem);
    }
   
    @Test
    public void testPut() {
        byte[] message = {41, 42, 43, 100};
        try {
            t.start();
            mem.put(message);
            Thread.sleep(1000);
            assertEquals("runnable\n)*+d\nempty\n", outContent.toString());            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @After
    public void tearDown() {
        mem.terminate();
        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println("Error while join thread : "+e.getMessage());
        }        
    }

}
