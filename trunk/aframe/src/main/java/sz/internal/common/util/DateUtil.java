package sz.internal.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * User: wanggang
 * Date: 12/30/15
 * Time: 1:04 PM
 */
public class DateUtil {
    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";
    public static final String DEFAULT_FORMAT_DATE_SLASH = "yyyy/MM/dd";
    public static final String DEFAULT_FORMAT_DATE_WITHOUT_SLASH = "yyyyMMdd";
    public static final String DEFAULT_FORMAT_TIME = "HH:mm:ss";
    public static final String DEFAULT_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * get current timestamp, no any parameter
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(getCurrentDate().getTime());
    }

    /**
     * get current sysdate ,no any parameter
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * return the current date as the default format
     */
    public static Date getCurrentFormatDate() {
        return parseDate(formatCurrentDate());
    }

    /**
     * return the current date as the default format
     *
     * @return the the currenteDate as the string
     */
    public static String formatCurrentDate() {
        return formatDate(getCurrentDate(), DEFAULT_FORMAT_DATE);
    }

    /**
     * get the date that before /after the special date
     */
    public static String b4AfterDate(Date date, int days) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.DAY_OF_MONTH, days);
        return formatDate(cale.getTime(), DEFAULT_FORMAT_DATE);
    }

    /**
     * return the special date as the special format;
     *
     * @param date   the ori date
     * @param format the format that the date will be format
     * @return return the dataString from the date as the format
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdFormat = new SimpleDateFormat(format);
        return sdFormat.format(date);
    }

    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_FORMAT_DATE);
    }

    public static String formatTimeStamp(Date date) {
        return formatDate(date, DEFAULT_FORMAT_DATETIME);
    }

    public static Date parseDate(String date, String format) {
        SimpleDateFormat sdFormat = new SimpleDateFormat(format);
        try {
            return sdFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDate(String date) {
        Date dt = parseDate(date, DEFAULT_FORMAT_DATE);
        if (dt == null) {
            dt = parseDate(date, DEFAULT_FORMAT_DATE_SLASH);
        }
        return dt;
    }

    public static Date parseDateWithoutSlash(String date) {
        return parseDate(date, DEFAULT_FORMAT_DATE_WITHOUT_SLASH);
    }

    /**
     * Set the time component as the start of the day.
     * <p/>
     * The Time Component of the date returned will be set to
     * 00:00:00.
     * <p/>
     *
     * @param date The Date to get the start of the day
     * @return The date with the time component reset to 00:00:00.
     */
    public static Date getStartOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // Clear the time component
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return getDate(year, month, day);
    }

    /**
     * Return the Date instance with the provided year,
     * month ( 1 - 12 ), and day ( 1 - 31 ) values.
     * <p/>
     * The date value will roll over when given a value that is greater
     * than the max possible. Eg. when getDate( 2002, 10, 32 )
     * is provided, the Date instance will be 1st Nov 2002.
     * <p/>
     *
     * @param year  Year
     * @param month Month ( 1 - 12 )
     * @param day   Day( 1 - 31 )
     * @return The Date instance created.
     */
    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();

        // Clear all fields first
        cal.clear();

        cal.set(year, month - 1, day);

        return cal.getTime();
    }

    /**
     * @param year  Year
     * @param month month
     * @param day   day
     * @return true or false
     */
    public static boolean isValidDate(int year, int month, int day) {
        if (day <= 0 || month <= 0 || year <= 0)
            return false;
        if (month > 12 || day > 31) {
            return false;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(1, year);
            cal.set(2, month - 1);
            int maxDay = cal.getActualMaximum(5);
            return day <= maxDay;
        }
    }

    /**
     * Check whether the two date is same
     *
     * @param date1 Date 1
     * @param date2 Date 2
     * @return boolean
     */
    public static boolean isSameDay(Date date1, Date date2) {

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);

        return !(date1 == null || date2 == null ||
                c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR) ||
                c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH) ||
                c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH));

    }

    /**
     * @return return the default format of the date(MM/dd/yyyy)
     */
    public static String getDateFormat() {
        return DEFAULT_FORMAT_DATE;
    }

    public static int getDateCompare(Date d1, Date d2) {
        DateFormat df = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
        try {
            return (df.parse(formatDate(d1))).compareTo(df.parse(formatDate(d2)));
        } catch (Throwable t) {
            return 0;
        }
    }

    /**
     * get the first day of the special month that the date belong to
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        gc.setTime(date);
        gc.set(Calendar.DAY_OF_MONTH, gc.getActualMinimum(Calendar.DAY_OF_MONTH));
        return gc.getTime();

    }

    /**
     * get the last day of the special month that the date belong to
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        gc.setTime(date);
        gc.set(Calendar.DAY_OF_MONTH, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
        return gc.getTime();
    }

    /**
     * @param date
     * @return
     */
    public static Date getFirstDayOfYear(Date date) {
        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        gc.setTime(date);
        int year = gc.get(Calendar.YEAR);
        return getDate(year, 1, 1);
    }

    /**
     * @param date
     * @return
     */
    public static Date getLastDayOfYear(Date date) {
        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        gc.setTime(date);
        int year = gc.get(Calendar.YEAR);
        return getDate(year, 12, 31);
    }

    public static Date getDayAfterSpan(int span) {
        Calendar cal = Calendar.getInstance();
        Date date;
        cal.add(Calendar.DATE, span);
        date = cal.getTime();
        return date;
    }

    /**
     * get the first day of the week(the day of sunday)
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        gc.setTime(date);
        int dayOfWeek = (gc.get(Calendar.DAY_OF_WEEK));
        if (dayOfWeek > 1) {//the day is not sunday(the value of sunday is1)
            gc.add(Calendar.DAY_OF_MONTH, (-1 * dayOfWeek + 2));
        } else {
            gc.add(Calendar.DAY_OF_MONTH, -6);
        }
        return gc.getTime();
    }

    /**
     * get the last day of the week  (the day of Saturday)
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        gc.setTime(date);
        int dayOfWeek = (gc.get(Calendar.DAY_OF_WEEK));
        if (dayOfWeek > 1) {//the day is not sunday(the value of sunday is 1)
            gc.add(Calendar.DAY_OF_MONTH, (7 - dayOfWeek) + 1);
        }
        return gc.getTime();
    }

    public static Date getHourStart(Date oriDate, int hours) {
        GregorianCalendar oriGc = (GregorianCalendar) GregorianCalendar.getInstance();
        oriGc.setTime(oriDate);
        int hour = oriGc.get(GregorianCalendar.HOUR_OF_DAY);
        oriGc.set(GregorianCalendar.HOUR_OF_DAY, hour + hours);
        oriGc.set(GregorianCalendar.MINUTE, 0);
        oriGc.set(GregorianCalendar.SECOND, 0);
        oriGc.set(GregorianCalendar.MILLISECOND, 0);
        return oriGc.getTime();
    }

    public static Date getHourEnd(Date oriDate, int hours) {
        GregorianCalendar oriGc = (GregorianCalendar) GregorianCalendar.getInstance();
        oriGc.setTime(oriDate);
        int hour = oriGc.get(GregorianCalendar.HOUR_OF_DAY);
        oriGc.set(GregorianCalendar.HOUR_OF_DAY, hour + hours);
        oriGc.set(GregorianCalendar.MINUTE, 59);
        oriGc.set(GregorianCalendar.SECOND, 59);
        oriGc.set(GregorianCalendar.MILLISECOND, 999);
        return oriGc.getTime();
    }

    public static void main(String[] args) {
        String s = "2015/1/2";
        Date d = parseDate(s);

        System.out.println(d);
    }
}
