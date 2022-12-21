package com.qqclient.view.controller;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.RequestEnum;
import com.qqclient.view.app.QQView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FriendPersonlInformationController implements Initializable {
    private Message message=new Message();
    private Data data=new Data();
    @FXML
    private ImageView friendHeadImage;

    @FXML
    private Label friendAccount;

    @FXML
    private Label friendName;

    @FXML
    private Label friendSex;

    @FXML
    private Label friendAge;

    @FXML
    private Label friendBirthday;

    @FXML
    private Label friendPersonalizedSignature;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        friendName.setText(Local.friend.getName());
        //age为int型，需要转换一下
        friendAge.setText(String.valueOf(Local.friend.getAge()));
        friendBirthday.setText(Local.friend.getBirthday());
        friendPersonalizedSignature.setText(Local.friend.getPersonalizedSignature());
        //放置头像
        friendHeadImage.setImage(new Image(Local.friend.getHeadimage()));
        friendAccount.setText(Local.friend.getUserId());
        friendSex.setText(Local.friend.getSex());
    }
    @FXML
    void deleteFriend(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("操作确认");
        alert.setContentText("您要删除好友吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ObjectOutputStream objectOutputStream ;
            try {
                objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                data.setMesType(RequestEnum.MESSAGE_DeleteFriend);
                message.setUser(Local.user);
                message.getFriend().setUserId(friendAccount.getText());
                data.setDate(message);
                objectOutputStream.writeObject(data);//发送data对象
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
