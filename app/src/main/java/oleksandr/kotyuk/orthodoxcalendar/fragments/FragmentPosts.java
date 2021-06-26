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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import oleksandr.kotyuk.orthodoxcalendar.MyView;

public class FragmentPosts extends Fragment implements OnClickListener{

public static final String IMAGE_RESOURCE_ID = "iconResourceID";
public static final String ITEM_NAME = "itemName";

private final String[] data_years_posts = { "2014", "2015", "2016",
"2017", "2018", "2019", "2020", "2021", "2022"};

private String[] data_posts1={"<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=1\">Великий пост</a> - 03.03.2014 - 19.04.2014<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=2\">Петров пост</a> - 16.06.2014 - 11.07.2014<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=3\">Успенский пост</a> - 14.08.2014 - 27.08.2014<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=4\">Рождественский пост</a> - 28.11.2014 - 06.01.2015",
 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=1\">Великий пост</a> - 23.02.2015 - 11.04.2015<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=2\">Петров пост</a> - 08.06.2015 - 11.07.2015<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=3\">Успенский пост</a> - 14.08.2015 - 27.08.2015<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=4\">Рождественский пост</a> - 28.11.2015 - 06.01.2016",
 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=1\">Великий пост</a> - 14.03.2016 - 30.04.2016<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=2\">Петров пост</a> - 27.06.2016 - 11.07.2016<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=3\">Успенский пост</a> - 14.08.2016 - 27.08.2016<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=4\">Рождественский пост</a> - 28.11.2016 - 06.01.2017",
 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=1\">Великий пост</a> - 27.02.2017 - 15.04.2017<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=2\">Петров пост</a> - 12.06.2017 - 11.07.2017<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=3\">Успенский пост</a> - 14.08.2017 - 27.08.2017<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=4\">Рождественский пост</a> - 28.11.2017 - 06.01.2018",
 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=1\">Великий пост</a> - 19.02.2018 - 07.04.2018<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=2\">Петров пост</a> - 04.06.2018 - 11.07.2018<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=3\">Успенский пост</a> - 14.08.2018 - 27.08.2018<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=4\">Рождественский пост</a> - 28.11.2018 - 06.01.2019",
  
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=1\">Великий пост</a> - 11.03.2019 - 27.04.2019<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=2\">Петров пост</a> - 24.06.2019 - 11.07.2019<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=3\">Успенский пост</a> - 14.08.2019 - 27.08.2019<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=4\">Рождественский пост</a> - 28.11.2019 - 06.01.2020",
   
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=1\">Великий пост</a> - 02.03.2020 - 18.04.2020<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=2\">Петров пост</a> - 15.06.2020 - 11.07.2020<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=3\">Успенский пост</a> - 14.08.2020 - 27.08.2020<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=4\">Рождественский пост</a> - 28.11.2020 - 06.01.2021",
 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=1\">Великий пост</a> - 15.03.2021 - 01.05.2021<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=2\">Петров пост</a> - 28.06.2021 - 11.07.2021<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=3\">Успенский пост</a> - 14.08.2021 - 27.08.2021<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=4\">Рождественский пост</a> - 28.11.2021 - 06.01.2022",
 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=1\">Великий пост</a> - 07.03.2022 - 23.04.2022<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=2\">Петров пост</a> - 20.06.2022 - 11.07.2022<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=3\">Успенский пост</a> - 14.08.2022 - 27.08.2022<br>"+
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_multi=4\">Рождественский пост</a> - 28.11.2022 - 06.01.2023"};

private String[] data_posts2={"18.01.2014 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=1\">Крещенский сочельник (Навечерие Богоявления)</a><br>"+
 "11.09.2014 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=2\">Усекновение главы Иоанна Предтечи</a><br>"+
 "27.09.2014 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=3\">Воздвижение Креста Господня</a><br>"+
 "Среда и пятница в течение всего года, за исключением сплошных седмиц и Святок",
 
 "18.01.2015 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=1\">Крещенский сочельник (Навечерие Богоявления)</a><br>"+
 "11.09.2015 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=2\">Усекновение главы Иоанна Предтечи</a><br>"+
 "27.09.2015 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=3\">Воздвижение Креста Господня</a><br>"+
 "Среда и пятница в течение всего года, за исключением сплошных седмиц и Святок",
 
 "18.01.2016 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=1\">Крещенский сочельник (Навечерие Богоявления)</a><br>"+
 "11.09.2016 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=2\">Усекновение главы Иоанна Предтечи</a><br>"+
 "27.09.2016 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=3\">Воздвижение Креста Господня</a><br>"+
 "Среда и пятница в течение всего года, за исключением сплошных седмиц и Святок",
 
 "18.01.2017 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=1\">Крещенский сочельник (Навечерие Богоявления)</a><br>"+
 "11.09.2017 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=2\">Усекновение главы Иоанна Предтечи</a><br>"+
 "27.09.2017 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=3\">Воздвижение Креста Господня</a><br>"+
 "Среда и пятница в течение всего года, за исключением сплошных седмиц и Святок",
 
 "18.01.2018 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=1\">Крещенский сочельник (Навечерие Богоявления)</a><br>"+
 "11.09.2018 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=2\">Усекновение главы Иоанна Предтечи</a><br>"+
 "27.09.2018 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=3\">Воздвижение Креста Господня</a><br>"+
 "Среда и пятница в течение всего года, за исключением сплошных седмиц и Святок",
  
 "18.01.2019 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=1\">Крещенский сочельник (Навечерие Богоявления)</a><br>"+
 "11.09.2019 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=2\">Усекновение главы Иоанна Предтечи</a><br>"+
 "27.09.2019 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=3\">Воздвижение Креста Господня</a><br>"+
 "Среда и пятница в течение всего года, за исключением сплошных седмиц и Святок",
   
 "18.01.2020 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=1\">Крещенский сочельник (Навечерие Богоявления)</a><br>"+
 "11.09.2020 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=2\">Усекновение главы Иоанна Предтечи</a><br>"+
 "27.09.2020 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=3\">Воздвижение Креста Господня</a><br>"+
 "Среда и пятница в течение всего года, за исключением сплошных седмиц и Святок",
 
 "18.01.2021 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=1\">Крещенский сочельник (Навечерие Богоявления)</a><br>"+
 "11.09.2021 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=2\">Усекновение главы Иоанна Предтечи</a><br>"+
 "27.09.2021 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=3\">Воздвижение Креста Господня</a><br>"+
 "Среда и пятница в течение всего года, за исключением сплошных седмиц и Святок",
 
 "18.01.2022 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=1\">Крещенский сочельник (Навечерие Богоявления)</a><br>"+
 "11.09.2022 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=2\">Усекновение главы Иоанна Предтечи</a><br>"+
 "27.09.2022 - <a href=\"activity-run://DescriptionOtherActivityHost?id_one=3\">Воздвижение Креста Господня</a><br>"+
 "Среда и пятница в течение всего года, за исключением сплошных седмиц и Святок"};

private String[] data_posts3={"<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=1\">Святки</a> - 07.01.2014 - 17.01.2014<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=2\">Мытаря и фарисея</a> - 10.02.2014 - 16.02.2014<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=3\">Сырная (масленица)</a> - 24.02.2014 - 02.03.2014<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=4\">Пасхальная (Светлая)</a> - 21.04.2014 - 27.04.2014<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=5\">Троицкая</a> - 09.06.2014 - 15.06.2014<br><br>",
 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=1\">Святки</a> - 07.01.2015 - 17.01.2015<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=2\">Мытаря и фарисея</a> - 02.02.2015 - 08.02.2015<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=3\">Сырная (масленица)</a> - 16.02.2015 - 22.02.2015<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=4\">Пасхальная (Светлая)</a> - 13.04.2015 - 19.04.2015<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=5\">Троицкая</a> - 01.06.2015 - 07.06.2015<br><br>",
 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=1\">Святки</a> - 07.01.2016 - 17.01.2016<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=2\">Мытаря и фарисея</a> - 22.02.2016 - 28.02.2016<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=3\">Сырная (масленица)</a> - 07.03.2016 - 13.03.2016<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=4\">Пасхальная (Светлая)</a> - 02.05.2016 - 08.05.2016<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=5\">Троицкая</a> - 20.06.2016 - 26.06.2016<br><br>",
 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=1\">Святки</a> - 07.01.2017 - 17.01.2017<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=2\">Мытаря и фарисея</a> - 06.02.2017 - 12.02.2017<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=3\">Сырная (масленица)</a> - 20.02.2017 - 26.02.2017<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=4\">Пасхальная (Светлая)</a> - 17.04.2017 - 23.04.2017<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=5\">Троицкая</a> - 05.06.2017 - 11.06.2017<br><br>",
 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=1\">Святки</a> - 07.01.2018 - 17.01.2018<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=2\">Мытаря и фарисея</a> - 29.01.2018 - 04.02.2018<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=3\">Сырная (масленица)</a> - 12.02.2018 - 18.02.2018<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=4\">Пасхальная (Светлая)</a> - 09.04.2018 - 15.04.2018<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=5\">Троицкая</a> - 28.05.2018 - 03.06.2018<br><br>",
  
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=1\">Святки</a> - 07.01.2019 - 17.01.2019<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=2\">Мытаря и фарисея</a> - 18.02.2019 - 24.02.2019<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=3\">Сырная (масленица)</a> - 04.03.2019 - 10.03.2019<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=4\">Пасхальная (Светлая)</a> - 29.04.2019 - 05.05.2019<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=5\">Троицкая</a> - 17.06.2019 - 23.06.2019<br><br>",
   
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=1\">Святки</a> - 07.01.2020 - 17.01.2020<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=2\">Мытаря и фарисея</a> - 10.02.2020 - 16.02.2020<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=3\">Сырная (масленица)</a> - 24.02.2020 - 01.03.2020<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=4\">Пасхальная (Светлая)</a> - 20.04.2020 - 26.04.2020<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=5\">Троицкая</a> - 08.06.2020 - 14.06.2020<br><br>",
 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=1\">Святки</a> - 07.01.2021 - 17.01.2021<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=2\">Мытаря и фарисея</a> - 22.02.2021 - 28.02.2021<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=3\">Сырная (масленица)</a> - 08.03.2021 - 14.03.2021<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=4\">Пасхальная (Светлая)</a> - 03.05.2021 - 09.05.2021<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=5\">Троицкая</a> - 21.06.2021 - 27.06.2021<br><br>",
 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=1\">Святки</a> - 07.01.2022 - 17.01.2022<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=2\">Мытаря и фарисея</a> - 14.02.2022 - 20.02.2022<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=3\">Сырная (масленица)</a> - 28.02.2022 - 06.03.2022<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=4\">Пасхальная (Светлая)</a> - 25.04.2022 - 01.05.2022<br>"+ 
 "<a href=\"activity-run://DescriptionOtherActivityHost?id_sedmitsa=5\">Троицкая</a> - 13.06.2022 - 19.06.2022<br><br>"};

/*private final String data_posts4=
 "1. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=1\">Вступление</a><br>"+
 "2. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=2\">Общие положения православного устава о трапезе</a><br>"+
 "3. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=3\">Порядок трапезы вне длительных постов</a><br>"+
 "4. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=4\">Трапеза во время Поста</a><br>"+
 "5. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=5\">Великий Пост</a><br>"+
 "6. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=6\">Пост свв. Апостолов</a><br>"+
 "7. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=7\">Успенский Пост</a><br>"+
 "8. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=8\">Рождественский Пост</a><br>"+
 "9. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=9\">Трапеза в Пятидесятницу</a><br>"+
 "10. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=10\">Заключение</a><br>"+
 "11. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=11\">Приложение</a><br><br>"+
 "Строгий «стандартный» распорядок поста из Устава Церкви(Типикон) предписан только монахам, которые обязаны его соблюдать полностью. Для мирского христианина строгость поста индивидуальна, все зависит от возможностей человека и его духовного рвения. Индивидуальные рекомендации, а также разрешение на некоторые послабления в связи с ограничениями по здоровью верующий получает от приходского священника или от своего духовника.";*/

MyCalendar cal = MyCalendar.getInstance();

private final String FONT_PATH1 = "fonts/Russo_One.ttf";
Spinner spinnerPosts;
MyView tvPostsTitle;
MyView tvDescriptionPosts3;
MyView tvDescriptionPosts5;
MyView tvDescriptionPosts7;
//MyView tvDescriptionPosts9;

String text_size="0";
float standart_text_size1=0;
float standart_text_size2=0;
float standart_text_size3=0;
MyView tvEasterSelectYear;
MyView tvPosts2;
MyView tvPosts4;
MyView tvPosts6;
//MyView tvPosts8;

public FragmentPosts(){
 
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {
 View view = inflater.inflate(R.layout.fragment_layout_posts,
  container, false);

 tvEasterSelectYear=(MyView) view.findViewById(R.id.MyViewPosts10);
 tvPosts2=(MyView) view.findViewById(R.id.MyViewPosts2);
ViewCompat.setAccessibilityHeading(tvPosts2,true);
 tvPosts4=(MyView) view.findViewById(R.id.MyViewPosts4);
 ViewCompat.setAccessibilityHeading(tvPosts4,true);
 tvPosts6=(MyView) view.findViewById(R.id.MyViewPosts6);
 ViewCompat.setAccessibilityHeading(tvPosts6,true);
 //tvPosts8=(MyView) view.findViewById(R.id.MyViewPosts8);

 tvPostsTitle=(MyView) view.findViewById(R.id.MyViewPosts1);
 ViewCompat.setAccessibilityHeading(tvPostsTitle,true);
 tvPostsTitle.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH1));
  tvDescriptionPosts3=(MyView) view.findViewById(R.id.MyViewPosts3);
 tvDescriptionPosts3.setLinksClickable(true);
 tvDescriptionPosts3.setMovementMethod(new LinkMovementMethod());
 
 tvDescriptionPosts5=(MyView) view.findViewById(R.id.MyViewPosts5);
 tvDescriptionPosts5.setLinksClickable(true);
 tvDescriptionPosts5.setMovementMethod(new LinkMovementMethod());
 
 tvDescriptionPosts7=(MyView) view.findViewById(R.id.MyViewPosts7);
 tvDescriptionPosts7.setLinksClickable(true);
 tvDescriptionPosts7.setMovementMethod(new LinkMovementMethod());
 
 /*tvDescriptionPosts9=(MyView) view.findViewById(R.id.MyViewPosts9);
 tvDescriptionPosts9.setLinksClickable(true);
 tvDescriptionPosts9.setMovementMethod(new LinkMovementMethod());
 
 tvDescriptionPosts9.setText(Html.fromHtml(data_posts4));*/
 
 
 // адаптер
 ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
  R.layout.my2_simple_spinner_item, data_years_posts);
 adapter.setDropDownViewResource(R.layout.my2_simple_spinner_dropdown_item);

 spinnerPosts = (Spinner) view.findViewById(R.id.spinnerPosts1);
 spinnerPosts.setAdapter(adapter);
 // заголовок
 spinnerPosts.setPrompt("Title");
 // выделяем элемент
 switch (cal.getYear()) {
 case 2014:
 spinnerPosts.setSelection(0);
 tvDescriptionPosts3.setText(Html.fromHtml(data_posts1[0].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts5.setText(Html.fromHtml(data_posts2[0].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts7.setText(Html.fromHtml(data_posts3[0].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 break;
 case 2015:
 spinnerPosts.setSelection(1);
 tvDescriptionPosts3.setText(Html.fromHtml(data_posts1[1].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts5.setText(Html.fromHtml(data_posts2[1].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts7.setText(Html.fromHtml(data_posts3[1].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 break;
 case 2016:
 spinnerPosts.setSelection(2);
 tvDescriptionPosts3.setText(Html.fromHtml(data_posts1[2].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts5.setText(Html.fromHtml(data_posts2[2].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts7.setText(Html.fromHtml(data_posts3[2].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 break;
 case 2017:
 spinnerPosts.setSelection(3);
 tvDescriptionPosts3.setText(Html.fromHtml(data_posts1[3].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts5.setText(Html.fromHtml(data_posts2[3].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts7.setText(Html.fromHtml(data_posts3[3].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 break;
 case 2018:
 spinnerPosts.setSelection(4);
 tvDescriptionPosts3.setText(Html.fromHtml(data_posts1[4].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts5.setText(Html.fromHtml(data_posts2[4].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts7.setText(Html.fromHtml(data_posts3[4].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 break;
 case 2019:
 spinnerPosts.setSelection(5);
 tvDescriptionPosts3.setText(Html.fromHtml(data_posts1[5].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts5.setText(Html.fromHtml(data_posts2[5].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts7.setText(Html.fromHtml(data_posts3[5].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 break;
 case 2020:
 spinnerPosts.setSelection(6);
 tvDescriptionPosts3.setText(Html.fromHtml(data_posts1[6].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts5.setText(Html.fromHtml(data_posts2[6].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts7.setText(Html.fromHtml(data_posts3[6].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 break;
 case 2021:
 spinnerPosts.setSelection(7);
 tvDescriptionPosts3.setText(Html.fromHtml(data_posts1[7].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts5.setText(Html.fromHtml(data_posts2[7].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts7.setText(Html.fromHtml(data_posts3[7].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 break;
 case 2022:
 spinnerPosts.setSelection(8);
 tvDescriptionPosts3.setText(Html.fromHtml(data_posts1[8].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts5.setText(Html.fromHtml(data_posts2[8].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts7.setText(Html.fromHtml(data_posts3[8].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 break;
 default:
 spinnerPosts.setSelection(0);
 tvDescriptionPosts3.setText(Html.fromHtml(data_posts1[0].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts5.setText(Html.fromHtml(data_posts2[0].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 tvDescriptionPosts7.setText(Html.fromHtml(data_posts3[0].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 break;
 } 
 // устанавливаем обработчик нажатия
 spinnerPosts.setOnItemSelectedListener(new OnItemSelectedListener() {
 @Override
 public void onItemSelected(AdapterView<?> parent, View view,
  int position, long id) {
  // показываем позиция нажатого элемента
  // Toast.makeText(getBaseContext(), "Position = " +
  // position,Toast.LENGTH_SHORT).show();
  tvDescriptionPosts3.setText(Html.fromHtml(data_posts1[position].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
  tvDescriptionPosts5.setText(Html.fromHtml(data_posts2[position].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
  tvDescriptionPosts7.setText(Html.fromHtml(data_posts3[position].replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())));
 }

 @Override
 public void onNothingSelected(AdapterView<?> arg0) {

 }
 });

 return view;
}

@Override
public void onClick(View v) {
 // TODO Auto-generated method stub

}

@Override
public void onStart() {
 // TODO Auto-generated method stub
 super.onStart();
 
 if(standart_text_size1==0) standart_text_size1=tvPostsTitle.getTextSize();//22
 if(standart_text_size2==0) standart_text_size2=tvEasterSelectYear.getTextSize();//17
 if(standart_text_size3==0) standart_text_size3=tvPosts2.getTextSize();//19
 
 String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_text_size", "0");
 if(!text_size.equals(tmp)){
 text_size=tmp;
 //int int_text_size=Integer.valueOf(text_size);
 /*Toast.makeText(getActivity(), text_size+"="+int_text_size,
  Toast.LENGTH_SHORT).show();*/
 //float size=MyView_holiday.getTextSize();
 if(text_size.equals("-5")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-10);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-10);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-10);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-10);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-10);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-10);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-10);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-10);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
 }
 if(text_size.equals("-4")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-8);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-8);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-8);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-8);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-8);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-8);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-8);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-8);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
 }
 if(text_size.equals("-3")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-6);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-6);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
 }
 if(text_size.equals("-2")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-4);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-4);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
 }
 if(text_size.equals("-1")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-2);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-2);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
 }
 if(text_size.equals("0")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
 }
 if(text_size.equals("+1")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+2);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+2);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
 }
 if(text_size.equals("+2")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+4);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+4);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
 }
 if(text_size.equals("+3")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+6);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+6);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
 }
 if(text_size.equals("+4")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+8);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+8);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
 }
 if(text_size.equals("+5")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+10);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+10);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
 }
 if(text_size.equals("+6")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+12);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+12);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
 }
 if(text_size.equals("+7")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+14);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+14);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
 }
 if(text_size.equals("+8")){
  tvPostsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+16);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+16);
  tvPosts2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
  tvPosts4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
  tvPosts6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
  //tvPosts8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
  tvDescriptionPosts3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
  tvDescriptionPosts5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
  tvDescriptionPosts7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
  //tvDescriptionPosts9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
 }
 }
}
}
