package com.qqclient.utils;

import java.util.regex.Pattern;

public class Regular {
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    public static boolean isMail(String str){
        Pattern mailJudge=Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        return mailJudge.matcher(str).matches();
    }
}
