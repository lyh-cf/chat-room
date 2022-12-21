package com.qqclient.server;
import com.qqclient.pojo.Data;
import com.qqclient.service.*;
import com.qqclient.pojo.RequestEnum;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    //该线程需要持有Socket
    private Socket socket;
    //构造器可以接受一个Socket对象
    private Data data=new Data();
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        //因为Thread需要在后台和服务器通信，因此我们while循环
        while (true) {
            ObjectInputStream objectInputStream = null;
            try {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有发送Data对象,线程会阻塞在这里
                data = (Data) objectInputStream.readObject();
                //注意，后面我们需要去使用data
                //判断这个data类型，然后做相应的业务处理
                //查询个人信息
                if(data.getMesType().equals(RequestEnum.MESSAGE_PersonalInformation_SUCCEED)) {
                    CheckPersonalInformation.checkPersonalInformation(data);
                }
                //修改个人头像
                else if(data.getMesType().equals(RequestEnum.MESSAGE_ChangeHeadImage_SUCCEED)){
                    ChangeHeadImage.changeHeadImage(data);
                }
                //修改个人信息
                else if(data.getMesType().equals(RequestEnum.MESSAGE_ChangeMsg_SUCCEED)){
                    ChangePersonalInformation.changePersonalInformation(data);
                }
                //搜索
                else if(data.getMesType().equals(RequestEnum.MESSAGE_CheckUserID_SUCCEED)){
                    CheckUserID.checkUserIDSucceed(data);
                }
                else if(data.getMesType().equals(RequestEnum.MESSAGE_CheckUserID_FAIL)){
                    CheckUserID.checkUserIDFail(data);
                }
                //修改密码
                else if(data.getMesType().equals(RequestEnum.MESSAGE_ChangeWord_SUCCEED)){
                    ChangeWord.changeWordSucceed(data);
                }
                else if(data.getMesType().equals(RequestEnum.MESSAGE_ChangeWord_FAIL)){
                    ChangeWord.changeWordFail();
                }
                //好友申请
                else if(data.getMesType().equals(RequestEnum.MESSAGE_FriendApplication_SUCCEED)){
                    FriendApplication.sendSucceed();
                }
                else if(data.getMesType().equals(RequestEnum.MESSAGE_FriendApplication_FAIL)){
                    FriendApplication.sendFail();
                }
                //查看好友申请
                else if(data.getMesType().equals(RequestEnum.MESSAGE_CheckFriendApplication_SUCCEED)){
                    CheckFriendApplication.checkFriendApplication(data);
                }
                //退出登录
                else if(data.getMesType().equals(RequestEnum.MESSAGE_SignOut_SUCCEED)){
                      SignOut.signOut();
                }
                //处理好友申请
                else if(data.getMesType().equals(RequestEnum.MESSAGE_AgreeFriendApplication)){
                    HandleApplication.reduceApplication(data);
                    HandleApplication.applicationPass(data);
                }
                else if(data.getMesType().equals(RequestEnum.MESSAGE_AgreeFriendApplication_FAIL)){
                    HandleApplication.reduceApplication(data);
                }
                else if(data.getMesType().equals(RequestEnum.MESSAGE_RefuseFriendApplication)){
                    HandleApplication.reduceApplication(data);
                }
                else if(data.getMesType().equals(RequestEnum.MESSAGE_RefuseFriendApplication_FAIL)){
                    HandleApplication.reduceApplication(data);
                }
                //对方同意了好友申请
                else if(data.getMesType().equals(RequestEnum.MESSAGE_PassFriendApplication_SUCCEED)){
                    HandleApplication.applicationPass(data);
                }
                //同意入群申请
                else if(data.getMesType().equals(RequestEnum.MESSAGE_AgreeGroupApplication)){
                    HandleApplication.reduceApplication(data);
                }
                //被对方同意入群申请
                else if(data.getMesType().equals(RequestEnum.MESSAGE_PassGroupApplication_SUCCEED)){
                    HandleApplication.groupApplicationPass(data);
                }
                //查看好友信息
                else if(data.getMesType().equals(RequestEnum.MESSAGE_FriendPersonalInformation_SUCCEED)){
                    CheckFriendPersonallnformation.checkFriendPersonalInformation(data);
                }
                //删除好友成功
                else if(data.getMesType().equals(RequestEnum.MESSAGE_DeleteFriend_SUCCEED)){
                    DeleteFriend.deleteFriend(data);
                }
                //你被对方删除
                else if(data.getMesType().equals(RequestEnum.MESSAGE_PassDeleteFriend_SUCCEED)){
                    DeleteFriend.deleteFriendPass(data);
                }
                //查看历史消息
                else if(data.getMesType().equals(RequestEnum.MESSAGE_CheckHistoricalMessages_SUCCEED)){
                    CheckHistoricalMessages.checkFriendHistoricalMessages(data);
                }
                //发送消息成功
                else if(data.getMesType().equals(RequestEnum.MESSAGE_SendFriendMsg_SUCCEED)){
                      SendFriendMsg.isUserSend(data);
                }
                //收到了其他好友的消息
                else if(data.getMesType().equals(RequestEnum.MESSAGE_SendOnlineFriend_SUCCEED)){
                       SendFriendMsg.isFriendSend(data);
                }
                //创建群聊
                else if(data.getMesType().equals(RequestEnum.MESSAGE_CreateGroup)){
                    CreateGroup.addGroup(data);
                }
                //查看群信息
                else if(data.getMesType().equals(RequestEnum.MESSAGE_CheckGroupInformation)){
                    CheckGroupInformation.checkGroupInformation(data);
                }
                //改变群头像
                else if(data.getMesType().equals(RequestEnum.MESSAGE_ChangeGroupHeadImage)){
                     ChangeGroupHeadImage.changeHeadImage(data);
                }
                //改变群资料
                else if(data.getMesType().equals(RequestEnum.MESSAGE_ChangeGroupInformation)){
                     ChangeGroupInformation.changeGroupInformation(data);
                }
                //退出群聊
                else if(data.getMesType().equals(RequestEnum.MESSAGE_QuitGroup)){
                     QuitGroup.quitGroup(data);
                }
                //设置群管理员
                else if(data.getMesType().equals(RequestEnum.MESSAGE_SetGroupManage)){
                     SetGroupManage.setGroupManage(data);
                }
                //设置群管理员失败
                else if(data.getMesType().equals(RequestEnum.MESSAGE_SetGroupManage_FAIL)){
                    SetGroupManage.setGroupManageFail();
                }
                //踢人成功
                else if(data.getMesType().equals(RequestEnum.MESSAGE_KickPeople)){
                    KickPeople.kickPeopleSucceed(data);
                }
                //被人踢了
                else if(data.getMesType().equals(RequestEnum.MESSAGE_PassKickPeople)){
                    KickPeople.passkickPeople(data);
                }
                //踢人失败
                else if(data.getMesType().equals(RequestEnum.MESSAGE_KickPeople_FAIL)){
                    KickPeople.kickPeopleFail();
                }
                //群解散了
                else if(data.getMesType().equals(RequestEnum.MESSAGE_DisbandGroup)){
                     DisbandGroup.disbandGroup(data);
                }
                //群被解散了
                else if(data.getMesType().equals(RequestEnum.MESSAGE_PassDisbandGroup)){
                    DisbandGroup.passDisbandGroup(data);
                }
                //好友上线
                else if(data.getMesType().equals(RequestEnum.MESSAGE_FriendOnline)){
                   FriendOnline.updateFriendList(data);
                }
                //好友下线
                else if(data.getMesType().equals(RequestEnum.MESSAGE_FriendOffline)){
                    FriendOnline.updateFriendList(data);
                }
                //添加常用语
                else if(data.getMesType().equals(RequestEnum.MESSAGE_AddCommonPhrases)){
                     CommonPhrases.updateCommonPhrases(data);
                }
                //添加常用语失败
                else if(data.getMesType().equals(RequestEnum.MESSAGE_AddCommonPhrases_FAIL)){
                     CommonPhrases.addCommonPhrasesFail();
                }
                //删除常用语
                else if(data.getMesType().equals(RequestEnum.MESSAGE_DeleteCommonPhrases)){
                    CommonPhrases.updateCommonPhrases(data);
                }
            } catch (IOException | ClassNotFoundException e) {
                if(objectInputStream!=null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                e.printStackTrace();
                System.out.println( "与服务器断开连接！");
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}