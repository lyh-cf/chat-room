package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.User;
import com.qqclient.view.Controller;
import com.qqclient.view.app.FriendPersonalInformationAPP;
import com.qqclient.view.controller.ChatPageController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.util.List;
import static com.qqclient.view.controller.ChatPageController.friend;
import static com.qqclient.view.controller.ChatPageController.messages;
public class DeleteFriend {
    public static void deleteFriend(Data data){
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
                FriendPersonalInformationAPP.friendPersonalInformationStage.close();
                System.out.println("删除成功");
            }
        });
    }
    public static void deleteFriendPass(Data data){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Message message=(Message)data.getDate();
                Local.friendList=data.getFriendList();
                //更新好友列表
                List<User> friendList=data.getFriendList();
                ListView listView=Controller.getChatPageController().getFriendListView();
                listView.getItems().clear();
                friend.clear();
                for(User item:friendList){
                    friend.add(item);
                }
                listView.setItems(friend);
                //ChatPageController.getterId可能为0，得放括号里面
                if(message.getFriend().getUserId().equals(ChatPageController.getterId)){
                    if(FriendPersonalInformationAPP.friendPersonalInformationStage!=null&&FriendPersonalInformationAPP.friendPersonalInformationStage.isShowing()) FriendPersonalInformationAPP.friendPersonalInformationStage.close();
                    ChatPageController.getterId=null;
                    ChatPageController.flag="0";
                    Label label=Controller.getChatPageController().getFriendName();
                    label.setText("");
                    //清空聊天记录
                    ListView listView1=Controller.getChatPageController().getMessageListView();
                    listView1.getItems().clear();
                    ChatPageController.messages.clear();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("抱歉");
                    alert.setContentText("您已被对方删除！");
                    alert.showAndWait();
                }
                System.out.println("被删除成功");
            }
        });
    }
}
