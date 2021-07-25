package fr.ChadOW.omegacore.utils;

import java.text.SimpleDateFormat;

public class NbrReader {
    public static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static boolean isTimeFormat(String str) {
        if (str.length() > 1) {
            char car = str.charAt(str.length() -1);
            return isNumber(str.substring(0, str.length() - 1))
                    && (
                    car == 's'
                            || car == 'm'
                            || car == 'h'
                            || car == 'd'
            );
        }
        return false;
    }


    public static int getTimeInSecondWithTimeFormat(String str, boolean bypassCheck) {
        if (bypassCheck || isTimeFormat(str)) {
            int time = Integer.parseInt(str.substring(0, str.length() - 1));
            char car = str.toLowerCase().charAt(str.length() -1);
            switch (car) {
                case 's':
                    return time;
                case 'm':
                    return time * 60;
                case 'h':
                    return time * 3600;
                case 'd':
                    return time * 86400;
                default:
                    return 0;
            }
        }
        return 0;
    }

    public static boolean isNumber(String str) {
        for (char car : str.toCharArray()) {
            if (!(
                    car == '0'
                    || car == '1'
                    || car == '2'
                    || car == '3'
                    || car == '4'
                    || car == '5'
                    || car == '6'
                    || car == '7'
                    || car == '8'
                    || car == '9'
            ))
                return false;
        }
        return true;
    }

    public static boolean isDouble(String str) {
        for (char car : str.toCharArray()) {
            if (!(
                    car == '0'
                            || car == '1'
                            || car == '2'
                            || car == '3'
                            || car == '4'
                            || car == '5'
                            || car == '6'
                            || car == '7'
                            || car == '8'
                            || car == '9'
                            || car == '.'
            ))
                return false;
        }
        return true;
    }

    public static String getTimeInString(long s) {
        int d = 0;
        int h = 0;
        int m = 0;

        while (s >= 86400) {
            d++;
            s -= 86400;
        }
        while (s >= 3600) {
            h++;
            s -= 3600;
        }
        while (s >= 60) {
            m++;
            s -= 60;
        }

        String msg = "";
        if (d > 1)
            msg += d + " jours ";
        else if (d == 1)
            msg += "1 jour ";

        if (h > 1)
            msg += h + " heures ";
        else if (h == 1)
            msg += "1 heure ";

        if (m > 1)
            msg += m + " minutes ";
        else if (m == 1)
            msg += "1 minute ";

        if (s > 1)
            msg += s + " secondes ";
        else if (s == 1)
            msg += "1 seconde ";

        if (msg.length() == 0)
            msg += "0 seconde ";

        return msg.substring(0, msg.length() -1);
    }



    public static double calcPyth(double xA, double yA, double xB, double yB) {
        double x = Math.abs(xA - xB);
        double y = Math.abs(yA - yB);

        return Math.sqrt(x*x + y*y);
    }
}
