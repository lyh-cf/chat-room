package com.qqclient.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
    private static final long serialVersionUID = 4L;
    private User user=new User();
    private int userStatus;
    private String name;
    private String groupLogo;
    private String groupNumber;//群编号

    private User groupMaster=new User();//群主
    private String registerTime;//创建时间
    private String groupProfile;//群简介
    private List<User>groupMembers=new ArrayList<>();//普通的群成员
    private List<Message>groupMsg=new ArrayList<>();
    private List<User>groupManage=new ArrayList<>();//群管理
    private User disposePeople=new User();//

    public User getDisposePeople() {
        return disposePeople;
    }

    public void setDisposePeople(User disposePeople) {
        this.disposePeople = disposePeople;
    }

    public String getGroupLogo() {
        return groupLogo;
    }

    public void setGroupLogo(String groupLogo) {
        this.groupLogo = groupLogo;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public List<User> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<User> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public List<Message> getGroupMsg() {
        return groupMsg;
    }

    public void setGroupMsg(List<Message> groupMsg) {
        this.groupMsg = groupMsg;
    }

    public List<User> getGroupManage() {
        return groupManage;
    }

    public void setGroupManage(List<User> groupManage) {
        this.groupManage = groupManage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public User getGroupMaster() {
        return groupMaster;
    }

    public void setGroupMaster(User groupMaster) {
        this.groupMaster = groupMaster;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getGroupProfile() {
        return groupProfile;
    }

    public void setGroupProfile(String groupProfile) {
        this.groupProfile = groupProfile;
    }
}
