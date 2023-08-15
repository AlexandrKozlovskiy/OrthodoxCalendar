package oleksandr.kotyuk.orthodoxcalendar.fragments;

import oleksandr.kotyuk.orthodoxcalendar.FontsHelper;
import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;
import oleksandr.kotyuk.orthodoxcalendar.GlobalData;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import oleksandr.kotyuk.orthodoxcalendar.MyView;

import android.widget.AdapterView.OnItemSelectedListener;

public class FragmentHolidays extends Fragment implements OnClickListener {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    static final String TAG = "myLogs";

    private final String[] data_years_holidays = {"2014", "2015", "2016",
            "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024"};

    private final String[] text_years_holidays_easter = {"20.04.2014 - <a href=\"activity-run://DescriptionHolyActivityHost?id=1920\">ПАСХА ХРИСТОВА</a>",
            "12.04.2015 - <a href=\"activity-run://DescriptionHolyActivityHost?id=1920\">ПАСХА ХРИСТОВА</a>",
            "01.05.2016 - <a href=\"activity-run://DescriptionHolyActivityHost?id=1920\">ПАСХА ХРИСТОВА</a>",
            "16.04.2017 - <a href=\"activity-run://DescriptionHolyActivityHost?id=1920\">ПАСХА ХРИСТОВА</a>",
            "08.04.2018 - <a href=\"activity-run://DescriptionHolyActivityHost?id=1920\">ПАСХА ХРИСТОВА</a>",
            "28.04.2019 - <a href=\"activity-run://DescriptionHolyActivityHost?id=1920\">ПАСХА ХРИСТОВА</a>",
            "19.04.2020 - <a href=\"activity-run://DescriptionHolyActivityHost?id=1920\">ПАСХА ХРИСТОВА</a>",
            "02.05.2021 - <a href=\"activity-run://DescriptionHolyActivityHost?id=1920\">ПАСХА ХРИСТОВА</a>",
            "24.04.2022 - <a href=\"activity-run://DescriptionHolyActivityHost?id=1920\">ПАСХА ХРИСТОВА</a>",
            "16.04.2023 - <a href=\"activity-run://DescriptionHolyActivityHost?id=1920\">ПАСХА ХРИСТОВА</a>",
            "05.05.2024 - <a href=\"activity-run://DescriptionHolyActivityHost?id=1920\">ПАСХА ХРИСТОВА</a>"};

    private final String[] text_years_holidays1 = {"13.04.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1919\">Вход Господень в Иерусалим</a><br>" +
            "29.05.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1921\">Вознесение Господне</a><br>" +
            "08.06.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1922\">День Святой Троицы. Пятидесятница</a>",

            "05.04.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1919\">Вход Господень в Иерусалим</a><br>" +
                    "21.05.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1921\">Вознесение Господне</a><br>" +
                    "31.05.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1922\">День Святой Троицы. Пятидесятница</a>",

            "24.04.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1919\">Вход Господень в Иерусалим</a><br>" +
                    "09.06.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1921\">Вознесение Господне</a><br>" +
                    "19.06.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1922\">День Святой Троицы. Пятидесятница</a>",

            "09.04.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1919\">Вход Господень в Иерусалим</a><br>" +
                    "25.05.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1921\">Вознесение Господне</a><br>" +
                    "04.06.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1922\">День Святой Троицы. Пятидесятница</a>",

            "01.04.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1919\">Вход Господень в Иерусалим</a><br>" +
                    "17.05.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1921\">Вознесение Господне</a><br>" +
                    "27.05.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1922\">День Святой Троицы. Пятидесятница</a>",

            "21.04.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1919\">Вход Господень в Иерусалим</a><br>" +
                    "06.06.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1921\">Вознесение Господне</a><br>" +
                    "16.06.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1922\">День Святой Троицы. Пятидесятница</a>",

            "12.04.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1919\">Вход Господень в Иерусалим</a><br>" +
                    "28.05.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1921\">Вознесение Господне</a><br>" +
                    "07.06.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1922\">День Святой Троицы. Пятидесятница</a>",

            "25.04.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1919\">Вход Господень в Иерусалим</a><br>" +
                    "10.06.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1921\">Вознесение Господне</a><br>" +
                    "20.06.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1922\">День Святой Троицы. Пятидесятница</a>",

            "17.04.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1919\">Вход Господень в Иерусалим</a><br>" +
                    "02.06.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1921\">Вознесение Господне</a><br>" +
                    "12.06.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1922\">День Святой Троицы. Пятидесятница</a>",

            "09.04.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1919\">Вход Господень в Иерусалим</a><br>" +
                    "25.05.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1921\">Вознесение Господне</a><br>" +
                    "04.06.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1922\">День Святой Троицы. Пятидесятница</a>",

            "28.04.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1919\">Вход Господень в Иерусалим</a><br>" +
                    "13.06.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1921\">Вознесение Господне</a><br>" +
                    "23.06.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1922\">День Святой Троицы. Пятидесятница</a>"};

    private final String[] text_years_holidays2 = {"07.01.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=30\">Рождество Христово</a><br>" +
            "19.01.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=83\">Крещение Господне. Богоявление</a><br>" +
            "15.02.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=230\">Сретение Господне</a><br>" +
            "07.04.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=478\">Благовещение Пресвятой Богородицы</a><br>" +
            "19.08.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1184\">Преображение Господне</a><br>" +
            "28.08.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1227\">Успение Пресвятой Богородицы</a><br>" +
            "21.09.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1360\">Рождество Пресвятой Богородицы</a><br>" +
            "27.09.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1401\">Воздвижение Креста Господня</a><br>" +
            "04.12.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1787\">Введение во храм Пресвятой Богородицы</a>",

            "07.01.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=30\">Рождество Христово</a><br>" +
                    "19.01.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=83\">Крещение Господне. Богоявление</a><br>" +
                    "15.02.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=230\">Сретение Господне</a><br>" +
                    "07.04.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=478\">Благовещение Пресвятой Богородицы</a><br>" +
                    "19.08.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1184\">Преображение Господне</a><br>" +
                    "28.08.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1227\">Успение Пресвятой Богородицы</a><br>" +
                    "21.09.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1360\">Рождество Пресвятой Богородицы</a><br>" +
                    "27.09.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1401\">Воздвижение Креста Господня</a><br>" +
                    "04.12.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1787\">Введение во храм Пресвятой Богородицы</a>",

            "07.01.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=30\">Рождество Христово</a><br>" +
                    "19.01.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=83\">Крещение Господне. Богоявление</a><br>" +
                    "15.02.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=230\">Сретение Господне</a><br>" +
                    "07.04.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=478\">Благовещение Пресвятой Богородицы</a><br>" +
                    "19.08.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1184\">Преображение Господне</a><br>" +
                    "28.08.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1227\">Успение Пресвятой Богородицы</a><br>" +
                    "21.09.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1360\">Рождество Пресвятой Богородицы</a><br>" +
                    "27.09.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1401\">Воздвижение Креста Господня</a><br>" +
                    "04.12.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1787\">Введение во храм Пресвятой Богородицы</a>",

            "07.01.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=30\">Рождество Христово</a><br>" +
                    "19.01.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=83\">Крещение Господне. Богоявление</a><br>" +
                    "15.02.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=230\">Сретение Господне</a><br>" +
                    "07.04.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=478\">Благовещение Пресвятой Богородицы</a><br>" +
                    "19.08.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1184\">Преображение Господне</a><br>" +
                    "28.08.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1227\">Успение Пресвятой Богородицы</a><br>" +
                    "21.09.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1360\">Рождество Пресвятой Богородицы</a><br>" +
                    "27.09.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1401\">Воздвижение Креста Господня</a><br>" +
                    "04.12.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1787\">Введение во храм Пресвятой Богородицы</a>",

            "07.01.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=30\">Рождество Христово</a><br>" +
                    "19.01.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=83\">Крещение Господне. Богоявление</a><br>" +
                    "15.02.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=230\">Сретение Господне</a><br>" +
                    "07.04.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=478\">Благовещение Пресвятой Богородицы</a><br>" +
                    "19.08.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1184\">Преображение Господне</a><br>" +
                    "28.08.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1227\">Успение Пресвятой Богородицы</a><br>" +
                    "21.09.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1360\">Рождество Пресвятой Богородицы</a><br>" +
                    "27.09.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1401\">Воздвижение Креста Господня</a><br>" +
                    "04.12.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1787\">Введение во храм Пресвятой Богородицы</a>",

            "07.01.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=30\">Рождество Христово</a><br>" +
                    "19.01.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=83\">Крещение Господне. Богоявление</a><br>" +
                    "15.02.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=230\">Сретение Господне</a><br>" +
                    "07.04.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=478\">Благовещение Пресвятой Богородицы</a><br>" +
                    "19.08.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1184\">Преображение Господне</a><br>" +
                    "28.08.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1227\">Успение Пресвятой Богородицы</a><br>" +
                    "21.09.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1360\">Рождество Пресвятой Богородицы</a><br>" +
                    "27.09.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1401\">Воздвижение Креста Господня</a><br>" +
                    "04.12.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1787\">Введение во храм Пресвятой Богородицы</a>",

            "07.01.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=30\">Рождество Христово</a><br>" +
                    "19.01.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=83\">Крещение Господне. Богоявление</a><br>" +
                    "15.02.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=230\">Сретение Господне</a><br>" +
                    "07.04.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=478\">Благовещение Пресвятой Богородицы</a><br>" +
                    "19.08.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1184\">Преображение Господне</a><br>" +
                    "28.08.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1227\">Успение Пресвятой Богородицы</a><br>" +
                    "21.09.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1360\">Рождество Пресвятой Богородицы</a><br>" +
                    "27.09.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1401\">Воздвижение Креста Господня</a><br>" +
                    "04.12.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1787\">Введение во храм Пресвятой Богородицы</a>",

            "07.01.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=30\">Рождество Христово</a><br>" +
                    "19.01.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=83\">Крещение Господне. Богоявление</a><br>" +
                    "15.02.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=230\">Сретение Господне</a><br>" +
                    "07.04.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=478\">Благовещение Пресвятой Богородицы</a><br>" +
                    "19.08.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1184\">Преображение Господне</a><br>" +
                    "28.08.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1227\">Успение Пресвятой Богородицы</a><br>" +
                    "21.09.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1360\">Рождество Пресвятой Богородицы</a><br>" +
                    "27.09.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1401\">Воздвижение Креста Господня</a><br>" +
                    "04.12.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1787\">Введение во храм Пресвятой Богородицы</a>",

            "07.01.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=30\">Рождество Христово</a><br>" +
                    "19.01.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=83\">Крещение Господне. Богоявление</a><br>" +
                    "15.02.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=230\">Сретение Господне</a><br>" +
                    "07.04.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=478\">Благовещение Пресвятой Богородицы</a><br>" +
                    "19.08.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1184\">Преображение Господне</a><br>" +
                    "28.08.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1227\">Успение Пресвятой Богородицы</a><br>" +
                    "21.09.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1360\">Рождество Пресвятой Богородицы</a><br>" +
                    "27.09.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1401\">Воздвижение Креста Господня</a><br>" +
                    "04.12.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1787\">Введение во храм Пресвятой Богородицы</a>",

            "07.01.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=30\">Рождество Христово</a><br>" +
                    "19.01.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=83\">Крещение Господне. Богоявление</a><br>" +
                    "15.02.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=230\">Сретение Господне</a><br>" +
                    "07.04.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=478\">Благовещение Пресвятой Богородицы</a><br>" +
                    "19.08.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1184\">Преображение Господне</a><br>" +
                    "28.08.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1227\">Успение Пресвятой Богородицы</a><br>" +
                    "21.09.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1360\">Рождество Пресвятой Богородицы</a><br>" +
                    "27.09.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1401\">Воздвижение Креста Господня</a><br>" +
                    "04.12.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1787\">Введение во храм Пресвятой Богородицы</a>",

            "07.01.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=30\">Рождество Христово</a><br>" +
                    "19.01.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=83\">Крещение Господне. Богоявление</a><br>" +
                    "15.02.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=230\">Сретение Господне</a><br>" +
                    "07.04.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=478\">Благовещение Пресвятой Богородицы</a><br>" +
                    "19.08.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1184\">Преображение Господне</a><br>" +
                    "28.08.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1227\">Успение Пресвятой Богородицы</a><br>" +
                    "21.09.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1360\">Рождество Пресвятой Богородицы</a><br>" +
                    "27.09.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1401\">Воздвижение Креста Господня</a><br>" +
                    "04.12.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1787\">Введение во храм Пресвятой Богородицы</a>"};

    private final String[] text_years_holidays3 = {"14.01.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=59\">Обрезание Господне</a><br>" +
            "07.07.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=965\">Рождество Иоанна Предтечи</a><br>" +
            "12.07.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=989\">Святых первоверховных апостолов Петра и Павла</a><br>" +
            "11.09.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1293\">Усекновение главы Иоанна Предтечи</a><br>" +
            "14.10.2014 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1499\">Покров Пресвятой Богородицы</a>",

            "14.01.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=59\">Обрезание Господне</a><br>" +
                    "07.07.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=965\">Рождество Иоанна Предтечи</a><br>" +
                    "12.07.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=989\">Святых первоверховных апостолов Петра и Павла</a><br>" +
                    "11.09.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1293\">Усекновение главы Иоанна Предтечи</a><br>" +
                    "14.10.2015 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1499\">Покров Пресвятой Богородицы</a>",

            "14.01.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=59\">Обрезание Господне</a><br>" +
                    "07.07.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=965\">Рождество Иоанна Предтечи</a><br>" +
                    "12.07.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=989\">Святых первоверховных апостолов Петра и Павла</a><br>" +
                    "11.09.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1293\">Усекновение главы Иоанна Предтечи</a><br>" +
                    "14.10.2016 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1499\">Покров Пресвятой Богородицы</a>",

            "14.01.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=59\">Обрезание Господне</a><br>" +
                    "07.07.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=965\">Рождество Иоанна Предтечи</a><br>" +
                    "12.07.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=989\">Святых первоверховных апостолов Петра и Павла</a><br>" +
                    "11.09.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1293\">Усекновение главы Иоанна Предтечи</a><br>" +
                    "14.10.2017 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1499\">Покров Пресвятой Богородицы</a>",

            "14.01.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=59\">Обрезание Господне</a><br>" +
                    "07.07.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=965\">Рождество Иоанна Предтечи</a><br>" +
                    "12.07.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=989\">Святых первоверховных апостолов Петра и Павла</a><br>" +
                    "11.09.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1293\">Усекновение главы Иоанна Предтечи</a><br>" +
                    "14.10.2018 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1499\">Покров Пресвятой Богородицы</a>",

            "14.01.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=59\">Обрезание Господне</a><br>" +
                    "07.07.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=965\">Рождество Иоанна Предтечи</a><br>" +
                    "12.07.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=989\">Святых первоверховных апостолов Петра и Павла</a><br>" +
                    "11.09.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1293\">Усекновение главы Иоанна Предтечи</a><br>" +
                    "14.10.2019 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1499\">Покров Пресвятой Богородицы</a>",

            "14.01.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=59\">Обрезание Господне</a><br>" +
                    "07.07.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=965\">Рождество Иоанна Предтечи</a><br>" +
                    "12.07.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=989\">Святых первоверховных апостолов Петра и Павла</a><br>" +
                    "11.09.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1293\">Усекновение главы Иоанна Предтечи</a><br>" +
                    "14.10.2020 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1499\">Покров Пресвятой Богородицы</a>",

            "14.01.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=59\">Обрезание Господне</a><br>" +
                    "07.07.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=965\">Рождество Иоанна Предтечи</a><br>" +
                    "12.07.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=989\">Святых первоверховных апостолов Петра и Павла</a><br>" +
                    "11.09.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1293\">Усекновение главы Иоанна Предтечи</a><br>" +
                    "14.10.2021 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1499\">Покров Пресвятой Богородицы</a>",

            "14.01.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=59\">Обрезание Господне</a><br>" +
                    "07.07.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=965\">Рождество Иоанна Предтечи</a><br>" +
                    "12.07.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=989\">Святых первоверховных апостолов Петра и Павла</a><br>" +
                    "11.09.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1293\">Усекновение главы Иоанна Предтечи</a><br>" +
                    "14.10.2022 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1499\">Покров Пресвятой Богородицы</a>",

            "14.01.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=59\">Обрезание Господне</a><br>" +
                    "07.07.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=965\">Рождество Иоанна Предтечи</a><br>" +
                    "12.07.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=989\">Святых первоверховных апостолов Петра и Павла</a><br>" +
                    "11.09.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1293\">Усекновение главы Иоанна Предтечи</a><br>" +
                    "14.10.2023 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1499\">Покров Пресвятой Богородицы</a>",

            "14.01.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=59\">Обрезание Господне</a><br>" +
                    "07.07.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=965\">Рождество Иоанна Предтечи</a><br>" +
                    "12.07.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=989\">Святых первоверховных апостолов Петра и Павла</a><br>" +
                    "11.09.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1293\">Усекновение главы Иоанна Предтечи</a><br>" +
                    "14.10.2024 – <a href=\"activity-run://DescriptionHolyActivityHost?id=1499\">Покров Пресвятой Богородицы</a>"};

    private final String FONT_PATH1 = "fonts/Russo_One.ttf";
    Spinner spinnerHolidays;
    MyView tvHolidaysTitle;
    MyView tvHolidaysEaster1;
    MyView tvHolidays1;
    MyView tvHolidays2;
    MyView tvHolidays3;

    MyCalendar cal = MyCalendar.getInstance();

    String text_size = "0";
    float standart_text_size1 = 0;
    float standart_text_size2 = 0;
    float standart_text_size3 = 0;
    MyView tvEasterSelectYear;
    MyView tvHolidays_3;
    MyView tvHolidays_5;
    MyView tvHolidays_7;

    public FragmentHolidays() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout_holidays,
                container, false);
        tvEasterSelectYear = (MyView) view.findViewById(R.id.MyViewHolidays9);
        tvHolidays_3 = (MyView) view.findViewById(R.id.MyViewHolidays3);
        ViewCompat.setAccessibilityHeading(tvHolidays_3, true);
        tvHolidays_5 = (MyView) view.findViewById(R.id.MyViewHolidays5);
        ViewCompat.setAccessibilityHeading(tvHolidays_5, true);
        tvHolidays_7 = (MyView) view.findViewById(R.id.MyViewHolidays7);
        ViewCompat.setAccessibilityHeading(tvHolidays_7, true);
        tvHolidaysTitle = (MyView) view.findViewById(R.id.MyViewHolidays1);
        ViewCompat.setAccessibilityHeading(tvHolidaysTitle, true);
        tvHolidaysTitle.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH1));
        tvHolidaysEaster1 = (MyView) view.findViewById(R.id.MyViewHolidays2);
        tvHolidaysEaster1.setLinksClickable(true);
        tvHolidaysEaster1.setMovementMethod(new LinkMovementMethod());
        tvHolidays1 = (MyView) view.findViewById(R.id.MyViewHolidays4);
        tvHolidays1.setLinksClickable(true);
        tvHolidays1.setMovementMethod(new LinkMovementMethod());
        tvHolidays2 = (MyView) view.findViewById(R.id.MyViewHolidays6);
        tvHolidays2.setLinksClickable(true);
        tvHolidays2.setMovementMethod(new LinkMovementMethod());
        tvHolidays3 = (MyView) view.findViewById(R.id.MyViewHolidays8);
        tvHolidays3.setLinksClickable(true);
        tvHolidays3.setMovementMethod(new LinkMovementMethod());

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.my2_simple_spinner_item, data_years_holidays);
        adapter.setDropDownViewResource(R.layout.my2_simple_spinner_dropdown_item);

        spinnerHolidays = (Spinner) view.findViewById(R.id.spinnerHolidays1);
        spinnerHolidays.setAdapter(adapter);
        // заголовок
        spinnerHolidays.setPrompt("Title");
        // выделяем элемент
        switch (cal.getYear()) {
            case 2014:
                spinnerHolidays.setSelection(0);
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[0].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[0].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[0].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[0].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                break;
            case 2015:
                spinnerHolidays.setSelection(1);
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[1].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[1].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[1].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[1].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                break;
            case 2016:
                spinnerHolidays.setSelection(2);
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[2].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[2].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[2].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[2].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                break;
            case 2017:
                spinnerHolidays.setSelection(3);
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[3].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[3].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[3].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[3].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                break;
            case 2018:
                spinnerHolidays.setSelection(4);
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[4].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[4].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[4].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[3].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                break;
            case 2019:
                spinnerHolidays.setSelection(5);
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[5].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[5].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[5].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[5].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                break;
            case 2020:
                spinnerHolidays.setSelection(6);
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[6].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[6].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[6].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[6].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                break;
            case 2021:
                spinnerHolidays.setSelection(7);
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[7].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[7].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[7].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[7].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                break;
            case 2022:
                spinnerHolidays.setSelection(8);
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[8].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[8].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[8].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[8].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                break;
            case 2023:
                spinnerHolidays.setSelection(9);
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[9].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[9].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[9].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[9].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                break;
            case 2024:
                spinnerHolidays.setSelection(10);
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[10].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[10].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[10].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[10].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                break;
            default:
                spinnerHolidays.setSelection(0);
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[0].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[0].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[0].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[0].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                break;
        }
        // устанавливаем обработчик нажатия
        spinnerHolidays.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                // Toast.makeText(getBaseContext(), "Position = " +
                // position,Toast.LENGTH_SHORT).show();
                tvHolidaysEaster1.setText(Html.fromHtml(text_years_holidays_easter[position].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays1.setText(Html.fromHtml(text_years_holidays1[position].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays2.setText(Html.fromHtml(text_years_holidays2[position].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
                tvHolidays3.setText(Html.fromHtml(text_years_holidays3[position].replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())));
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

        if (standart_text_size1 == 0) standart_text_size1 = tvHolidaysTitle.getTextSize();//22
        if (standart_text_size2 == 0) standart_text_size2 = tvEasterSelectYear.getTextSize();//17
        if (standart_text_size3 == 0) standart_text_size3 = tvHolidaysEaster1.getTextSize();//19

        String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_text_size", "0");
        if (!text_size.equals(tmp)) {
            text_size = tmp;
            //int int_text_size=Integer.valueOf(text_size);
 /*Toast.makeText(getActivity(), text_size+"="+int_text_size,
  Toast.LENGTH_SHORT).show();*/
            //float size=MyView_holiday.getTextSize();
            if (text_size.equals("-5")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 10);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 10);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 10);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 10);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 10);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 10);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 10);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 10);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 10);
            }
            if (text_size.equals("-4")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 8);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 8);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 8);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 8);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 8);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 8);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 8);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 8);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 8);
            }
            if (text_size.equals("-3")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 6);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 6);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 6);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 6);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 6);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 6);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 6);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 6);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 6);
            }
            if (text_size.equals("-2")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 4);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 4);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 4);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 4);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 4);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 4);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 4);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 4);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 4);
            }
            if (text_size.equals("-1")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 2);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 2);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 2);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 2);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 2);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 2);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 2);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 2);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 2);
            }
            if (text_size.equals("0")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
            }
            if (text_size.equals("+1")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 2);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 2);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 2);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 2);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 2);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 2);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 2);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 2);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 2);
            }
            if (text_size.equals("+2")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 4);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 4);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 4);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 4);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 4);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 4);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 4);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 4);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 4);
            }
            if (text_size.equals("+3")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 6);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 6);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 6);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 6);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 6);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 6);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 6);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 6);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 6);
            }
            if (text_size.equals("+4")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 8);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 8);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 8);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 8);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 8);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 8);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 8);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 8);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 8);
            }
            if (text_size.equals("+5")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 10);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 10);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 10);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 10);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 10);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 10);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 10);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 10);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 10);
            }
            if (text_size.equals("+6")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 12);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 12);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 12);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 12);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 12);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 12);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 12);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 12);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 12);
            }
            if (text_size.equals("+7")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 14);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 14);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 14);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 14);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 14);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 14);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 14);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 14);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 14);
            }
            if (text_size.equals("+8")) {
                tvHolidaysTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 16);
                tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 16);
                tvHolidaysEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 16);
                tvHolidays1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 16);
                tvHolidays2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 16);
                tvHolidays3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 16);
                tvHolidays_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 16);
                tvHolidays_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 16);
                tvHolidays_7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 16);
            }
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}