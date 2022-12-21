package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.view.app.ChangeMessageAPP;
import com.qqclient.view.controller.ChangeMessageController;
import javafx.application.Platform;
import javafx.stage.Stage;

public class CheckPersonalInformation {
    public static void checkPersonalInformation(Data data){
        User user=(User)data.getDate();
        Local.user=user;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //更新JavaFX的主线程的代码放在此处
                try {
                    new ChangeMessageAPP().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
