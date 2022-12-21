package com.qqclient.utils;

import java.io.*;

public class FlieIO {
    //文件转换成二进制
    public static byte[] get(File file){
        try {
            FileInputStream fis=new FileInputStream(file);
            ByteArrayOutputStream bos=new ByteArrayOutputStream(1024);
            byte[] b=new byte[1024];
            int n;
            while((n=fis.read(b))!=-1){
                bos.write(b,0,n);
            }
            byte[] data=bos.toByteArray();
            System.out.println("获取成功");
            fis.close();
            bos.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] shardingGet(File file){
        try {
            FileInputStream fis=new FileInputStream(file);
            ByteArrayOutputStream bos=new ByteArrayOutputStream(1024*1024);
            byte[] b=new byte[1024*1024];
            int n;
            while((n=fis.read(b))!=-1){

                bos.write(b,0,n);
            }
            byte[] data=bos.toByteArray();
            System.out.println("获取成功");
            fis.close();
            bos.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //二进制转换成文件
    public static String set(String fileName,byte[] data){
        File file=new File("D:\\BaiduNetdiskDownload\\qqClientWareHouse\\"+fileName);
        try {
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(data,0,data.length);
            System.out.println("加载图片");
            System.out.println(fos.toString());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "file:"+file.getAbsolutePath();
    }
}
