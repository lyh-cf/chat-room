package com.qqclient.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserFileMsgItemController {
    @FXML
    private Label userName;

    @FXML
    private Label sendTime;

    @FXML
    private ImageView userHeadImage;

    @FXML
    void openFile(ActionEvent event) {

    }
    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage.setImage(new Image(userHeadImage));
    }
    public void setSendTime(String sendTime) {
        this.sendTime.setText(sendTime);
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

}
