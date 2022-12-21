package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.Message;
import com.qqclient.view.Controller;
import com.qqclient.view.app.AddFriendAPP;
import com.qqclient.view.controller.AddFriendController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.io.IOException;

public class CheckUserID {
    //查账号
    public static void checkUserIDSucceed(Data data){
        Message message=(Message) data.getDate();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if("1".equals(message.getFlag()))Controller.getAddFriendController().setKind("用户");
                else Controller.getAddFriendController().setKind("群");
                Controller.getAddFriendController().setFriendHeadImage(message.getFriend().getHeadimage());
                Controller.getAddFriendController().setFriendName(message.getFriend().getName());
                Controller.getAddFriendController().setFriendPersonalizedSignature(message.getFriend().getPersonalizedSignature());
                Controller.getAddFriendController().setFriendAccountFiled(message.getFriend().getUserId());
            }
        });
    }
    public static void checkUserIDFail(Data data){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Controller.getAddFriendController().setKind("");
                Controller.getAddFriendController().clearFriendHeadImage();
                Controller.getAddFriendController().setFriendName("");
                Controller.getAddFriendController().setFriendPersonalizedSignature("");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("警告");
                alert.setContentText("该用户或群不存在！");
                alert.showAndWait();
            }
        });
    }
}
