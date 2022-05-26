package oleksandr.kotyuk.orthodoxcalendar.fragments;

import oleksandr.kotyuk.orthodoxcalendar.DescriptionActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.adapters.MySimpleCursorTreeAdapter;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleCursorTreeAdapter;

public class FragmentDirectory2 extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    static final String TAG = "myLogs";

    private DatabaseHelper db;
    Cursor cursor;

    MyCalendar cal = MyCalendar.getInstance();

    String text_size = "0";

    String sql = "";

    ExpandableListView elvMain;
    SimpleCursorTreeAdapter sctAdapter;

    String[] groupFrom;
    int[] groupTo;
    String[] childFrom;
    int[] childTo;

    View v;

    public FragmentDirectory2() {

    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.fragment_view_pager_psaltur, null);
        v = inflater.inflate(R.layout.activity_dictionary_terms, null);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
        cursor = db.executeQuery("select * from termin_list;");

        // сопоставление данных и View для групп
        String[] groupFrom = {"name"};
        int[] groupTo = {R.id.text_list_group};
        // сопоставление данных и View для элементов
        String[] childFrom = {"name"};
        int[] childTo = {R.id.text_list_child};

        // создаем адаптер и настраиваем список
        SimpleCursorTreeAdapter sctAdapter = new MyAdapter(getActivity(), cursor,
                R.layout.my_simple_expandable_list_item_1, groupFrom, groupTo,
                R.layout.my_simple_list_item_1, childFrom, childTo);
        elvMain = (ExpandableListView) v.findViewById(R.id.elv_dictionary_terms);
        elvMain.setAdapter(sctAdapter);

        elvMain.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(
                    ExpandableListView paramExpandableListView, View paramView,
                    int paramInt1, int paramInt2, long paramLong) {
                Log.d(TAG, "paramInt1=" + paramInt1 + " paramInt2=" + paramInt2
                        + " paramLong=" + paramLong);

                // создаем новое окно просмотра статей, передаем номер раздела
                Intent intent = new Intent(
                        paramExpandableListView.getContext(),
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
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        // Log.d(TAG, "FragmentPrayers===onDestroyView()");
        if (cursor != null)
            cursor.close();
        if (db != null)
            db.closeConnecion();
    }

}
