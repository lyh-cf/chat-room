package com.qqclient.service;

import com.qqclient.pojo.*;
import com.qqclient.view.Controller;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import static com.qqclient.view.controller.GroupPersonalInformationController.groupMembers;
public class SetGroupManage {
    public static void setGroupManage(Data data){
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
                    alert.setContentText("设置成功！");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void  setGroupManageFail(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("抱歉");
                alert.setContentText("设置失败，只能对群成员进行设置！");
                alert.showAndWait();
            }
        });
    }
}
