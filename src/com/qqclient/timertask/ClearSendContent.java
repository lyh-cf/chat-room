package com.qqclient.timertask;

import com.qqclient.view.Controller;

import java.util.TimerTask;

public class ClearSendContent extends TimerTask {
    @Override
    public void run() {
        Controller.getChatPageController().getSendContent().clear();
    }
}
