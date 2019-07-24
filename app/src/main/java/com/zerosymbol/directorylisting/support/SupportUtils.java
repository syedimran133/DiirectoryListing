package com.zerosymbol.directorylisting.support;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Base64;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 03-05-2017.
 */

public class SupportUtils {

    public static String getAppLable(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "Unknown");
    }
//searchiigo
    public static List<String> getlung() {
        List<String> array = new ArrayList<>();
        array.add("English");
        array.add("Hindi(हिंदी)");
        array.add("Bengali(বাঙালি)");
        array.add("Gujarati(ગુજરાતી)");
        array.add("Kannada(ಕನ್ನಡ)");
        array.add("Marathi(मराठी)");
        array.add("Telugu(తెలుగు)");
        array.add("Kannada(ಕನ್ನಡ)");
        array.add("Marathi(मराठी)");
        array.add("Telugu(తెలుగు)");
        return array;
    }

    public static List<String> getusercat() {
        List<String> array = new ArrayList<>();
        array.add("About");
        array.add("Service & FEES");
        array.add("BROCHURE");
        array.add("GALLERY");
        array.add("AMENITIES");
        array.add("TEAM/BRANCHES");
        array.add("LICENSE/REGISTRATION");
        array.add("OFFERS");
        return array;
    }

    public static void deleteFiles(String path) {

        File file = new File(path);

        if (file.exists()) {
            String deleteCmd = "rm -r " + path;
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(deleteCmd);
            } catch (IOException e) {
            }
        }
    }

    public static String returnFromatedDate(String dateToParse) {
//        dateToParse = "2017-07-27 17:24:53.0";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

        String finalDate = "";
        if (!AppValidate.isValidString(dateToParse))
            return finalDate;
        try {
            Date date = sdf.parse(dateToParse);
            System.out.println(date);
            finalDate = sdfs.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalDate;
    }

    public static String returnFromatedDate2(String dateToParse) {
//        dateToParse = "2017-07-27 17:24:53.0";
        String newDate = dateToParse.replace("T", " ");
        newDate = newDate.substring(0, newDate.length() - 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

        String finalDate = "";
        if (!AppValidate.isValidString(newDate))
            return finalDate;
        try {
            Date date = sdf.parse(newDate);
            System.out.println(date);
            finalDate = sdfs.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalDate;
    }

    public static boolean checkIf24HrsCompleted(long SavedTime) {
        boolean is24Hrs = false;
        try {

            long msDiff = Calendar.getInstance().getTimeInMillis() - SavedTime;
            long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
            if (daysDiff >= 1)
                is24Hrs = true;
        } catch (Exception pe) {
        }
        return is24Hrs;
    }

    public static long getCurrentTimeMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String getNodeValue(String tagName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                NodeList childNodes = node.getChildNodes();
                for (int y = 0; y < childNodes.getLength(); y++) {
                    Node data = childNodes.item(y);
                    if (data.getNodeType() == Node.TEXT_NODE)
                        return data.getNodeValue();
                }
            }
        }
        return "";
    }


    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

}
