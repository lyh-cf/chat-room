package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Group;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.view.Controller;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import java.util.List;

import static com.qqclient.view.controller.ChatPageController.friend;

public class ChangeGroupInformation {
    public static void changeGroupInformation(Data data){
        Group group=(Group) data.getDate();
        Local.group=group;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //javaFX不允许将刷新页面和更新控件的操作放在其他线程，只能放在Application线程，得用此方法将其插入到Application线程中
                //更新JavaFX的主线程的代码放在此处
                try {

                    Controller.getGroupPersonalInformationController().getGroupName().setText(group.getName());
                    Controller.getGroupPersonalInformationController().getGroupProfile().setText(group.getGroupProfile());
                    Local.friendList=data.getFriendList();
                    //更新好友列表
                    List<User> friendList=data.getFriendList();
                    ListView listView= Controller.getChatPageController().getFriendListView();
                    listView.getItems().clear();
                    friend.clear();
                    for(User item:friendList){
                        friend.add(item);
                    }
                    listView.setItems(friend);
                    //User user=Controller.getChatPageController().getFriendListView()
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("恭喜");
                    alert.setContentText("修改成功！");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
