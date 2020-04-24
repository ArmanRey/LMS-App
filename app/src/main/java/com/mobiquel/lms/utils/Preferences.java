package com.mobiquel.lms.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static Preferences instance;
    private String preferenceName = "com.mobiquel.lms";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    private String KEY_RANDOM_NUMBER = "RANDOM_NUMBER";
    private String KEY_OFFICIAL_ID = "OFFICIAL_ID";
    private String KEY_TOKEN = "TOKEN";

    private String KEY_NAME = "NAME";
    private String KEY_MOBILE = "MOBILE";
    private String KEY_DESIGNATION = "DESIGNATION";
    private String KEY_DISTRICT_ID = "DISTRICT_ID";
    private String KEY_DISTRICT_NAME = "DISTRICT_NAME";
    private String KEY_TEHSIL_ID = "TEHSIL_ID";
    private String KEY_TEHSIL_NAME = "TEHSIL_NAME";
    private String KEY_VILLAGE_ID = "VILLAGE_ID";
    private String KEY_VILLAGE_NAME = "VILLAGE_NAME";
    private String KEY_AREA = "AREA";
    private String KEY_LEVEL = "LEVEL";
    private String DAMAGE_LIST = "DAMAGE_LIST";
    private String KEY_IS_LOGGED_IN = "IS_LOGGED_IN";
    private String KEY_USER_TYPE = "USER_TYPE";
    private String DAMAGE_CATEG_LIST = "DAMAGE_CATEG_LIST";
    public String userType,randomNumber, officialId, token,name,mobile,designation,districtId,tehsilId,villageId,area,level,tehsilName,villageName,districtName,damageList,damageCategList;
    public boolean isLoggedin;
    private Preferences() {

    }

    public synchronized static Preferences getInstance() {
        if (instance == null)
            instance = new Preferences();
        return instance;
    }

    public void loadPreferences(Context c) {
        preferences = c.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE);
        randomNumber = preferences.getString(KEY_RANDOM_NUMBER, "6543");
        officialId = preferences.getString(KEY_OFFICIAL_ID, "");
        token = preferences.getString(KEY_TOKEN, "");

        name = preferences.getString(KEY_NAME, "");
        mobile = preferences.getString(KEY_MOBILE, "");
        designation = preferences.getString(KEY_DESIGNATION, "");
        tehsilId = preferences.getString(KEY_TEHSIL_ID, "");
        tehsilName = preferences.getString(KEY_TEHSIL_NAME, "");
        villageName = preferences.getString(KEY_VILLAGE_NAME, "");
        villageId = preferences.getString(KEY_VILLAGE_ID, "");
        districtId = preferences.getString(KEY_DISTRICT_ID, "");
        districtName = preferences.getString(KEY_DISTRICT_NAME, "");
        area = preferences.getString(KEY_AREA, "");
        level = preferences.getString(KEY_LEVEL, "");
        damageList = preferences.getString(DAMAGE_LIST, "");
        damageCategList = preferences.getString(DAMAGE_CATEG_LIST, "");
        userType = preferences.getString(KEY_USER_TYPE, "");
        isLoggedin = preferences.getBoolean(KEY_IS_LOGGED_IN, false);


    }

    public void savePreferences(Context c) {
        preferences = c.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(KEY_RANDOM_NUMBER, randomNumber);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_OFFICIAL_ID, officialId);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_DESIGNATION, designation);
        editor.putString(KEY_TEHSIL_ID,tehsilId);
        editor.putString(KEY_TEHSIL_NAME, tehsilName);
        editor.putString(KEY_VILLAGE_NAME, villageName);
        editor.putString(KEY_VILLAGE_ID, villageId);
        editor.putString(KEY_DISTRICT_ID, districtId);
        editor.putString(KEY_DISTRICT_NAME, districtName);
        editor.putString(KEY_AREA, area);
        editor.putString(KEY_LEVEL, level);
        editor.putString(DAMAGE_LIST, damageList);
        editor.putString(DAMAGE_CATEG_LIST, damageCategList);
        editor.putString(KEY_USER_TYPE, userType);
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedin);
        editor.commit();
    }
}