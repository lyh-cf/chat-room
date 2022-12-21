package com.qqclient.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class FriendMsgItemController {
    @FXML
    private Label friendName;
    @FXML
    private ImageView friendHeadImage;

    @FXML
    private TextFlow friendMsg;

    public TextFlow getFriendMsg() {
        return friendMsg;
    }

    public void setFriendMsg(TextFlow friendMsg) {
        this.friendMsg = friendMsg;
    }

    @FXML
    private Label sendTime;
//    public double setContentText(String content){
//        this.friendMsg.setText(content);
//        this.friendMsg.setFont(new Font(13));
//        if(friendMsg.getBoundsInLocal().getWidth()>=400){
//            friendMsg.setWrappingWidth(400);
//        }
//        return  friendMsg.getBoundsInLocal().getHeight();
//    }

    public void setSendTime(String sendTime) {
        this.sendTime.setText(sendTime);
    }

//    public Text getFriendMsg() {
//        return friendMsg;
//    }

    public void setFriendHeadImage(String friendHeadImage) {
        this.friendHeadImage.setImage(new Image(friendHeadImage));
    }

//    public void setFriendMsg(String friendMsg) {
//        this.friendMsg.setText(friendMsg);
//    }

    public void setFriendName(String friendName) {
        this.friendName.setText(friendName);
    }

}
