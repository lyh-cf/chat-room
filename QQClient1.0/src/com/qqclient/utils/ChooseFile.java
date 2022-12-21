package com.qqclient.utils;

import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;

public class ChooseFile {
    public static File chooseFile(Label Return){
        FileChooser chooser=new FileChooser();
        chooser.setInitialDirectory(new File("D:\\BaiduNetdiskDownload\\qqHeadImage"));
        chooser.setTitle("选择图片");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
        File file=chooser.showOpenDialog(Return.getScene().getWindow());
        if(file==null){
            System.out.println("未选择");
        }
        else {
            System.out.println("成功选择");
            System.out.println(file.toURI().toString());
        }
        return file;
    }
}
