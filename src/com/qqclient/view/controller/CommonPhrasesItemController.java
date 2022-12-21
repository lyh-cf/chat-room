package com.qqclient.view.controller;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.RequestEnum;
import com.qqclient.view.app.QQView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

public class CommonPhrasesItemController {
    private Data data=new Data();
    private Message message=new Message();
    @FXML
    private Text commonPhrases;

    public void setCommonPhrases(String commonPhrases) {
        this.commonPhrases.setText(commonPhrases);
    }

    @FXML
    void deleteCommonPhrases(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("操作确认");
        alert.setContentText("您要删除吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            message.setUser(Local.user);
            message.getMsg().setContent(commonPhrases.getText());
            data.setDate(message);
            data.setMesType(RequestEnum.MESSAGE_DeleteCommonPhrases);
            ObjectOutputStream objectOutputStream;
            try {
                objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                objectOutputStream.writeObject(data);//发送data对象
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
