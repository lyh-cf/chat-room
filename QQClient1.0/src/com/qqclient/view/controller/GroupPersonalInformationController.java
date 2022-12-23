package com.qqclient.view.controller;
import com.qqclient.pojo.*;
import com.qqclient.utils.ChooseFile;
import com.qqclient.view.Controller;
import com.qqclient.view.app.QQView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GroupPersonalInformationController implements Initializable {
    private Data data=new Data();
    private Group group=new Group();
    @FXML
    private ListView<User> groupMembersListView;

    public static ObservableList<User> groupMembers= FXCollections.observableArrayList();//ListView的数据源
    @FXML
    private TextField groupName;

    @FXML
    private Label groupAccount;

    @FXML
    private Label groupPerson;//群人数

    @FXML
    private Label createTime;

    @FXML
    private TextArea groupProfile;

    @FXML
    private Button keepUpdate;

    @FXML
    private Button disbandGroup;

    @FXML
    private Button quitGroup;

    @FXML
    private Button changeGroupHeadImage;
    @FXML
    private ImageView groupHeadImage;

    @FXML
    private Label Return;
    private int status= Local.group.getUserStatus();//该用户在该群的权利
    private int groupPersonNums=1;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.setGroupPersonalInformationController(this);
        groupAccount.setText(Local.group.getGroupNumber());
        createTime.setText(Local.group.getRegisterTime());
        groupName.setText(Local.group.getName());
        groupProfile.setText(Local.group.getGroupProfile());
        groupHeadImage.setImage(new Image(Local.group.getGroupLogo()));
        System.out.println(Local.group.getUser().getName()+"在"+Local.group.getName()+"的权限为"+status);
        if(status==2){
            quitGroup.setVisible(false);
            quitGroup.setDisable(true);
        }
        else if(status==1||status==0){
            disbandGroup.setVisible(false);
            disbandGroup.setDisable(true);
            changeGroupHeadImage.setVisible(false);
            changeGroupHeadImage.setDisable(true);
            keepUpdate.setVisible(false);
            keepUpdate.setDisable(true);
            groupName.setEditable(false);
            groupProfile.setEditable(false);
        }
        groupMembersListView.getItems().clear();
        groupMembers.clear();
        groupMembers.add(Local.group.getGroupMaster());
        for(User item:Local.group.getGroupManage()){
            groupMembers.add(item);
            groupPersonNums++;
        }
        for(User item:Local.group.getGroupMembers()){
            groupMembers.add(item);
            groupPersonNums++;
        }
        groupPerson.setText(String.valueOf(groupPersonNums));
        //ListView是ObservableList的观察者，ObservableList改变时，ListView会被通知改变
        groupMembersListView.setItems(groupMembers);
        //friendListView.setEditable(true);//使ListView处于可编辑状态
        groupMembersListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {//单元格工厂
            @Override
            public ListCell<User> call(ListView<User> param) {
                ContextMenu contextMenu=new ContextMenu();//右键菜单
                ListCell<User> listCell= new ListCell<User>() {
                    @Override
                    protected void updateItem(User item, boolean empty) {//自定义单元格
                        super.updateItem(item, empty);

                        if (!empty) {
//                            setStyle("-fx-background-color:transparent");
                            HBox hBox = new HBox();
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/com/qqclient/view/fxml/GroupMembersItem.fxml"));
                            try {
                                hBox.getChildren().add(fxmlLoader.load());
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("列表加载错误");
                            }
                            //item是数据源里的一个元素，在这里即user
                            GroupMembersItemController groupMembersItemController = fxmlLoader.getController();
                            hBox.setPadding(new Insets(0, 0, 0, 0));
                            groupMembersItemController.setHeadImage(item.getHeadimage());
                            groupMembersItemController.setAccount(item.getUserId());
                            groupMembersItemController.setName(item.getName());
                            if(item.getUserStatus()==0)groupMembersItemController.setStatus("群成员");
                            else if(item.getUserStatus()==1)groupMembersItemController.setStatus("群管理");
                            else if(item.getUserStatus()==2)groupMembersItemController.setStatus("群主");
                            this.setGraphic(hBox);//加载到ListView中
                            setOnMouseClicked(event -> {
                                contextMenu.getItems().clear();//要先清除
                                //设置右键菜单
                                if(status==2){
                                    if(item.getUserStatus()==0) {
                                        MenuItem upItem = new MenuItem("设为管理员");
                                        upItem.setOnAction(events -> {
                                            String disposePeopleId = item.getUserId();
                                            System.out.println("正在点击的人账号:" + disposePeopleId);
                                            group.setUser(Local.user);
                                            group.setGroupNumber(groupAccount.getText());
                                            group.getDisposePeople().setUserId(disposePeopleId);
                                            data.setDate(group);
                                            data.setMesType(RequestEnum.MESSAGE_SetGroupManage);
                                            ObjectOutputStream objectOutputStream;
                                            try {
                                                objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                                                objectOutputStream.writeObject(data);//发送data对象
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        });
                                        contextMenu.getItems().addAll(upItem);
                                    }
                                    if(item.getUserStatus()!=2) {
                                        MenuItem deleteItem = new MenuItem("踢出群聊");
                                        deleteItem.setOnAction(events -> {
                                            String disposePeopleId = item.getUserId();
                                            int disposePeopleStatus = item.getUserStatus();
                                            System.out.println("正在点击的人账号:" + disposePeopleId);
                                            group.setUser(Local.user);
                                            group.setUserStatus(status);
                                            group.setGroupNumber(groupAccount.getText());
                                            group.getDisposePeople().setUserId(disposePeopleId);
                                            group.getDisposePeople().setUserStatus(disposePeopleStatus);
                                            data.setDate(group);
                                            data.setMesType(RequestEnum.MESSAGE_KickPeople);
                                            ObjectOutputStream objectOutputStream;
                                            try {
                                                objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                                                objectOutputStream.writeObject(data);//发送data对象
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        });
                                        contextMenu.getItems().addAll(deleteItem);
                                    }
                                }
                                if(status==1){
                                    if(item.getUserStatus()==0) {
                                        MenuItem deleteItem = new MenuItem("踢出群聊");
                                        deleteItem.setOnAction(events -> {
                                            String disposePeopleId = item.getUserId();
                                            int disposePeopleStatus = item.getUserStatus();
                                            System.out.println("正在点击的人账号:" + disposePeopleId);
                                            group.setUser(Local.user);
                                            group.setUserStatus(status);
                                            group.setGroupNumber(groupAccount.getText());
                                            group.getDisposePeople().setUserId(disposePeopleId);
                                            group.getDisposePeople().setUserStatus(disposePeopleStatus);
                                            data.setDate(group);
                                            data.setMesType(RequestEnum.MESSAGE_KickPeople);
                                            ObjectOutputStream objectOutputStream;
                                            try {
                                                objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                                                objectOutputStream.writeObject(data);//发送data对象
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        });
                                        contextMenu.getItems().addAll(deleteItem);
                                    }
                                }
                            });
                        } else {
                            this.setGraphic(null);
                        }
                    }
                };
                listCell.emptyProperty().addListener((obs,wasEmpty,isNowEmpty)->{
                    if(isNowEmpty){
                        listCell.setContextMenu(null);
                    }else{
                        listCell.setContextMenu(contextMenu);
                    }
                });
                return listCell;
            }
        });
    }
    @FXML
    void quitGroup(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("操作确认");
        alert.setContentText("您要退出群聊吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ObjectOutputStream objectOutputStream ;
            try {
                objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                group.setUser(Local.user);
                group.setGroupNumber(groupAccount.getText());
                data.setDate(group);
                data.setMesType(RequestEnum.MESSAGE_QuitGroup);
                objectOutputStream.writeObject(data);//发送data对象
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void disbandGroup(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("操作确认");
        alert.setContentText("您要解散群聊吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ObjectOutputStream objectOutputStream ;
            try {
                objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                group.setUser(Local.user);
                group.setGroupNumber(groupAccount.getText());
                data.setDate(group);
                data.setMesType(RequestEnum.MESSAGE_DisbandGroup);
                objectOutputStream.writeObject(data);//发送data对象
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @FXML
    void keepUpdate(ActionEvent event) {
        group.setUser(Local.user);
        group.setGroupNumber(groupAccount.getText());
        group.setName(groupName.getText());
        group.setGroupProfile(groupProfile.getText());
        data.setMesType(RequestEnum.MESSAGE_ChangeGroupInformation);
        data.setDate(group);
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            objectOutputStream.writeObject(data);//发送data对象
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void changeGroupHeadImage(ActionEvent event) throws IOException {
        File file= ChooseFile.chooseFile(Return);
        if(file!=null){
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            group.setGroupLogo(file.toURI().toString());
            group.setUser(Local.user);
            group.setGroupNumber(groupAccount.getText());
            data.setMesType(RequestEnum.MESSAGE_ChangeGroupHeadImage);
            data.setDate(group);
            objectOutputStream.writeObject(data);//发送data对象
        }
    }

    public TextField getGroupName() {
        return groupName;
    }

    public ImageView getGroupHeadImage() {
        return groupHeadImage;
    }

    public TextArea getGroupProfile() {
        return groupProfile;
    }

    public ListView<User> getGroupMembersListView() {
        return groupMembersListView;
    }

}
