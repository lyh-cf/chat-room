package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Message;
import com.qqclient.view.Controller;
import com.qqclient.view.controller.ChatPageController;
import javafx.application.Platform;
import javafx.scene.control.ListView;

import static com.qqclient.view.controller.ChatPageController.messages;

public class SendFriendMsg {
    public static void isUserSend(Data data){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Message message=(Message) data.getDate();
                messages.add(message);
                ListView listView= Controller.getChatPageController().getFriendListView();
                listView.scrollTo(100000);
                System.out.println("我发出了："+message.getMsg().getContent());
                //else if(message.getMsg().getMsgType()==1)System.out.println("我发出了："+message.getMsg().getContent());
            }
        });
    }
    public static void isFriendSend(Data data){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Message message=(Message) data.getDate();
                //若为群聊，要把发送者的账号改成群聊账号
                //若为群聊，要把发送者的账号改成群聊账号
                //若为群聊，要把发送者的账号改成群聊账号
                if(message.getUser().getUserId().equals(ChatPageController.getterId)){
                    messages.add(message);
                    ListView listView= Controller.getChatPageController().getFriendListView();
                    listView.scrollTo(100000);
                    System.out.println("我收到了："+message.getMsg().getContent());
                }
            }
        });
    }
}
