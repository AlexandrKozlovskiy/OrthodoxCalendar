package oleksandr.kotyuk.orthodoxcalendar.widget;

import java.util.ArrayList;

import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendarWidget;

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

public class MyFactoryFZ implements RemoteViewsFactory {

    static final String TAG = "myLogs";
    ArrayList<String> data;
    Context context;
    // SimpleDateFormat sdf;
    int widgetID;

    private DatabaseHelper db;
    // private DatabaseHelperWidget db;
    Cursor cursor;
    MyCalendarWidget cal;
    String sql;

    String text_fz = "";

    SharedPreferences sp;
    int widgetTextSize = 0;

    @SuppressLint("SimpleDateFormat")
    MyFactoryFZ(Context ctx, Intent intent) {
        Log.d(TAG, "MyFactory-constructor");
        context = ctx;
        // sdf = new SimpleDateFormat("HH:mm:ss");
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        sp = context.getSharedPreferences(
                ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE);
        widgetTextSize = sp.getInt(ConfigActivity.WIDGET_TEXT_SIZE
                + widgetID, 0);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "MyFactory-onCreate()");
        data = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        Log.d(TAG, "MyFactory-getCount()");
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "MyFactory-getItemId");
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

        widgetTextSize = sp.getInt(ConfigActivity.WIDGET_TEXT_SIZE
                + widgetID, 0);

        RemoteViews rView = new RemoteViews(context.getPackageName(),
                R.layout.widget_item_fz);

        switch (widgetTextSize) {
            case -5:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 - 5);
                break;
            case -4:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 - 4);
                break;
            case -3:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 - 3);
                break;
            case -2:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 - 2);
                break;
            case -1:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 - 1);
                break;
            case 0:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16);
                break;
            case 1:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 + 1);
                break;
            case 2:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 + 2);
                break;
            case 3:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 + 3);
                break;
            case 4:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 + 4);
                break;
            case 5:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 + 5);
                break;
            case 6:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 + 6);
                break;
            case 7:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 + 7);
                break;
            case 8:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16 + 8);
                break;
            default:
                rView.setFloat(R.id.tvItemTextWidgetFZ, "setTextSize", 16);
                break;
        }
        if (position >= 0 && position < data.size()) {

            rView.setTextViewText(R.id.tvItemTextWidgetFZ, Html.fromHtml(data.get(position)));

            Intent clickIntent = new Intent();
            clickIntent.putExtra(MyWidgetFZ.ITEM_POSITION, position);
            rView.setOnClickFillInIntent(R.id.tvItemTextWidgetFZ, clickIntent);
        } else{
            rView.setTextViewText(R.id.tvItemTextWidgetFZ, "");
        }

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
        // Log.d(TAG, "MyFactory-onDataSetChanged");
        data.clear();

        cal = MyCalendarWidget.getInstance();
        cal.setTodayDate();
        if (cal.getDateEntersPeriods()) {
            // db =
            // DatabaseHelperWidget.getInstance(context.getApplicationContext());
            sql = "SELECT tf" + cal.getYear()
                    + " FROM data_calendar WHERE month=" + (cal.getMonth() + 1)
                    + " AND day=" + cal.getDayMonth() + ";";
            Log.d(TAG, "MyFactotyFZ sql=" + sql);

            int id_think_feofan1 = 0;
            int id_think_feofan2 = 0;
            //-------------------------------

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
            data.add("<FONT COLOR=RED><big><b>Мысли Феофана Затворника:</b></big></FONT>");

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
                data.add(text_fz);
                data.add("");

                if (id_think_feofan2 != 0) {
                    cursor = null;
                    sql = "select thinks_text from think_feofan where _id=" + id_think_feofan2 + ";";
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
                    data.add(text_fz);
                    data.add("");
                }

            } else {
                data.add("На сегодняшние Евангельские чтения отсутствуют мысли святителя Феофана Затворника.");
                data.add("");
            }
        } else {
            data.add("Пожалуйста проверьте дату на устройстве, программа работает в интервале 2022г - 2025г.");
            //data.add(" ");
        }

        if (cursor != null)
            cursor.close();
        // if(db!=null) db.closeConnecion();

    }

    @Override
    public void onDestroy() {

    }

}