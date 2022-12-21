package com.qqclient.timertask;

import com.qqclient.view.controller.LookingForWordController;

import java.util.TimerTask;

public class StopChangeWordSend extends TimerTask {
    @Override
    public void run() {
        LookingForWordController.flag=1;
        LookingForWordController.yanzhengma="6753241654384";
    }
}
