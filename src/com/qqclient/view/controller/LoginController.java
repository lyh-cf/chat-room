package com.qqclient.view.controller;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.view.app.ChatPageAPP;
import com.qqclient.server.ClientConnectServerThread;
import com.qqclient.view.app.EnrollAPP;
import com.qqclient.view.app.LookForWordAPP;
import com.qqclient.view.app.QQView;
import com.qqclient.pojo.RequestEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//只解决登录！！！！！
//只解决登录！！！！！
//只解决登录！！！！！
//只解决登录！！！！！
public class LoginController {
    private  Data data=new Data();
    private  User user=new User();
    @FXML
    private TextField userWordField;
    @FXML
    private TextField passWordField;
    @FXML
    public void logIn(ActionEvent event) {
        user.setUserId(userWordField.getText());
        user.setPasswd(passWordField.getText());
        data.setDate(user);
        data.setMesType(RequestEnum.MESSAGE_LOGIN);
        //连接到服务端，发送data对象
        try {
            //得到ObjectOutputStream对象
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            objectOutputStream.writeObject(data);//发送data对象
            //读取从服务器回复的Message对象
            objectOutputStream.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(QQView.socket.getInputStream());
            data = (Data) objectInputStream.readObject();
            //登陆成功
            if (data.getMesType().equals(RequestEnum.MESSAGE_LOGIN_SUCCEED)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("恭喜");
                    alert.setContentText("登录成功！");
                    alert.showAndWait();
                    user=(User)data.getDate();
                    QQView.ViewStage.close();
                    Local.friendList=data.getFriendList();
                    Local.commonPhrases=data.getCommonPhrases();
                    Local.user = user;
                    //传递数据
                    new ChatPageAPP().start(new Stage());
                    //创建一个和服务器端保持通信的线程-> 创建一个类
                    //创建一个和服务器端保持通信的线程-> 创建一个类 ClientConnectServerThread
                    ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(QQView.socket);
                    //启动客户端的线程
                    clientConnectServerThread.start();
                    //这里为了后面客户端的扩展，我们将线程放入到集合管理
                    //ManageClientConnectServerThread.addClientConnectServerThread(user.getUserId(), clientConnectServerThread);
            }
            else if(data.getMesType().equals(RequestEnum.MESSAGE_LOGIN_Msg_FAIL)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("警告");
                alert.setContentText("账号或密码错误！");
                alert.showAndWait();
            }
            else if(data.getMesType().equals(RequestEnum.MESSAGE_LOGIN_FAIL)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("警告");
                alert.setContentText("该账号在登录中，不可重复登录！");
                alert.showAndWait();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    void startnew(ActionEvent event) throws Exception {
        if(EnrollAPP.EnrollStage==null||!EnrollAPP.EnrollStage.isShowing()) new EnrollAPP().start(new Stage());
        else if(EnrollAPP.EnrollStage.isShowing())EnrollAPP.EnrollStage.requestFocus();
    }
    @FXML
    void lookfor(ActionEvent event) throws Exception {
        if(LookForWordAPP.ChangewordStage==null||!LookForWordAPP.ChangewordStage.isShowing()) new LookForWordAPP().start(new Stage());
        else if(LookForWordAPP.ChangewordStage.isShowing())LookForWordAPP.ChangewordStage.requestFocus();
    }
}
