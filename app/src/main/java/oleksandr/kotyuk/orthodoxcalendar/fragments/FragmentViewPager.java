package oleksandr.kotyuk.orthodoxcalendar.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import oleksandr.kotyuk.orthodoxcalendar.MainActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;

public class FragmentViewPager extends Fragment {

    static final String TAG = "myLogs";

    int selected_item_position = 0;
    MyCalendar cal = MyCalendar.getInstance();

    // к-во дней в период с 2020г - 2023г
    private final int PAGE_COUNT_DAY = 1461;

    // к-во месяцев в период с 2020г - 2023г
    private final int PAGE_COUNT_MONTH = 48;

    ViewPager pager;
    PagerAdapter pagerAdapter;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";


    public FragmentViewPager() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewpager_main, container, false);
        pager = (ViewPager) view.findViewById(R.id.viewPager);
        // pagerAdapter = new
        // MyFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter = new MyFragmentPagerAdapter(getFragmentManager());
        pager.setAdapter(pagerAdapter);

        // выставляем фокус на нужную дату календаря
        if (MainActivity.list_navigation_item == 0
                || MainActivity.list_navigation_item == -1) {
            selected_item_position = cal.getTodayDay();
            //*******************************************************
            int notifi_date_app_start = getArguments().getInt("notifi_date_app_start");
            //Toast.makeText(getActivity(), "notifi_date_app_start="+notifi_date_app_start, Toast.LENGTH_LONG).show();
            if (notifi_date_app_start != 0 && notifi_date_app_start > 0 && notifi_date_app_start < 1462)
                selected_item_position = notifi_date_app_start;
            cal.setTodayDate();
            //******************************************************
            pager.setCurrentItem(selected_item_position);
        } else {
            selected_item_position = cal.getTodayMonth();
            pager.setCurrentItem(selected_item_position);
        }

        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position = " + position);

                // смещаем влево вправо дату или месяц
                /*
                 * if (MainActivity.list_navigation_item == 0 ||
                 * MainActivity.list_navigation_item == -1) { if
                 * (selected_item_position < position) cal.AddDay(1); else
                 * cal.AddDay(-1); selected_item_position = cal.getTodayDay();
                 *
                 * } else { if (selected_item_position < position)
                 * cal.AddMonth(1); else cal.AddMonth(-1);
                 * selected_item_position = cal.getTodayMonth();
                 *
                 * }
                 */

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return view;
    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
        //int count=0;

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (MainActivity.list_navigation_item == 0
                    || MainActivity.list_navigation_item == -1) {
  /*cal.AddDay(position-selected_item_position);
  selected_item_position = cal.getTodayDay();*/
                return PageFragmentViewPagerDay.newInstance(position);
            } else {
  /*cal.AddMonth(position-selected_item_position);
  selected_item_position = cal.getTodayMonth();*/
                return PageFragmentViewPagerMonth.newInstance(position);
            }
        }

        @Override
        public int getCount() {
 /*if (MainActivity.list_navigation_item == 0 || MainActivity.list_navigation_item == -1){
  //this.count=PAGE_COUNT_DAY;
  return PAGE_COUNT_DAY;
 }else{
  //this.count=PAGE_COUNT_MONTH;
  return PAGE_COUNT_MONTH;
 }*/
            int count = 0;
            //int count2=MainActivity.list_navigation_item;

            switch (MainActivity.list_navigation_item) {
                case 0:
                    count = PAGE_COUNT_DAY;
                    break;
                case -1:
                    count = PAGE_COUNT_DAY;
                    break;
                case 1:
                    count = PAGE_COUNT_MONTH;
                    break;
                default:
                    count = PAGE_COUNT_DAY;
                    break;
            }
            return count;
        }

    }

}
