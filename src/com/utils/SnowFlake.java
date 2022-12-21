package com.utils;

//雪花算法申城唯一ID
public class SnowFlake {
    private final static long START_TIME=1420041600000L;
    private final  static long SEQUENCE_BIT=12L;
    private final static long WORKER_BIT=5L;
    private final static long DATA_BIT=5L;

    private final static long MAX_SEQUENCE_ID=-1L^(-1L<<SEQUENCE_BIT);
    private final static long MAX_WORKER_ID=-1L^(-1L<<WORKER_BIT);
    private final static long MAX_DATA_ID=-1L^(-1L<<DATA_BIT);

    private final static long WORKER_SHIFT=SEQUENCE_BIT;
    private final static long DATA_SHIFT=WORKER_SHIFT+WORKER_BIT;
    private final static long TIME_STAMP_SHIFT=DATA_SHIFT+DATA_BIT;

    private long dataCenterID;
    private long workderID;
    private long sequenceID=0L;
    private long lastTimeStamp=-1L;

    public SnowFlake(long dataCenterID,long workerID)throws IllegalAccessException{
        if(dataCenterID>MAX_DATA_ID||dataCenterID<0){
            throw new IllegalAccessException("数据ID生产错误");
        }
        if(workerID>MAX_WORKER_ID||workerID<0){
            throw new IllegalAccessException("机器ID生产错误");
        }
        this.dataCenterID=dataCenterID;
        this.workderID=workerID;
    }
    public synchronized long getNextID(){
        long curTimeStamp=System.currentTimeMillis();

        if(curTimeStamp==lastTimeStamp){
            sequenceID=(sequenceID+1)&MAX_SEQUENCE_ID;
            if(sequenceID==0){
                curTimeStamp=getNextMill();
            }
        }else{
            sequenceID=0L;
        }
        lastTimeStamp=curTimeStamp;
        return(((curTimeStamp-START_TIME)<<TIME_STAMP_SHIFT)|
                (dataCenterID<<DATA_SHIFT)|(workderID<<WORKER_SHIFT)|
                sequenceID);
    }
    public long getNextMill(){
        long mill=System.currentTimeMillis();
        while(mill<=lastTimeStamp){
            mill=System.currentTimeMillis();
        }
        return mill;
    }
}