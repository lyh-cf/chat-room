package com.qqclient.view.app;
import com.qqclient.view.controller.ChatPageController;
import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javafx.fxml.FXMLLoader.load;
public class ChatPageAPP extends Application {
    public static Stage ChatPageStage;
    public static Scene scene;
    static Parent root;
    Double xOffSet;
    Double yOffSet;
    @Override
    public void start(Stage primaryStage) throws Exception {
        root =load(ChatPageController.class.getResource("/com/qqclient/view/fxml/ChatPage.fxml"));//用一个根节点的变量去引用
        //窗口拖动事件
        root.setOnMousePressed(event -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffSet);
            primaryStage.setY(event.getScreenY() - yOffSet);
        });
        scene=new Scene(root,1200,750);
        primaryStage.setScene(scene);//将场景添加到stage
        scene.setCursor(new ImageCursor(new Image("/com/qqclient/view/pictures/line-cursor.png")));//设置光标样式,路径不能带中文！！！
        primaryStage.getIcons().add(new Image("/com/qqclient/view/pictures/qq.png"));//设置左上角图标,要选第四个路径
        primaryStage.setTitle("聊天室");//设置标题
        primaryStage.setResizable(false);//设置为不能修改窗口大小
        primaryStage.initStyle(StageStyle.UNDECORATED); /**设置窗口隐藏**/
        primaryStage.show();
        ChatPageStage=primaryStage;
    }
}
