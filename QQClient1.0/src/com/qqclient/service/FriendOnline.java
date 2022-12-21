package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.User;
import com.qqclient.view.Controller;
import javafx.application.Platform;
import javafx.scene.control.ListView;

import java.util.List;

import static com.qqclient.view.controller.ChatPageController.friend;

public class FriendOnline {
    public static void updateFriendList(Data data) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Local.friendList = data.getFriendList();
                //更新好友列表
                List<User> friendList = data.getFriendList();
                ListView listView = Controller.getChatPageController().getFriendListView();
                listView.getItems().clear();
                friend.clear();
                for (User item : friendList) {
                    friend.add(item);
                }
                listView.setItems(friend);
            }
        });
    }
}
