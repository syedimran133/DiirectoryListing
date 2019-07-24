package com.zerosymbol.directorylisting.constants;

/**
 * Created by root on 23-12-2016.
 */

public class AppConstants {

    public interface IPrefLoc {
        int SUCCESS_RESULT = 0;
        int FAILURE_RESULT = 1;
        String PACKAGE_NAME =
                "com.google.android.gms.location.sample.locationaddress";
        String RECEIVER = PACKAGE_NAME + ".RECEIVER";
        String RESULT_DATA_KEY = PACKAGE_NAME +
                ".RESULT_DATA_KEY";
        String LOCATION_DATA_EXTRA = PACKAGE_NAME +
                ".LOCATION_DATA_EXTRA";
    }

    public interface IPrefConstant {
        String PREF_SESSION_ID = "session";
        String PREF_INITIATOR = "initiator";
        String PREF_BALANCE = "balance";
        String PREF_LAST_UPDATED = "last_updated";
        String PREF_NAME = "name";
        String PREF_TYPE_ID = "type_id";
        String PREF_APPKEY = "app_key";
        String PREF_RATING = "rating";
        String PREF_EMAIL = "email";
        String PREF_LOCATION = "location";
        String PREF_PIN = "pin";
        String PREF_CATEGORY = "category";
        String PREF_IS_FOUR_TIER = "is_four_tier";
        String PREF_ACTIVITY_EXECUTED = "activity_executed";
        String PREF_DEVICE_IMEI = "deviceIMEI";
        String PREF_SESSIONID = "session_id";
    }


    public interface IAppPref {
        int REQUEST_TIME_OUT = 120;
        int REQUEST_TIME_OUT2 = 30;
        int CODE_FOR_UPDATE = 1050;
        int CODE_FOR_MULTIPLE_ACCOUNT = 7019;
        String FILE_NAME = "app-logs.txt";
        String FOLDER = "MobiCashT2";
        //        String FOLDER = ".TestHiddenFolder";
        String OLD_FOLDER = "MobiCashR";
    }


    public interface IAppUrls {
        //Production URl
        String URL_U_MARKET_SC = "https://emobicash-mws.qpass.com/services/umarketsc";
        //UAT Url
        //String URL_U_MARKET_SC = "https://bsnl-uat-ws-1.dcstest-us.net:8285/services/umarketsc";
        String URL_SDP_SERVICE = "https://emobicash-sdp.qpass.com/soap/v2/services/sdp/sdpService.wsdl";
    }


    public interface IRequestResponseUtils {
        int user_channel = 8;
        String S_REGISTRATION = "register";
        String S_LOGINWOTP = "loginWotp";
        String S_LOGIN = "login";
        String S_CATEGORY = "getcategory";
        String S_GENOTP = "generateOTP";
        String S_LOGOUT = "logout";
    }

    public interface IMessages {
        String SUCCESS = "Success";
        String FAIL = "Fail";
        String IN_PROGRESS = "In Progress";
        String NO_NEW_UPDATE = "No new updates are there for now";
        String ISSUE_WITH_SESSION = "Issue with new session";
        String ISSUE_WITH_PARAMETERS = "There is some issue with Parameters list";
        String ISSUE_WITH_REGEX = "Issue with RegEx";
        String NO_INTERNET = "No internet connection";
        String ISSUE_WITH_BILL_AMOUNT = "Issue with Bill Amount";
        String MIN_BILL_AMT = "Payable amount range should be Rs ";
        /* String SERVER_GOT_ERROR = "Server error please try again";
         String SERVER_TIMEOUT = "Server Time Out Error";*/
        String SERVER_GOT_ERROR = "System is Under Maintenance. Please try after some Time.";
        String SERVER_TIMEOUT = "Looks like the server is taking to long to respond,Please check the status in mini statement after some time.";
        String SERVER_TIMEOUT_PROGRESS = "Your transaction is in progress, Please check the status in mini statement after some time.";
        String SERVER_TIMEOUT_PROGRESS_DMT = "Your transaction is in progress, Please check the status in money transfer transaction history after some time.";
        String VALID_4_DIGIT = "Please enter valid 4 digit pin";
        String VALID_10_DIGIT = "Please enter valid 10 digit Mobile Number";
        String VALID_10 = "Please enter valid 10 digit Mobile Number";
        String VALID_NUMBER = "Please enter valid Number";
        String VALID_FIRST_NAME = "Please enter valid First Name";
        String VALID_LAST_NAME = "Please enter valid Last Name";
        String VALID_DOB = "Please enter valid Date Of Birth";
        String VALID_BEN_NAME = "Please enter valid Beneficiary Name";
        String VALID_BEN_AC = "Please enter valid Beneficiary Account Number";
        String VALID_BEN_MOB = "Please enter valid Beneficiary Mobile Number";
        String VALID_NAME = "Please enter valid Name";
        String ENTER_VALID = "Please enter valid ";
        String ENTER_VALID_EMAIL = "Please enter valid Email Id";
        String SELECT_DOB = "Please select Date Of Birth";
        String SELECT_GENDER = "Please select Gender";
        String VALID_6_DIGIT = "Please enter valid 6 digit OTP";
        String VALID_4_DIGIT_OTP = "Please enter valid OTP";
        String PIN_NOT_MATCHED = "New and Confirm Pin not matched";
        String VALID_AMOUNT = "Please enter valid Amount";
        String VALID_AMOUNT_TO_PAY = "Amount should be in between 100 to 5000";
        String VALID_MMID = "Please enter valid MMID";
        String VALID_IFSC = "Please enter valid IFSC Code";
        String VALID_AC = "Please enter valid Account Number";
        String VALID_11_DIGIT = "Please enter valid 11 digit IFSC code";
        String VALID_SBI_AC = "Please enter valid 11 or 17 digits SBI Account number";
    }
}
