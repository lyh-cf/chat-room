package com.qqclient.view.controller;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.view.app.QQView;
import com.qqclient.pojo.RequestEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

public class ChangeHeadImageController {
    private User user= Local.user;
    private Data data=new Data();
    @FXML
    void Change1(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("操作确认");
        alert.setContentText("您要修改头像吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            user.setHeadimage("/com/qqclient/view/pictures/海绵宝宝.jpg");
            data.setMesType(RequestEnum.MESSAGE_ChangeHeadImage);
            data.setDate(user);
            objectOutputStream.writeObject(data);//发送data对象
        }
    }

    @FXML
    void Change2(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("操作确认");
        alert.setContentText("您要修改头像吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            user.setHeadimage("/com/qqclient/view/pictures/头像2.png");
            data.setMesType(RequestEnum.MESSAGE_ChangeHeadImage);
            data.setDate(user);
            objectOutputStream.writeObject(data);//发送data对象
        }
    }

    @FXML
    void Change3(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("操作确认");
        alert.setContentText("您要修改头像吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            user.setHeadimage("/com/qqclient/view/pictures/头像3.png");
            data.setMesType(RequestEnum.MESSAGE_ChangeHeadImage);
            data.setDate(user);
            objectOutputStream.writeObject(data);//发送data对象
        }
    }

    @FXML
    void Change4(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("操作确认");
        alert.setContentText("您要修改头像吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            user.setHeadimage("/com/qqclient/view/pictures/头像4.png");
            data.setMesType(RequestEnum.MESSAGE_ChangeHeadImage);
            data.setDate(user);
            objectOutputStream.writeObject(data);//发送data对象
        }
    }

    @FXML
    void Change5(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("操作确认");
        alert.setContentText("您要修改头像吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            user.setHeadimage("/com/qqclient/view/pictures/头像5.png");
            data.setMesType(RequestEnum.MESSAGE_ChangeHeadImage);
            data.setDate(user);
            objectOutputStream.writeObject(data);//发送data对象
        }
    }

    @FXML
    void Change6(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("操作确认");
        alert.setContentText("您要修改头像吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            user.setHeadimage("/com/qqclient/view/pictures/头像6.png");
            data.setMesType(RequestEnum.MESSAGE_ChangeHeadImage);
            data.setDate(user);
            objectOutputStream.writeObject(data);//发送data对象
        }
    }

    @FXML
    void Change7(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("操作确认");
        alert.setContentText("您要修改头像吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            user.setHeadimage("/com/qqclient/view/pictures/头像7.png");
            data.setMesType(RequestEnum.MESSAGE_ChangeHeadImage);
            data.setDate(user);
            objectOutputStream.writeObject(data);//发送data对象
        }
    }

    @FXML
    void Change8(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("操作确认");
        alert.setContentText("您要修改头像吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            user.setHeadimage("/com/qqclient/view/pictures/头像8.png");
            data.setMesType(RequestEnum.MESSAGE_ChangeHeadImage);
            data.setDate(user);
            objectOutputStream.writeObject(data);//发送data对象
        }
    }
}
