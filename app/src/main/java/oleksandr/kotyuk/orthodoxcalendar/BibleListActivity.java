package oleksandr.kotyuk.orthodoxcalendar;

import java.util.ArrayList;

import oleksandr.kotyuk.orthodoxcalendar.adapters.MyArrayAdapterBibleBookmarks;
import oleksandr.kotyuk.orthodoxcalendar.adapters.MySimpleCursorTreeAdapter;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;

import androidx.appcompat.app.ActionBar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorTreeAdapter;

import oleksandr.kotyuk.orthodoxcalendar.MyView;


public class BibleListActivity extends AppCompatActivity {

    private DatabaseHelper db;
    Cursor cursor;
    String sql = "";

    ExpandableListView elvMain;
    SimpleCursorTreeAdapter sctAdapter;

    MyView tv_title_bookmarks;
    int intent_id = 1;
    int intent_id_bible_book = 0;
    int intent_line_text_bible_book = 0;

    public int pageLine;

    static final String TAG = "my_log";

    String[] list_bookmarks;

    MenuItem menu_item1;

    boolean flag = true;
    boolean flag_open_dialog_long = true;

    float size = 0;

    boolean save_page = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        // получаем ID выбраного раздела(подпункта)
        Intent intent = getIntent();
        intent_id = intent.getIntExtra("id", 1);

        intent_id_bible_book = intent.getIntExtra("id_bible_book", 0);
        intent_line_text_bible_book = intent.getIntExtra("line_text_bible_book", 0);

        save_page = intent.getBooleanExtra("save_page", true);

        if (intent_id == 3) {
            setContentView(R.layout.activity_bible_bookmarks_list);

            tv_title_bookmarks = (MyView) findViewById(R.id.tvBibleBookmarks);
            ViewCompat.setAccessibilityHeading(tv_title_bookmarks, true);
            if (size == 0) size = tv_title_bookmarks.getTextSize();
            String text_size = PreferencesActivity.MyPreferenceFragment.ReadString(
                    this, "pref_text_size", "0");
            if (text_size.equals("-5")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 10);
            }
            if (text_size.equals("-4")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 8);
            }
            if (text_size.equals("-3")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 6);
            }
            if (text_size.equals("-2")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 4);
            }
            if (text_size.equals("-1")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 2);
            }
            if (text_size.equals("0")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            }
            if (text_size.equals("+1")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 2);
            }
            if (text_size.equals("+2")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 4);
            }
            if (text_size.equals("+3")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 6);
            }
            if (text_size.equals("+4")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 8);
            }
            if (text_size.equals("+5")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 10);
            }
            if (text_size.equals("+6")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 12);
            }
            if (text_size.equals("+7")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 14);
            }
            if (text_size.equals("+8")) {
                tv_title_bookmarks.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 16);
            }

            onCreateBibleBookmarks();

        } else {
            setContentView(R.layout.activity_bible_list);

            if (intent_id == 1) sql = "select * from bible where _id<51;";
            else sql = "select * from bible where _id>50;";
            db = DatabaseHelper.getInstance(this);

            // готовим данные по группам для адаптера
            cursor = db.executeQuery(sql);
            //cursor = db.getCompanyData();
            //startManagingCursor(cursor);

            // сопоставление данных и View для групп
            String[] groupFrom = {"name_book"};
            int[] groupTo = {R.id.text_list_group};
            // сопоставление данных и View для элементов
            String[] childFrom = {"chapter_name"};
            int[] childTo = {R.id.text_list_child};

            // создаем адаптер и настраиваем список
            SimpleCursorTreeAdapter sctAdapter = new MyAdapter(this, cursor,
                    R.layout.my_simple_expandable_list_item_1, groupFrom,
                    groupTo, R.layout.my_simple_list_item_1, childFrom,
                    childTo);
            elvMain = (ExpandableListView) findViewById(R.id.elv_bible);
            elvMain.setAdapter(sctAdapter);
            if (intent_id_bible_book != 0) {
                int group = 0;
                int child = 0;
                sql = "select id_bible, chapter_id from bible_book where _id=" + intent_id_bible_book;
                db = DatabaseHelper.getInstance(this);
                cursor = db.executeQuery(sql);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        try {
                            //ID_BIBLE = cursor.getInt(cursor.getColumnIndex("id_bible"));
                            group = cursor.getInt(cursor
                                    .getColumnIndex("id_bible")) - 1;
                            child = cursor.getInt(cursor
                                    .getColumnIndex("chapter_id")) - 1;
                            if (intent_id_bible_book > 1101) group = group - 50;
                        } catch (NumberFormatException e) {
                            group = 0;
                            child = 0;
                        }
                    }
                }

                elvMain.expandGroup(group);
                elvMain.setSelectedChild(group, child, false);

            }

            elvMain.setOnChildClickListener(new OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView paramExpandableListView,
                                            View paramView, int paramInt1, int paramInt2, long paramLong) {
                    Log.d(TAG, "paramInt1=" + paramInt1 + " paramInt2=" + paramInt2 + " paramLong=" + paramLong);
                    // создаем новое окно просмотра статей, передаем номер раздела
                    Intent intent = new Intent(paramExpandableListView.getContext(),
                            BibleReadActivity.class);
                    intent.putExtra("id", (int) paramLong);
                    intent.putExtra("save_page", true);
                    startActivity(intent);
                    return true;
                }
            });
        }
    }

    public void onCreateBibleBookmarks() {
        ArrayList<String> arr_bookmark = PreferencesActivity.MyPreferenceFragment.getList(this, "bible_bookmarks");
        int i = 0;
        String[] mas_title;
        String[] mas_text;
        if (arr_bookmark == null) {
            //list_bookmarks=new String [1];
            //list_bookmarks[0] ="Чтобы добавить закладку, нажмите на нужный стих и удерживайте, за 2 секунды появиться диалог добавления закладки.";
            mas_title = new String[1];
            mas_title[0] = "";
            mas_text = new String[1];
            mas_text[0] = "Чтобы добавить закладку, нажмите на нужный стих и удерживайте, за 2 секунды появится диалог добавления закладки.";
            tv_title_bookmarks.setText("Сохраненные закладки");
        } else {
            i = arr_bookmark.size();
            list_bookmarks = arr_bookmark.toArray(new String[arr_bookmark.size()]);
            mas_title = new String[i];
            mas_text = new String[i];
            tv_title_bookmarks.setText("Сохраненные закладки (" + i + ")");
        }

        // находим список
        ListView lvMain = (ListView) findViewById(R.id.lvBibleBookmarks);

        if (i > 0) {
            for (int j = 0; j < i; j++) {

                String[] text_line_bookmark = arr_bookmark.get(j).split(",");
                int id_book = Integer.parseInt(text_line_bookmark[0]);
                int id_page_line = Integer.parseInt(text_line_bookmark[1]);

                sql = "select (select name_book from bible where _id=bible_book.id_bible) as name_bible, chapter_name, chapter_text from bible_book where _id=" + id_book;
                db = DatabaseHelper.getInstance(this);
                cursor = db.executeQuery(sql);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        try {
                            if (id_book < 1102) {
                                mas_title[j] = "Ветхий Завет\n" + cursor.getString(cursor
                                        .getColumnIndex("name_bible")) + ". " + cursor.getString(cursor
                                        .getColumnIndex("chapter_name"));
                            } else {
                                mas_title[j] = "Новый Завет\n" + cursor.getString(cursor
                                        .getColumnIndex("name_bible")) + ". " + cursor.getString(cursor
                                        .getColumnIndex("chapter_name"));
                            }
                            mas_text[j] = getShortText(cursor.getString(cursor.getColumnIndex("chapter_text")), id_page_line);
                        } catch (NumberFormatException e) {
                            mas_title[j] = "";
                            mas_text[j] = "";
                        }
                    }
                }
            }
        }

        // создаем адаптер
        MyArrayAdapterBibleBookmarks adapter = new MyArrayAdapterBibleBookmarks(this,
                R.layout.my_list_item_bible_bookmarks, mas_title, mas_text);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                ArrayList<String> arr_bookmark = PreferencesActivity.MyPreferenceFragment.getList(getApplicationContext(), "bible_bookmarks");
                if (arr_bookmark != null && flag_open_dialog_long) {
                    pageLine = position;
                    String[] mas_tmp = arr_bookmark.get(position).split(",");
                    int id_bible_book = Integer.parseInt(mas_tmp[0]);
                    int line_text_bible_book = Integer.parseInt(mas_tmp[1]);
                    int intent_id_number;
                    if (id_bible_book < 1102) intent_id_number = 1;
                    else intent_id_number = 2;
                    Intent intent = new Intent(getApplicationContext(), BibleListActivity.class);
                    intent.putExtra("id", intent_id_number);
                    intent.putExtra("id_bible_book", id_bible_book);
                    intent.putExtra("line_text_bible_book", line_text_bible_book);
                    intent.putExtra("save_page", false);
                    startActivity(intent);
                }
            }
        });

        lvMain.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view, int position, long id) {
                ArrayList<String> arr_bookmark = PreferencesActivity.MyPreferenceFragment.getList(getApplicationContext(), "bible_bookmarks");
                if (arr_bookmark != null) {
                    flag_open_dialog_long = false;
                    pageLine = position;
                    DialogFragment dialog_bookmarks = new MyDialogBibleBookmarks();
                    dialog_bookmarks.show(getSupportFragmentManager(), "dialog_bible_read");
                }
                return false;
            }
        });
    }

    public String getShortText(String text, int line) {
        int start = text.indexOf("<p>" + line);
        int end = text.indexOf("<p>" + (line + 1));
        if (end == -1) end = text.length();
        else end = end - 2;
        return text.substring(start, end).replace("<p>", "");
    }

    public Dialog createDialog() {
        return new AlertDialog.Builder(this)
                // Set Dialog Icon
                //.setIcon(R.drawable.androidhappy)
                // Set Dialog Title
                .setTitle(R.string.bible_dialog4)
                // Set Dialog Message
                .setMessage(R.string.bible_dialog5)

                // Positive button
                .setPositiveButton(R.string.bible_dialog1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity(), "YES", Toast.LENGTH_LONG).show();
                        flag_open_dialog_long = true;
                        ArrayList<String> arr_bookmark = PreferencesActivity.MyPreferenceFragment.getList(BibleListActivity.this, "bible_bookmarks");
                        if (arr_bookmark != null) {
                            arr_bookmark.remove(pageLine);
                            PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "bible_bookmarks", arr_bookmark);
                            onCreateBibleBookmarks();
                        }

                    }
                })

                // Negative Button
                .setNegativeButton(R.string.bible_dialog2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity(), "NO", Toast.LENGTH_LONG).show();
                        flag_open_dialog_long = true;
                    }
                }).create();
    }

    public static class MyDialogBibleBookmarks extends DialogFragment implements OnClickListener {

        final String LOG_TAG = "myLogs";

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return ((BibleListActivity) getActivity()).createDialog();
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

        }

    }

    class MyAdapter extends MySimpleCursorTreeAdapter {

        Context context;

        public MyAdapter(Context context, Cursor cursor, int groupLayout,
                         String[] groupFrom, int[] groupTo, int childLayout,
                         String[] childFrom, int[] childTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo,
                    childLayout, childFrom, childTo);
            this.context = context;
        }

        protected Cursor getChildrenCursor(Cursor groupCursor) {
            // получаем курсор по элементам для конкретной группы
            int idColumn = groupCursor.getInt(groupCursor.getColumnIndex("_id"));
            String sql2 = "select _id, id_bible, chapter_name, chapter_id from bible_book where id_bible=" + idColumn;
            db = DatabaseHelper.getInstance(context);

            // готовим данные по группам для адаптера
            return db.executeQuery(sql2);
            //return db.getPhoneData(groupCursor.getInt(idColumn));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //Log.d(TAG, "onCreateOptionsMenu");
        if (intent_id == 3) {
            getMenuInflater().inflate(R.menu.main_bookmarks, menu);
            menu_item1 = (MenuItem) menu.findItem(R.id.item1_bookmarks_menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    // позволяет востановить данные
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Log.d(LOG_TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        // Log.d(LOG_TAG, "onRestoreInstanceState");
    }

    // позволяет сохранить данные
    protected void onSaveInstanceState(Bundle outState) {
        // Log.d(LOG_TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        // Log.d(LOG_TAG, "onSaveInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (intent_id_bible_book != 0 && flag) {
            flag = false;
            Intent intent = new Intent(this, BibleReadActivity.class);
            intent.putExtra("id", intent_id_bible_book);
            intent.putExtra("line_text_bible_book", intent_line_text_bible_book);
            intent.putExtra("save_page", save_page);
            startActivity(intent);
        }
    }


    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        if (intent_id == 3) {

            tv_title_bookmarks = (MyView) findViewById(R.id.tvBibleBookmarks);

            onCreateBibleBookmarks();

        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (cursor != null)
            cursor.close();
        if (db != null)
            db.closeConnecion();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // cursor.close();
        super.onBackPressed();
        flag_open_dialog_long = true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
            case R.id.item1_bookmarks_menu:
                ArrayList<String> arr_bookmark = PreferencesActivity.MyPreferenceFragment.getList(this, "bible_bookmarks");
                if (arr_bookmark != null) {
                    arr_bookmark.clear();
                    PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "bible_bookmarks", arr_bookmark);
                    onCreateBibleBookmarks();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
