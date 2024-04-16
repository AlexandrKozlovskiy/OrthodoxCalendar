package oleksandr.kotyuk.orthodoxcalendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import oleksandr.kotyuk.orthodoxcalendar.DescriptionOtherActivity;
import oleksandr.kotyuk.orthodoxcalendar.adapters.MyArrayAdapterHolySearch;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;

public class SearchHolyActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    static final String TAG = "myLogs";
    Spinner spinner;
    MyView tvCountSearch;
    private DatabaseHelper db;
    Cursor cursor;
    String sql = "";
    ArrayList<String> arr_id_name_holy = new ArrayList<String>();
    ArrayList<String> arr_name_holy = new ArrayList<String>();
    ArrayList<String> arr_memory_date_holy = new ArrayList<>();
    int count_item_search = 0;
    CheckBox checkboxRegistr;
    float standart_text_size = 0;
    String text_size = "0";

    String name_holy_group = "Все";
    String[] myArray_holy_group;
    private java.util.Arrays Arrays;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_search_holy);

        ActionBar actionBar = getSupportActionBar();
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvCountSearch = (MyView) findViewById(R.id.MyView_holy_search);
        checkboxRegistr = (CheckBox) findViewById(R.id.checkBoxSearchHoly);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.my_simple_spinner_item_bible, getResources()
                .getStringArray(R.array.entries_holy_search_group));
        adapter.setDropDownViewResource(R.layout.my_simple_spinner_dropdown_item_bible);

        spinner = (Spinner) findViewById(R.id.spinner_holy_search);
        spinner.setAdapter(adapter);
        // заглавие
        spinner.setPrompt("Групы");
        // выделяем элемент
        spinner.setSelection(0);

        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                SetSpinerItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void SetSpinerItemSelected(int position) {
        myArray_holy_group = getResources().getStringArray(R.array.entries_holy_search_group);
        name_holy_group = myArray_holy_group[position];
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        if (standart_text_size == 0)
            standart_text_size = tvCountSearch.getTextSize();// 19

        String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(this,
                "pref_text_size", "0");
        if (!text_size.equals(tmp)) {
            text_size = tmp;
            if (text_size.equals("-5")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size - 10);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size - 10);
            }
            if (text_size.equals("-4")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size - 8);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size - 8);
            }
            if (text_size.equals("-3")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size - 6);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size - 6);
            }
            if (text_size.equals("-2")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size - 4);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size - 4);
            }
            if (text_size.equals("-1")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size - 2);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size - 2);
            }
            if (text_size.equals("0")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size);
            }
            if (text_size.equals("+1")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 2);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 2);
            }
            if (text_size.equals("+2")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 4);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 4);
            }
            if (text_size.equals("+3")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 6);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 6);
            }
            if (text_size.equals("+4")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 8);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 8);
            }
            if (text_size.equals("+5")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 10);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 10);
            }
            if (text_size.equals("+6")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 12);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 12);
            }
            if (text_size.equals("+7")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 14);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 14);
            }
            if (text_size.equals("+8")) {
                tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 16);
                checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        standart_text_size + 16);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        getMenuInflater().inflate(R.menu.main_bible_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(searchItem);
        searchView.setQueryHint("Поиск... (мин. 3 символа)");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "Search text is " + query);

        if (query.length() > 2) {
            SearchForList(query);
            SearchMemoryDateHoly();
            createSearchList();
        }
        return false;
    }

    void alert(String message, String title) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setTitle(title);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        //Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    public void SearchMemoryDateHoly() {

        if (arr_id_name_holy != null && arr_id_name_holy.size() > 0) {

            StringBuilder tmp_memory_date_holy = new StringBuilder();
            String tmp_id_search = "";

            MyCalendar cal = MyCalendar.getInstance();
            String this_year = "" + cal.getYear();
            boolean flag_v_year = this_year.equals("" + cal.v_year);

            db = DatabaseHelper.getInstance(this);
            for (int i = 0; i < arr_id_name_holy.size(); i++) {
                tmp_id_search = arr_id_name_holy.get(i);

                //таблица date_calendar (убираем даты высокосного года 2.29-3.13)  AND NOT (month = 3 AND day >= 1 AND day < 14);
                if (flag_v_year) {
                    sql = "SELECT month, day FROM data_calendar WHERE (ru_great_holiday LIKE '%id=" + tmp_id_search + "\"%' OR ru_big_holiday LIKE '%id=" + tmp_id_search + "\"%' OR ru_holiday LIKE '%id=" + tmp_id_search + "\"%' OR ru_icon_holiday LIKE '%id=" + tmp_id_search + "\"%')" + " AND NOT (month = 3 AND day >= 1 AND day < 14);";
                } else {
                    sql = "SELECT month, day FROM data_calendar WHERE ru_great_holiday LIKE '%id=" + tmp_id_search + "\"%' OR ru_big_holiday LIKE '%id=" + tmp_id_search + "\"%' OR ru_holiday LIKE '%id=" + tmp_id_search + "\"%' OR ru_icon_holiday LIKE '%id=" + tmp_id_search + "\"%';";
                }
                cursor = db.executeQuery(sql);
                tmp_memory_date_holy = new StringBuilder();
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            try {
                                int monthColumnIndex = cursor.getColumnIndex("month");
                                int dayColumnIndex = cursor.getColumnIndex("day");
                                int tmp_int_day = cursor.getInt(dayColumnIndex);
                                int tmp_int_month = cursor.getInt(monthColumnIndex);
                                String tmp_string_day = Integer.toString(tmp_int_day);
                                String tmp_string_month = Integer.toString(tmp_int_month);
                                if (tmp_int_day < 10) tmp_string_day = "0" + tmp_string_day;
                                if (tmp_int_month < 10) tmp_string_month = "0" + tmp_string_month;

                                tmp_memory_date_holy.append(tmp_string_day).append(".").append(tmp_string_month).append(".").append(this_year).append("\n");
                            } catch (NumberFormatException e) {
                                Log.d(TAG, "ERROR=" + e.toString());
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }

                //таблица date_calendar_leap_year (добавляем даты высокосного года 2.29-3.13)
                sql = "SELECT month, day FROM data_calendar_leap_year WHERE holiday LIKE '%id=" + tmp_id_search + "\"%';";
                cursor = db.executeQuery(sql);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            try {
                                int monthColumnIndex = cursor.getColumnIndex("month");
                                int dayColumnIndex = cursor.getColumnIndex("day");
                                int tmp_int_day = cursor.getInt(dayColumnIndex);
                                int tmp_int_month = cursor.getInt(monthColumnIndex);
                                String tmp_string_day = Integer.toString(tmp_int_day);
                                String tmp_string_month = Integer.toString(tmp_int_month);
                                if (tmp_int_day < 10) tmp_string_day = "0" + tmp_string_day;
                                if (tmp_int_month < 10) tmp_string_month = "0" + tmp_string_month;

                                tmp_memory_date_holy.append(tmp_string_day).append(".").append(tmp_string_month).append(".").append(this_year).append("\n");
                            } catch (NumberFormatException e) {
                                Log.d(TAG, "ERROR=" + e.toString());
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }

                //таблица gr_unmovable_holiday (добавляем переходящие даты)
                if (flag_v_year) {
                    sql = "SELECT m" + this_year + ", d" + this_year + " FROM gr_unmovable_holiday WHERE (ru_great_holiday LIKE '%id=" + tmp_id_search + "\"%') AND NOT (m" + this_year + " = 3 AND d" + this_year + " >= 1 AND d" + this_year + " < 14)";
                } else {
                    sql = "SELECT m" + this_year + ", d" + this_year + " FROM gr_unmovable_holiday WHERE ru_great_holiday LIKE '%id=" + tmp_id_search + "\"%';";
                }
                cursor = db.executeQuery(sql);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            try {
                                int monthColumnIndex = cursor.getColumnIndex("m" + this_year);
                                int dayColumnIndex = cursor.getColumnIndex("d" + this_year);
                                int tmp_int_day = cursor.getInt(dayColumnIndex);
                                int tmp_int_month = cursor.getInt(monthColumnIndex);
                                String tmp_string_day = Integer.toString(tmp_int_day);
                                String tmp_string_month = Integer.toString(tmp_int_month);
                                if (tmp_int_day < 10) tmp_string_day = "0" + tmp_string_day;
                                if (tmp_int_month < 10) tmp_string_month = "0" + tmp_string_month;

                                tmp_memory_date_holy.append(tmp_string_day).append(".").append(tmp_string_month).append(".").append(this_year).append("\n");
                            } catch (NumberFormatException e) {
                                Log.d(TAG, "ERROR=" + e.toString());
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }

                //таблица big_unmovable_holiday (добавляем переходящие даты)
                if (flag_v_year) {
                    sql = "SELECT m" + this_year + ", d" + this_year + " FROM big_unmovable_holiday WHERE (ru_big_holiday LIKE '%id=" + tmp_id_search + "\"%') AND NOT (m" + this_year + " = 3 AND d" + this_year + " >= 1 AND d" + this_year + " < 14)";
                } else {
                    sql = "SELECT m" + this_year + ", d" + this_year + " FROM big_unmovable_holiday WHERE ru_big_holiday LIKE '%id=" + tmp_id_search + "\"%';";
                }
                cursor = db.executeQuery(sql);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            try {
                                int monthColumnIndex = cursor.getColumnIndex("m" + this_year);
                                int dayColumnIndex = cursor.getColumnIndex("d" + this_year);
                                int tmp_int_day = cursor.getInt(dayColumnIndex);
                                int tmp_int_month = cursor.getInt(monthColumnIndex);
                                String tmp_string_day = Integer.toString(tmp_int_day);
                                String tmp_string_month = Integer.toString(tmp_int_month);
                                if (tmp_int_day < 10) tmp_string_day = "0" + tmp_string_day;
                                if (tmp_int_month < 10) tmp_string_month = "0" + tmp_string_month;

                                tmp_memory_date_holy.append(tmp_string_day).append(".").append(tmp_string_month).append(".").append(this_year).append("\n");
                            } catch (NumberFormatException e) {
                                Log.d(TAG, "ERROR=" + e.toString());
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }

                //таблица unmovable_holiday (добавляем переходящие даты)
                if (flag_v_year) {
                    sql = "SELECT m" + this_year + ", d" + this_year + " FROM unmovable_holiday WHERE (ru_holiday LIKE '%id=" + tmp_id_search + "\"%') AND NOT (m" + this_year + " = 3 AND d" + this_year + " >= 1 AND d" + this_year + " < 14)";
                } else {
                    sql = "SELECT m" + this_year + ", d" + this_year + " FROM unmovable_holiday WHERE ru_holiday LIKE '%id=" + tmp_id_search + "\"%';";
                }

                cursor = db.executeQuery(sql);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            try {
                                int monthColumnIndex = cursor.getColumnIndex("m" + this_year);
                                int dayColumnIndex = cursor.getColumnIndex("d" + this_year);
                                int tmp_int_day = cursor.getInt(dayColumnIndex);
                                int tmp_int_month = cursor.getInt(monthColumnIndex);
                                String tmp_string_day = Integer.toString(tmp_int_day);
                                String tmp_string_month = Integer.toString(tmp_int_month);
                                if (tmp_int_day < 10) tmp_string_day = "0" + tmp_string_day;
                                if (tmp_int_month < 10) tmp_string_month = "0" + tmp_string_month;

                                tmp_memory_date_holy.append(tmp_string_day).append(".").append(tmp_string_month).append(".").append(this_year).append("\n");
                            } catch (NumberFormatException e) {
                                Log.d(TAG, "ERROR=" + e.toString());
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                //arr_memory_date_holy.add(tmp_memory_date_holy.toString());
                arr_memory_date_holy.add(SortMemoryDateHoly(tmp_memory_date_holy.toString()));
            }
            assert cursor != null;
            cursor.close();
        }
    }

    public String SortMemoryDateHoly(String text) {
        StringBuilder tmp_sort_memory_date_holy = new StringBuilder(text);
        if (!text.isEmpty()) {
            tmp_sort_memory_date_holy = new StringBuilder(text.replaceAll("$\\n", ""));
            if (tmp_sort_memory_date_holy.toString().contains("\n")) {

                String[] lines = text.split("\n");

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

                Date[] arrayOfDates = new Date[lines.length];
                for (int index = 0; index < lines.length; index++) {
                    try {
                        arrayOfDates[index] = sdf.parse(lines[index]);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                // Сортуйте arrayOfDates
                Arrays.sort(arrayOfDates);

                tmp_sort_memory_date_holy.setLength(0);
                // Форматуйте результати назад у рядки
                for (int index = 0; index < lines.length; index++) {
                    if (lines.length - 1 > index) {
                        tmp_sort_memory_date_holy.append(sdf.format(arrayOfDates[index])).append("\n");
                    } else {
                        tmp_sort_memory_date_holy.append(sdf.format(arrayOfDates[index]));
                    }
                }

            }
        }

        return tmp_sort_memory_date_holy.toString();
    }

    @SuppressLint("Range")
    public void SearchForList(String text) {
        String text_UpCase = text.toUpperCase(Locale.getDefault());

        if (arr_id_name_holy != null)
            arr_id_name_holy.clear();
        if (arr_name_holy != null)
            arr_name_holy.clear();
        if(arr_memory_date_holy!=null) arr_memory_date_holy.clear();

        count_item_search = 0;

        //SELECT _id, name FROM description_holiday WHERE name_title = 'Преподобные' ORDER BY name;
        //SELECT _id, name FROM description_holiday ORDER BY name;
        if (name_holy_group.equals(myArray_holy_group[0])) {
            sql = "SELECT _id, name FROM description_holiday WHERE name_title <> '#' ORDER BY name;";
        } else {
            sql = "SELECT _id, name FROM description_holiday WHERE name_title = \"" + name_holy_group + "\" ORDER BY name;";
        }

        db = DatabaseHelper.getInstance(this);
        cursor = db.executeQuery(sql);


        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        int idColumnIndex = cursor.getColumnIndex("_id");
                        int nameColumnIndex = cursor.getColumnIndex("name");
                        arr_id_name_holy.add(cursor.getString(idColumnIndex));
                        arr_name_holy.add(cursor.getString(nameColumnIndex));
                    } catch (NumberFormatException e) {
                        Log.d(TAG, "ERROR=" + e.toString());
                    }
                } while (cursor.moveToNext());
            }

        }
        assert cursor != null;
        cursor.close();

        for (int i = arr_name_holy.size() - 1; i >= 0; i--) {
            if (checkboxRegistr.isChecked()) {
                if (!arr_name_holy.get(i).contains(text)) {
                    arr_name_holy.remove(i);
                    arr_id_name_holy.remove(i);
                }
            } else {
                String text_arr_UpCase = arr_name_holy.get(i).toUpperCase(Locale.getDefault());
                if (!text_arr_UpCase.contains(text_UpCase)) {
                    arr_name_holy.remove(i);
                    arr_id_name_holy.remove(i);
                }
            }
        }
        count_item_search = arr_name_holy.size();
        String tmp_set_text = "Найдено результатов - " + Integer.toString(count_item_search);
        tvCountSearch.setText(tmp_set_text);
    }

    public void createSearchList() {
        if (arr_id_name_holy != null) {
            int iii = arr_id_name_holy.size();
        }
        // конвертируем ArrayList в массив
        String[] my_arr_memory_date_holy = arr_memory_date_holy.toArray(new String[arr_memory_date_holy.size()]);

        String[] my_arr_search_holy = arr_name_holy.toArray(new String[arr_name_holy.size()]);

        ListView lvMain = (ListView) findViewById(R.id.listViewSearchHoly);
        MyArrayAdapterHolySearch adapter = new MyArrayAdapterHolySearch(this,
                R.layout.my_list_item_search_holy, my_arr_memory_date_holy, my_arr_search_holy);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent_description_holy = new Intent(getApplicationContext(), DescriptionOtherActivity.class);
                intent_description_holy.putExtra("id", arr_id_name_holy.get(position));
                intent_description_holy.putExtra("description_id_search_holy", "DescriptionHolyActivityHostFree");
                startActivity(intent_description_holy);
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "New text is " + newText);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Log.d(LOG_TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        // Log.d(LOG_TAG, "onRestoreInstanceState");
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Log.d(LOG_TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        // Log.d(LOG_TAG, "onSaveInstanceState");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (arr_id_name_holy != null) arr_id_name_holy.clear();
        if (arr_name_holy != null) arr_name_holy.clear();
        if (arr_memory_date_holy != null) arr_memory_date_holy.clear();

        if (cursor != null) cursor.close();
        if (db != null) db.closeConnecion();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // cursor.close();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "Cliced MenuItem is " + item.getTitle());
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
