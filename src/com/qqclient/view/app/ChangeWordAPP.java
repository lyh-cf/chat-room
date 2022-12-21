package com.qqclient.view.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ChangeWordAPP extends Application {
    public static Stage ChangeWordStage;
    public static Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/qqclient/view/fxml/ChangeWord.fxml"));//用一个根节点的变量去引用
        scene=new Scene(root,555,310);
        primaryStage.setScene(scene);//将场景添加到stage
        scene.setCursor(new ImageCursor(new Image("/com/qqclient/view/pictures/line-cursor.png")));//设置光标样式,路径不能带中文！！！
        primaryStage.getIcons().add(new Image("/com/qqclient/view/pictures/qq.png"));//设置左上角图标,要选第四个路径
        primaryStage.setTitle("聊天室");//设置标题
        primaryStage.setResizable(false);//设置为不能修改窗口大小
        primaryStage.show();
        ChangeWordStage=primaryStage;
    }
}
