package com.qqclient.pojo;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 3L;
    private User user=new User();
    private User friend=new User();
    private MessageEntity msg=new MessageEntity();

    public MessageEntity getMsg() {
        return msg;
    }

    public void setMsg(MessageEntity msg) {
        this.msg = msg;
    }
    private String time;
    private String flag="0";//1代表用户与好友，2代表用户与群
    private String tempUserWord;//必要时暂时存用户账号

    public String getTempUserWord() {
        return tempUserWord;
    }

    public void setTempUserWord(String tempUserWord) {
        this.tempUserWord = tempUserWord;
    }
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
//进行扩展 和文件相关的成员
//     private byte[] fileBytes;
//     private int fileLen = 0;
//     private String dest; //将文件传输到哪里
//     private String src; //源文件路径
}
