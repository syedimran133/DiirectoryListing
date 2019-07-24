package com.zerosymbol.directorylisting.support;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by root on 23-12-2016.
 */

public class AppValidate {


    public static boolean isValidDmtAmount(String str) {
        boolean result = false;
        if (str == null || str.trim().equals("")) {
            result = false;
        }
        try {
            double amount = Double.parseDouble(str);
            if (amount >= 100 && amount <= 25000) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
    public static boolean isValidAadhaarNo(String str) {
        if (str == null || str.trim().equals(""))
            return false;
        if (str.trim().length() != 12)
            return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean isValidString(String str) {
        if (str == null || str.trim().equals(""))
            return false;
        else if (str.equalsIgnoreCase("null"))
            return false;
        return true;
    }

    public static boolean isValidMobileNo(String str) {
        if (str == null || str.trim().equals(""))
            return false;
        if (str.trim().length() != 10)
            return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElevenCharacters(String str) {
        if (str == null || str.trim().equals(""))
            return false;
        if (str.trim().length() >= 8 && str.trim().length() <= 11)
            return true;
        else
            return false;
    }

    public static boolean isFourToSixDigit(String str) {
        if (str == null || str.trim().equals(""))
            return false;
        if (str.trim().length() < 4 || str.trim().length() > 6)
            return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isFourDigit(String str) {
        if (str == null || str.trim().equals(""))
            return false;
        if (str.trim().length() != 4)
            return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isSixDigit(String str) {
        if (str == null || str.trim().equals(""))
            return false;
        if (str.trim().length() != 6)
            return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidac(String str) {
        if (str == null || str.trim().equals(""))
            return false;
        if (str.trim().length() >= 8)
            return true;
        return false;
    }

    public static boolean isValidSBIac(String str) {
        if (str == null || str.trim().equals(""))
            return false;
        if (str.trim().length() == 11 || str.trim().length() == 17)
            return true;
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isToMatchRegEx(String regex, String data) {
        boolean isToMatch = false;
        if (!isValidString(regex))
            return isToMatch;
        if (!isValidString(data))
            return isToMatch;
        Pattern sPattern
                = Pattern.compile(regex);
        isToMatch = sPattern.matcher(data).matches();
        return isToMatch;
    }


    public static boolean isValidName(String data) {
        String regex = "^[\\p{L} .'-]+$";
        boolean isToMatch = false;
        if (!isValidString(regex))
            return isToMatch;
        if (!isValidString(data))
            return isToMatch;
        Pattern sPattern
                = Pattern.compile(regex);
        isToMatch = sPattern.matcher(data).matches();
        return isToMatch;
    }

    public static boolean isValidPan(String data) {
        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";//[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}
        boolean isToMatch = false;
        if (!isValidString(regex))
            return isToMatch;
        if (!isValidString(data))
            return isToMatch;
        Pattern sPattern
                = Pattern.compile(regex);
        isToMatch = sPattern.matcher(data).matches();
        return isToMatch;
    }

    public static boolean isValidLen(String name) {
        boolean res=false;
        if (name.length() >= 3 && name.length() <= 35)
            res=true;
        return res;
    }

    public static boolean isValidPassport(String data) {
        String regex = "[A-Z]{1}[0-9]{7}";//[a-zA-Z]{2}[0-9]{7}
        boolean isToMatch = false;
        if (!isValidString(regex))
            return isToMatch;
        if (!isValidString(data))
            return isToMatch;
        Pattern sPattern
                = Pattern.compile(regex);
        isToMatch = sPattern.matcher(data).matches();
        return isToMatch;
    }


    public static boolean isDateSelectionValid(String fromDate, String toDate) {
        boolean isValid = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(fromDate);
            Date endDate = sdf.parse(toDate);
            return endDate.after(startDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }


    public static boolean isValidAmount(String str) {
        if (str == null || str.trim().equals(""))
            return false;
        try {
            double amount = Double.parseDouble(str);
            if (amount > 0)
                return true;
            else return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String removeCountryCode(String number) {
        number.trim();
        number.replaceAll("\\s", "");
        Log.e("Number", "Number : " + number);
        number = number.substring(number.length() - 10, number.length());
        Log.e("new Number", "new Number : " + number);
        return number;
    }

    public static boolean isValidCommingDate(String parseDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(parseDate);
            Date dt2 = new Date();

            int diffInDays = (int) ((dt2.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
            int day = 18 * 365;
            Log.e("@@@@@@@@@@@@", "Difference in number of days (2) : " + diffInDays + " day : " + day);
            //Toast.makeText(mContext, "Difference in number of days (2) : " + diffInDays+" day : "+day, Toast.LENGTH_LONG).show();
            System.err.println("Difference in number of days (2) : " + diffInDays + " day : " + day);
            if (diffInDays > 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean isValidEmail(String email) {
        if (!isValidString(email))
            return false;
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getValidDateFormat(String parseDate) {
        try {
            Log.e("parseDate", "- " + parseDate);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
            Date date = format.parse(parseDate);
            String formatdate = format2.format(date);
            Log.e("date.toString()", "- " + date.toString() + " - " + formatdate);
            return formatdate;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isValidDate(Date yourDate) {
        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.setTime(yourDate);
        try {
            cal.getTime();
            return true;
        } catch (Exception e) {
            System.out.println("Invalid date");
        }
        return false;
    }

    public static String dateCompairFuture(String c_date, String g_date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(c_date);
            Date date2 = sdf.parse(g_date);

            System.out.println(sdf.format(date1));
            System.out.println(sdf.format(date2));

            if (date1.after(date2)) {
                System.out.println("Date1 is after Date2");
                return "yes";
            }

            if (date1.before(date2)) {
                System.out.println("Date1 is before Date2");
            }

            if (date1.equals(date2)) {
                System.out.println("Date1 is equal Date2");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static boolean dateComp(Date dt1) {
        {
            try {
                Date dt2 = new Date();
                int diffInDays = (int) ((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24));
                int day = 18 * 365;
                android.util.Log.e("@@@@@@@@@@@@", "Difference in number of days (2) : " + diffInDays + " day : " + day);
                System.err.println("Difference in number of days (2) : " + diffInDays + " day : " + day);
                if (diffInDays >= day) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}