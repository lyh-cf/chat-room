package com.qqclient.view.controller;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.Local;
import com.qqclient.pojo.User;
import com.qqclient.utils.ChooseFile;
import com.qqclient.utils.GetBirthday;
import com.qqclient.view.Controller;
import com.qqclient.view.app.ChangeWordAPP;
import com.qqclient.view.app.QQView;
import com.qqclient.pojo.RequestEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ChangeMessageController implements Initializable {
    private User user= Local.user;
    private Data data=new Data();
    @FXML
    private  ImageView headImage;
    //控件不要用static修饰！
    //控件不要用static修饰！
    //控件不要用static修饰!
    //控件不要用static修饰！
    @FXML
    private Label account;

    @FXML
    private DatePicker birthday;
    @FXML
    private TextField name;

    @FXML
    private RadioButton man;

    @FXML
    private RadioButton girl;

    @FXML
    private Label age;
    @FXML
    private TextField PersonalizedSignature;

    @FXML
    private Label Return;

    public void setHeadImage(String headImage) {
        this.headImage.setImage(new Image(headImage));
    }
    public void setBirthday(String birthday) {
        this.birthday.setValue(LocalDate. parse(birthday));
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setMan() {
        man.setSelected(true);//让它被选中
    }

    public void setGirl() {
        girl.setSelected(true);
    }

    public void setAge(String age) {
        this.age.setText(age);
    }
    public void setPersonalizedSignature(String personalizedSignature) {
        this.PersonalizedSignature.setText(personalizedSignature);
    }
    private ToggleGroup toggleGroup;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.setChangeMessageController(this);
        name.setText(user.getName());
        //age为int型，需要转换一下
        age.setText(String.valueOf(user.getAge()));
        setBirthday(user.getBirthday());
        PersonalizedSignature.setText(user.getPersonalizedSignature());
        //放置头像
        headImage.setImage(new Image(Local.user.getHeadimage()));
        birthday.setDayCellFactory(param -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty||item.compareTo(LocalDate.now())>0);
            }
        });
        //单选按钮组
        toggleGroup = new ToggleGroup();
        man.setToggleGroup(toggleGroup);
        girl.setToggleGroup(toggleGroup);
        if(man.getText().equals(user.getSex())){
            //指定了哪个Radio Button在程序启动时会被选中
            man.setSelected(true);
        }else{
           girl.setSelected(true);
        }
        account.setText(user.getUserId());
    }

    @FXML
    void ChangeMsg(ActionEvent event) throws IOException {
        if(checkName()) {
                data.setMesType(RequestEnum.MESSAGE_ChangeMsg);
                user.setName(name.getText());
                //获取用户所选时间！
                user.setBirthday(birthday.getValue().toString());
                if (man.isSelected()) {
                    user.setSex("男");
                } else {
                    user.setSex("女");
                }
                user.setPersonalizedSignature(PersonalizedSignature.getText());
                //通过生日改年龄
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date bithday = format.parse(birthday.getValue().toString());
                user.setAge(GetBirthday.getAge(bithday));
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //得到ObjectOutputStream对象
                data.setDate(user);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
                objectOutputStream.writeObject(data);//发送data对象
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("警告");
            alert.setContentText("昵称由2~8个字符组成！");
            alert.showAndWait();
        }
    }
    @FXML
    void changeImage(ActionEvent event) throws Exception {
        File file= ChooseFile.chooseFile(Return);
        if(file!=null){
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(QQView.socket.getOutputStream());
            user.setHeadimage(file.toURI().toString());
            data.setMesType(RequestEnum.MESSAGE_ChangeHeadImage);
            data.setDate(user);
            objectOutputStream.writeObject(data);//发送data对象
        }
    }
    public boolean checkName(){
        String names=name.getText();
        //正则表达式检验
        String regStr=".{2,8}";
        boolean matches = Pattern.matches(regStr, names);
        return matches;
    }

    @FXML
    void changeWord(ActionEvent event) throws Exception {
        if(ChangeWordAPP.ChangeWordStage==null||!ChangeWordAPP.ChangeWordStage.isShowing()) new ChangeWordAPP().start(new Stage());
    }
}
