package com.qqclient.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserImageMsgItemController {
    @FXML
    private Label sendTime;

    @FXML
    private ImageView userHeadImage;

    @FXML
    private Label userName;

    @FXML
    private ImageView userSendImage;

    public Label getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime.setText(sendTime);
    }

    public ImageView getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage.setImage(new Image(userHeadImage));
    }

    public Label getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    public ImageView getUserSendImage() {
        return userSendImage;
    }

    public void setUserSendImage(String userSendImage) {
        this.userSendImage.setImage(new Image(userSendImage));
    }
}
