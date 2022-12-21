package com.qqserver;
import com.dao.*;
import com.qqclient.pojo.*;
import com.service.SendCode;
import com.utils.FlieIO;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;

//与客户端保持通信
public class ServerConnectClientThread extends Thread{
    private Socket socket;
    public static ServerConnectClientThread serverConnectClientThread;
    ObjectInputStream objectInputStream=null;
    ObjectOutputStream objectOutputStream=null;
    public ServerConnectClientThread(Socket socket) {
        this.socket = socket;
    }
    public Socket getSocket() {
        return socket;
    }
    public void send(Data data){
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendOther(Data data,String userword){
        try {
            objectOutputStream=new ObjectOutputStream(ManageClientThreads.getServerConnectClientThread(userword).getSocket().getOutputStream());
            objectOutputStream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {//发送/接收消息
        while (true){
            try {
                 objectInputStream=new ObjectInputStream(socket.getInputStream());
                 Data data=(Data) objectInputStream.readObject();
                //根据data的类型，做相应的业务处理
                //用户登录,先检查登录信息是否正确
                //用户登录
                 if(data.getMesType().equals(RequestEnum.MESSAGE_LOGIN)){
                    if(Login.checkLogin(data)){
                        if(Login.checkState(data)) {
                            Login.changeState(data);
                            data=Login.friendList(data);//好友列表信息
                            data=Login.commonPhrases(data);
                            data.setMesType(RequestEnum.MESSAGE_LOGIN_SUCCEED);
                            send(Login.Login(data));
                            for(String item:Login.friendOnlineId){
                                Data data1=new Data();
                                Message message=new Message();
                                message.getUser().setUserId(item);
                                data1.setDate(message);
                                data1.setMesType(RequestEnum.MESSAGE_FriendOnline);
                                sendOther(DeleteFriend.checkFriendList(data1),item);
                            }
                        }else {
                            data.setMesType(RequestEnum.MESSAGE_LOGIN_FAIL);
                            //将data对象回复客户端
                            send(data);
                        }
                    }else {
                        data.setMesType(RequestEnum.MESSAGE_LOGIN_Msg_FAIL);
                        send(data);
                    }
                }
                 //发送验证码
                 else if(data.getMesType().equals(RequestEnum.MESSAGE_VerificationCode)){
                    send(SendCode.checkEmail(data));
                 }
                 //用户注册
                 else if(data.getMesType().equals(RequestEnum.MESSAGE_Enroll)){
                      if(Enroll.checkEnroll(data)){
                          data=Enroll.workID(data);
                          data=Enroll.adduser(data);
                          Login.changeState(data);
                          data=Login.commonPhrases(data);
                          data.setMesType(RequestEnum.MESSAGE_Enroll_SUCCEED);
                          //将data对象回复客户端
                          send(Enroll.checkPersonalInformation(data));
                      }else{
                          data.setMesType(RequestEnum.MESSAGE_Enroll_FAIL);
                          send(data);
                      }
                 }
                 //用户找回密码
                 else if(data.getMesType().equals(RequestEnum.MESSAGE_LookingforWord)){
                    if(LookingForWord.checkEmail(data)) {
                        if(Login.checkLogin(data)) {
                            LookingForWord.change(data);
                            Login.changeState(data);
                            data=Login.friendList(data);//好友列表信息
                            data=Login.commonPhrases(data);
                            data.setMesType(RequestEnum.MESSAGE_LookingforWord_SUCCEED);
                            //将data对象回复客户端
                            send(LookingForWord.checkPersonalInformation(data));
                            for(String item:Login.friendOnlineId){
                                 Data data1=new Data();
                                 Message message=new Message();
                                 message.getUser().setUserId(item);
                                 data1.setDate(message);
                                 data1.setMesType(RequestEnum.MESSAGE_FriendOnline);
                                 sendOther(DeleteFriend.checkFriendList(data1),item);
                            }
                        }else{
                            data.setMesType(RequestEnum.MESSAGE_LOGIN_FAIL);
                            send(data);
                        }
                    }else {
                        data.setMesType(RequestEnum.MESSAGE_LookingforWord_FAIL);
                        //将data对象回复客户端
                        send(data);
                    }
                 }
                //查询个人信息
                 else if(data.getMesType().equals(RequestEnum.MESSAGE_PersonalInformation)){
                     data.setMesType(RequestEnum.MESSAGE_PersonalInformation_SUCCEED);
                     //将data对象回复客户端
                    send(CheckPersonalInformation.CheckPersonalMsg(data));
                 }
                //修改头像
                else if(data.getMesType().equals(RequestEnum.MESSAGE_ChangeHeadImage)){
                     ChangeHeadImage.ChangeHeadImage(data);
                     data.setMesType(RequestEnum.MESSAGE_ChangeHeadImage_SUCCEED);
                    send(data);
                 }
                //修改个人信息
                else if(data.getMesType().equals(RequestEnum.MESSAGE_ChangeMsg)){
                     data.setMesType(RequestEnum.MESSAGE_ChangeMsg_SUCCEED);
                     ChangePersonalInformation.ChangePersonalInformation(data);
                     send(data);
                 }
                //修改密码
                else if(data.getMesType().equals(RequestEnum.MESSAGE_ChangeWord)){
                    if(ChangeWord.checkOldPassword(data)) {
                        data.setMesType(RequestEnum.MESSAGE_ChangeWord_SUCCEED);
                        ChangeWord.changeWord(data);
                        send(data);
                    }else{
                        data.setMesType(RequestEnum.MESSAGE_ChangeWord_FAIL);
                        send(data);
                    }
                }
                //查找用户或群
                else if(data.getMesType().equals(RequestEnum.MESSAGE_CheckUserID)){
                       if(CheckUserId.checkUserId(data)){
                           data=CheckUserId.userMsg(data);
                           data.setMesType(RequestEnum.MESSAGE_CheckUserID_SUCCEED);
                           send(data);
                       }
                       else if(CheckUserId.checkGroupId(data)){
                           data=CheckUserId.groupMsg(data);
                           data.setMesType(RequestEnum.MESSAGE_CheckUserID_SUCCEED);
                           send(data);
                       }
                       else{
                           data.setMesType(RequestEnum.MESSAGE_CheckUserID_FAIL);
                           send(data);
                       }
                 }
                //申请，用户或群
                else if(data.getMesType().equals(RequestEnum.MESSAGE_FriendApplication)){
                    if(CheckUserId.checkUserId(data)) {
                        if (!FriendApplication.checkFriend(data)) {
                            if (!FriendApplication.checkApplication(data)) FriendApplication.addApplication(data);
                            data.setMesType(RequestEnum.MESSAGE_FriendApplication_SUCCEED);
                            send(data);
                        } else {
                            data.setMesType(RequestEnum.MESSAGE_FriendApplication_FAIL);
                            send(data);
                        }
                    }
                    else if(CheckUserId.checkGroupId(data)){
                         if(!FriendApplication.checkGroup(data)){
                             if(!FriendApplication.checkGroupApplication(data))FriendApplication.addGroupApplication(data);
                             data.setMesType(RequestEnum.MESSAGE_FriendApplication_SUCCEED);
                             send(data);
                         }else{
                             data.setMesType(RequestEnum.MESSAGE_FriendApplication_FAIL);
                             send(data);
                         }
                    }
                    else{
                        data.setMesType(RequestEnum.MESSAGE_CheckUserID_FAIL);
                        send(data);
                    }
                }
                //查看加好友和进群申请
                else if(data.getMesType().equals(RequestEnum.MESSAGE_CheckFriendApplication)){
                     data=CheckFriendApplication.checkFriendApplication(data);
                     data.setMesType(RequestEnum.MESSAGE_CheckFriendApplication_SUCCEED);
                     send(data);
                }
                //同意申请
                else if(data.getMesType().equals(RequestEnum.MESSAGE_AgreeFriendApplication)){
                    Message message=(Message)data.getDate();
                    if("1".equals(message.getFlag())) {
                        HandleFriendApplication.reduceApplication(data);
                        if (!FriendApplication.checkFriend(data)) {//查看是否已经是好友
                            HandleFriendApplication.addFriend(data);
                            //重新加载申请列表
                            data = HandleFriendApplication.checkFriendApplication(data);
                            data=DeleteFriend.checkFriendList(data);
                            send(data);
                            Data temp = HandleFriendApplication.agreeFriendApplication(data);//放在send的后面！
                            if (temp.getMesType().equals(RequestEnum.MESSAGE_PassFriendApplication_SUCCEED)) {
                                temp=DeleteFriend.checkFriendList(temp);
                                Message temp2 = (Message) temp.getDate();
                                sendOther(temp, temp2.getUser().getUserId());
                            }
                        } else {
                            data.setMesType(RequestEnum.MESSAGE_AgreeFriendApplication_FAIL);
                            data=HandleFriendApplication.checkFriendApplication(data);
                            send(data);
                        }
                    }
                    else if("2".equals(message.getFlag())){
                        HandleFriendApplication.reduceGroupApplication(data);
                        //if(!FriendApplication.checkGroup(data))这里以后在写邀请好友入群的时候需要加上
                        HandleFriendApplication.addGroupUser(data);
                        data=DeleteFriend.checkFriendList(data);
                        //重新加载申请列表
                        data = HandleFriendApplication.checkFriendApplication(data);
                        data.setMesType(RequestEnum.MESSAGE_AgreeGroupApplication);
                        send(data);
                        Data temp = HandleFriendApplication.agreeFriendApplication(data);//放在send的后面！
                        if (temp.getMesType().equals(RequestEnum.MESSAGE_PassGroupApplication_SUCCEED)) {
                            temp=DeleteFriend.checkFriendList(temp);
                            Message temp2 = (Message) temp.getDate();
                            sendOther(temp, temp2.getUser().getUserId());
                        }
                    }
                }
                //拒绝申请
                else if(data.getMesType().equals(RequestEnum.MESSAGE_RefuseFriendApplication)){
                      Message message=(Message)data.getDate();
                     if("1".equals(message.getFlag())) {
                         HandleFriendApplication.reduceApplication(data);
                         //重新加载好友申请
                         data = HandleFriendApplication.checkFriendApplication(data);
                         if (FriendApplication.checkFriend(data)) data.setMesType(RequestEnum.MESSAGE_RefuseFriendApplication_FAIL);//查看是否已经是好友
                         send(data);
                     }
                     else if("2".equals(message.getFlag())){
                         HandleFriendApplication.reduceGroupApplication(data);
                         data = HandleFriendApplication.checkFriendApplication(data);
                         //if(!FriendApplication.checkGroup(data))这里以后在写邀请好友入群的时候需要加上
                         send(data);
                     }
                }
                //查看好友个人信息申请
                else if(data.getMesType().equals(RequestEnum.MESSAGE_FriendPersonalInformation)){
                     data=CheckFriendPersonalInformation.CheckFriendPersonalMsg(data);
                     data.setMesType(RequestEnum.MESSAGE_FriendPersonalInformation_SUCCEED);
                     send(data);
                }
                //删除好友
                else if(data.getMesType().equals(RequestEnum.MESSAGE_DeleteFriend)){
                    DeleteFriend.deleteFriend(data);
                    data=DeleteFriend.checkFriendList(data);
                    DeleteFriend.deletePrivateMsg(data);
                    data.setMesType(RequestEnum.MESSAGE_DeleteFriend_SUCCEED);
                    send(data);
                    Data temp=DeleteFriend.passDeleteFriend(data);//放在send的后面！
                    if(temp.getMesType().equals(RequestEnum.MESSAGE_PassDeleteFriend_SUCCEED)){
                        Message message=(Message) temp.getDate();
                        sendOther(temp,message.getUser().getUserId());/////////
                    }
                }
                //查看历史消息
                else if(data.getMesType().equals(RequestEnum.MESSAGE_CheckHistoricalMessages)){
                    Message message=(Message) data.getDate();
                    if("1".equals(message.getFlag())) data=CheckHistoricalMessages.CheckFriendHistoricalMessages(data);
                    else if("2".equals(message.getFlag()))data=CheckHistoricalMessages.CheckGroupHistoricalMessages(data);
                     data.setMesType(RequestEnum.MESSAGE_CheckHistoricalMessages_SUCCEED);
                     send(data);
                }
                //发消息
                else if(data.getMesType().equals(RequestEnum.MESSAGE_SendFriendMsg)){
                    Message message=(Message) data.getDate();
                    if("1".equals(message.getFlag())) {//发给好友
                            //由用户Id加时间戳加原本的图片名命名
                            if (message.getMsg().getMsgType()==1){
                                String fileName=message.getUser().getUserId()+"-"+Calendar.getInstance().getTimeInMillis()+"_"+message.getMsg().getContent();
                                FlieIO.setImage(fileName,message.getMsg().getImage());
                            }
                            SendFriendMsg.addMsg(data);
                            data.setMesType(RequestEnum.MESSAGE_SendFriendMsg_SUCCEED);
                            send(data);
                            Data temp = SendFriendMsg.sendMsg(data);
                            if (temp.getMesType().equals(RequestEnum.MESSAGE_SendOnlineFriend_SUCCEED)) {
                                Message message2 = (Message) temp.getDate();
                                //只需加载发送者的有关信息即可
                                sendOther(temp, message2.getFriend().getUserId());
                            }
                    }
                    else if("2".equals(message.getFlag())){//发送给群聊
                        if (message.getMsg().getMsgType()==1){
                            String fileName=message.getUser().getUserId()+"-"+Calendar.getInstance().getTimeInMillis()+"_"+message.getMsg().getContent();
                            FlieIO.setImage(fileName,message.getMsg().getImage());
                        }
                            SendGroupMsg.addMsg(data);
                            data.setMesType(RequestEnum.MESSAGE_SendFriendMsg_SUCCEED);
                            send(data);
                            for (String userId : SendGroupMsg.sendMsg(data)) {
                                //只需加载发送者的有关信息即可
                                //把它设为群聊账号
                                //把它设为群聊账号
                                //把它设为群聊账号
                                message.getUser().setUserId(message.getFriend().getUserId());
                                data.setDate(message);
                                data.setMesType(RequestEnum.MESSAGE_SendOnlineFriend_SUCCEED);
                                sendOther(data, userId);
                            }
                        }
                }
                //创建群聊
                else if(data.getMesType().equals(RequestEnum.MESSAGE_CreateGroup)){
                       data=CreateGroup.workID(data);
                       CreateGroup.addgroup(data);
                       send(data);
                }
                //退出登录
                else if(data.getMesType().equals(RequestEnum.MESSAGE_SignOut)){
                    SignOut.changeState(data);
                    data.setMesType(RequestEnum.MESSAGE_SignOut_SUCCEED);
                    send(data);
                    Login.friendList(data);//好友列表信息
                     for(String item:Login.friendOnlineId){
                         Data data1=new Data();
                         Message message=new Message();
                         message.getUser().setUserId(item);
                         data1.setDate(message);
                         data1.setMesType(RequestEnum.MESSAGE_FriendOffline);
                         sendOther(DeleteFriend.checkFriendList(data1),item);
                     }
                }
                //查看群资料
                else if(data.getMesType().equals(RequestEnum.MESSAGE_CheckGroupInformation)){
                      data=CheckGroupInformation.checkGroupInformation(data);
                      data=CheckGroupInformation.checkGroupMembers(data);
                      send(data);
                 }
                //更改群头像
                else if(data.getMesType().equals(RequestEnum.MESSAGE_ChangeGroupHeadImage)){
                      ChangeGroupHeadImage.changeGroupHeadImage(data);
                      send(ChangeGroupHeadImage.checkFriendList(data));
                 }
                //更改群资料
                else if(data.getMesType().equals(RequestEnum.MESSAGE_ChangeGroupInformation)){
                      ChangeGroupInformation.changeGroupInformation(data);
                      send(ChangeGroupHeadImage.checkFriendList(data));
                 }
                //退出群聊
                else if(data.getMesType().equals(RequestEnum.MESSAGE_QuitGroup)){
                      QuitGroup.deleteFriend(data);
                      send(QuitGroup.checkFriendList(data));
                 }
                //设置管理员
                else if(data.getMesType().equals(RequestEnum.MESSAGE_SetGroupManage)){
                    if(SetGroupManage.checkStatus(data)) {
                        SetGroupManage.setGroupManage(data);
                        data = CheckGroupInformation.checkGroupMembers(data);
                        send(data);
                    }else{
                        data.setMesType(RequestEnum.MESSAGE_SetGroupManage_FAIL);
                        send(data);
                    }
                 }
                //踢人
                else if(data.getMesType().equals(RequestEnum.MESSAGE_KickPeople)){
                     if(KickPeople.compareStatus(data)){
                          KickPeople.deleteGroupMember(data);
                          data=CheckGroupInformation.checkGroupMembers(data);
                          send(data);
                          Data temp=KickPeople.sendMsg(data);
                          if(temp.getMesType().equals(RequestEnum.MESSAGE_PassKickPeople)){
                              Message message=(Message) temp.getDate();
                              System.out.println("被踢出的人的账号:"+message.getUser().getUserId());
                              sendOther(temp,message.getUser().getUserId());
                          }
                     }else{
                         data.setMesType(RequestEnum.MESSAGE_KickPeople_FAIL);
                         send(data);
                     }
                 }
                //解散群
                else if(data.getMesType().equals(RequestEnum.MESSAGE_DisbandGroup)){
                      //先转发给在群里的人
                      Group group=(Group) data.getDate();
                      Message temp=new Message();
                      temp.getFriend().setUserId(group.getGroupNumber());
                      temp.getUser().setUserId(group.getUser().getUserId());
                      data.setDate(temp);
                      List<String> sendToUser=SendGroupMsg.sendMsg(data);
                      data.setDate(group);
                      DisbandGroup.deleteGroupMembers(data);
                     for(String userId:sendToUser){
                         Data data1=new Data();
                         Message message=new Message();
                         message.getUser().setUserId(userId);
                         message.getFriend().setUserId(group.getGroupNumber());
                         data1.setDate(message);
                         data1.setMesType(RequestEnum.MESSAGE_PassDisbandGroup);
                         data1=DeleteFriend.checkFriendList(data1);
                         sendOther(data1,userId);
                     }
                     DisbandGroup.deleteGroup(data);
                     DisbandGroup.deleteGroupMsg(data);
                     send(DisbandGroup.checkFriendList(data));
                 }
                //添加常用语
                else if(data.getMesType().equals(RequestEnum.MESSAGE_AddCommonPhrases)){
                    if(CommonPhrases.checkAgain(data)) {
                        CommonPhrases.addCommonPhrases(data);
                        Message message = (Message) data.getDate();
                        User user = new User();
                        user.setUserId(message.getUser().getUserId());
                        data.setDate(user);
                        data = Login.commonPhrases(data);
                        send(data);
                    }else{
                        data.setMesType(RequestEnum.MESSAGE_AddCommonPhrases_FAIL);
                        send(data);
                    }
                 }
                //删除常用语
                else if(data.getMesType().equals(RequestEnum.MESSAGE_DeleteCommonPhrases)){
                     CommonPhrases.deleteCommonPhrases(data);
                     Message message=(Message)data.getDate();
                     User user=new User();
                     user.setUserId(message.getUser().getUserId());
                     data.setDate(user);
                     data=Login.commonPhrases(data);
                     send(data);
                 }
            }
            catch (Exception e) {
                if(objectInputStream!=null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                if(objectOutputStream!=null){
                    try {
                        objectOutputStream.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.out.println(socket.getRemoteSocketAddress() + "下线了！！！");
                e.printStackTrace();//不要删掉！
                break;
            }
        }
    }
}
