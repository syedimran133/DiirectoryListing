package com.zerosymbol.directorylisting.service;


import com.zerosymbol.directorylisting.models.CatUserData;
import com.zerosymbol.directorylisting.models.ModelBase;
import com.zerosymbol.directorylisting.models.UserTypeData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by root on 21-04-2017.
 */

public interface ApiServices {

    String SERVER_DIRECTORY_PATH = "/sAPI.php";

    String COMMAND = "command";
    String TUID = "TUID";
    String APP_VERSION = "app_version";
    String SESSIONID = "sessionId";

    @POST(SERVER_DIRECTORY_PATH)
    @FormUrlEncoded
    Call<ModelBase> request(@Field(COMMAND) String command,
                            @Field(TUID) String initiater,
                            @Field(APP_VERSION) String appVersion);

    @POST(SERVER_DIRECTORY_PATH)
    @FormUrlEncoded
    Call<UserTypeData> request_user_type(@Field(COMMAND) String command,
                                         @Field(TUID) String initiater,
                                         @Field(APP_VERSION) String appVersion);

    @POST(SERVER_DIRECTORY_PATH)
    @FormUrlEncoded
    Call<ModelBase> requestwsession(@Field(COMMAND) String command,
                                    @Field(TUID) String initiater,
                                    @Field(APP_VERSION) String appVersion, @Field(SESSIONID) String sessionId);

    @POST(SERVER_DIRECTORY_PATH)
    @FormUrlEncoded
    Call<CatUserData> requestallcat(@Field(COMMAND) String command,
                                      @Field(TUID) String initiater,
                                      @Field(APP_VERSION) String appVersion, @Field(SESSIONID) String sessionId);
}
