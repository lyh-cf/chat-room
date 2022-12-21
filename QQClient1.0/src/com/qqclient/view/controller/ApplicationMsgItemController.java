package com.qqclient.view.controller;

import com.qqclient.pojo.*;
import com.qqclient.view.app.QQView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ApplicationMsgItemController {
    Message message=new Message();
    private Data data=new Data();
    @FXML
    private ImageView friendHeadImage;
    @FXML
    private Label friendAccount;
    @FXML
    private Label groupApplication;
    @FXML
    private Label friendName;
    private User groupMsg;//若为群申请，则存对应的群消息,以上是为好友申请时存的发送人的信息
    private String friendHeadImageString;
    private String friendPersonalizedSignature;
    private String flag;//1代表用户与好友，2代表用户与群
    public void setGroupMsg(User groupMsg) {
        this.groupMsg = groupMsg;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public void setGroupApplication(String groupApplication) {
        this.groupApplication.setText(groupApplication);
    }
    public void setFriendAccount(String friendAccount) {
        this.friendAccount.setText(friendAccount);
    }

    public void setFriendHeadImage(String friendHeadImage) {
        friendHeadImageString=friendHeadImage;
        this.friendHeadImage.setImage(new Image(friendHeadImage));
    }
    public void setFriendName(String friendName) {
        this.friendName.setText(friendName);
    }

    public void setFriendPersonalizedSignature(String friendPersonalizedSignature) {
        this.friendPersonalizedSignature = friendPersonalizedSignature;
    }
    @FXML
    void agree(ActionEvent event) throws IOException {
        if("1".equals(flag)) {
            data.setMesType(RequestEnum.MESSAGE_AgreeFriendApplication);
            message.setUser(Local.user);
            message.getFriend().setName(friendName.getText());
            message.getFriend().setUserId(friendAccount.getText());
            message.getFriend().setHeadimage(friendHeadImageString);
            message.getFriend().setPersonalizedSignature(friendPersonalizedSignature);
            message.setFlag(flag);
            data.setDate(message);
        }
        else if("2".equals(flag)){
            data.setMesType(RequestEnum.MESSAGE_AgreeFriendApplication);
            message.getFriend().setUserId(friendAccount.getText());//想加群的用户的id
            message.setUser(groupMsg);//存的是群信息
            //存的是群信息
            //存的是群信息
            message.setTempUserWord(Local.user.getUserId());//用户的账号
            message.setFlag(flag);
            data.setDate(message);
        }
        //得到ObjectOutputStream对象
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
        objectOutputStream.writeObject(data);//发送data对象
    }
    @FXML
    void refuse(ActionEvent event) throws IOException {
        if("1".equals(flag)) {
            message.setUser(Local.user);
            data.setMesType(RequestEnum.MESSAGE_RefuseFriendApplication);
            message.getFriend().setUserId(friendAccount.getText());
            message.setFlag(flag);
            data.setDate(message);
        }
        else if("2".equals(flag)){
            data.setMesType(RequestEnum.MESSAGE_RefuseFriendApplication);
            //存的是群信息
            //存的是群信息
            //存的是群信息
           message.setUser(groupMsg);
           message.setTempUserWord(Local.user.getUserId());
           message.getFriend().setUserId(friendAccount.getText());//想加群的用户的id
           message.setFlag(flag);
           data.setDate(message);
        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
        objectOutputStream.writeObject(data);//发送data对象
    }
}
