package com.qqclient.view.controller;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Group;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.RequestEnum;
import com.qqclient.view.app.QQView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CreateGroupChatController {
    private Group group=new Group();
    private Data data=new Data();
    @FXML
    private TextField groupName;

    @FXML
    private TextArea groupProfile;

    @FXML
    void creat(ActionEvent event) {
       if(checkName()){
           if(checkProfile()){
                group.setGroupMaster(Local.user);
                data.setMesType(RequestEnum.MESSAGE_CreateGroup);
                group.setName(groupName.getText());
                group.setGroupProfile(groupProfile.getText());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
                String s = simpleDateFormat.format(new Date());
                group.setRegisterTime(s);
                data.setDate(group);
               try {
                   ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                   objectOutputStream.writeObject(data);//发送data对象
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }else{
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("提示");
               alert.setHeaderText("警告");
               alert.setContentText("群简介最少10个字符!");
               alert.showAndWait();
           }
       }else{
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("提示");
           alert.setHeaderText("警告");
           alert.setContentText("群名称应在2~10个字符之间!");
           alert.showAndWait();
       }
    }
    public boolean checkName(){
        String names=groupName.getText();
        //正则表达式检验
        String regStr=".{2,10}";
        boolean matches = Pattern.matches(regStr, names);
        return matches;
    }
    public boolean checkProfile(){
        String profile=groupProfile.getText();
        //正则表达式检验
        String regStr="^.{10}.*";
        boolean matches = Pattern.matches(regStr, profile);
        return matches;
    }
}
