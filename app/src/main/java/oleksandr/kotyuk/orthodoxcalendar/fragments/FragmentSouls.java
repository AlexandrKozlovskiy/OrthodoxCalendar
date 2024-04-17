package oleksandr.kotyuk.orthodoxcalendar.fragments;

import oleksandr.kotyuk.orthodoxcalendar.FontsHelper;
import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import oleksandr.kotyuk.orthodoxcalendar.MyView;

import android.widget.AdapterView.OnItemSelectedListener;

public class FragmentSouls extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    private final String[] data_years_souls = {"2022", "2023", "2024", "2025", "2026"};

    private final String[] text_years_souls = {"06.02.2022 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>" +
            "26.02.2022 – Вселенская родительская мясопустная суббота<br>" +
            "19.03.2022 – Родительская суббота 2-й седмицы Великого поста<br>" +
            "26.03.2022 – Родительская суббота 3-й седмицы Великого поста<br>" +
            "02.04.2022 – Родительская суббота 4-й седмицы Великого поста<br>" +
            "03.05.2022 – Радоница<br>" +
            "09.05.2022 – Поминовение усопших воинов<br>" +
            "11.06.2022 – Троицкая родительская суббота<br>" +
            "05.11.2022 – Суббота Димитриевская",

            "05.02.2023 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>" +
                    "18.02.2023 – Вселенская родительская мясопустная суббота<br>" +
                    "11.03.2023 – Родительская суббота 2-й седмицы Великого поста<br>" +
                    "18.03.2023 – Родительская суббота 3-й седмицы Великого поста<br>" +
                    "25.03.2023 – Родительская суббота 4-й седмицы Великого поста<br>" +
                    "25.04.2023 – Радоница<br>" +
                    "09.05.2023 – Поминовение усопших воинов<br>" +
                    "03.06.2023 – Троицкая родительская суббота<br>" +
                    "30.10.2023 – Поминовение всех православных христиан, безвинно богоборцами убиенных или безвинно пребывавших в заключении<br>" +
                    "28.10.2023 – Суббота Димитриевская",

            "04.02.2024 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>" +
                    "09.03.2024 – Вселенская родительская мясопустная суббота<br>" +
                    "30.03.2024 – Родительская суббота 2-й седмицы Великого поста<br>" +
                    "06.04.2024 – Родительская суббота 3-й седмицы Великого поста<br>" +
                    "13.04.2024 – Родительская суббота 4-й седмицы Великого поста<br>" +
                    "14.05.2024 – Радоница<br>" +
                    "09.05.2024 – Поминовение усопших воинов<br>" +
                    "22.06.2024 – Троицкая родительская суббота<br>" +
                    "30.10.2024 – Поминовение всех православных христиан, безвинно богоборцами убиенных или безвинно пребывавших в заключении<br>" +
                    "02.11.2024 – Суббота Димитриевская",

            "09.02.2025 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>" +
                    "22.02.2025 – Вселенская родительская мясопустная суббота<br>" +
                    "15.03.2025 – Родительская суббота 2-й седмицы Великого поста<br>" +
                    "22.03.2025 – Родительская суббота 3-й седмицы Великого поста<br>" +
                    "29.03.2025 – Родительская суббота 4-й седмицы Великого поста<br>" +
                    "29.04.2025 – Радоница<br>" +
                    "09.05.2025 – Поминовение усопших воинов<br>" +
                    "07.06.2025 – Троицкая родительская суббота<br>" +
                    "30.10.2025 – Поминовение всех православных христиан, безвинно богоборцами убиенных или безвинно пребывавших в заключении<br>" +
                    "01.11.2025 – Суббота Димитриевская",

            "08.02.2026 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>" +
                    "14.02.2026 – Вселенская родительская мясопустная суббота<br>" +
                    "07.03.2026 – Родительская суббота 2-й седмицы Великого поста<br>" +
                    "14.03.2026 – Родительская суббота 3-й седмицы Великого поста<br>" +
                    "21.03.2026 – Родительская суббота 4-й седмицы Великого поста<br>" +
                    "21.04.2026 – Радоница<br>" +
                    "09.05.2026 – Поминовение усопших воинов<br>" +
                    "30.05.2026 – Троицкая родительская суббота<br>" +
                    "30.10.2026 – Поминовение всех православных христиан, безвинно богоборцами убиенных или безвинно пребывавших в заключении<br>" +
                    "07.11.2026 – Суббота Димитриевская"};

    MyCalendar cal = MyCalendar.getInstance();

    private final String FONT_PATH1 = "fonts/Russo_One.ttf";
    MyView tvSoulsTitle;
    MyView tvDescriptionSouls;

    Spinner spinnerSouls;

    String text_size = "0";
    float standart_text_size1 = 0;
    float standart_text_size2 = 0;
    float standart_text_size3 = 0;
    MyView tvEasterSelectYear;

    public FragmentSouls() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_souls,
                container, false);

        tvEasterSelectYear = (MyView) view.findViewById(R.id.MyViewSouls3);
        tvSoulsTitle = (MyView) view.findViewById(R.id.MyViewSouls1);
        ViewCompat.setAccessibilityHeading(tvSoulsTitle, true);
        tvSoulsTitle.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH1));
        tvDescriptionSouls = (MyView) view.findViewById(R.id.MyViewSouls2);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.my2_simple_spinner_item, data_years_souls);
        adapter.setDropDownViewResource(R.layout.my2_simple_spinner_dropdown_item);

        spinnerSouls = (Spinner) view.findViewById(R.id.spinnerSouls1);
        spinnerSouls.setAdapter(adapter);
        // заголовок
        spinnerSouls.setPrompt("Title");
        // выделяем элемент
        switch (cal.getYear()) {
            case 2023:
                spinnerSouls.setSelection(1);
                tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[1]));
                break;
            case 2024:
                spinnerSouls.setSelection(2);
                tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[2]));
                break;
            case 2025:
                spinnerSouls.setSelection(3);
                tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[3]));
                break;
            case 2026:
                spinnerSouls.setSelection(4);
                tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[4]));
                break;
            default:
                spinnerSouls.setSelection(0);
                tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[0]));
                break;
        }
        // устанавливаем обработчик нажатия
        spinnerSouls.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                // Toast.makeText(getBaseContext(), "Position = " +
                // position,Toast.LENGTH_SHORT).show();
                tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (standart_text_size1 == 0) standart_text_size1 = tvSoulsTitle.getTextSize();//22
        if (standart_text_size2 == 0) standart_text_size2 = tvEasterSelectYear.getTextSize();//17
        if (standart_text_size3 == 0) standart_text_size3 = tvDescriptionSouls.getTextSize();//19
        String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_text_size", "0");
        if (!text_size.equals(tmp)) {
            text_size = tmp;
            if (text_size.equals("-5")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 10);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 10);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 10);
            }
            if (text_size.equals("-4")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 8);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 8);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 8);
            }
            if (text_size.equals("-3")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 6);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 6);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 6);
            }
            if (text_size.equals("-2")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 4);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 4);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 4);
            }
            if (text_size.equals("-1")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 2);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 2);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 2);
            }
            if (text_size.equals("0")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
            }
            if (text_size.equals("+1")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 2);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 2);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 2);
            }
            if (text_size.equals("+2")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 4);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 4);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 4);
            }
            if (text_size.equals("+3")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 6);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 6);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 6);
            }
            if (text_size.equals("+4")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 8);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 8);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 8);
            }
            if (text_size.equals("+5")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 10);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 10);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 10);
            }
            if (text_size.equals("+6")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 12);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 12);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 12);
            }
            if (text_size.equals("+7")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 14);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 14);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 14);
            }
            if (text_size.equals("+8")) {
                tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 16);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 16);
                tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 16);
            }

        }
    }

}
