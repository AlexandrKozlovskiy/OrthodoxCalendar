package oleksandr.kotyuk.orthodoxcalendar.widget;

import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.SplashScreen;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendarWidget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class MyWidgetFZ extends AppWidgetProvider {
    static final String TAG = "myLogs";
    final static String ACTION_ON_CLICK = "ua.sasha.kotuk.listwidget.itemonclickFZ";
    final static String ITEM_POSITION = "item_position";

    static MyCalendarWidget cal;

    static private DatabaseHelper db;
    static Cursor cursor;

    @SuppressLint("SimpleDateFormat")
// SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        SharedPreferences sp = context.getSharedPreferences(
                ConfigActivityFZ.WIDGET_PREF, Context.MODE_PRIVATE);

        for (int i : appWidgetIds) {
            updateWidget(context, appWidgetManager, i, sp);
        }
    }

    static void updateWidget(Context context,
                             AppWidgetManager appWidgetManager, int appWidgetId,
                             SharedPreferences sp) {
        Log.d(TAG, "MyProviderFZ ListWidget");
        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.widget_fz);
        rv.setContentDescription(R.id.imageView1, context.getString(R.string.widget_app_name2));
        rv.setContentDescription(R.id.imageView2, context.getString(R.string.widget_app_name));
        setUpdateTV(rv, context, appWidgetId, sp);

        setOpenApp(rv, context, appWidgetId);

        setOpenConfigActivity(rv, context, appWidgetId);

        setList(rv, context, appWidgetId);

        setListClick(rv, context, appWidgetId);

        appWidgetManager.updateAppWidget(appWidgetId, rv);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,
                R.id.lvListFZ);
    }

    static void setUpdateTV(RemoteViews rv, Context context, int appWidgetId,
                            SharedPreferences sp) {
        cal = MyCalendarWidget.getInstance();
        cal.setTodayDate();
        int widgetColorTransparent = sp.getInt(
                ConfigActivityFZ.WIDGET_COLOR_TRANSPARENT + appWidgetId, 0);
        int widgetVisibleWeek = sp.getInt(ConfigActivityFZ.WIDGET_VISIBLE_WEEK
                + appWidgetId, 0);
        int widgetTextSize = sp.getInt(ConfigActivityFZ.WIDGET_TEXT_SIZE
                + appWidgetId, 0);
        Log.d(TAG, "widgetColorTransparent=" + widgetColorTransparent);
        switch (widgetColorTransparent) {
            case 0:
                if (cal.getDayWeek() == 1) {
                    rv.setInt(R.id.relativeLayout_widget1FZ,
                            "setBackgroundResource", R.drawable.ro2_widget);
                    rv.setInt(R.id.relativeLayout_widget2FZ,
                            "setBackgroundResource", R.drawable.rx2_widget);
                } else {
                    rv.setInt(R.id.relativeLayout_widget1FZ,
                            "setBackgroundResource", R.drawable.ro1_widget);
                    rv.setInt(R.id.relativeLayout_widget2FZ,
                            "setBackgroundResource", R.drawable.rx1_widget);
                }
                break;
            case 20:
                if (cal.getDayWeek() == 1) {
                    rv.setInt(R.id.relativeLayout_widget1FZ,
                            "setBackgroundResource", R.drawable.ro2_widget_20);
                    rv.setInt(R.id.relativeLayout_widget2FZ,
                            "setBackgroundResource", R.drawable.rx2_widget_20);
                } else {
                    rv.setInt(R.id.relativeLayout_widget1FZ,
                            "setBackgroundResource", R.drawable.ro1_widget_20);
                    rv.setInt(R.id.relativeLayout_widget2FZ,
                            "setBackgroundResource", R.drawable.rx1_widget_20);
                }
                break;
            case 40:
                if (cal.getDayWeek() == 1) {
                    rv.setInt(R.id.relativeLayout_widget1FZ,
                            "setBackgroundResource", R.drawable.ro2_widget_40);
                    rv.setInt(R.id.relativeLayout_widget2FZ,
                            "setBackgroundResource", R.drawable.rx2_widget_40);
                } else {
                    rv.setInt(R.id.relativeLayout_widget1FZ,
                            "setBackgroundResource", R.drawable.ro1_widget_40);
                    rv.setInt(R.id.relativeLayout_widget2FZ,
                            "setBackgroundResource", R.drawable.rx1_widget_40);
                }
                break;
            case 60:
                if (cal.getDayWeek() == 1) {
                    rv.setInt(R.id.relativeLayout_widget1FZ,
                            "setBackgroundResource", R.drawable.ro2_widget_60);
                    rv.setInt(R.id.relativeLayout_widget2FZ,
                            "setBackgroundResource", R.drawable.rx2_widget_60);
                } else {
                    rv.setInt(R.id.relativeLayout_widget1FZ,
                            "setBackgroundResource", R.drawable.ro1_widget_60);
                    rv.setInt(R.id.relativeLayout_widget2FZ,
                            "setBackgroundResource", R.drawable.rx1_widget_60);
                }
                break;
            default:
                if (cal.getDayWeek() == 1) {
                    rv.setInt(R.id.relativeLayout_widget1FZ,
                            "setBackgroundResource", R.drawable.ro2_widget);
                    rv.setInt(R.id.relativeLayout_widget2FZ,
                            "setBackgroundResource", R.drawable.rx2_widget);
                } else {
                    rv.setInt(R.id.relativeLayout_widget1FZ,
                            "setBackgroundResource", R.drawable.ro1_widget);
                    rv.setInt(R.id.relativeLayout_widget2FZ,
                            "setBackgroundResource", R.drawable.rx1_widget);
                }
                break;
        }

        switch (widgetVisibleWeek) {
            case 0:
                rv.setViewVisibility(R.id.MyView_widget_sedmitsa_weekFZ,
                        View.VISIBLE);
                break;
            case 1:
                rv.setViewVisibility(R.id.MyView_widget_sedmitsa_weekFZ,
                        View.GONE);
                break;
            default:
                rv.setViewVisibility(R.id.MyView_widget_sedmitsa_weekFZ,
                        View.VISIBLE);
                break;
        }

        switch (widgetTextSize) {
            case -5:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 - 5);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 - 5);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 - 5);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 - 5);
                break;
            case -4:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 - 4);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 - 4);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 - 4);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 - 4);
                break;
            case -3:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 - 3);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 - 3);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 - 3);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 - 3);
                break;
            case -2:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 - 2);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 - 2);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 - 2);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 - 2);
                break;
            case -1:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 - 1);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 - 1);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 - 1);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 - 1);
                break;
            case 0:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14);
                break;
            case 1:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 + 1);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 + 1);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 + 1);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 + 1);
                break;
            case 2:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 + 2);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 + 2);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 + 2);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 + 2);
                break;
            case 3:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 + 3);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 + 3);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 + 3);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 + 3);
                break;
            case 4:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 + 4);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 + 4);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 + 4);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 + 4);
                break;
            case 5:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 + 5);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 + 5);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 + 5);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 + 5);
                break;
            case 6:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 + 6);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 + 6);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 + 6);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 + 6);
                break;
            case 7:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 + 7);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 + 7);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 + 7);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 + 7);
                break;
            case 8:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17 + 8);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17 + 8);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14 + 8);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14 + 8);
                break;
            default:
                rv.setFloat(R.id.MyView_widget_weekday_nameFZ, "setTextSize", 17);
                rv.setFloat(R.id.MyView_widget_dateFZ, "setTextSize", 17);
                rv.setFloat(R.id.MyView_widget_sedmitsa_weekFZ, "setTextSize", 14);
                rv.setFloat(R.id.MyView_widget_postFZ, "setTextSize", 14);
                break;
        }

        rv.setTextViewText(R.id.MyView_widget_weekday_nameFZ,
                cal.getDayWeekNamesLongCaps());

        rv.setTextViewText(R.id.MyView_widget_dateFZ, cal.getWidgetDate());

        if (cal.getDateEntersPeriods()) {
            // db =
            // DatabaseHelperWidget.getInstance(context.getApplicationContext());
            String sql = "SELECT p" + cal.getYear() + ", r" + cal.getYear()
                    + " FROM data_calendar WHERE month=" + (cal.getMonth() + 1)
                    + " AND day=" + cal.getDayMonth() + ";";

            db = DatabaseHelper.getInstance(context.getApplicationContext(), context.getSharedPreferences(SplashScreen.WIDGET_PREF, Context.MODE_PRIVATE));
            cursor = db.executeQuery(sql);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {

                        rv.setTextViewText(
                                R.id.MyView_widget_postFZ,
                                cursor.getString(cursor.getColumnIndex("p"
                                        + cal.getYear())));
                        String tmp = cursor.getString(cursor.getColumnIndex("r"
                                + cal.getYear()));
                        if (tmp.equals("#"))
                            tmp = "";
                        rv.setTextViewText(
                                R.id.MyView_widget_sedmitsa_weekFZ, tmp);

                    } catch (NumberFormatException e) {
                        // Log.d(TAG, "ERROR=" + e.toString());
                    }
                }
            } else {
                rv.setTextViewText(R.id.MyView_widget_postFZ, "");
                rv.setTextViewText(R.id.MyView_widget_sedmitsa_weekFZ, "");
            }
        } else {
            rv.setTextViewText(R.id.MyView_widget_post, "");
            rv.setTextViewText(R.id.MyView_widget_sedmitsa_week, "");
        }

        if (cursor != null)
            cursor.close();

        Intent updIntent = new Intent(context, MyWidgetFZ.class);
        updIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                new int[]{appWidgetId});
        PendingIntent updPIntent = PendingIntent.getBroadcast(context,
                appWidgetId, updIntent, 0);
        rv.setOnClickPendingIntent(R.id.imageView1FZ, updPIntent);
    }

    // по клику шапки виджета открываем календарь
    static void setOpenApp(RemoteViews rv, Context context, int appWidgetId) {
        // Log.d(TAG, "MyWidget setOpenApp()");
        Intent intent = new Intent(context, SplashScreen.class);
        // intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appId); //
        // Identifies the particular widget...
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        // Make the pending intent unique...
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        rv.setOnClickPendingIntent(R.id.relativeLayout_widget1FZ, pendIntent);

    }

    // по клику на кнопку открываем конфигурационный активити
    static void setOpenConfigActivity(RemoteViews rv, Context context,
                                      int appWidgetId) {
        Intent configIntent = new Intent(context, ConfigActivityFZ.class);
        configIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pIntent = PendingIntent.getActivity(context, appWidgetId,
                configIntent, 0);
        rv.setOnClickPendingIntent(R.id.imageView2FZ, pIntent);
    }

    static void setList(RemoteViews rv, Context context, int appWidgetId) {
        Intent adapter = new Intent(context, MyServiceFZ.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        Uri data = Uri.parse(adapter.toUri(Intent.URI_INTENT_SCHEME));
        adapter.setData(data);
        rv.setRemoteAdapter(R.id.lvListFZ, adapter);
    }

    static void setListClick(RemoteViews rv, Context context, int appWidgetId) {
        Intent listClickIntent = new Intent(context, MyWidgetFZ.class);
        listClickIntent.setAction(ACTION_ON_CLICK);
        PendingIntent listClickPIntent = PendingIntent.getBroadcast(context, 0,
                listClickIntent, 0);
        rv.setPendingIntentTemplate(R.id.lvListFZ, listClickPIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        // Log.d(TAG, "onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        // Log.d(TAG, "onDisabled");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        // Log.d(TAG, "onDeleted " + Arrays.toString(appWidgetIds));

        // ”дал¤ем Preferences
        Editor editor = context.getSharedPreferences(
                ConfigActivityFZ.WIDGET_PREF, Context.MODE_PRIVATE).edit();
        for (int widgetID : appWidgetIds) {
            // editor.remove(ConfigActivity.WIDGET_TEXT + widgetID);
            editor.remove(ConfigActivityFZ.WIDGET_COLOR_TRANSPARENT + widgetID);
            editor.remove(ConfigActivityFZ.WIDGET_VISIBLE_WEEK + widgetID);
            editor.remove(ConfigActivity.WIDGET_TEXT_SIZE + widgetID);
        }
        editor.commit();
        if (db != null)
            db.closeConnecion();
    }

}