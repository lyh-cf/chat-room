package com.qqclient.pojo;

import java.io.Serializable;

public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 5L;
    private String content;
    private int msgType;
    private byte[] image;
//    private String flieName;
//
//    public String getFlieName() {
//        return flieName;
//    }
//
//    public void setFlieName(String flieName) {
//        this.flieName = flieName;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
