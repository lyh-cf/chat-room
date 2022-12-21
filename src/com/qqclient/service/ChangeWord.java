package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.view.app.ChangeWordAPP;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ChangeWord {
    public static void changeWordSucceed(Data data){
        Local.user=(User) data.getDate();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("恭喜");
                alert.setContentText("密码修改成功！");
                alert.showAndWait();
                ChangeWordAPP.ChangeWordStage.close();
            }
        });
    }
    public static void changeWordFail(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("警告");
                alert.setContentText("旧密码错误！");
                alert.showAndWait();
            }
        });
    }
}
