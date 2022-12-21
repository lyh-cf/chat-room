package com.qqclient.view.controller;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.view.app.ChatPageAPP;
import com.qqclient.server.ClientConnectServerThread;
import com.qqclient.view.app.LookForWordAPP;
import com.qqclient.timertask.StopChangeWordSend;
import com.qqclient.view.app.QQView;
import com.qqclient.pojo.RequestEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.util.Timer;
import java.util.regex.Pattern;


public class LookingForWordController {
    private Data data=new Data();
    private User user=new User();
    @FXML
    private TextField newpassWordField;
    @FXML
    private TextField Verificationcode;
    @FXML
    private PasswordField passWordFieldAgain;
    @FXML
    private TextField emailfield;
    public static String yanzhengma="6753241654384";
    private Timer timer=new Timer();
    public static int flag=1;//1表示可以发送验证码
    @FXML
    void changeword(ActionEvent event) {
            if(checkpassword()) {
                if (newpassWordField.getText().equals(passWordFieldAgain.getText())) {
                    if(checkemail()) {
                        if (yanzhengma.equals(Verificationcode.getText())) {//默认值在此发挥作用
                            data.setMesType(RequestEnum.MESSAGE_LookingforWord);
                            user.setPasswd(newpassWordField.getText());
                            user.setEmail(emailfield.getText());
                            data.setDate(user);
                            //连接到服务端，发送data对象
                            try {
                                //得到ObjectOutputStream对象
                                ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                                objectOutputStream.writeObject(data);//发送data对象
                                //读取从服务器回复的Message对象
                                ObjectInputStream objectInputStream = new ObjectInputStream(QQView.socket.getInputStream());
                                 data= (Data) objectInputStream.readObject();
                                 user=(User) data.getDate();
                                //修改密码成功
                                if (data.getMesType().equals(RequestEnum.MESSAGE_LookingforWord_SUCCEED)) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("提示");
                                    alert.setHeaderText("恭喜");
                                    alert.setContentText("登录成功！");
                                    alert.showAndWait();
                                    LookForWordAPP.ChangewordStage.close();
                                    QQView.ViewStage.close();
                                    //传递数据
                                    Local.user=user;
                                    Local.friendList=data.getFriendList();
                                    Local.commonPhrases=data.getCommonPhrases();
                                    new ChatPageAPP().start(new Stage());
                                    //创建一个和服务器端保持通信的线程-> 创建一个类
                                    //创建一个和服务器端保持通信的线程-> 创建一个类 ClientConnectServerThread
                                    ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(QQView.socket);
                                    //启动客户端的线程
                                    clientConnectServerThread.start();
                                    //这里为了后面客户端的扩展，我们将线程放入到集合管理
                                    //ManageClientConnectServerThread.addClientConnectServerThread(user.getUserId(), clientConnectServerThread);
                                }else if(data.getMesType().equals(RequestEnum.MESSAGE_LookingforWord_FAIL)){
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("提示");
                                    alert.setHeaderText("警告");
                                    alert.setContentText("该邮箱未被注册！");
                                    alert.showAndWait();
                                }
                                else if(data.getMesType().equals(RequestEnum.MESSAGE_LOGIN_FAIL)){
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("提示");
                                    alert.setHeaderText("警告");
                                    alert.setContentText("该账号在登录中，不可找回密码！");
                                    alert.showAndWait();
                                }
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("提示");
                            alert.setHeaderText("警告");
                            alert.setContentText("验证码输入错误！");
                            alert.showAndWait();
                        }
                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("警告");
                        alert.setContentText("邮箱输入格式错误！");
                        alert.showAndWait();
                    }
                } else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("警告");
                    alert.setContentText("两次密码输入不同！");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("警告");
                alert.setContentText("密码由6~10个数字或字母组成！");
                alert.showAndWait();
            }
    }
    @FXML
    void SendEmail(ActionEvent event) throws GeneralSecurityException {
        if(flag==1) {
            if (checkemail()) {
                user.setEmail(emailfield.getText());
                data.setMesType(RequestEnum.MESSAGE_VerificationCode);
                data.setDate(user);
                //连接到服务端，发送data对象
                try {
                    //得到ObjectOutputStream对象
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                    objectOutputStream.writeObject(data);//发送data对象
                    //读取从服务器回复的Message对象
                    ObjectInputStream objectInputStream = new ObjectInputStream(QQView.socket.getInputStream());
                    data = (Data) objectInputStream.readObject();
                    user = (User) data.getDate();
                    //验证码发送成功
                    if (data.getMesType().equals(RequestEnum.MESSAGE_VerificationCode_SUCCEED)) {
                        yanzhengma = user.getVerificationCode();
                        flag = 0;
                        timer.schedule(new StopChangeWordSend(), 60000);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("恭喜");
                        alert.setContentText("验证码发送成功！");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("警告");
                        alert.setContentText("该邮箱不存在！");
                        alert.showAndWait();
                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("警告");
                alert.setContentText("邮箱输入格式错误！");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("警告，发送失败！");
            alert.setContentText("请一分钟后再点击！");
            alert.showAndWait();
        }
    }
    public boolean checkpassword(){
        String newpassword=newpassWordField.getText();
        String regStr="\\w{6,10}";
        boolean matches = Pattern.matches(regStr, newpassword);
        return matches;
    }
    public boolean checkemail(){
        String email=emailfield.getText();
        //正则表达式检验
        String regStr="^[1-9]{1}[0-9]{4,14}@qq.com$";
        boolean matches = Pattern.matches(regStr, email);
        return matches;
    }
}