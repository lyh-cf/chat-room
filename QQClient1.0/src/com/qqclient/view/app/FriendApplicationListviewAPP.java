package com.qqclient.view.app;

import com.qqclient.view.controller.FriendApplicationListviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static javafx.fxml.FXMLLoader.load;

public class FriendApplicationListviewAPP extends Application {
    public static Scene scene;
    public static Stage FriendListviewStage;
    static Parent root;
    @Override
    public void start(Stage primaryStage) throws Exception {
        root = load(FriendApplicationListviewController.class.getResource("/com/qqclient/view/fxml/FriendApplicationListview.fxml"));//用一个根节点的变量去引用
        scene=new Scene(root,661,444);
        primaryStage.setScene(scene);//将场景添加到stage
        scene.setCursor(new ImageCursor(new Image("/com/qqclient/view/pictures/line-cursor.png")));//设置光标样式,路径不能带中文！！！
        primaryStage.getIcons().add(new Image("/com/qqclient/view/pictures/qq.png"));//设置左上角图标,要选第四个路径
        primaryStage.setTitle("聊天室");//设置标题
        primaryStage.setResizable(false);//设置为不能修改窗口大小
        primaryStage.show();
        FriendListviewStage=primaryStage;
    }
}
