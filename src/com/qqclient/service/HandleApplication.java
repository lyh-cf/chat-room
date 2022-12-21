package com.qqclient.service;
import com.qqclient.pojo.*;
import com.qqclient.view.Controller;
import com.qqclient.view.app.FriendApplicationListviewAPP;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import static com.qqclient.view.controller.ChatPageController.friend;
import static com.qqclient.view.controller.FriendApplicationListviewController.friendApplication;
import java.util.List;

public class HandleApplication {
    public static void reduceApplication(Data data){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                List<Message>applicationList=data.getapplicationList();
                ListView listView= Controller.getFriendApplicationListviewController().getApplicatiomListView();
                listView.getItems().clear();
                friendApplication.clear();
                for(Message item:applicationList){
                    friendApplication.add(item);
                }
                listView.setItems(friendApplication);
                if(data.getMesType().equals(RequestEnum.MESSAGE_AgreeFriendApplication_FAIL)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("警告");
                    alert.setContentText("添加失败，好友不可重复添加！");
                    alert.showAndWait();
                }
                if(data.getMesType().equals(RequestEnum.MESSAGE_RefuseFriendApplication_FAIL)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("警告");
                    alert.setContentText("拒绝失败，该用户已是您的好友！");
                    alert.showAndWait();
                }
            }
        });

    }
    public static void  applicationPass(Data data){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //在好友列表里增加
//                Message message=(Message) data.getDate();
//                message.getFriend().setFlag("1");
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
//                friend.add(message.getFriend());
            }
        });
    }
    public static void  groupApplicationPass(Data data){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //在好友列表里增加
//                Message message=(Message) data.getDate();
//                message.getFriend().setFlag("2");
//                friend.add(message.getFriend());
                Local.friendList=data.getFriendList();
                //更新好友列表
                List<User> friendList=data.getFriendList();
                ListView listView= Controller.getChatPageController().getFriendListView();
                listView.getItems().clear();
                friend.clear();
                for(User item:friendList){
                    friend.add(item);
                }
            }
        });
    }
}
