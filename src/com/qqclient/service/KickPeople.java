package com.qqclient.service;

import com.qqclient.pojo.*;
import com.qqclient.view.Controller;
import com.qqclient.view.app.CreateGroupAPP;
import com.qqclient.view.app.FriendPersonalInformationAPP;
import com.qqclient.view.app.GroupPersonalInformationAPP;
import com.qqclient.view.controller.ChatPageController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

import static com.qqclient.view.controller.ChatPageController.friend;
import static com.qqclient.view.controller.ChatPageController.messages;
import static com.qqclient.view.controller.GroupPersonalInformationController.groupMembers;

public class KickPeople {
    public static void  kickPeopleFail(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("操作失败");
                alert.setContentText("权限不足！");
                alert.showAndWait();
            }
        });
    }
    public static void  passkickPeople(Data data){
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
                    if(GroupPersonalInformationAPP.groupPersonalInformationStage !=null&&GroupPersonalInformationAPP.groupPersonalInformationStage.isShowing()) GroupPersonalInformationAPP.groupPersonalInformationStage.close();
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
                    alert.setContentText("您已被踢出群聊！");
                    alert.showAndWait();
                }
                System.out.println("被踢出群了");

            }
        });
    }
    public static void kickPeopleSucceed(Data data){
        Group group=(Group) data.getDate();
        Local.group=group;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //更新JavaFX的主线程的代码放在此处
                try {
                    ListView listView= Controller.getGroupPersonalInformationController().getGroupMembersListView();
                    listView.getItems().clear();
                    groupMembers.clear();
                    groupMembers.add(group.getGroupMaster());
                    for(User item:group.getGroupManage()){
                        groupMembers.add(item);
                    }
                    for(User item:group.getGroupMembers()){
                        groupMembers.add(item);
                    }
                    listView.setItems(groupMembers);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("恭喜");
                    alert.setContentText("踢出成功！");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
