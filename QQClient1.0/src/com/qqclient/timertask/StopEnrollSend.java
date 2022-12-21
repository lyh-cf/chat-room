package com.qqclient.timertask;

import com.qqclient.view.controller.EnrollController;

import java.util.TimerTask;

public class StopEnrollSend extends TimerTask {
    @Override
    public void run() {
        EnrollController.flag=1;
        EnrollController.yanzhengma="6753241654384";
    }
}
