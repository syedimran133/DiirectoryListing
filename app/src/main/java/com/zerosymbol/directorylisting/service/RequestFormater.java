package com.zerosymbol.directorylisting.service;

import android.util.Log;
import com.zerosymbol.directorylisting.constants.AppConstants;
import com.zerosymbol.directorylisting.support.EncryptionDecryptionUtil;

/**
 * Created by root on 26-07-2017.
 */

public class RequestFormater implements AppConstants.IRequestResponseUtils {

    public static String formatRegisterRequest(String type,String name,String msisdn,String email,String identityTypeID,String identityNo,String dob,String sex,String address) {
        //String title = "register|1|IMRAN|8860414336|syedimran133@gmail.com|1|ABHSC1232R|1989-9-23|M|NA";
        String title = S_REGISTRATION + "|" + type + "|" + name + "|" + msisdn+ "|"+ email+ "|" + identityTypeID+ "|" + identityNo+ "|" + dob+ "|" + sex+ "|" + address;
        Log.e("RequestFormater", title);
        String encrypt = EncryptionDecryptionUtil.doEncrypt(title);
        return encrypt + "00000";
    }

    public static String formatloginWotpRequest(String otp,String tid) {
        //loginWotp|app_key|passowrd
        String title = S_LOGINWOTP + "|" + otp + "|" + tid ;
        Log.e("RequestFormater", title);
        String encrypt = EncryptionDecryptionUtil.doEncrypt(title);
        return encrypt + "00000";
    }

    public static String formatloginRequest(String app_key,String passowrd) {
        //login|app_key|passowrd
        String title = S_LOGIN + "|" + app_key + "|" + passowrd ;
        Log.e("RequestFormater", title);
        String encrypt = EncryptionDecryptionUtil.doEncrypt(title);
        return encrypt + "00000";
    }
    public static String formatCategoryRequest() {
        //login|app_key|passowrd
        String title = S_CATEGORY + "|NA";
        Log.e("RequestFormater", title);
        String encrypt = EncryptionDecryptionUtil.doEncrypt(title);
        return encrypt + "00000";
    }
    public static String formatLoginWOtpRequest(String mobile) {
        //generateOTP|mobile
        String title = S_GENOTP + "|"+mobile;
        Log.e("RequestFormater", title);
        String encrypt = EncryptionDecryptionUtil.doEncrypt(title);
        return encrypt + "00000";
    }

    public static String formatLogoutRequest(String sessionid) {
        //logout|sessionId
        String title = S_LOGOUT + "|"+sessionid;
        Log.e("RequestFormater", title);
        String encrypt = EncryptionDecryptionUtil.doEncrypt(title);
        return encrypt + "00000";
    }
    public static String formatUserTypeRequest() {
        //logout|sessionId
        String title = "getusertypeee";
        Log.e("RequestFormater", title);
        String encrypt = EncryptionDecryptionUtil.doEncrypt(title);
        return encrypt + "00000";
    }
}