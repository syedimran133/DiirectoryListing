package com.zerosymbol.directorylisting.service;

/**
 * Created by root on 21-04-2017.
 */

public class ApiUtils {

    //static String BASE_URL = "http://192.168.1.40:9889";//Private
    static String BASE_URL = "http://122.160.164.233:9892";//Public

    //PHP_URL
    static String BASE_URL_PHP = "http://bsnltrms.triomoney.co.in";

    //UAT Temp new office
    //static String BASE_URL = "http://14.98.64.130:9889";

    private ApiUtils() {

    }

    public static ApiServices getAPIService(String name) {
        return RetrofitClient.getClient(BASE_URL).create(ApiServices.class);
    }
    public static ApiServices getAPIService2(String name) {
        return RetrofitClient.getClient2(BASE_URL).create(ApiServices.class);
    }
    public static ApiServices getServicePhp(String name) {
        return RetrofitClient.getAPIServicePhp(BASE_URL_PHP).create(ApiServices.class);
    }

}
