package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Group;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.User;
import com.qqclient.view.app.CreateGroupAPP;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import static com.qqclient.view.controller.ChatPageController.friend;

public class CreateGroup {
    public static void  addGroup(Data data){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Group group=(Group) data.getDate();
                //把group抽成一个user
                User user=new User();
                user.setName(group.getName());
                user.setUserId(group.getGroupNumber());
                user.setHeadimage("/com/qqclient/view/pictures/群聊(1).png");
                user.setPersonalizedSignature(group.getGroupProfile());
                user.setFlag("2");
                friend.add(user);
                CreateGroupAPP.CreateGroupStage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("创建成功！");
                alert.setContentText("您的群号为:"+user.getUserId());
                alert.showAndWait();
            }
        });
    }
}
