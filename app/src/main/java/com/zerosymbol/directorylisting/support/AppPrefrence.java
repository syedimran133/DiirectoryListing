package com.zerosymbol.directorylisting.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.zerosymbol.directorylisting.constants.AppConstants;

import java.util.Arrays;
import java.util.List;


/**
 * Created by root on 23-12-2016.
 */

public class AppPrefrence implements AppConstants.IPrefConstant {

    private static AppPrefrence _appPref;
    private static Object obj = new Object();
    private SharedPreferences _prefrence;

    public static AppPrefrence getInstance() {
        if (_appPref == null) {
            synchronized (obj) {
                _appPref = new AppPrefrence();
            }
        }
        return _appPref;
    }

    public void initPrefrence(Context context) {
        if (context != null)
            _prefrence = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void clearPrefrence() {
        if (_prefrence != null)
            _prefrence.edit().clear().commit();
    }

    public boolean isActivityExecuted() {
        if (_prefrence != null)
            return _prefrence.getBoolean(PREF_ACTIVITY_EXECUTED, false);
        return false;
    }

    public void setActivityExecuted(boolean isRemember) {
        if (_prefrence != null)
            _prefrence.edit().putBoolean(PREF_ACTIVITY_EXECUTED, isRemember).commit();
    }
    public void setsessionId(String sessionId) {
        if (_prefrence != null)
            _prefrence.edit().putString(PREF_SESSIONID, EncryptionDecryptionUtil.doEncrypt(sessionId)).commit();
    }

    public String getsessionId() {
        if (_prefrence != null)
            try {
                return EncryptionDecryptionUtil.doDecrypt(_prefrence.getString(PREF_SESSIONID, "NA"));
            } catch (Exception e) {
            }
        return "NA";
    }
    public String getInitiator() {
        if (_prefrence != null)
            return _prefrence.getString(PREF_INITIATOR, "");
        return "";
    }

    public void setInitiator(String init) {
        if (_prefrence != null)
            _prefrence.edit().putString(PREF_INITIATOR, init).commit();
    }

    public String getName() {
        if (_prefrence != null)
            return _prefrence.getString(PREF_NAME, "");
        return "";
    }
    public void setName(String name) {
        if (_prefrence != null)
            _prefrence.edit().putString(PREF_NAME, name).commit();
    }
    public String getTypeId() {
        if (_prefrence != null)
            return _prefrence.getString(PREF_TYPE_ID, "");
        return "";
    }
    public void setTypeId(String type_id) {
        if (_prefrence != null)
            _prefrence.edit().putString(PREF_TYPE_ID, type_id).commit();
    }

    public String getAppKey() {
        if (_prefrence != null)
            return _prefrence.getString(PREF_APPKEY, "NA");
        return "NA";
    }
    public void setAppKey(String appkey) {
        if (_prefrence != null)
            _prefrence.edit().putString(PREF_APPKEY, appkey).commit();
    }

    public String getRating() {
        if (_prefrence != null)
            return _prefrence.getString(PREF_RATING, "");
        return "";
    }
    public void setRating(String rating) {
        if (_prefrence != null)
            _prefrence.edit().putString(PREF_RATING, rating).commit();
    }

    public String getEmail() {
        if (_prefrence != null)
            return _prefrence.getString(PREF_EMAIL, "");
        return "";
    }
    public void setEmail(String email) {
        if (_prefrence != null)
            _prefrence.edit().putString(PREF_EMAIL, email).commit();
    }

    public String getLocation() {
        if (_prefrence != null)
            return _prefrence.getString(PREF_LOCATION, "");
        return "";
    }
    public void setLocation(String location) {
        if (_prefrence != null)
            _prefrence.edit().putString(PREF_LOCATION, location).commit();
    }

    public String getLunagang() {
        if (_prefrence != null)
            return _prefrence.getString(PREF_BALANCE, "");
        return "";
    }

    public void setLunagang(String balance) {
        if (_prefrence != null)
            _prefrence.edit().putString(PREF_BALANCE, balance).commit();
    }
    ////////////////////////////////////////////////////////////////
    public String getLastUpdated() {
        if (_prefrence != null)
            return _prefrence.getString(PREF_LAST_UPDATED, "0");
        return "";
    }

    public void setLastUpdated(String lastupdated) {
        if (_prefrence != null)
            _prefrence.edit().putString(PREF_LAST_UPDATED, lastupdated).commit();
    }
    ///////////////////////////////////////////////////////////////
    public String getPin() {
        if (_prefrence != null)
            return _prefrence.getString(PREF_PIN, "");
        return "";
    }

    public void setPin(String pin) {
        if (_prefrence != null)
            _prefrence.edit().putString(PREF_PIN, pin).commit();
    }

    public boolean getiSLunagang() {
        if (_prefrence != null)
            return _prefrence.getBoolean(PREF_IS_FOUR_TIER, false);
        return false;
    }

    public void setiSLunagang(boolean islunagang) {
        if (_prefrence != null)
            _prefrence.edit().putBoolean(PREF_IS_FOUR_TIER, islunagang).commit();
    }

    public int getDeviceIMEI() {
        if (_prefrence != null)
            return _prefrence.getInt(PREF_DEVICE_IMEI, 0);
        return 0;
    }

    public void setDeviceIMEI(int nouce) {
        if (_prefrence != null)
            _prefrence.edit().putInt(PREF_DEVICE_IMEI, nouce).commit();
    }

    public List<String> getCategory() {
        if (_prefrence != null)
            return Arrays.asList(_prefrence.getString(PREF_CATEGORY, "").split("\\s*,\\s*"));
        return null;
    }

    public void setCategory(List<String> category) {
        if (_prefrence != null)
            _prefrence.edit().putString(PREF_CATEGORY, android.text.TextUtils.join(",", category)).commit();
    }
}
