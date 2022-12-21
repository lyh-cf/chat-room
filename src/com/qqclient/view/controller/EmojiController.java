package com.qqclient.view.controller;

import com.qqclient.utils.GetEmoji;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class EmojiController implements Initializable {
    @FXML
    private VBox emojiVbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox hBox=new HBox();
        for(int i=1;i<=180;i++){
            hBox.getChildren().add(GetEmoji.getEmoji(i));
            if(i%10==0){
                emojiVbox.getChildren().add(hBox);
                hBox=new HBox();
            }
        }
    }

}
