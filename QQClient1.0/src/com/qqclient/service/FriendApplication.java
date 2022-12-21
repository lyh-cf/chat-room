package com.qqclient.service;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class FriendApplication {
    public static void sendSucceed(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("恭喜");
                alert.setContentText("申请发送成功！");
                alert.showAndWait();
            }
        });
    }
    public static void sendFail(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("抱歉");
                alert.setContentText("该用户或群已经添加了，不可重复添加！");
                alert.showAndWait();
            }
        });
    }
}
