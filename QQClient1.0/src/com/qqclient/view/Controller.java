package com.qqclient.view;
import com.qqclient.view.controller.*;

public class Controller {
    private static ChatPageController chatPageController;
    private static ChangeMessageController changeMessageController;
    private static FriendApplicationListviewController friendApplicationListviewController;
    private static AddFriendController addFriendController;
    private static EditCommonPhrasesController editCommonPhrasesController;

    public static EditCommonPhrasesController getEditCommonPhrasesController() {
        return editCommonPhrasesController;
    }

    public static void setEditCommonPhrasesController(EditCommonPhrasesController editCommonPhrasesController) {
        Controller.editCommonPhrasesController = editCommonPhrasesController;
    }

    public static GroupPersonalInformationController getGroupPersonalInformationController() {
        return groupPersonalInformationController;
    }

    public static void setGroupPersonalInformationController(GroupPersonalInformationController groupPersonalInformationController) {
        Controller.groupPersonalInformationController = groupPersonalInformationController;
    }

    private static GroupPersonalInformationController groupPersonalInformationController;
    public static AddFriendController getAddFriendController() {
        return addFriendController;
    }

    public static void setAddFriendController(AddFriendController addFriendController) {
        Controller.addFriendController = addFriendController;
    }

    public static FriendApplicationListviewController getFriendApplicationListviewController() {
        return friendApplicationListviewController;
    }

    public static void setFriendApplicationListviewController(FriendApplicationListviewController friendApplicationListviewController) {
        Controller.friendApplicationListviewController = friendApplicationListviewController;
    }

    public static ChangeMessageController getChangeMessageController() {
        return changeMessageController;
    }

    public static void setChangeMessageController(ChangeMessageController changeMessageController) {
        Controller.changeMessageController = changeMessageController;
    }

    public static ChatPageController getChatPageController() {
        return chatPageController;
    }

    public static void setChatPageController(ChatPageController chatPageController) {
        Controller.chatPageController = chatPageController;
    }
}
