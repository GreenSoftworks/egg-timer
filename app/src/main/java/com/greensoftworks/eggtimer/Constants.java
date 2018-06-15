package com.greensoftworks.eggtimer;

public class Constants {

    public interface Action{
        // "Soft", "Mid Soft", "Soft Set", "Hard Boiled"
        String MAIN_ACTION = "com.example.slickrick.myapplication.action.main";
        String STARTFOREGROUND_ACTION = "com.example.slickrick.myapplication.action.startforeground";
        String STOPFOREGROUND_ACTION = "com.example.slickrick.myapplication.action.stopforeground";
        String SOFT_EGG = "com.example.slickrick.myapplication.action.soft";
        String MIDSOFT_EGG = "com.example.slickrick.myapplication.action.midsoft";
        String SOFTSET_EGG = "com.example.slickrick.myapplication.action.softset";
        String HARDBOILED_EGG = "com.example.slickrick.myapplication.action.hardboiled";
        String CH_ID = "rick";
        String CUSTOM_INTENT = "com.example.slickrick.myapplication.action.ALARM";
    }

    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
    }
}
