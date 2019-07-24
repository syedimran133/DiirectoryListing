package com.zerosymbol.directorylisting.support;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by root on 24-07-2017.
 */

public class JsonUtils {

    private static String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = AppSingle.getInstance().getAssets().open("json/" + fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    public static String getMessage(String code, String nameSpace, String extraMessage) {
        String message = "";
        String fileName = "";
        String failedError = (AppValidate.isValidString(extraMessage)) ? extraMessage : "Failed";
        try {
            if (!AppValidate.isValidString(nameSpace))
                fileName = "json_agg.json";
            else {
                if (nameSpace.equals("umarket"))
                    fileName = "json_umarket.json";
                else if (nameSpace.equals("core"))
                    fileName = "json_core.json";
                else if (nameSpace.equals("session"))
                    fileName = "json_session.json";
                else if (nameSpace.equals("soap"))
                    fileName = "json_soap.json";
                else if (nameSpace.equals("sdp"))
                    fileName = "json_sdp.json";
                else if (nameSpace.equals("searchiigo"))
                    fileName = "json_agg.json";
            }
            JSONObject obj = new JSONObject(loadJSONFromAsset(fileName));
            message = obj.getString(code);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppValidate.isValidString(message) ? message : failedError;
    }

    public static String getMessage(String code, String nameSpace) {
        String message = "";
        String fileName = "";
        try {
            if (!AppValidate.isValidString(nameSpace))
                fileName = "json_agg.json";
            else {
                if (nameSpace.equals("umarket"))
                    fileName = "json_umarket.json";
                else if (nameSpace.equals("core"))
                    fileName = "json_core.json";
                else if (nameSpace.equals("session"))
                    fileName = "json_session.json";
                else if (nameSpace.equals("soap"))
                    fileName = "json_soap.json";
                else if (nameSpace.equals("sdp"))
                    fileName = "json_sdp.json";
                else if (nameSpace.equals("triomoney"))
                    fileName = "json_agg.json";
            }
            JSONObject obj = new JSONObject(loadJSONFromAsset(fileName));
            message = obj.getString(code);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppValidate.isValidString(message) ? message : "Failed";
    }

}
