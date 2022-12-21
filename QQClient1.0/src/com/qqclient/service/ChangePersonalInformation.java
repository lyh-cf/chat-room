package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.view.Controller;
import com.qqclient.view.app.ChangeMessageAPP;
import com.qqclient.view.controller.ChangeMessageController;
import com.qqclient.view.app.ChatPageAPP;
import com.qqclient.view.controller.ChatPageController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class ChangePersonalInformation {
    public static void changePersonalInformation(Data data){
        User user=(User) data.getDate();
        Local.user=user;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //更新JavaFX的主线程的代码放在此处
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("恭喜");
                    alert.setContentText("修改成功！");
                    alert.showAndWait();
                      //修改聊天主界面
                      Controller.getChatPageController().getName().setText(user.getName());
                      Controller.getChatPageController().getPersonalizedSignature().setText(user.getPersonalizedSignature());
                      //修改个人信息界面
                      Controller.getChangeMessageController().setBirthday(user.getBirthday());
                      Controller.getChangeMessageController().setAge(String.valueOf(user.getAge()));
                      Controller.getChangeMessageController().setName(user.getName());
                      Controller.getChangeMessageController().setPersonalizedSignature(user.getPersonalizedSignature());
                      if(user.getSex().equals("男")){
                          Controller.getChangeMessageController().setMan();
                      }else{
                          Controller.getChangeMessageController().setGirl();
                      }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
