package com.qqclient.service;

import com.qqclient.pojo.*;
import com.qqclient.view.Controller;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import java.util.List;

import static com.qqclient.view.controller.ChatPageController.friend;

public class ChangeGroupHeadImage {
    public static void changeHeadImage(Data data){
        Group group=(Group) data.getDate();
        Local.group=group;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //javaFX不允许将刷新页面和更新控件的操作放在其他线程，只能放在Application线程，得用此方法将其插入到Application线程中
                //更新JavaFX的主线程的代码放在此处
                try {
                    System.out.println(group.getGroupLogo());
                    Controller.getGroupPersonalInformationController().getGroupHeadImage().setImage(new Image(group.getGroupLogo()));
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
