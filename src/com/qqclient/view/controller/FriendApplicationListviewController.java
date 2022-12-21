package com.qqclient.view.controller;
import com.qqclient.pojo.Message;
import com.qqclient.view.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;

public class FriendApplicationListviewController {
    @FXML
    private ListView<Message> applicatiomListView;

    public ListView<Message> getApplicatiomListView() {
        return applicatiomListView;
    }

    public static ObservableList<Message> friendApplication= FXCollections.observableArrayList();
    public void initialize() {
        Controller.setFriendApplicationListviewController(this);
        //ListView是ObservableList的观察者，ObservableList改变时，ListView会被通知改变
        applicatiomListView.setItems(friendApplication);
        //applicatiomListView.setEditable(true);//使ListView处于可编辑状态
        applicatiomListView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> param) {
                return new ListCell<Message>(){
                    @Override
                    protected void updateItem(Message item, boolean empty) {//自定义单元格
                        super.updateItem(item, empty);
                        if(!empty){
//                            setStyle("-fx-background-color:transparent");
                            HBox hBox=new HBox();
                            FXMLLoader fxmlLoader=new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/com/qqclient/view/fxml/ApplicationMsgItem.fxml"));
                            try {
                                hBox.getChildren().add(fxmlLoader.load());
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("列表加载错误");
                            }
                            ApplicationMsgItemController applicationMsgItemController=fxmlLoader.getController();
                            //hBox.setPadding(new Insets(0,0,0,0));
                            if("1".equals(item.getFlag()))applicationMsgItemController.setFlag("1");
                            //若为加群申请
                            else if("2".equals(item.getFlag())){
                                applicationMsgItemController.setFlag("2");
                                applicationMsgItemController.setGroupMsg(item.getUser());//item.getUser()得到的是群信息
                                applicationMsgItemController.setGroupApplication("申请加入:"+item.getUser().getName());//item.getUser().getName()得到的是群名
                            }
                            //发送的申请人friend的有关信息
                            //发送的申请人friend的有关信息
                            //发送的申请人friend的有关信息
                            applicationMsgItemController.setFriendHeadImage(item.getFriend().getHeadimage());
                            applicationMsgItemController.setFriendName(item.getFriend().getName());
                            applicationMsgItemController.setFriendAccount(item.getFriend().getUserId());
                            applicationMsgItemController.setFriendPersonalizedSignature(item.getFriend().getPersonalizedSignature());
                            this.setGraphic(hBox);//加载到ListView中
                        }
                        else
                        {
                            this.setGraphic(null);
                        }
                    }
                };
            }
        });
    }
}
