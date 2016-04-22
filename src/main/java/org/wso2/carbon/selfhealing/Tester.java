package org.wso2.carbon.selfhealing;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tester {
    FileWriter fileWriter;

    public Tester (){

    }
    public Tester (String test){

        MemoryManager memoryManager = new MemoryManager();
        List <String> list = new ArrayList<String>();
        JVMManager jvmManager = new JVMManager();
        List <Tester> lister = new ArrayList<Tester>();
        list.add(test);
        lister.add(this);
        Long maxMemory = Runtime.getRuntime().maxMemory();
       // org.wso2.carbon.selfhealing.Tester testerr = new org.wso2.carbon.selfhealing.Tester("Hey");
        for(int i = 0; i < maxMemory; ++i) {
           // int matrixArray [] = new int[100000*1000+i*10000*100];
           // matrixArray[i] = i;
            Tester tester = new Tester();
           // lister.add(tester);
            SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss:ms");//dd/MM/yyyy
            Date now = new Date();
            String strDate = sdfDate.format(now);
            list.add(strDate);
           // list.add(test);
        }
        /*//Long maxMemory = Runtime.getRuntime().maxMemory();
        //System.out.println(maxMemory);
        //write(12l,"test");
        //int[] matrix = new int[(int) (maxMemory + 1)];
        for(int i = 0; i < maxMemory; ++i) {
            *//*int matrixArray [] = new int[100000*1000+i*10000*100];
            matrixArray[i]=100000*1000+i*10000;
            System.out.println("JVM version : " + jvmManager.getJVMVersion());
            System.out.println("Total Memory (HeapSize) : " + memoryManager.getTotalMemory() + "mb");
            System.out.println("Free Memory : " + memoryManager.getFreeMemory() + "mb");
            System.out.println("Max Memory : " + memoryManager.getMaxMemory() + "mb");
            System.out.println("Used Memory : " + memoryManager.getUsedMemory() + "mb");

            System.out.println();*//*
            SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss:ms");//dd/MM/yyyy
            Date now = new Date();
            String strDate = sdfDate.format(now);*/
          //  write(memoryManager.getUsedMemory(),"time");
           /* try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        /*try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*//*

        org.wso2.carbon.selfhealing.Tester tester = new org.wso2.carbon.selfhealing.Tester(test);*/



    public void write (long memory, String time){

        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/main/resources/usedMemory.txt", true)));
            out.println(memory);
            out.close();
           /* FileWriter fileWriter = new FileWriter("src/main/resources/usedMemory.txt");
            //FileOutputStream outputStream = new FileOutputStream("usedMemory.txt");
           // OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-16");
            //fileWriter.write(time);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(memory + "\n");
            //bufferedWriter.flush();
            bufferedWriter.close();*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
