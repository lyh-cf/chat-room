package com.service;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.RequestEnum;
import com.qqclient.pojo.User;
import com.utils.SendEmail;
import java.security.GeneralSecurityException;

public class SendCode {
    static String yanzhengma;
    public static Data checkEmail(Data data){
        User user=(User) data.getDate();
        try {
            yanzhengma= SendEmail.send(user.getEmail());
            if("0".equals(yanzhengma)){
                data.setMesType(RequestEnum.MESSAGE_VerificationCode_FAIL);
                //将data对象回复客户端
            }else{ //发送成功
                user.setVerificationCode(yanzhengma);
                data.setDate(user);
                data.setMesType(RequestEnum.MESSAGE_VerificationCode_SUCCEED);
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return data;
    }
}
