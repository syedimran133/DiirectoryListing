package com.zerosymbol.directorylisting.support;

import android.app.Application;

import com.zerosymbol.directorylisting.activity.MainActivity;

/**
 * Created by root on 12-12-2016.
 */

public class AppSingle extends Application {

        private static AppSingle _app;
        private MainActivity mainActivity;
        private boolean isAllowBack = true;
        private String numberForDMT;
        private String refrenceOTCNumber;
        private String userName;
        private String userBalance;
        private String userEmail;
        private boolean isSuccessLogin;
        private String appVersion;

        public static AppSingle getInstance() {
            return _app;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            _app = this;
        }

        public MainActivity getActivity() {
            return mainActivity;
        }

        public void initActivity(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        public boolean isAllowBack() {
            return isAllowBack;
        }

        public void setAllowBack(boolean allowBack) {
            isAllowBack = allowBack;
        }

        public String getNumberForDMT() {
            return numberForDMT;
        }

        public void setNumberForDMT(String numberForDMT) {
            this.numberForDMT = numberForDMT;
        }

        public String getRefrenceOTCNumber() {
            return refrenceOTCNumber;
        }

        public void setRefrenceOTCNumber(String refrenceOTCNumber) {
            this.refrenceOTCNumber = refrenceOTCNumber;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }


        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public boolean isSuccessLogin() {
            return isSuccessLogin;
        }

        public void setSuccessLogin(boolean successLogin) {
            isSuccessLogin = successLogin;
        }

        public String getUserBalance() {
            return userBalance;
        }

        public void setUserBalance(String userBalance) {
            this.userBalance = userBalance;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

}
