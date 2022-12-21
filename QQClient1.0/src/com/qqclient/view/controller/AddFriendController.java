package com.qqclient.view.controller;
import com.qqclient.pojo.*;
import com.qqclient.view.Controller;
import com.qqclient.view.app.CreateGroupAPP;
import com.qqclient.view.app.QQView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class AddFriendController implements Initializable {
    private  Message message=new Message();//需要实例化！
    private  Data data=new Data();
    @FXML
    private TextField friendAccountFiled;

    @FXML
    private ImageView friendHeadImage;

    @FXML
    private Label friendName;

    @FXML
    private Label friendPersonalizedSignature;

    @FXML
    private Label kind;

    public void setKind(String kind) {
        this.kind.setText(kind);
    }

    public void setFriendAccountFiled(String friendAccountFiled) {
        this.friendAccountFiled.setText(friendAccountFiled);
    }

    public void setFriendHeadImage(String friendHeadImage) {
        this.friendHeadImage.setImage(new Image(friendHeadImage));
    }

    public void setFriendName(String friendName) {
        this.friendName.setText(friendName);
    }

    public void setFriendPersonalizedSignature(String friendPersonalizedSignature) {
        this.friendPersonalizedSignature.setText(friendPersonalizedSignature);
    }
    public void clearFriendHeadImage() {
              Image image=null;
              this.friendHeadImage.setImage(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.setAddFriendController(this);
    }
    @FXML
    void addFriend(ActionEvent event) {
        if(!("".equals(friendAccountFiled.getText()))) {
            if(!Local.user.getUserId().equals(friendAccountFiled.getText())) {
                message.getFriend().setUserId(friendAccountFiled.getText());
                message.setUser(Local.user);
                data.setDate(message);
                data.setMesType(RequestEnum.MESSAGE_FriendApplication);
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                    objectOutputStream.writeObject(data);//发送data对象
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("抱歉");
                alert.setContentText("无法添加自己哦！");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("警告");
            alert.setContentText("账号不能为空！");
            alert.showAndWait();
        }
    }
    @FXML
    void search(ActionEvent event) {
        message.setUser(Local.user);
        message.getFriend().setUserId(friendAccountFiled.getText());
        data.setMesType(RequestEnum.MESSAGE_CheckUserID);
        data.setDate(message);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            objectOutputStream.writeObject(data);//发送data对象
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void creatGroupChat(ActionEvent event) throws Exception {
        if(CreateGroupAPP.CreateGroupStage==null||!CreateGroupAPP.CreateGroupStage.isShowing())new CreateGroupAPP().start(new Stage());
    }
}
