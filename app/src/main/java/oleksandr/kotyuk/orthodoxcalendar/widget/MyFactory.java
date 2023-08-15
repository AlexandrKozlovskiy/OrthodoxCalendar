package oleksandr.kotyuk.orthodoxcalendar.widget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import java.util.ArrayList;

import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendarWidget;

public class MyFactory implements RemoteViewsFactory {

    static final String TAG = "myLogs";
    ArrayList<String> data;
    Context context;
    // SimpleDateFormat sdf;
    int widgetID;

    private DatabaseHelper db;
    // private DatabaseHelperWidget db;
    Cursor cursor;
    MyCalendarWidget cal;
    MyCalendar cal2;
    String sql;

    String text_gr_holiday = "";
    String text_big_holiday = "";
    String text_holiday = "";
    String text_icon_holiday = "";
    String text_fz = "";
//На сегодняшние Евангельские чтения отсутствуют мысли святителя Феофана Затворника.

    SharedPreferences sp;
    int widgetVisibleFZ = 0;
    int widgetTextSize = 0;

//public final static String WIDGET_PREF = "widget_pref";

    @SuppressLint("SimpleDateFormat")
    MyFactory(Context ctx, Intent intent) {
        // Log.d(TAG, "MyFactory-constructor");
        context = ctx;
        // sdf = new SimpleDateFormat("HH:mm:ss");
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        sp = context.getSharedPreferences(
                ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE);
        widgetVisibleFZ = sp.getInt(ConfigActivity.WIDGET_VISIBLE_FZ
                + widgetID, 0);
        widgetTextSize = sp.getInt(ConfigActivity.WIDGET_TEXT_SIZE
                + widgetID, 0);
    }

    @Override
    public void onCreate() {
        // Log.d(TAG, "MyFactory-onCreate()");
        data = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        // Log.d(TAG, "MyFactory-getCount()");
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        // Log.d(TAG, "MyFactory-getItemId");
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        // Log.d(TAG, "MyFactory-getLoadingView");
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        // Log.d(TAG, "MyFactory-getViewAt"+data.get(position));
        // Log.d(TAG, "MyFactory-getViewAt"+position);
        RemoteViews rView;

        widgetTextSize = sp.getInt(ConfigActivity.WIDGET_TEXT_SIZE
                + widgetID, 0);

        rView = new RemoteViews(context.getPackageName(),
                R.layout.widget_item);

        switch (widgetTextSize) {
            case -5:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 - 5);
                break;
            case -4:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 - 4);
                break;
            case -3:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 - 3);
                break;
            case -2:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 - 2);
                break;
            case -1:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 - 1);
                break;
            case 0:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16);
                break;
            case 1:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 + 1);
                break;
            case 2:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 + 2);
                break;
            case 3:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 + 3);
                break;
            case 4:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 + 4);
                break;
            case 5:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 + 5);
                break;
            case 6:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 + 6);
                break;
            case 7:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 + 7);
                break;
            case 8:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16 + 8);
                break;
            default:
                rView.setFloat(R.id.tvItemTextWidget, "setTextSize", 16);
                break;
        }

        rView.setTextViewText(R.id.tvItemTextWidget,
                Html.fromHtml(data.get(position)));
        // rView.setTextViewText(R.id.tvItemTextWidget, data.get(position));
        Intent clickIntent = new Intent();
        clickIntent.putExtra(MyWidget.ITEM_POSITION, position);
        rView.setOnClickFillInIntent(R.id.tvItemTextWidget, clickIntent);

        return rView;
    }

    @Override
    public int getViewTypeCount() {
        // Log.d(TAG, "MyFactory-getViewTypeCount");
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        // Log.d(TAG, "MyFactory-hasStableIds");
        return true;
    }

    @Override
    public void onDataSetChanged() {

        widgetVisibleFZ = sp.getInt(ConfigActivity.WIDGET_VISIBLE_FZ
                + widgetID, 0);
        // Log.d(TAG, "MyFactory-onDataSetChanged");
        data.clear();

        cal = MyCalendarWidget.getInstance();
        cal.setTodayDate();
        if (cal.getDateEntersPeriods()) {
            // db =
            // DatabaseHelperWidget.getInstance(context.getApplicationContext());
            sql = "SELECT _id, month, day, ru_great_holiday, ru_big_holiday, ru_holiday,ru_icon_holiday FROM data_calendar WHERE month="
                    + (cal.getMonth() + 1)
                    + " AND day="
                    + cal.getDayMonth()
                    + ";";

            db = DatabaseHelper.getInstance(context.getApplicationContext());
            cursor = db.executeQuery(sql);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        // String bufer;

                        text_gr_holiday = cursor.getString(cursor
                                .getColumnIndex("ru_great_holiday"));
                        if (text_gr_holiday.equals("#"))
                            text_gr_holiday = "";
                        else {
                            text_gr_holiday = text_gr_holiday.replace("\r\n",
                                    "<br>");
                            text_gr_holiday = "<FONT COLOR=RED><b>"
                                    + text_gr_holiday + "</b></FONT><br>";
                        }

                        text_big_holiday = cursor.getString(cursor
                                .getColumnIndex("ru_big_holiday"));
                        if (text_big_holiday.equals("#"))
                            text_big_holiday = "";
                        else {
                            text_big_holiday = text_big_holiday.replace("\r\n",
                                    "<br>");
                            text_big_holiday = text_big_holiday + "<br>";
                        }

                        text_holiday = cursor.getString(cursor
                                .getColumnIndex("ru_holiday"));
                        if (text_holiday.equals("#"))
                            text_holiday = "";
                        else {
                            text_holiday = text_holiday.replace("\r\n", "<br>");
                            text_holiday = text_holiday + "<br>";
                        }

                        text_icon_holiday = cursor.getString(cursor
                                .getColumnIndex("ru_icon_holiday"));
                        if (text_icon_holiday.equals("#"))
                            text_icon_holiday = "";
                        text_icon_holiday = text_icon_holiday.replace("\r\n",
                                "<br>");
                        if (!text_icon_holiday.equals(""))
                            text_icon_holiday = text_icon_holiday + "<br>";
                        // text_icon_holiday=text_icon_holiday+"<br>";

                    } catch (NumberFormatException e) {
                        // Log.d(TAG, "ERROR=" + e.toString());
                    }
                }
            }
            AddValuesGrDay();
            AddValuesBigDay();
            AddValuesDay();
            if (widgetVisibleFZ == 0) AddValuesFZ();
            if (cursor != null)
                cursor.close();

            String tmp = text_gr_holiday + text_big_holiday + text_holiday
                    + text_icon_holiday;

            cal2 = MyCalendar.getInstance();
            cal2.setTodayDate();
            int y = cal2.getYear();
            int m = cal2.getMonth() + 1;
            int d = cal2.getDayMonth();
            Log.d(TAG, "cal2.getYear()= " + y + "  cal2.getMonth()+1=" + m + "  cal2.getDayMonth()=" + d);
            if (y == 2024) {
                if (m == 2 && d == 29) {
                    tmp = findTextVisYear(context.getApplicationContext(), m, d);
                    //Log.d(TAG,"ru_great_holiday=" + ru_great_holiday+"  ru_big_holiday="+ru_big_holiday);
                }
                if (m == 3 && (d > 0 && d < 14)) {
                    tmp = findTextVisYear(context.getApplicationContext(), m, d);
                    //Log.d(TAG,"ru_great_holiday=" + ru_great_holiday+"  ru_big_holiday="+ru_big_holiday);
                }
            }

            if (widgetVisibleFZ == 0 && !text_fz.equals("")) {
                if (tmp.equals(""))
                    tmp = "<FONT COLOR=RED><big><b>Мысли Феофана Затворника:</b></big></FONT><br>" + text_fz;
                else
                    tmp = tmp + "<br><FONT COLOR=RED><big><b>Мысли Феофана Затворника:</b></big></FONT><br>" + text_fz;
            }
            Log.d(TAG, "Мысли Феофана Затворника:= " + tmp);
            if (tmp.equals("")) {
                data.add(" ");
            } else {
                if (tmp.contains("<br>")) {
                    String[] parts = tmp.split("<br>");
                    for (int i = 0; i < parts.length; i++) {
                        data.add(parts[i]);
                    }
                    data.add(" ");
                } else {
                    data.add(tmp);
                    data.add(" ");
                }

            }
        } else {
            data.add("Пожалуйста проверьте дату на устройстве, программа работает в интервале 2021г - 2024г.");
            data.add(" ");
        }
        // data.add("test");

    }

    public String findTextVisYear(Context context, int m, int d) {
        String text_holiday_vis_year = "";
        cursor = null;
        String sql = "select holiday from data_calendar_leap_year where month="
                + m + " AND day=" + d + ";";
        // Log.d(TAG,"запросDayGR= " + sql);
        db = DatabaseHelper.getInstance(context);
        cursor = db.executeQuery(sql);

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                try {
                    text_holiday_vis_year = cursor.getString(cursor
                            .getColumnIndex("holiday"));
                } catch (NumberFormatException e) {
                    // Log.d(TAG, "ERROR=" + e.toString());
                }
            }
        }
        return text_holiday_vis_year.replace("\r\n", "<br>") + "<br>";
    }

    // добавляем перемещаемые даты GREAT праздников
    public void AddValuesGrDay() {
        cursor = null;
        sql = "SELECT ru_great_holiday FROM gr_unmovable_holiday WHERE m"
                + cal.getYear() + "=" + (cal.getMonth() + 1) + " and d"
                + cal.getYear() + "=" + cal.getDayMonth();
        // Log.d(TAG,"запросDayGR= " + sql);

        db = DatabaseHelper.getInstance(context.getApplicationContext());
        cursor = db.executeQuery(sql);

        // Log.d(TAG,"cursor.getCount()= " + cursor.getCount());
        // Log.d(TAG,"text_gr_holiday= " + text_gr_holiday);
        if (cursor != null && cursor.getCount() > 0) {
            // Log.d(TAG,"text_gr_holiday= " + text_gr_holiday);
            if (cursor.moveToFirst()) {
                do {
                    if (text_gr_holiday.length() == 0) {
                        text_gr_holiday = "<FONT COLOR=RED><b>"
                                + cursor.getString(cursor
                                .getColumnIndex("ru_great_holiday"))
                                + "</b></FONT><br>";
                    } else {
                        text_gr_holiday = "<FONT COLOR=RED><b>"
                                + cursor.getString(cursor
                                .getColumnIndex("ru_great_holiday"))
                                + "</b></FONT>" + "<br>" + text_gr_holiday;
                    }
                } while (cursor.moveToNext());

            }
        }
        // text_gr_holiday="<FONT COLOR=RED>"+text_gr_holiday+"</FONT><br>";
    }

    // добавляем перемещаемые даты BIG праздников
    public void AddValuesBigDay() {
        cursor = null;
        sql = "SELECT ru_big_holiday, level FROM big_unmovable_holiday WHERE m"
                + cal.getYear() + "=" + (cal.getMonth() + 1) + " and d"
                + cal.getYear() + "=" + cal.getDayMonth();
        // Log.d(TAG,"запросDayBIG= " + sql);

        db = DatabaseHelper.getInstance(context.getApplicationContext());
        cursor = db.executeQuery(sql);

        // Log.d(TAG,"cursor.getCount()= " + cursor.getCount());
        // Log.d(TAG,"text_big_holiday= " + text_big_holiday);
        if (cursor != null && cursor.getCount() > 0) {
            // Log.d(TAG,"text_gr_holiday= " + text_gr_holiday);
            int length;
            if (cursor.moveToFirst()) {
                do {
                    length = text_big_holiday.length();

                    switch (cursor.getInt(cursor.getColumnIndex("level"))) {
                        case 0:
                            if (length == 0)
                                text_big_holiday = cursor.getString(cursor
                                        .getColumnIndex("ru_big_holiday")) + "<br>";
                            else
                                text_big_holiday = cursor.getString(cursor
                                        .getColumnIndex("ru_big_holiday"))
                                        + "<br>"
                                        + text_big_holiday;
                            break;
                        case 1:
                            if (length == 0)
                                text_big_holiday = cursor.getString(cursor
                                        .getColumnIndex("ru_big_holiday")) + "<br>";
                            else {
                                int i = text_big_holiday.indexOf("<br>");
                                if (i == -1)
                                    text_big_holiday = text_big_holiday
                                            + "<br>"
                                            + cursor.getString(cursor
                                            .getColumnIndex("ru_big_holiday"));
                                else {
                                    // values_big[index].replace("\r\n",
                                    // "<br>"+values+"<br>");

                                    StringBuilder sb = new StringBuilder(
                                            text_big_holiday);
                                    sb.replace(
                                            i,
                                            i + 4,
                                            ("<br>"
                                                    + cursor.getString(cursor
                                                    .getColumnIndex("ru_big_holiday")) + "<br>"));
                                    text_big_holiday = sb.toString();
                                }
                            }
                            break;
                        case 2:
                            if (length == 0)
                                text_big_holiday = cursor.getString(cursor
                                        .getColumnIndex("ru_big_holiday")) + "<br>";
                            else
                                text_big_holiday = text_big_holiday
                                        + cursor.getString(cursor
                                        .getColumnIndex("ru_big_holiday"))
                                        + "<br>";
                            break;

                        default:
                            break;
                    }

                } while (cursor.moveToNext());

            }
        }
        if (text_big_holiday.length() != 0)
            text_big_holiday = text_big_holiday.replace("\r\n", "<br>");
    }

    // добавляем перемещаемые даты небольших праздников
    public void AddValuesDay() {
        cursor = null;
        sql = "SELECT ru_holiday, level FROM unmovable_holiday WHERE m"
                + cal.getYear() + "=" + (cal.getMonth() + 1) + " and d"
                + cal.getYear() + "=" + cal.getDayMonth();
        // Log.d(TAG,"запросDayHOLIDAY= " + sql);

        db = DatabaseHelper.getInstance(context.getApplicationContext());
        cursor = db.executeQuery(sql);

        if (cursor != null && cursor.getCount() > 0) {

            int length;
            if (cursor.moveToFirst()) {
                do {
                    length = text_holiday.length();

                    switch (cursor.getInt(cursor.getColumnIndex("level"))) {

                        case 1:
                            if (length == 0)
                                text_holiday = cursor.getString(cursor
                                        .getColumnIndex("ru_holiday")) + "<br>";
                            else {
                                int i = text_holiday.indexOf("<br>");
                                if (i == -1)
                                    text_holiday = text_holiday
                                            + "<br>"
                                            + cursor.getString(cursor
                                            .getColumnIndex("ru_holiday"))
                                            + "<br>";
                                else {
                                    // values_big[index].replace("\r\n",
                                    // "<br>"+values+"<br>");

                                    StringBuilder sb = new StringBuilder(
                                            text_holiday);
                                    sb.replace(
                                            i,
                                            i + 4,
                                            ("<br>"
                                                    + cursor.getString(cursor
                                                    .getColumnIndex("ru_holiday")) + "<br>"));
                                    text_holiday = sb.toString();
                                }
                            }
                            break;
                        case 2:
                            if (length == 0)
                                text_holiday = cursor.getString(cursor
                                        .getColumnIndex("ru_holiday")) + "<br>";
                            else {
                                int i = text_holiday.indexOf("<br>");

                                if (i == -1)
                                    text_holiday = text_holiday
                                            + "<br>"
                                            + cursor.getString(cursor
                                            .getColumnIndex("ru_holiday"))
                                            + "<br>";
                                else {

                                    int j = text_holiday.indexOf("<br>", i);
                                    if (j == -1) {
                                        StringBuilder sb = new StringBuilder(
                                                text_holiday);
                                        sb.replace(
                                                i,
                                                i + 4,
                                                ("<br>"
                                                        + cursor.getString(cursor
                                                        .getColumnIndex("ru_holiday")) + "<br>"));
                                        text_holiday = sb.toString();
                                    } else {
                                        StringBuilder sb = new StringBuilder(
                                                text_holiday);
                                        sb.replace(
                                                j,
                                                j + 4,
                                                ("<br>"
                                                        + cursor.getString(cursor
                                                        .getColumnIndex("ru_holiday")) + "<br>"));
                                        text_holiday = sb.toString();
                                    }
                                }
                            }
                            break;
                        case 4:
                            if (text_icon_holiday.length() == 0)
                                text_icon_holiday = cursor.getString(cursor
                                        .getColumnIndex("ru_holiday")) + "<br>";
                            else
                                text_icon_holiday = text_icon_holiday
                                        + cursor.getString(cursor
                                        .getColumnIndex("ru_holiday")) + "<br>";
                            break;

                        default:
                            break;
                    }

                } while (cursor.moveToNext());

            }
        }
        if (text_holiday.length() != 0)
            text_holiday = text_holiday.replace("\r\n", "<br>");
        if (text_icon_holiday.length() != 0)
            text_icon_holiday = text_icon_holiday.replace("\r\n", "<br>");
    }

    void AddValuesFZ() {
        cursor = null;
        text_fz = "";
        sql = "SELECT tf" + cal.getYear()
                + " FROM data_calendar WHERE month=" + (cal.getMonth() + 1)
                + " AND day=" + cal.getDayMonth() + ";";
        //Log.d(TAG, "MyFactotyFZ sql=" + sql);

        int id_think_feofan1 = 0;
        int id_think_feofan2 = 0;


        db = DatabaseHelper.getInstance(context.getApplicationContext());
        cursor = db.executeQuery(sql);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                try {

                    String tmp_id_think_feofan = cursor.getString(cursor
                            .getColumnIndex("tf" + cal.getYear()));
                    int tmp_id = tmp_id_think_feofan.indexOf("#");
                    if (tmp_id != -1) {
                        String[] mas_id_think_feofan = tmp_id_think_feofan
                                .split("#");
                        id_think_feofan1 = Integer
                                .parseInt(mas_id_think_feofan[0]);
                        id_think_feofan2 = Integer
                                .parseInt(mas_id_think_feofan[1]);
                    } else {
                        id_think_feofan1 = Integer
                                .parseInt(tmp_id_think_feofan);
                    }

                    //Log.d(TAG, "id_fz_text=" + id_fz_text);
                } catch (NumberFormatException e) {
                    // Log.d(TAG, "ERROR=" + e.toString());
                }
            }
        }

        if (id_think_feofan1 != 0) {
            cursor = null;
            sql = "select thinks_text from think_feofan where _id=" + id_think_feofan1 + ";";
            // Log.d(TAG,"запросDayGR= " + sql);
            db = DatabaseHelper.getInstance(context.getApplicationContext());
            cursor = db.executeQuery(sql);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    try {
                        text_fz = cursor.getString(cursor
                                .getColumnIndex("thinks_text"));
                    } catch (NumberFormatException e) {
                        // Log.d(TAG, "ERROR=" + e.toString());
                    }

                }
            }

            if (id_think_feofan2 != 0) {
                cursor = null;
                sql = "select thinks_text from think_feofan where _id=" + id_think_feofan2 + ";";
                // Log.d(TAG,"запросDayGR= " + sql);
                db = DatabaseHelper.getInstance(context.getApplicationContext());
                cursor = db.executeQuery(sql);

                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        try {
                            text_fz = text_fz + "<br><br>" + cursor.getString(cursor
                                    .getColumnIndex("thinks_text"));
                        } catch (NumberFormatException e) {
                            // Log.d(TAG, "ERROR=" + e.toString());
                        }

                    }
                }
            }

        } else {
            text_fz = "На сегодняшние Евангельские чтения отсутствуют мысли святителя Феофана Затворника.";
        }

    }

    @Override
    public void onDestroy() {

    }

}