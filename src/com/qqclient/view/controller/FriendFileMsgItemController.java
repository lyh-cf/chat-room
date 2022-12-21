package com.qqclient.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FriendFileMsgItemController {
    @FXML
    private ImageView friendHeadImage;

    @FXML
    private Label friendName;

    @FXML
    private Label sendTime;

    @FXML
    void openFile(ActionEvent event) {

    }
    public void setSendTime(String sendTime) {
        this.sendTime.setText(sendTime);
    }
    public void setFriendHeadImage(String friendHeadImage) {
        this.friendHeadImage.setImage(new Image(friendHeadImage));
    }
    public void setFriendName(String friendName) {
        this.friendName.setText(friendName);
    }
}
