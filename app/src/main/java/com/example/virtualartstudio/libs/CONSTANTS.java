package com.example.virtualartstudio.libs;

public class CONSTANTS {
    static String TAG_SPLASH_SCREEN = "";

    static String LogTagCreater(String ActivityName) {
        return LogTagCreater(ActivityName, "");
    }



    static String LogTagCreater(String ActivityName, String Tag) {
        return ActivityName + "_" + Tag;
    }
}
