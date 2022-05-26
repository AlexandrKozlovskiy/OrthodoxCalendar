package oleksandr.kotyuk.orthodoxcalendar.models;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyCalendarWidget {

    private Calendar cal;
    private static MyCalendarWidget calendar;

    static final String[] day_week_names_long_caps_widget = {"",
            "ВОСКРЕСЕНЬЕ", "ПОНЕДЕЛЬНИК", "  ВТОРНИК  ", "   СРЕДА   ",
            "  ЧЕТВЕРГ  ", "  ПЯТНИЦА  ", "  СУББОТА  ",};

    static final String[] month_names_widget = {"января", "февраля", "марта",
            "апреля", "мая", "июня", "июля", "августа", "сентября", "октября",
            "ноября", "декабря"};


    private MyCalendarWidget() {
        this.cal = new GregorianCalendar();
    }

    public static synchronized MyCalendarWidget getInstance() {
        if (calendar == null) {
            calendar = new MyCalendarWidget();
        }
        return calendar;
    }

    //устанавливаем дату на сегодн¤шний день
    public void setTodayDate() {
        this.cal = new GregorianCalendar();
    }

    // номер месяца c 0 - 11
    public int getMonth() {

        return cal.get(Calendar.MONTH);
    }

    // день месяца( какой по счету день в месяце 1 - 31)
    public String getDayMonth() {
        return Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
    }

    // день месяца( какой по счету день в месяце 1 - 31)
    public int getDayMonthInt() {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    // название месяца: январь, февраль...
    public String getMonthName() {

        return month_names_widget[cal.get(Calendar.MONTH)];
    }

    // номер года
    public String getYear() {
        return Integer.toString(cal.get(Calendar.YEAR));
    }

    // название дня недели (ПОНЕДЕЛЬНИК, ВТОРНИК...)
    public String getDayWeekNamesLongCaps() {
        return day_week_names_long_caps_widget[cal.get(Calendar.DAY_OF_WEEK)];
    }

    // день недели (воскресенье - 1; субота - 7)
    public int getDayWeek() {
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    // номер года
    public int getYearInt() {
        return cal.get(Calendar.YEAR);
    }

    //провер¤ем или дата на устройстве попадает в период с 2020 по 2023
    public boolean getDateEntersPeriods() {
        int today_year = this.getYearInt();

        if (today_year > 2019 && today_year < 2024)
            return true;
        else
            return false;
    }

    //устанавливаем дату по старому стилю -13 дней
    public void SetOldStyleDate() {
        cal.add(Calendar.DAY_OF_MONTH, -13);
    }

    //устанавливаем дату по новому стилю +13 дней
    public void SetNewStyleDate() {
        cal.add(Calendar.DAY_OF_MONTH, 13);
    }

    // номер мес¤ца c 01 - 12
    public String getMonthString() {

        String str = "" + (cal.get(Calendar.MONTH) + 1);
        if (str.length() == 1)
            str = "0" + str;
        return str;
    }

    // номер мес¤ца c 01 - 12
    public String getDayString() {

        String str = "" + cal.get(Calendar.DAY_OF_MONTH);
        ;
        if (str.length() == 1)
            str = "0" + str;
        return str;
    }

    // день месяца( какой по счету день в месяце 1 - 31)
    public String getWidgetDate() {
        String str_date = getDayString() + "." + getMonthString() + "."
                + cal.get(Calendar.YEAR) + " (";
        SetOldStyleDate();
        str_date += getDayString() + "." + getMonthString() + "."
                + cal.get(Calendar.YEAR) + ")";
        SetNewStyleDate();
        return str_date;
    }
}
