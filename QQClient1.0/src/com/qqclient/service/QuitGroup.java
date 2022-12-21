package com.qqclient.service;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.view.Controller;
import com.qqclient.view.app.GroupPersonalInformationAPP;
import com.qqclient.view.controller.ChatPageController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

import static com.qqclient.view.controller.ChatPageController.friend;
import static com.qqclient.view.controller.ChatPageController.messages;

public class QuitGroup {
    public static void quitGroup(Data data){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
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
                //清空聊天记录
                ListView listView1=Controller.getChatPageController().getMessageListView();
                listView1.getItems().clear();
                messages.clear();
                Label label=Controller.getChatPageController().getFriendName();
                label.setText("");
                ChatPageController.flag="0";
                ChatPageController.getterId=null;
                GroupPersonalInformationAPP.groupPersonalInformationStage.close();
                System.out.println("退出群聊成功");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("恭喜");
                alert.setContentText("退出成功！");
                alert.showAndWait();
            }
        });
    }
}
