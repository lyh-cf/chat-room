package com.qqclient.view.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FriendListItemController {
    @FXML
    private ImageView friendHeadImage;

    @FXML
    private Label friendName;
    @FXML
    private Label friendState;

    @FXML
    private Label friendPersonalizedSignature;
    public void setFriendHeadImage(String friendHeadImage) {
        this.friendHeadImage.setImage(new Image(friendHeadImage));
    }

    public void setFriendName(String friendName) {
        this.friendName.setText(friendName);
    }

    public void setFriendPersonalizedSignature(String friendPersonalizedSignature) {
        this.friendPersonalizedSignature.setText(friendPersonalizedSignature);
    }
    public void setFriendState(String friendState) {
        this.friendState.setText(friendState);
    }

}
