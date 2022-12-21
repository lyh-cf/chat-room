package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.Message;

import com.qqclient.view.app.FriendPersonalInformationAPP;
import javafx.application.Platform;
import javafx.stage.Stage;

public class CheckFriendPersonallnformation {
    public static void checkFriendPersonalInformation(Data data){
        Message message=(Message) data.getDate();
        Local.friend=message.getFriend();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //更新JavaFX的主线程的代码放在此处
                try {
                   new FriendPersonalInformationAPP().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
