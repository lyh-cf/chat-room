package com.qqclient.pojo;


import javafx.scene.image.ImageView;

import java.io.Serializable;

public class User implements Serializable {
    // 申明序列化的版本号码
    // 序列化的版本号与反序列化的版本号必须一致才不会出错！
    private static final long serialVersionUID = 2L;
    private String userId;//用户Id/用户名
    private String passwd;//用户密码
    private String email;
    private String sex;
    private String headimage;
    private int age;
    private String birthday;
    private String name;
    private String oldPassWord;//老密码
    private String VerificationCode;//验证码
    private String PersonalizedSignature;//个性签名
    private int state;//用户状态，在线还是不在线
    //private Object image;
    private String flag;//1表示好友，2表示群聊
    private int userStatus;//表示权限
    private  byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
//    public Object getImage() {
//        return image;
//    }
//
//    public void setImage(Object image) {
//        this.image = image;
//    }

    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
    }
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public String getPersonalizedSignature() {
        return PersonalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        PersonalizedSignature = personalizedSignature;
    }
    public String getVerificationCode() {
        return VerificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        VerificationCode = verificationCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadimage() {
        return headimage;
    }
    public void setHeadimage(String headimage) {
        this.headimage = headimage;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
