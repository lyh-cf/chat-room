package com.qqclient.utils;

import com.qqclient.view.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetEmoji {
    public static List<Image>moveEmoji=new ArrayList<>();

    //静态代码块
    static {
        File file=new File("src/com/qqclient/view/emojis");
        for(int i=1;i<=180;i++){
            Image image=new Image("file:"+file.getAbsolutePath()+"\\"+i+".gif");
            moveEmoji.add(image);
        }
    }
    public static ImageView getEmoji(int i){
        ImageView imageView=new ImageView(moveEmoji.get(i-1));
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        imageView.setOnMouseClicked(event -> {
            String s= Controller.getChatPageController().getSendContent().getText();
            s+="<e"+i+">";
            Controller.getChatPageController().getSendContent().setText(s);
        });
        return imageView;
    }
}
