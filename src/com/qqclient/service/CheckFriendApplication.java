package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.Message;
import com.qqclient.view.Controller;
import com.qqclient.view.app.FriendApplicationListviewAPP;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

import static com.qqclient.view.controller.FriendApplicationListviewController.friendApplication;
public class CheckFriendApplication {
    public static void checkFriendApplication(Data data){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new FriendApplicationListviewAPP().start(new Stage());
                    ListView listView= Controller.getFriendApplicationListviewController().getApplicatiomListView();
                    List<Message> applicationList=data.getapplicationList();
                    listView.getItems().clear();
                    friendApplication.clear();
                    for(Message item:applicationList){
                        friendApplication.add(item);
                    }
                    listView.setItems(friendApplication);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
