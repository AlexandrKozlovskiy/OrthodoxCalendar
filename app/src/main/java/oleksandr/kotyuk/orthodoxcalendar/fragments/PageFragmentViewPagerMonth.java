package oleksandr.kotyuk.orthodoxcalendar.fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import oleksandr.kotyuk.orthodoxcalendar.MyView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.ListFragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

import oleksandr.kotyuk.orthodoxcalendar.FontsHelper;
import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.MyView;
import oleksandr.kotyuk.orthodoxcalendar.adapters.MyArrayAdapter;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;

public class PageFragmentViewPagerMonth extends ListFragment implements
        OnClickListener {

    static final String TAG = "myLogs";

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;

    int _data;
    int _month;
    int _year;

    // Calendar calendar;
    private DatabaseHelper db;
    Cursor cursor;


    MyView MyView_month_name_list;

    private final String FONT_PATH1 = "fonts/Russo_One.ttf";

    String data[];
    String day[];
    String values_gr[];
    String values_big[];
    String values_post[];

    MyCalendar cal = MyCalendar.getInstance();

    String text_size = "0";
    float standart_text_size1 = 0;
    boolean flag = false;

    Calendar calendar = new GregorianCalendar();
    int _month2 = calendar.get(Calendar.MONTH) + 1;
    int _year2 = calendar.get(Calendar.YEAR);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        addDataArray();

        findTextVisYear();

        MyArrayAdapter adapter = new MyArrayAdapter(getActivity(),
                R.layout.my_list_item_month, values_gr, values_big, data, day,
                values_post);
        setListAdapter(adapter);

        Log.d(TAG, "!!!onActivityCreated!!! ");
        /*
         * MyArrayAdapter adapter = new MyArrayAdapter (getActivity(),
         * R.layout.my_list_item_month, values_gr, values_big, data,
         * day,values_post); setListAdapter(adapter);
         */


        if (_month == _month2 && _year == _year2) {
            // выставляем фокус на нужную дату в списке
            //int pos=cal.getDayMonth()-1;
            int pos = calendar.get(Calendar.DAY_OF_MONTH) - 1;
            getListView().setSelection(pos);
            //getListView().smoothScrollToPosition(pos);
        }

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (cursor != null)
            cursor.close();
        if (db != null)
            db.closeConnecion();
    }

    // если високосный год вносим изменения
    public void findTextVisYear() {
        if (cal.getYear() == 2020) {
            if (cal.getMonth() + 1 == 2) {// && data==29
                // text_all_holidays=findTextVisYear().replace("\r\n", "<br>");
                cursor = null;
                String sql = "select holiday_month from data_calendar_leap_year where month=2 AND day=29;";
                // Log.d(TAG,"запросDayGR= " + sql);
                db = DatabaseHelper.getInstance(getActivity()
                        .getApplicationContext());
                cursor = db.executeQuery(sql);

                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        try {
                            values_big[28] = cursor.getString(cursor
                                    .getColumnIndex("holiday_month"));
                        } catch (NumberFormatException e) {
                            // Log.d(TAG, "ERROR=" + e.toString());
                        }
                    }
                }
                //Log.d(TAG,"запрос!!!!!!!= " + values_gr[28]);
                values_gr[28] = "";
                //values_big[28] = "";
            }
            if (cal.getMonth() + 1 == 3) {// && (data>0 && data<14)
                // text_all_holidays=findTextVisYear().replace("\r\n", "<br>");
                cursor = null;
                String sql = "select holiday_month from data_calendar_leap_year where month=3;";
                // Log.d(TAG,"запросDayGR= " + sql);
                db = DatabaseHelper.getInstance(getActivity()
                        .getApplicationContext());
                cursor = db.executeQuery(sql);

                int ii = 0;
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            try {
                                values_big[ii] = cursor.getString(cursor
                                        .getColumnIndex("holiday_month"));
                                values_gr[ii] = "";
                                ii++;
                            } catch (NumberFormatException e) {
                                // Log.d(TAG, "ERROR=" + e.toString());
                            }
                        } while (cursor.moveToNext());
                    }
                }
            }
        }
    }

    // вытаскиваем из базы даные по месяцу и вносим в массивы
    public void addDataArray() {

        int position = 0;
        String buffer;

        // db =
        // DatabaseHelper.getInstance(getActivity().getApplicationContext());
        String sql = "SELECT _id, day, ru_great_holiday, ru_big_holiday, p"
                + cal.getYear() + " FROM data_calendar WHERE month="
                + (cal.getMonth() + 1) + ";";
        // Log.d(TAG,"запрос= " + sql);
        db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
        cursor = db.executeQuery(sql);

        if (cursor != null) {
            int c = cursor.getCount();
            if (cal.v_month == cal.getMonth() && cal.v_year != cal.getYear()) {
                c--;
            }
            data = new String[c];
            day = new String[c];
            values_gr = new String[c];
            values_big = new String[c];
            values_post = new String[c];
            if (cursor.moveToFirst()) {
                do {
                    data[position] = cursor.getString(cursor
                            .getColumnIndex("day"));

                    day[position] = cal.getDayWeekNamesShort(cal.getYear(),
                            cal.getMonth(), position + 1);

                    buffer = cursor.getString(cursor
                            .getColumnIndex("ru_great_holiday"));
                    if (buffer.equals("#"))
                        buffer = "";
                    values_gr[position] = buffer;

                    buffer = cursor.getString(cursor
                            .getColumnIndex("ru_big_holiday"));
                    if (buffer.equals("#"))
                        buffer = "";
                    values_big[position] = buffer;

                    /*
                     * buffer=cursor.getString(cursor.getColumnIndex("p"+cal.getYear
                     * ())); if(buffer.equals("#")) buffer="";
                     * values_post[position]=buffer;
                     */

                    values_post[position] = cursor.getString(cursor
                            .getColumnIndex("p" + cal.getYear()));
                    /*
                     * String str = cursor.getString(cursor.getColumnIndex("p" +
                     * cal.getYear())); int i = str.indexOf("Поста нет"); int j
                     * = str.indexOf("Постный день"); if (i != -1 || j != -1) {
                     * if (i != -1) values_post[position]="Поста нет"; else
                     * values_post[position]="Постный день";
                     *
                     * } else values_post[position]="";
                     */

                    // Log.d(TAG,"position= " + position);
                    // Log.d(TAG,"c= " + c);
                    if (position == (c - 1))
                        break;
                    position++;
                } while (cursor.moveToNext());

            }
        }

        // делаем выборку по перемещаемым GREAT праздникам
        cursor = null;
        sql = "SELECT ru_great_holiday, d" + cal.getYear()
                + " FROM gr_unmovable_holiday WHERE m" + cal.getYear() + "="
                + (cal.getMonth() + 1) + ";";
        // Log.d(TAG,"запрос1= " + sql);

        db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
        cursor = db.executeQuery(sql);
        // position=0;
        if (cursor != null && cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {
                    AddValuesGrMonth(cursor.getInt(cursor.getColumnIndex("d"
                            + cal.getYear())), cursor.getString(cursor
                            .getColumnIndex("ru_great_holiday")));
                } while (cursor.moveToNext());

            }
        }

        // делаем выборку по перемищаемим BIG праздникам
        cursor = null;
        sql = "SELECT ru_big_holiday, level, d" + cal.getYear()
                + " FROM big_unmovable_holiday WHERE m" + cal.getYear() + "="
                + (cal.getMonth() + 1) + ";";
        // Log.d(TAG,"запрос1= " + sql);
        db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
        cursor = db.executeQuery(sql);
        // position=0;
        if (cursor != null && cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {
                    AddValuesBigMonth(cursor.getInt(cursor.getColumnIndex("d"
                                    + cal.getYear())), cursor.getString(cursor
                                    .getColumnIndex("ru_big_holiday")),
                            cursor.getInt(cursor.getColumnIndex("level")));
                } while (cursor.moveToNext());

            }
        }

        cursor.close();
    }

    // добавляем перемещаемы GREAT праздники в массив
    void AddValuesGrMonth(int data, String values) {
        int index = data - 1;

        if (index >= 0 && index < 31) {
            if (values_gr[index].length() == 0)
                values_gr[index] = values;
            else
                values_gr[index] = values_gr[index] + "<br>" + values;
        }
    }

    // добавляем перемещаемы BIG праздники в массив
    void AddValuesBigMonth(int data, String values, int level) {
        int index = data - 1;

        if (index >= 0 && index < 31) {
            switch (level) {
                case 0:
                    if (values_big[index].length() == 0)
                        values_big[index] = values;
                    else
                        values_big[index] = values + "<br>" + values_big[index];
                    break;
                case 1:
                    if (values_big[index].length() == 0)
                        values_big[index] = values;
                    else {
                        int i = values_big[index].indexOf("\r\n");
                        if (i == -1)
                            values_big[index] = values_big[index] + "<br>" + values;
                        else {
                            // values_big[index].replace("\r\n",
                            // "<br>"+values+"<br>");

                            StringBuilder sb = new StringBuilder(values_big[index]);
                            sb.replace(i, i + 2, ("<br>" + values + "<br>"));
                            values_big[index] = sb.toString();
                        }
                    }
                    break;
                case 2:
                    if (values_big[index].length() == 0)
                        values_big[index] = values;
                    else
                        values_big[index] = values_big[index] + "<br>" + values;
                    break;

                default:
                    break;
            }
        }
    }

    static PageFragmentViewPagerMonth newInstance(int page) {
        PageFragmentViewPagerMonth pageFragmentMonth = new PageFragmentViewPagerMonth();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragmentMonth.setArguments(arguments);
        return pageFragmentMonth;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_view_pager_month, null);

        MyView_month_name_list = (MyView) view
                .findViewById(R.id.MyView_month_name_list);
        ViewCompat.setAccessibilityHeading(MyView_month_name_list, true);
        MyView_month_name_list.setTypeface(FontsHelper.getTypeFace(
                getActivity().getApplicationContext(), FONT_PATH1));
        // Log.d(TAG,"pageNumber"+ pageNumber);
        cal.AddMonth(pageNumber);

        // _data=cal.getDayMonth();
        _month = cal.getMonth() + 1;
        _year = cal.getYear();

        // cal.getMonthName();
        MyView_month_name_list.setText(cal.getMonthName2() + " "
                + cal.getYear() + "г.(нов. ст.)");

        return view;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        _data = position + 1;

        // //////////////////////////////////////////////////////////////////////////////////
        /*
         * Intent intent=new Intent(getActivity(), DescriptionActivity.class);
         * intent.putExtra("id", 4); intent.putExtra("data", _data);
         * intent.putExtra("month", _month); intent.putExtra("year", _year);
         * startActivity(intent);
         */
        // //////////////////////////////////////////////////////////////////////////////////
        // Toast.makeText(getActivity(), "position = " + position,
        // Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        if (standart_text_size1 == 0)
            standart_text_size1 = MyView_month_name_list.getTextSize();// 22

        String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(
                getActivity(), "pref_text_size", "0");
        if (!text_size.equals(tmp)) {
            text_size = tmp;
            // int int_text_size=Integer.valueOf(text_size);
            /*
             * Toast.makeText(getActivity(), text_size+"="+int_text_size,
             * Toast.LENGTH_SHORT).show();
             */
            // float size=MyView_holiday.getTextSize();
            if (text_size.equals("-5")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 10);

            }
            if (text_size.equals("-4")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 8);

            }
            if (text_size.equals("-3")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 6);

            }
            if (text_size.equals("-2")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 4);

            }
            if (text_size.equals("-1")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 2);

            }
            if (text_size.equals("0")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1);

            }
            if (text_size.equals("+1")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 2);

            }
            if (text_size.equals("+2")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 4);

            }
            if (text_size.equals("+3")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 6);

            }
            if (text_size.equals("+4")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 8);

            }
            if (text_size.equals("+5")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 10);

            }
            if (text_size.equals("+6")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 12);

            }
            if (text_size.equals("+7")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 14);

            }
            if (text_size.equals("+8")) {
                MyView_month_name_list.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 16);

            }

            MyArrayAdapter adapter = new MyArrayAdapter(getActivity(),
                    R.layout.my_list_item_month, values_gr, values_big, data,
                    day, values_post);
            setListAdapter(adapter);

            if (_month == _month2 && _year == _year2) {
                // выставляем фокус на нужную дату в списке
                //int pos=cal.getDayMonth()-1;
                int pos = calendar.get(Calendar.DAY_OF_MONTH) - 1;
                getListView().setSelection(pos);
                //getListView().smoothScrollToPosition(pos);
            }

        }

        /*
         * MyArrayAdapter adapter = new MyArrayAdapter (getActivity(),
         * R.layout.my_list_item_month, values_gr, values_big, data,
         * day,values_post); setListAdapter(adapter);
         */
    }

}
