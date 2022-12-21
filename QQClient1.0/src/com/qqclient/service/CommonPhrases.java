package com.qqclient.service;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.RequestEnum;
import com.qqclient.view.Controller;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import static com.qqclient.view.controller.EditCommonPhrasesController.commonPhrases;
public class CommonPhrases {
    public static void  updateCommonPhrases(Data data){
        Local.commonPhrases=data.getCommonPhrases();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ListView commonPhrasesListView=Controller.getEditCommonPhrasesController().getCommonPhrasesListView();
                commonPhrasesListView.getItems().clear();
                commonPhrases.clear();
                for(String item:Local.commonPhrases)commonPhrases.add(item);
                commonPhrasesListView.setItems(commonPhrases);
                ChoiceBox choiceBox=Controller.getChatPageController().getCommonPhrases();
                choiceBox.getItems().clear();
                for(String item:Local.commonPhrases)choiceBox.getItems().addAll(item);
                if(data.getMesType().equals(RequestEnum.MESSAGE_AddCommonPhrases)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("恭喜");
                    alert.setContentText("添加成功！");
                    alert.showAndWait();
                }
                else if(data.getMesType().equals(RequestEnum.MESSAGE_DeleteCommonPhrases)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("恭喜");
                    alert.setContentText("删除成功！");
                    alert.showAndWait();
                }
            }
        });
    }
    public static void addCommonPhrasesFail(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("添加失败");
                alert.setContentText("不可添加重复的常用语！");
                alert.showAndWait();
            }
        });
    }
}
