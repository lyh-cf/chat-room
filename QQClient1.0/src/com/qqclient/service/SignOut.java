package com.qqclient.service;

import com.qqclient.view.app.*;
import javafx.application.Platform;

import java.io.IOException;

public class SignOut {
    public static void  signOut() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ChatPageAPP.ChatPageStage.close();
                if (ChangeMessageAPP.ChangeMessageStage!=null&&ChangeMessageAPP.ChangeMessageStage.isShowing()) ChangeMessageAPP.ChangeMessageStage.close();
                if (ChangeWordAPP.ChangeWordStage!=null&&ChangeWordAPP.ChangeWordStage.isShowing()) ChangeWordAPP.ChangeWordStage.close();
                if (AddFriendAPP.AddFriendStage!=null&&AddFriendAPP.AddFriendStage.isShowing())AddFriendAPP.AddFriendStage.close();
                if(FriendApplicationListviewAPP.FriendListviewStage!=null&&FriendApplicationListviewAPP.FriendListviewStage.isShowing())FriendApplicationListviewAPP.FriendListviewStage.close();
                if(FriendPersonalInformationAPP.friendPersonalInformationStage!=null&&FriendPersonalInformationAPP.friendPersonalInformationStage.isShowing())FriendPersonalInformationAPP.friendPersonalInformationStage.close();
                if(GroupPersonalInformationAPP.groupPersonalInformationStage!=null&&GroupPersonalInformationAPP.groupPersonalInformationStage.isShowing())GroupPersonalInformationAPP.groupPersonalInformationStage.close();
                if(EditCommonPhrasesAPP.EditCommonPhrasesStage!=null&&EditCommonPhrasesAPP.EditCommonPhrasesStage.isShowing())EditCommonPhrasesAPP.EditCommonPhrasesStage.close();
            }
        });
    }
}
