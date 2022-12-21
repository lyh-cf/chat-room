package com.qqclient.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GroupMembersItemController {
    @FXML
    private ImageView headImage;

    @FXML
    private Label name;

    @FXML
    private Label status;
    @FXML
    private Label account;

    public void setAccount(String account) {
        this.account.setText(account);
    }
    public void setHeadImage(String headImage) {
        this.headImage.setImage(new Image(headImage));
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }
}
