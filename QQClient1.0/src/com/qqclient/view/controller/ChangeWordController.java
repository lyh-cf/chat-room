package com.qqclient.view.controller;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.view.app.QQView;
import com.qqclient.pojo.RequestEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.regex.Pattern;

public class ChangeWordController {
    private User user= Local.user;
    private Data data=new Data();
    @FXML
    private PasswordField oldPassWordField;

    @FXML
    private PasswordField newPassWordField;

    @FXML
    private PasswordField newPassWordFieldAgain;

    @FXML
    void changeWord(ActionEvent event) {
         if(checkpassword()){
             if(newPassWordField.getText().equals(newPassWordFieldAgain.getText())){
                 //得到ObjectOutputStream对象
                 ObjectOutputStream objectOutputStream;
                 try {
                     data.setMesType(RequestEnum.MESSAGE_ChangeWord);
                     user.setPasswd(newPassWordField.getText());
                     user.setOldPassWord(oldPassWordField.getText());
                     data.setDate(user);
                     objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                     objectOutputStream.writeObject(data);//发送data对象
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }else{
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("提示");
                 alert.setHeaderText("警告");
                 alert.setContentText("两次密码输入不同！");
                 alert.showAndWait();
             }
         }else{
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("提示");
             alert.setHeaderText("警告");
             alert.setContentText("新密码由6~10个数字或字母组成！");
             alert.showAndWait();
         }
    }
    public boolean checkpassword(){
        String newpassword=newPassWordField.getText();
        String regStr="\\w{6,10}";
        boolean matches = Pattern.matches(regStr, newpassword);
        return matches;
    }
}
