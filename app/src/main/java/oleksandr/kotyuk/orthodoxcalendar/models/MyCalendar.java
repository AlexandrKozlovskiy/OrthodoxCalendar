package oleksandr.kotyuk.orthodoxcalendar.models;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyCalendar {

    Calendar cal;
    private static MyCalendar calendar;

    static final String[] month_names = {"января", "февраля", "марта",
            "апреля", "мая", "июня", "июля", "августа", "сентября", "октября",
            "ноября", "декабря"};

    static final String[] month_names2 = {"Январь", "Февраль", "Март",
            "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь",
            "Ноябрь", "Декабрь"};

    static final String[] day_week_names_long = {"", "Воскресенье",
            "Понедельник", "  Вторник  ", "   Среда   ", "  Четверг  ", "  Пятница  ", "  Суббота  ",};

    static final String[] day_week_names_long_caps = {"", "ВОСКРЕСЕНЬЕ",
            "ПОНЕДЕЛЬНИК", "  ВТОРНИК  ", "   СРЕДА   ", "  ЧЕТВЕРГ  ", "  ПЯТНИЦА  ", "  СУББОТА  ",};

    static final String[] day_week_names_short = {"", "Вс", "Пн", "Вт", "Ср",
            "Чт", "Пт", "Сб",};


    int r2014 = 365;
    int r2015 = 365;
    int r2016 = 366;
    int r2017 = 365;
    int r2018 = 365;
    int r2019 = 365;
    int r2020 = 366;
    int r2021 = 365;
    int r2022 = 365;

    //высокосный год
    public int v_year = 2021;
    //высокосный мес¤ц, начало от 0
    public int v_month = 1;

    // к-во дней в период с 2019г - 2022г
    @SuppressWarnings("unused")
    private final int PAGE_COUNT_DAY = 1461;

    // к-во мес¤цев в период с 2019г - 2022г
    @SuppressWarnings("unused")
    private final int PAGE_COUNT_MONTH = 48;

    private MyCalendar() {
        this.cal = new GregorianCalendar();
    }

    public static synchronized MyCalendar getInstance() {
        if (calendar == null) {
            calendar = new MyCalendar();
        }
        return calendar;
    }

    //устанавливаем календарь на определенную дату (мес¤ц)
    public void setDate(int year, int month, int day) {
        this.cal.set(year, month, day);
    }

    // часы
    public int getHours() {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    // минуты
    public int getMinutes() {
        return cal.get(Calendar.MINUTE);
    }

    public void setHours(int hour) {
        cal.set(Calendar.HOUR_OF_DAY, hour);
    }

    public void setMinutes(int minute) {
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    // номер года
    public int getYear() {
        return cal.get(Calendar.YEAR);
    }

    // номер года
    public String getShortYear() {
        String str = "" + getYear();
        str = str.substring(2, 4);
        return str;
    }

    // номер мес¤ца c 0 - 11
    public int getMonth() {

        return cal.get(Calendar.MONTH);
    }

    // номер мес¤ца c 01 - 12
    public String getMonthString() {

        String str = "" + cal.get(Calendar.MONTH);
        if (str.length() == 1) str = "0" + str;
        return str;
    }

    // название мес¤ца январ¤, ‘еврал¤...
    public String getMonthName() {

        return month_names[cal.get(Calendar.MONTH)];
    }

    // название мес¤ца январь, ‘евраль...
    public String getMonthName2() {

        return month_names2[cal.get(Calendar.MONTH)];
    }

    // день мес¤ца( какой по счету день в мес¤це 1 Ч 31)
    public int getDayMonth() {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    // день года (1- 366)
    public int getDayYear() {
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    // день недели (воскресенье - 1; субота - 7)
    public int getDayWeek() {
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    // название дн¤ недели (Воскресенье,Понедельник...)
    public String getDayWeekNamesLong() {
        return day_week_names_long[cal.get(Calendar.DAY_OF_WEEK)];
    }

    // название дня недели (ПОНЕДЕЛЬНИК, ВТОРНИК...)
    public String getDayWeekNamesLongCaps() {
        return day_week_names_long_caps[cal.get(Calendar.DAY_OF_WEEK)];
    }

    // название дня недели (Вс, Пн...)
    public String getDayWeekNamesShort(int year, int month, int day) {
        Calendar cal_tmp = new GregorianCalendar(year, month, day);
        return day_week_names_short[cal_tmp.get(Calendar.DAY_OF_WEEK)];
    }

    // получаем номер сегодн¤шней даты относительно периода с 2019г - 2022г
    public int getTodayDay() {
        int year_today = getYear();
        int today_day = 0;
        switch (year_today) {
            case 2019:
                today_day = 0 + getDayYear() - 1;
                break;
            case 2020:
                today_day = r2019 + getDayYear() - 1;
                break;
            case 2021:
                today_day = r2019 + r2020 + getDayYear() - 1;
                break;
            case 2022:
                today_day = r2019 + r2020 + r2021 + getDayYear() - 1;
                break;
            default:
                break;
        }

        return today_day;
    }

    // получаем номер сегодн¤шнего мес¤ца относительно периода с 2019г - 2022г
    public int getTodayMonth() {
        int year_today = getYear();
        int today_month = 0;
        switch (year_today) {
            case 2019:
                today_month = 0 + getMonth();
                break;
            case 2020:
                today_month = 12 + getMonth();
                break;
            case 2021:
                today_month = 24 + getMonth();
                break;
            case 2022:
                today_month = 36 + getMonth();
                break;
            default:
                break;
        }

        return today_month;
    }

    //устанавливаем дату по старому стилю -13 дней
    public void SetOldStyleDate() {
        cal.add(Calendar.DAY_OF_MONTH, -13);
    }

    //устанавливаем дату по новому стилю +13 дней
    public void SetNewStyleDate() {
        cal.add(Calendar.DAY_OF_MONTH, 13);
    }

    // добавляем (вычитаем) определенное количество дней (перелистывание вперед-назад по дате)
    public void AddDay(int value) {
        int day = getTodayDay();
        cal.add(Calendar.DAY_OF_MONTH, value - day);
    }

    // добавл¤ем (вычитаем) определенное количество дней дл¤ уведомлений
    public void AddDayNew(int value) {
        cal.add(Calendar.DAY_OF_YEAR, value);
    }

    // добавл¤ем (вычитаем) определенное количество мес¤цев (перелистывание вперед-назад по мес¤цам)
    public void AddMonth(int value) {
        //if(value!=0)cal.add(Calendar.MONTH, value);
        int month = getTodayMonth();
        cal.add(Calendar.MONTH, value - month);
    }

    //устанавливаем дату на сегодн¤шний день
    public void setTodayDate() {
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    //проверяем или дата на устройстве попадает в период с 2019 по 2022
    public boolean getDateEntersPeriods() {
        int today_year = this.getYear();
        if (today_year > 2018 && today_year < 2023) return true;
        else return false;
    }

    //получаем полное название даты 15/02/2019
    public String getFullNameDate() {
        return this.getDayMonth() + "/" + (this.getMonth() + 1) + "/" + this.getYear();
    }

    //Получаем время календаря в секундах.
    public long getTimeInMillis() {
        return cal.getTimeInMillis();
    }

    //Вычисляем дату Пасхи по старому/новому стилю,используя алгоритм Гаусса.
    public byte[] getEaster(String year, Boolean newStyle) {
        byte[] results = new byte[2];
        long easterYer = 0;
        try {
            easterYer = Long.parseLong(year);
        } catch (NumberFormatException e) {
            return new byte[]{0};
        }
        byte a = (byte) ((19 * (easterYer % 19) + 15) % 30);
        byte b = (byte) ((2 * (easterYer % 4) + 4 * (easterYer % 7) + 6 * a + 6) % 7);
        return new byte[]{(byte) (!newStyle ? (a + b <= 9 ? 22 + a + b : a + b - 9) : a + b <= 26 ? 4 + a + b : a + b - 26), (byte) (!newStyle ? (a + b <= 9 ? 2 : 3) : a + b <= 26 ? 3 : 4)};
    }

    //Получаем дату Пасхи по новому стилю.
    public byte[] getEaster(String year) {
        return getEaster(year, true);
    }

    //Получаем дату Пасхи по новому стилю в виде адекватной строки.
    public String getEasterDate(String year) {
        byte[] results = getEaster(year);
        if (results.length == 1) return "";
//else return (results[0]+13)%(results[1]==2?31:30)+" "+month_names[results[1]+(results[0]+13>(results[1]==2?31:30)?1:0)];
        else return results[0] + " " + month_names[results[1]];
    }
}
