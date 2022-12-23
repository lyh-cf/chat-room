 package com.qqclient.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

 /*
  * 表示客户端和服务端通信时的消息对象
  */
 public class Data<T> implements Serializable {
     private Serializable serializable = 1L;
     private RequestEnum mesType;//消息类型
     //用户数据
     private T date;
     private List<Message> applicationList=new ArrayList<>();
     private List<User>friendList=new ArrayList<>();
     private List<Message> Message=new ArrayList<>();
     private List<String>commonPhrases=new ArrayList<>();
     public List<String> getCommonPhrases() {
         return commonPhrases;
     }

     public void setCommonPhrases(List<String> commonPhrases) {
         this.commonPhrases = commonPhrases;
     }
     public List<com.qqclient.pojo.Message> getMessage() {
         return Message;
     }

     public void setMessage(List<com.qqclient.pojo.Message> message) {
         Message = message;
     }

     public Data() {
     }
     public Data(T date) {
         this.date = date;
     }

     public List<User> getFriendList() {
         return friendList;
     }

     public void setFriendList(List<User> friendList) {
         this.friendList = friendList;
     }


     public List<Message> getapplicationList() {
         return applicationList;
     }

     public void setapplicationList(List<Message> list) {
         this.applicationList = list;
     }
     public RequestEnum getMesType() {
         return mesType;
     }

     public void setMesType(RequestEnum mesType) {
         this.mesType = mesType;
     }
     public T getDate() {
         return date;
     }

     public void setDate(T date) {
         this.date = date;
     }

 }
