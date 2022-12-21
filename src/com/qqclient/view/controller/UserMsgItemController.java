package com.qqclient.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMsgItemController {
    @FXML
    private ImageView userHeadImage;

    @FXML
    private TextFlow userMsg;

    @FXML
    private Label sendTime;

    @FXML
    private Label userName;


    //    public double setContentText(String content){
//        this.userMsg.setText(content);
//        this.userMsg.setFont(new Font(13));
//        if(userMsg.getBoundsInLocal().getWidth()>=400){
//            userMsg.setWrappingWidth(400);
//        }
//        return  userMsg.getBoundsInLocal().getHeight();
//    }

    public TextFlow getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(TextFlow userMsg) {
        this.userMsg = userMsg;
    }

    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage.setImage(new Image(userHeadImage));
    }

//    public void setUserMsg(String userMsg) {
//        this.userMsg.setText(userMsg);
//    }

    public void setSendTime(String sendTime) {
        this.sendTime.setText(sendTime);
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

//    public Text getUserMsg() {
//        return userMsg;
//    }
}
