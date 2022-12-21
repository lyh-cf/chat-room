package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.view.Controller;
import com.qqclient.view.app.ChangeHeadImageAPP;
import com.qqclient.view.app.ChangeMessageAPP;
import com.qqclient.view.controller.ChangeMessageController;
import com.qqclient.view.app.ChatPageAPP;
import com.qqclient.view.controller.ChatPageController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.awt.image.renderable.ContextualRenderedImageFactory;

public class ChangeHeadImage {
    public static void changeHeadImage(Data data){
        User user=(User)data.getDate();
        Local.user=user;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //javaFX不允许将刷新页面和更新控件的操作放在其他线程，只能放在Application线程，得用此方法将其插入到Application线程中
                //更新JavaFX的主线程的代码放在此处
                try {
                    Controller.getChatPageController().getHeadImage().setImage(new Image(user.getHeadimage()));
                    Controller.getChangeMessageController().setHeadImage(user.getHeadimage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
