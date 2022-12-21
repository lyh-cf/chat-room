package com.qqclient.view.controller;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.RequestEnum;
import com.qqclient.view.Controller;
import com.qqclient.view.app.QQView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
public class EditCommonPhrasesController implements Initializable {
    private Data data=new Data();
    private Message message=new Message();
    @FXML
    private ListView<String> commonPhrasesListView;

    public ListView<String> getCommonPhrasesListView() {
        return commonPhrasesListView;
    }

    @FXML
    private TextField commonPhrasesFeild;
    public static ObservableList<String> commonPhrases= FXCollections.observableArrayList();//ListView的数据源
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.setEditCommonPhrasesController(this);
        //ListView是ObservableList的观察者，ObservableList改变时，ListView会被通知改变
        commonPhrasesListView.getItems().clear();
        commonPhrases.clear();
        for(String item: Local.commonPhrases){
            commonPhrases.add(item);
        }
        commonPhrasesListView.setItems(commonPhrases);
        commonPhrasesListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {//单元格工厂
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String>listCell= new ListCell<String>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        //自定义单元格
                        super.updateItem(item, empty);
                        if(!empty){
//                            setStyle("-fx-background-color:transparent");
                            HBox hBox=new HBox();
                            FXMLLoader fxmlLoader=new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/com/qqclient/view/fxml/CommonPhrasesItem.fxml"));
                            try {
                                hBox.getChildren().add(fxmlLoader.load());
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("列表加载错误");
                            }
                            CommonPhrasesItemController commonPhrasesItemController=fxmlLoader.getController();
                            hBox.setPadding(new Insets(0,0,0,0));
                            commonPhrasesItemController.setCommonPhrases(item);
                            this.setGraphic(hBox);//加载到ListView中
                        }
                        else
                        {
                            this.setGraphic(null);
                        }
                    }

                };
                return listCell;
            }
        });
    }

    @FXML
    void addCommonPhrases(ActionEvent event) {
        if(!"".equals(commonPhrasesFeild.getText())){
            message.setUser(Local.user);
            message.getMsg().setContent(commonPhrasesFeild.getText());
            data.setDate(message);
            data.setMesType(RequestEnum.MESSAGE_AddCommonPhrases);
            ObjectOutputStream objectOutputStream;
            try {
                objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                objectOutputStream.writeObject(data);//发送data对象
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("警告");
            alert.setContentText("添加内容不能为空！");
            alert.showAndWait();
        }
    }
}
