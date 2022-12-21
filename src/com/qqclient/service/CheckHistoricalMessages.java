package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Message;
import com.qqclient.view.Controller;
import com.qqclient.view.app.ChatPageAPP;
import javafx.application.Platform;
import javafx.scene.control.ListView;

import java.util.List;
import static com.qqclient.view.controller.ChatPageController.messages;
public class CheckHistoricalMessages {
    public static void checkFriendHistoricalMessages(Data data){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                List<Message> friendHistoricalMessages=data.getMessage();
                ListView listView= Controller.getChatPageController().getMessageListView();
                listView.getItems().clear();
                messages.clear();
                for(Message item:friendHistoricalMessages){
                    messages.add(item);
                }
                listView.setItems(messages);
                listView.scrollTo(100000);
            }
        });
    }
}
