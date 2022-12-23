package com.qqclient.view.controller;
import com.qqclient.pojo.*;
import com.qqclient.service.ParseText;
import com.qqclient.timertask.ClearSendContent;
import com.qqclient.utils.FlieIO;
import com.qqclient.utils.ChooseFile;
import com.qqclient.view.Controller;
import com.qqclient.view.app.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class ChatPageController implements Initializable {
    private  User user=Local.user;
    private  Data data=new Data();
    private Message message=new Message();//需要实例化！！！！！！！！
    private Group group=new Group();
    public static String getterId=null;
    public static String flag="0";
    public static User items;
    @FXML
    private Label name;
    @FXML
    private Label friendName;
    @FXML
    private Label PersonalizedSignature;
    @FXML
    private ImageView headImage;
    @FXML
    private TextArea sendContent;
    @FXML
    private ListView<Message> messageListView;
    @FXML
    private ListView<User> friendListView;
    @FXML
    private Label Return;
    @FXML
    private ImageView emoji;
    public static Popup popup=new Popup();
    @FXML
    void openEmoji(MouseEvent event) {
        if(!popup.isShowing()){
            popup.setX(event.getScreenX()+10);
            popup.setY(event.getScreenY()-210);
            popup.show(emoji.getScene().getWindow());
        }
    }
    @FXML
    private ChoiceBox<String> commonPhrases;
    //private List<User> friendList;
    public static ObservableList<User> friend= FXCollections.observableArrayList();//ListView的数据源
    public static ObservableList<Message> messages= FXCollections.observableArrayList();//ListView的数据源
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.setChatPageController(this);
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/qqclient/view/fxml/Emoji.fxml"));
        Parent root= null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        popup.getContent().add(root);
        popup.setAutoHide(true);
         commonPhrases.getItems().clear();
         for(String temp:Local.commonPhrases) commonPhrases.getItems().addAll(temp);
         commonPhrases.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
             @Override
             public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if(getterId!=null) {
                     String str = sendContent.getText();
                     if (!"".equals(str)) {
                         sendContent.setText(str + newValue);
                     } else {
                         sendContent.setText(newValue);
                     }
                 }
             }
         });
         //设置文本输入框
         name.setText(user.getName());
         PersonalizedSignature.setText(user.getPersonalizedSignature());
         //放置头像
         headImage.setImage(new Image(user.getHeadimage()));
         friendListView.getItems().clear();
         friend.clear();
        for(User item:Local.friendList){
            friend.add(item);
        }
        //ListView是ObservableList的观察者，ObservableList改变时，ListView会被通知改变
        friendListView.setItems(friend);
        //friendListView.setEditable(true);//使ListView处于可编辑状态
        friendListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {//单元格工厂
            @Override
            public ListCell<User> call(ListView<User> param) {
                ListCell<User>listCell= new ListCell<User>(){
                    @Override
                    protected void updateItem(User item, boolean empty) {
                        //自定义单元格
                        super.updateItem(item, empty);
                        if(!empty){
//                            setStyle("-fx-background-color:transparent");
                            HBox hBox=new HBox();
                            FXMLLoader fxmlLoader=new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/com/qqclient/view/fxml/FriendListItem.fxml"));
                            try {
                                hBox.getChildren().add(fxmlLoader.load());
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("列表加载错误");
                            }
                            //item是数据源里的一个元素，在这里即user
                            FriendListItemController friendListItemController=fxmlLoader.getController();
                            hBox.setPadding(new Insets(0,0,0,0));
                            //若为好友则加载的是好友的相关信息，若为群则加载的是群的相关信息
                            //若为好友则加载的是好友的相关信息，若为群则加载的是群的相关信息
                            //若为好友则加载的是好友的相关信息，若为群则加载的是群的相关信息
                            friendListItemController.setFriendHeadImage(item.getHeadimage());
                            friendListItemController.setFriendName(item.getName());
                            friendListItemController.setFriendPersonalizedSignature(item.getPersonalizedSignature());
                            //friendListItemController
                            if(item.getFlag().equals("1")) {
                                if (item.getState() == 1) friendListItemController.setFriendState("在线");
                                else if (item.getState() == 0) friendListItemController.setFriendState("离线");
                            }
                            this.setGraphic(hBox);//加载到ListView中
                            setOnMouseClicked(event -> {
                                if(!item.getUserId().equals(getterId)) {
                                    //每一个单元格都需要为其设置一个flag,来表明是好友还是群聊
                                    //每一个单元格都需要为其设置一个flag,来表明是好友还是群聊
                                    //每一个单元格都需要为其设置一个flag,来表明是好友还是群聊
                                    items=item;
                                    getterId = item.getUserId();
                                    friendName.setText(item.getName());
                                    System.out.println("你点击了" + item.getName());
                                    message.setUser(Local.user);
                                    message.getFriend().setUserId(item.getUserId());
                                    data.setMesType(RequestEnum.MESSAGE_CheckHistoricalMessages);
                                    if("1".equals(item.getFlag())) {
                                        message.setFlag("1");
                                        flag="1";
                                    } else{
                                        message.setFlag("2");
                                        flag="2";
                                    }
                                    data.setDate(message);
                                    try {
                                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                                        objectOutputStream.writeObject(data);
                                        TimeUnit.MILLISECONDS.sleep(50);//毫秒
                                    } catch (IOException | InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
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
        messageListView.setItems(messages);
        //messageListView.setEditable(true);
        messageListView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {//单元格工厂
            @Override
            public ListCell<Message> call(ListView<Message> param) {
                return new ListCell<Message>(){
                    @Override
                    protected void updateItem(Message item, boolean empty) {//自定义单元格
                        super.updateItem(item, empty);
                        //以消息发送者的账号来确定加载哪个fxml文件
                        //以消息发送者的账号来确定加载哪个fxml文件
                        //以消息发送者的账号来确定加载哪个fxml文件
                        if(!empty&&Objects.equals(item.getUser().getUserId(), Local.user.getUserId())){//用户的消息
//                            setStyle("-fx-background-color:transparent");
                            if(item.getMsg().getMsgType()==0) {
                                HBox hBox = new HBox();
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("/com/qqclient/view/fxml/UserMsgItem.fxml"));
                                try {
                                    hBox.getChildren().add(fxmlLoader.load());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println("用户消息加载错误");
                                }
                                //item是数据源里的一个元素，在这里即user
                                UserMsgItemController userMsgItemController = fxmlLoader.getController();
                                //hBox.setPrefHeight(userMsgItemController.setContentText(item.getMsg().getContent()) - 22 + 70);
                                userMsgItemController.setUserName(item.getUser().getName());
                                userMsgItemController.setUserHeadImage(item.getUser().getHeadimage());
                                userMsgItemController.setSendTime(item.getTime());
                                ParseText.isEmoji(item.getMsg().getContent(),userMsgItemController.getUserMsg());
                                this.setGraphic(hBox);//加载到ListView中
                            }
                            else if(item.getMsg().getMsgType()==1){
                                HBox hBox = new HBox();
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("/com/qqclient/view/fxml/UserImageMsgItem.fxml"));
                                try {
                                    hBox.getChildren().add(fxmlLoader.load());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println("用户图片加载错误");
                                }
                                //item是数据源里的一个元素，在这里即user
                                String image=FlieIO.set(item.getMsg().getContent(),item.getMsg().getImage());
                                UserImageMsgItemController userImageMsgItemController = fxmlLoader.getController();
                                userImageMsgItemController.setUserName(item.getUser().getName());
                                userImageMsgItemController.setUserHeadImage(item.getUser().getHeadimage());
                                userImageMsgItemController.setSendTime(item.getTime());
                                userImageMsgItemController.setUserSendImage(image);
                                //userImageMsgItemController
                                this.setGraphic(hBox);//加载到ListView中
                            }
                        }
                        else if(!empty&& !Objects.equals(item.getUser().getUserId(), Local.user.getUserId())){//好友的消息
//                            setStyle("-fx-background-color:transparent");
                            if(item.getMsg().getMsgType()==0) {
                                HBox hBox = new HBox();
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("/com/qqclient/view/fxml/FriendMsgItem.fxml"));
                                try {
                                    hBox.getChildren().add(fxmlLoader.load());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println("好友消息加载错误");
                                }
                                //item是数据源里的一个元素，在这里即user
                                FriendMsgItemController friendMsgItemController = fxmlLoader.getController();
                                //hBox.setPrefHeight(friendMsgItemController.setContentText(item.getMsg().getContent()) - 22 + 70);
                                friendMsgItemController.setFriendName(item.getUser().getName());
                                friendMsgItemController.setFriendHeadImage(item.getUser().getHeadimage());
                                friendMsgItemController.setSendTime(item.getTime());
                                ParseText.isEmoji(item.getMsg().getContent(),friendMsgItemController.getFriendMsg());
                                this.setGraphic(hBox);//加载到ListView中
                            }
                            else if(item.getMsg().getMsgType()==1){
                                HBox hBox = new HBox();
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("/com/qqclient/view/fxml/FriendImageMsgItem.fxml"));
                                try {
                                    hBox.getChildren().add(fxmlLoader.load());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println("好友图片加载错误");
                                }
                                //item是数据源里的一个元素，在这里即user
                                String image=FlieIO.set(item.getMsg().getContent(),item.getMsg().getImage());
                                FriendImageMsgItemController friendImageMsgItemController = fxmlLoader.getController();
                                friendImageMsgItemController.setFriendName(item.getUser().getName());
                                friendImageMsgItemController.setFriendHeadImage(item.getUser().getHeadimage());
                                friendImageMsgItemController.setSendTime(item.getTime());
                                friendImageMsgItemController.setFriendSendImage(image);
                                //userImageMsgItemController
                                this.setGraphic(hBox);//加载到ListView中
                            }
                        }
                        else
                        {
                            this.setGraphic(null);
                        }
                    }
                };
            }
        });
    }
    @FXML
    void addFriend(MouseEvent event) throws Exception {
        if(AddFriendAPP.AddFriendStage==null||!AddFriendAPP.AddFriendStage.isShowing()) new AddFriendAPP().start(new Stage());
        else if(AddFriendAPP.AddFriendStage.isShowing())AddFriendAPP.AddFriendStage.requestFocus();
    }
    @FXML
    void ChangeMsg(MouseEvent event)  {
        if(ChangeMessageAPP.ChangeMessageStage==null||!ChangeMessageAPP.ChangeMessageStage.isShowing()) {
            //得到ObjectOutputStream对象
            ObjectOutputStream objectOutputStream;
            try {
                objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                data.setMesType(RequestEnum.MESSAGE_PersonalInformation);
                data.setDate(user);
                objectOutputStream.writeObject(data);//发送data对象
            } catch (IOException e) {
                e.printStackTrace();
            }
            //id就不用设置了，因为之前设置好了，一般操作也不改变id
        }
        else if(ChangeMessageAPP.ChangeMessageStage.isShowing())ChangeMessageAPP.ChangeMessageStage.requestFocus();
    }
    @FXML
    void checkApplication(MouseEvent event)  {
        if(FriendApplicationListviewAPP.FriendListviewStage==null||!FriendApplicationListviewAPP.FriendListviewStage.isShowing()) {
            try {

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                data.setMesType(RequestEnum.MESSAGE_CheckFriendApplication);
                data.setDate(user);
                objectOutputStream.writeObject(data);//发送data对象
                TimeUnit.MILLISECONDS.sleep(50);//毫秒
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(FriendApplicationListviewAPP.FriendListviewStage.isShowing())FriendApplicationListviewAPP.FriendListviewStage.requestFocus();
    }
    @FXML
    void friendPersonalInformation(MouseEvent event) {
        if("1".equals(flag)) {
            if (FriendPersonalInformationAPP.friendPersonalInformationStage == null || !FriendPersonalInformationAPP.friendPersonalInformationStage.isShowing()) {
                if (getterId != null) {
                    message.setUser(Local.user);
                    message.getFriend().setUserId(getterId);
                    //得到ObjectOutputStream对象
                    ObjectOutputStream objectOutputStream;
                    try {
                        objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                        data.setMesType(RequestEnum.MESSAGE_FriendPersonalInformation);
                        data.setDate(message);
                        objectOutputStream.writeObject(data);//发送data对象
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (FriendPersonalInformationAPP.friendPersonalInformationStage.isShowing())
                FriendPersonalInformationAPP.friendPersonalInformationStage.requestFocus();
        }
        else if("2".equals(flag)){
            if(GroupPersonalInformationAPP.groupPersonalInformationStage==null||!GroupPersonalInformationAPP.groupPersonalInformationStage.isShowing()){
                if (getterId != null) {
                     group.setUser(Local.user);
                     group.setGroupNumber(getterId);
                    ObjectOutputStream objectOutputStream;
                    try {
                        objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                        data.setMesType(RequestEnum.MESSAGE_CheckGroupInformation);
                        data.setDate(group);
                        objectOutputStream.writeObject(data);//发送data对象
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else if(GroupPersonalInformationAPP.groupPersonalInformationStage.isShowing())
                GroupPersonalInformationAPP.groupPersonalInformationStage.requestFocus();
        }
    }
    @FXML
    void SendMsg(ActionEvent event) {
        send();
        Timer timer=new Timer();
        //要开个线程推迟一会清除输入框的内容
        timer.schedule(new ClearSendContent(),30);
    }
    @FXML
    void sendFile(MouseEvent event) {
        File file=ChooseFile.chooseFile(Return);

    }

    public void send(){
        if(getterId!=null) {
            if (!sendContent.getText().equals("")) {
                if(flag.equals("1"))message.setFlag("1");
                else if(flag.equals("2"))message.setFlag("2");
                message.setUser(Local.user);
                message.getFriend().setUserId(getterId);
                //得到ObjectOutputStream对象
                ObjectOutputStream objectOutputStream;
                try {
                    objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                    data.setMesType(RequestEnum.MESSAGE_SendFriendMsg);
                    message.getMsg().setMsgType(0);
                    message.getMsg().setContent(sendContent.getText());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String s = simpleDateFormat.format(new Date());
                    message.setTime(s);
                    data.setDate(message);
                    objectOutputStream.writeObject(data);//发送data对象
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("警告");
                alert.setContentText("发送内容不能为空！");
                alert.showAndWait();
            }
        }
    }
    @FXML
    void sendImage(MouseEvent event) {
        if(getterId!=null) {
            File file = ChooseFile.chooseFile(Return);
            if (file != null) {
                if(flag.equals("1"))message.setFlag("1");
                else if(flag.equals("2"))message.setFlag("2");
                message.setUser(Local.user);
                message.getFriend().setUserId(getterId);
                //得到ObjectOutputStream对象
                ObjectOutputStream objectOutputStream;
                try {
                    objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                    data.setMesType(RequestEnum.MESSAGE_SendFriendMsg);
                    message.getMsg().setMsgType(1);
                    message.getMsg().setImage(FlieIO.get(file));
                    message.getMsg().setContent(file.getName());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String s = simpleDateFormat.format(new Date());
                    message.setTime(s);
                    data.setDate(message);
                    objectOutputStream.writeObject(data);//发送data对象
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @FXML
    void close(MouseEvent event) {
       data.setDate(user);
       data.setMesType(RequestEnum.MESSAGE_SignOut);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            objectOutputStream.writeObject(data);//发送data对象
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editCommonPhrases(MouseEvent event) throws Exception {
        if(EditCommonPhrasesAPP.EditCommonPhrasesStage==null||!EditCommonPhrasesAPP.EditCommonPhrasesStage.isShowing())new EditCommonPhrasesAPP().start(new Stage());
    }

//    @FXML
//    void getCommonPhrases(MouseEvent event) {
//
//    }
    @FXML
    void enterListen(KeyEvent event) {
         if(event.getCode()== KeyCode.ENTER){
             send();
             Timer timer=new Timer();
             timer.schedule(new ClearSendContent(),20);
         }
    }

    @FXML
    void mim(MouseEvent event) {
       ChatPageAPP.ChatPageStage.setIconified(true);
    }
    public ImageView getHeadImage() {
        return headImage;
    }

    public Label getName() {
        return name;
    }

    public Label getPersonalizedSignature() {
        return PersonalizedSignature;
    }
    public ListView<Message> getMessageListView() {
        return messageListView;
    }

    public ListView<User> getFriendListView() {
        return friendListView;
    }

    public TextArea getSendContent() {
        return sendContent;
    }

    public Label getFriendName() {
        return friendName;
    }

    public ChoiceBox<String> getCommonPhrases() {
        return commonPhrases;
    }


}
