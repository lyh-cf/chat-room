package com.qqclient.view.controller;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.view.app.ChatPageAPP;
import com.qqclient.server.ClientConnectServerThread;
import com.qqclient.view.app.EnrollAPP;
import com.qqclient.timertask.StopEnrollSend;
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
import java.util.Timer;
import java.util.regex.Pattern;

public class EnrollController {
    private  Data data=new Data();
    private  User user=new User();
    @FXML
    private TextField passWordField;
    @FXML
    private TextField passWordFieldAgain;
    @FXML
    private TextField Verificationcode;
    @FXML
    private TextField name;
    @FXML
    private TextField emailfield;
    private Timer timer=new Timer();
    public static int flag=1;//1表示可以发送验证码
    public static String yanzhengma="6753241654384";
   @FXML
    void Enroll_(ActionEvent event) {
       if(checkadduser()) {
           if(yanzhengma.equals(Verificationcode.getText())){//默认值在此发挥作用
                data.setMesType(RequestEnum.MESSAGE_Enroll);//先设置消息类型！！！！！！！！！！！！
                user.setPasswd(passWordField.getText());
                user.setEmail(emailfield.getText());
                user.setName(name.getText());
                data.setDate(user);
                //连接到服务端，发送data对象
                try {
                    //得到ObjectOutputStream对象
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                    objectOutputStream.writeObject(data);//发送data对象
                    //读取从服务器回复的Message对象
                    ObjectInputStream objectInputStream = new ObjectInputStream(QQView.socket.getInputStream());
                    data = (Data) objectInputStream.readObject();
                    user=(User)data.getDate();
                    //注册成功
                    if (data.getMesType().equals(RequestEnum.MESSAGE_Enroll_SUCCEED)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("恭喜您注册成功！");
                        alert.setContentText("您的账号为"+user.getUserId());
                        alert.showAndWait();
                        EnrollAPP.EnrollStage.close();
                        QQView.ViewStage.close();
//                        byte[] temp=(byte[])user.getImage();
//                        user.setHeadimage(ChangeImage.set(user.getUserId(),temp));
                        //传递数据
                        Local.user=user;
                        Local.friendList=data.getFriendList();//虽然刚注册没有好友列表，但是需要传实例过来
                        Local.commonPhrases=data.getCommonPhrases();
                        new ChatPageAPP().start(new Stage());
                        //创建一个和服务器端保持通信的线程-> 创建一个类
                        //创建一个和服务器端保持通信的线程-> 创建一个类 ClientConnectServerThread
                        ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(QQView.socket);
                        //启动客户端的线程
                        clientConnectServerThread.start();
                        //这里为了后面客户端的扩展，我们将线程放入到集合管理
                        //ManageClientConnectServerThread.addClientConnectServerThread(user.getUserId(), clientConnectServerThread);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("注册失败");
                        alert.setContentText("该邮箱已被绑定！");
                        alert.showAndWait();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
           }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("警告");
            alert.setContentText("验证码输入错误！");
            alert.showAndWait();
            }
        }
    }
    @FXML
    void SendEmail(ActionEvent event){
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
                   user=(User)data.getDate();
                   //验证码发送成功
                   if (data.getMesType().equals(RequestEnum.MESSAGE_VerificationCode_SUCCEED)) {
                       yanzhengma = user.getVerificationCode();
                       flag=0;
                       timer.schedule(new StopEnrollSend(),60000);
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
    public boolean checkadduser(){
        String password= passWordField.getText();
        String passwordagain=passWordFieldAgain.getText();
        String email=emailfield.getText();
        String names= name.getText();
        // 正则表达式  QQ号5至15位 + @qq.com
        // 第一位1-9  后4-14位0-9 + @qq.com
        String regStr="^[1-9]{1}[0-9]{4,14}@qq.com$";
        String regStr2="\\w{6,10}";
        String regStr3=".{2,8}";
        boolean matches = Pattern.matches(regStr, email);
        boolean matches2 = Pattern.matches(regStr2, password);
        boolean matches3 = Pattern.matches(regStr3, names);
        if(!matches3){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("警告");
            alert.setContentText("昵称由2~8个字符组成！");
            alert.showAndWait();
            return false;
        }
        if(!matches2){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("警告");
            alert.setContentText("密码由6~10个数字或字母组成！");
            alert.showAndWait();
            return false;
        }
        if(!password.equals(passwordagain)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("警告");
            alert.setContentText("两次密码输入不同！");
            alert.showAndWait();
            return false;
        }
        if(!matches){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("警告");
            alert.setContentText("邮箱输入格式错误！");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    public boolean checkemail(){
        String email=emailfield.getText();
        //正则表达式检验
        String regStr="^[1-9]{1}[0-9]{4,14}@qq.com$";
        boolean matches = Pattern.matches(regStr, email);
        return matches;
    }
}
