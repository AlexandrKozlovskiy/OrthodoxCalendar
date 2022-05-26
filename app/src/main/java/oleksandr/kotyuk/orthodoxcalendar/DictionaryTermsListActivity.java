package oleksandr.kotyuk.orthodoxcalendar;

import oleksandr.kotyuk.orthodoxcalendar.adapters.MySimpleCursorTreeAdapter;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;

import androidx.appcompat.app.ActionBar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleCursorTreeAdapter;

public class DictionaryTermsListActivity extends AppCompatActivity {

    private DatabaseHelper db;
    Cursor cursor;
    String sql = "";

    ExpandableListView elvMain;
    SimpleCursorTreeAdapter sctAdapter;

    public int pageLine;

    static final String TAG = "my_log";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_dictionary_terms);

        sql = "select * from termin_list;";
        db = DatabaseHelper.getInstance(this);

        // готовим данные по группам для адаптера
        cursor = db.executeQuery(sql);
        //cursor = db.getCompanyData();
        //startManagingCursor(cursor);

        // сопоставление данных и View для групп
        String[] groupFrom = {"name"};
        int[] groupTo = {R.id.text_list_group};
        // сопоставление данных и View для элементов
        String[] childFrom = {"name"};
        int[] childTo = {R.id.text_list_child};

        // создаем адаптер и настраиваем список
        SimpleCursorTreeAdapter sctAdapter = new MyAdapter(this, cursor,
                R.layout.my_simple_expandable_list_item_1, groupFrom,
                groupTo, R.layout.my_simple_list_item_1, childFrom,
                childTo);
        elvMain = (ExpandableListView) findViewById(R.id.elv_dictionary_terms);
        elvMain.setAdapter(sctAdapter);

        elvMain.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView paramExpandableListView,
                                        View paramView, int paramInt1, int paramInt2, long paramLong) {
                Log.d(TAG, "paramInt1=" + paramInt1 + " paramInt2=" + paramInt2 + " paramLong=" + paramLong);

                // создаем новое окно просмотра статей, передаем номер раздела
                Intent intent = new Intent(paramExpandableListView.getContext(),
                        DescriptionActivity.class);
                intent.putExtra("id", 7);
                intent.putExtra("id_termin", (int) paramLong);
                startActivity(intent);
                return true;
            }
        });
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
            String sql2 = "select _id, id_termin_list, name from termin_description where id_termin_list=" + idColumn;
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
    }


    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
