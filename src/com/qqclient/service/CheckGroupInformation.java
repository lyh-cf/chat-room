package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Group;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.view.app.GroupPersonalInformationAPP;
import javafx.application.Platform;
import javafx.stage.Stage;

public class CheckGroupInformation {
    public static void checkGroupInformation(Data data){
        Group group=(Group) data.getDate();
        Local.group=group;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //更新JavaFX的主线程的代码放在此处
                try {
                     new GroupPersonalInformationAPP().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
