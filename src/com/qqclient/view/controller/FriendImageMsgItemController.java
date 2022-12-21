package com.qqclient.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FriendImageMsgItemController {
    @FXML
    private Label sendTime;

    @FXML
    private Label friendName;

    @FXML
    private ImageView friendHeadImage;

    @FXML
    private ImageView friendSendImage;

    public Label getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime.setText(sendTime);
    }

    public Label getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName.setText(friendName);
    }

    public ImageView getFriendHeadImage() {
        return friendHeadImage;
    }

    public void setFriendHeadImage(String friendHeadImage) {
        this.friendHeadImage.setImage(new Image(friendHeadImage));
    }

    public ImageView getFriendSendImage() {
        return friendSendImage;
    }

    public void setFriendSendImage(String friendSendImage) {
        this.friendSendImage.setImage(new Image(friendSendImage));
    }
}
