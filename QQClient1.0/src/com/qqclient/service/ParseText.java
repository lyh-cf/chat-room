package com.qqclient.service;

import com.qqclient.utils.GetEmoji;
import com.qqclient.utils.Regular;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;

public class ParseText {
    public static void isEmoji(String text, TextFlow textFlow){
        for(int i=0;i<text.length();i++){
            if(text.charAt(i)=='<'&&i<text.length()-1&&text.charAt(i+1)=='e') {
                int len=i+2;
                while(len<text.length()&&text.charAt(len)!='>')len++;
                if(len>=text.length()){
                    Label label=new Label(text.charAt(i)+"");
                    textFlow.getChildren().add(label);
                    continue;
                }
                String s=text.substring(i+2,len);
                if(Regular.isNumeric(s)&&Integer.parseInt(s)>=1&&Integer.parseInt(s)<=180){
                    ImageView imageView=GetEmoji.getEmoji(Integer.parseInt(s));
                    imageView.setDisable(true);
                    textFlow.getChildren().add(imageView);
                    i=len;
                }else {
                    Label label=new Label(text.charAt(i)+"");
                    textFlow.getChildren().add(label);
                }
            }else{
                Label label=new Label(text.charAt(i)+"");
                textFlow.getChildren().add(label);
            }
        }
    }
}
