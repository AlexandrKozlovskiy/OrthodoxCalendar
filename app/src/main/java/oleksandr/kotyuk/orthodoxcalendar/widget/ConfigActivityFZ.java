package oleksandr.kotyuk.orthodoxcalendar.widget;

import oleksandr.kotyuk.orthodoxcalendar.R;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ConfigActivityFZ extends Activity {

    int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
    Intent resultValue;

    final String LOG_TAG = "myLogs";

    public final static String WIDGET_PREF = "widget_pref";
    // public final static String WIDGET_TEXT = "widget_text_";
    public final static String WIDGET_COLOR_TRANSPARENT = "widget_color_transparent_";
    public final static String WIDGET_VISIBLE_WEEK = "widget_visible_week_";
    public final static String WIDGET_TEXT_SIZE = "widget_text_size_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Log.d(LOG_TAG, "onCreate config");

        // извлекаем ID конфигурируемого виджета
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // и проверяем его корректность
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        // формируем intent ответа
        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);

        // отрицательный ответ
        setResult(RESULT_CANCELED, resultValue);

        setContentView(R.layout.config_fz);

        SharedPreferences sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
        int widgetColorTransparent = sp.getInt(
                ConfigActivityFZ.WIDGET_COLOR_TRANSPARENT + widgetID, 0);
        int widgetVisibleWeek = sp.getInt(ConfigActivityFZ.WIDGET_VISIBLE_WEEK
                + widgetID, 0);
        int widgetTextSize = sp.getInt(ConfigActivity.WIDGET_TEXT_SIZE
                + widgetID, 0);

        // адаптер
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                R.layout.my2_simple_spinner_item, getResources()
                .getStringArray(R.array.widget_spiner1));
        adapter1.setDropDownViewResource(R.layout.my3_simple_spinner_dropdown_item);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinnerConfig1);
        spinner1.setAdapter(adapter1);
        // заголовок
        // spinner1.setPrompt("Title");
        // выделяем элемент
        switch (widgetColorTransparent) {
            case 0:
                spinner1.setSelection(0);
                break;
            case 20:
                spinner1.setSelection(1);
                break;
            case 40:
                spinner1.setSelection(2);
                break;
            case 60:
                spinner1.setSelection(3);
                break;
            default:
                spinner1.setSelection(0);
                break;
        }

        // адаптер
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                R.layout.my2_simple_spinner_item, getResources()
                .getStringArray(R.array.widget_spiner2));
        adapter2.setDropDownViewResource(R.layout.my3_simple_spinner_dropdown_item);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerConfig2);
        spinner2.setAdapter(adapter2);
        // заголовок
        // spinner2.setPrompt("Title");
        switch (widgetVisibleWeek) {
            case 0:
                spinner2.setSelection(0);
                break;
            case 1:
                spinner2.setSelection(1);
                break;
            default:
                spinner2.setSelection(0);
                break;

        }

        // адаптер
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                R.layout.my2_simple_spinner_item, getResources()
                .getStringArray(R.array.entries_text_size));
        adapter3.setDropDownViewResource(R.layout.my3_simple_spinner_dropdown_item);

        Spinner spinner3 = (Spinner) findViewById(R.id.spinnerConfig3);
        spinner3.setAdapter(adapter3);
        // заголовок
        // spinner2.setPrompt("Title");
        switch (widgetTextSize) {
            case -5:
                spinner3.setSelection(0);
                break;
            case -4:
                spinner3.setSelection(1);
                break;
            case -3:
                spinner3.setSelection(2);
                break;
            case -2:
                spinner3.setSelection(3);
                break;
            case -1:
                spinner3.setSelection(4);
                break;
            case 0:
                spinner3.setSelection(5);
                break;
            case 1:
                spinner3.setSelection(6);
                break;
            case 2:
                spinner3.setSelection(7);
                break;
            case 3:
                spinner3.setSelection(8);
                break;
            case 4:
                spinner3.setSelection(9);
                break;
            case 5:
                spinner3.setSelection(10);
                break;
            case 6:
                spinner3.setSelection(11);
                break;
            case 7:
                spinner3.setSelection(12);
                break;
            case 8:
                spinner3.setSelection(13);
                break;

            default:
                spinner3.setSelection(0);
                break;

        }

    }

    public void onClick(View v) {

        Spinner spinner1 = (Spinner) findViewById(R.id.spinnerConfig1);
        int selRBColor = spinner1.getSelectedItemPosition();
        int color_transparent = 0;
        switch (selRBColor) {
            case 0:
                Log.d(LOG_TAG, "radioTransparent_0");
                color_transparent = 0;
                break;
            case 1:
                Log.d(LOG_TAG, "radioTransparent_20");
                color_transparent = 20;
                break;
            case 2:
                Log.d(LOG_TAG, "radioTransparent_40");
                color_transparent = 40;
                break;
            case 3:
                Log.d(LOG_TAG, "radioTransparent_60");
                color_transparent = 60;
                break;
        }

        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerConfig2);
        int selRBWeek = spinner2.getSelectedItemPosition();
        int week = 0;
        switch (selRBWeek) {
            case 0:
                // Log.d(LOG_TAG, "radioTransparent_0");
                week = 0;
                break;
            case 1:
                // Log.d(LOG_TAG, "radioTransparent_20");
                week = 1;
                break;
        }

        Spinner spinner3 = (Spinner) findViewById(R.id.spinnerConfig3);
        int selRBTS = spinner3.getSelectedItemPosition();
        int ts = 0;
        switch (selRBTS) {
            case 0:
                ts = -5;
                break;
            case 1:
                ts = -4;
                break;
            case 2:
                ts = -3;
                break;
            case 3:
                ts = -2;
                break;
            case 4:
                ts = -1;
                break;
            case 5:
                ts = 0;
                break;
            case 6:
                ts = 1;
                break;
            case 7:
                ts = 2;
                break;
            case 8:
                ts = 3;
                break;
            case 9:
                ts = 4;
                break;
            case 10:
                ts = 5;
                break;
            case 11:
                ts = 6;
                break;
            case 12:
                ts = 7;
                break;
            case 13:
                ts = 8;
                break;
        }

        Log.d(LOG_TAG, "color_transparent=" + color_transparent);
        // EditText etText = (EditText) findViewById(R.id.etText);

        // Записываем значения с экрана в Preferences
        SharedPreferences sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
        Editor editor = sp.edit();
        // editor.putString(WIDGET_TEXT + widgetID,
        // etText.getText().toString());
        editor.putInt(WIDGET_COLOR_TRANSPARENT + widgetID, color_transparent);
        editor.putInt(WIDGET_VISIBLE_WEEK + widgetID, week);
        editor.putInt(WIDGET_TEXT_SIZE + widgetID, ts);
        editor.commit();

        // В этих строках мы вызываем метод updateWidget и передаем туда
        // context, AppWidgetManager, SharedPreferences и ID экземпляра. Они
        // должны сработать после того, как мы сохраним Preferences
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        MyWidgetFZ.updateWidget(this, appWidgetManager, widgetID, sp);

        // положительный ответ
        setResult(RESULT_OK, resultValue);

        // Log.d(LOG_TAG, "finish config " + widgetID);
        finish();
    }
}
