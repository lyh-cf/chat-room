package com.qqclient.service;

import com.qqclient.utils.GetEmoji;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;

import java.util.regex.Pattern;

public class ParseText {
    public static boolean isThree(String s){
        String t="<e\\d{3}>";
        boolean matches = Pattern.matches(t, s);
        return matches;
    }
    public static boolean isTwo(String s){
        String t="<e\\d{2}>";
        boolean matches = Pattern.matches(t, s);
        return matches;
    }
    public static boolean isOne(String s){
        String t="<e\\d>";
        boolean matches = Pattern.matches(t, s);
        return matches;
    }
    public static void isEmoji(String text, TextFlow textFlow){
        int len=text.length();
        for(int i=0;i<len;i++){
            if(i+5<len)  {
                String temp = text.substring(i, i + 6);
                String temp1 = text.substring(i, i + 5);
                String temp2 = text.substring(i, i + 4);
                if (isThree(temp)) {
                    int num = Integer.valueOf(text.substring(i + 2, i + 5));
                    if (num <= 180) {
                        textFlow.getChildren().add(GetEmoji.getEmoji(num));
                        i=i+5;
                    }else textFlow.getChildren().add(new Label(String.valueOf(text.charAt(i))));
                }else if (isTwo(temp1)) {
                    int num1 = Integer.valueOf(text.substring(i + 2, i + 4));
                    i=i+4;
                    textFlow.getChildren().add(GetEmoji.getEmoji(num1));
                } else if (isOne(temp2)) {
                    int num2 = Integer.valueOf(text.charAt(i + 2)-'0');
                    textFlow.getChildren().add(GetEmoji.getEmoji(num2));
                    i=i+3;
                }else textFlow.getChildren().add(new Label(String.valueOf(text.charAt(i))));
            }
           else if(i+4<len){
                String temp1 = text.substring(i, i + 5);
                String temp2 = text.substring(i, i + 4);
                if (isTwo(temp1)) {
                    int num1 = Integer.valueOf(text.substring(i + 2, i + 4));
                    i=i+4;
                    textFlow.getChildren().add(GetEmoji.getEmoji(num1));
                } else if (isOne(temp2)) {
                    int num2 = Integer.valueOf(text.charAt(i + 2)-'0');
                    textFlow.getChildren().add(GetEmoji.getEmoji(num2));
                    i=i+3;
                }
            }
           else if(i+3<len){
                String temp2 = text.substring(i, i + 4);
                if (isOne(temp2)) {
                    int num2 =text.charAt(i + 2)-'0';
                    textFlow.getChildren().add(GetEmoji.getEmoji(num2));
                    i=i+3;
                }else textFlow.getChildren().add(new Label(String.valueOf(text.charAt(i))));
            }
           else textFlow.getChildren().add(new Label(String.valueOf(text.charAt(i))));

        }
    }
}
