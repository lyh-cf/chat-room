package com.qqclient.view.app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
public class QQView extends Application{
    public static Socket socket;
    static {
        try {
            socket = new Socket("127.0.0.1",2341);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Application.launch(args);//启动程序时，初始化javaFX设置,会自动调用start
    }
    public static Stage ViewStage;
    @Override
    public void start(Stage primaryStage)throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/qqclient/view/fxml/Login.fxml"));//用一个根节点的变量去引用
        Scene scene=new Scene(root,600,400);
        primaryStage.setScene(scene);//将场景添加到stage
        scene.setCursor(new ImageCursor(new Image("/com/qqclient/view/pictures/line-cursor.png")));//设置光标样式,路径不能带中文！！！
        primaryStage.getIcons().add(new Image("/com/qqclient/view/pictures/qq.png"));//设置左上角图标,要选第四个路径
        primaryStage.setTitle("聊天室");//设置标题
        primaryStage.setResizable(false);//设置为不能修改窗口大小
        primaryStage.show();
        ViewStage=primaryStage;
    }
}
