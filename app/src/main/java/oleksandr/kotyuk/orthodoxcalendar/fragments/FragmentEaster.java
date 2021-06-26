package oleksandr.kotyuk.orthodoxcalendar.fragments;

import oleksandr.kotyuk.orthodoxcalendar.FontsHelper;
import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import oleksandr.kotyuk.orthodoxcalendar.MyView;

public class FragmentEaster extends Fragment {

private final String description_easter1 = "Христос Воскресе! Воистину Воскресе!";
private final String description_easter2 = "Назвать этот день праздником, даже самым большим праздником – слишком мало. Он важнее любого праздника и значимее любого события в мировой истории. В этот день все человечество, а значит – каждый из нас, получили надежду на спасение, потому что Христос воскрес. Этот день называется Пасхой, что значит – «переход», и отмечается в Православной Церкви как самый главный день в году. В Пасхе – вся суть христианства, весь смысл нашей веры.\n\n"
 + "«Слово «Пасха», – пишет святой Амвросий Медиоланский, – означает «прехождение». Назван же так этот праздник, торжественнейший из праздников, в ветхозаветной Церкви – в воспоминание исхода сынов Израилевых из Египта и вместе с тем избавления их от рабства, а в Церкви новозаветной – в ознаменование того, что Сам Сын Божий, чрез Воскресение из мертвых, перешел от мира сего к Отцу Небесному, от земли на Небо, освободив нас от вечной смерти и рабства врагу, даровав нам «власть быть чадами Божиими» (Ин. 1,12).\n\n"
 + "Распятие Христа произошло в пятницу, которую мы теперь называем страстной, на горе Голгофе, вблизи городских стен Иерусалима. Один из учеников Спасителя, Иосиф Аримафейский, с разрешения прокуратора Иудеи Понтия Пилата снял тело Спасителя с Креста и похоронил Его. Первосвященники выставили у Гроба Господня стражу.\n\n"
 + "По иудейским обычаям, гроб представлял собой пещеру, выдолбленную в скале. Тело умершего смазывали маслами и благовониями, обвивали тканью и клали на каменную плиту. А вход в пещеру закрывали большим камнем. Так поступили и с телом Иисуса – за одним исключением. Его Погребение было совершено в спешке – заканчивалась пятница, а в субботу (которая наступает с вечера пятницы) по иудейским обычаям нельзя делать никаких дел. И поэтому тело Иисуса не успели умастить благовониями.\n\n"
 + "Благочестивые женщины, ученицы Христа, очень переживали по этому поводу. Они любили Христа, и им хотелось, чтобы Он ушел в Свой последний земной путь «как полагается». Поэтому рано утром в воскресенье, взяв благовонные масла, они поспешили к Гробу, чтобы исполнить все, что нужно. Благовонные масла еще называются миром, вот отчего тех женщин мы называем женами-мироносицами.\n\n"
 + "«По прошествии субботы, на рассвете первого дня недели, пришли Мария Магдалина и другая Мария посмотреть гроб. И вот сделалось великое землетрясение, ибо Ангел Господень, сошедший с небес, приступив, отвалил камень от двери гроба и сидел на нем; вид его был, как молния, и одежда его бела, как снег; устрашившись его, стерегущие пришли в трепет и стали, как мертвые; Ангел же, обратив речь к женщинам, сказал: не бойтесь, ибо знаю, что вы ищете Иисуса распятого; Его нет здесь – Он воскрес, как сказал. Подойдите, посмотрите место, где лежал Господь, и пойдите скорее, скажите ученикам Его, что Он воскрес из мертвых…» (Мф. 28,1-7)– так повествует Евангелие.\n\n"
 + "Женщины, изумленные самим фактом явления им Ангела, действительно подошли и посмотрели. И удивились еще больше, увидев, что гробница пуста. В пещере лежала только ткань, в которую было завернуто тело, и платок, который был на голове Христа. Немного придя в себя, они вспомнили слова, сказанные когда-то Спасителем: «Как Иона был во чреве кита три дня и три ночи, так и Сын Человеческий будет в сердце земли три дня и три ночи» (Мф. 12,40). Они вспомнили и другие слова Христа о Воскресении через три дня после смерти, казавшиеся им туманными и непонятными. Ученики Христа думали, что слова о Воскресении – это метафора, что Христос говорил о Своем Воскресении не в прямом смысле, а в переносном, что речь шла о чем-то другом! Но оказалось, что Христос воскрес – в самом прямом смысле этого слова! Печаль женщин сменилась радостью, и они побежали сообщить о Воскресении апостолам… А стражники, которые дежурили возле Гроба и видели все, немного придя в себя от удивления и испуга, пошли рассказать об этом первосвященникам.\n\n"
 + "Это сейчас мы точно знаем, что после мучений Христа будет Его вечная слава, а после распятия на Кресте – Его светлое Воскресение. Но представьте состояние Его учеников: униженный, ненавидимый властями и не принятый большинством людей, их Учитель умер. И ничто не вселяло в апостолов надежду. Ведь даже Сам Иисус умирал со страшными словами: «Боже мой! для чего Ты оставил Меня?» (Лк. 15,34). И вдруг ученицы Христа сообщают им такую радостную новость…\n\n"
 + "Вечером того же дня апостолы собрались в одном иерусалимском доме, чтобы обсудить происшедшее: сначала они отказывались верить в то, что Христос воскрес – слишком уж это было неподвластно человеческому пониманию. Двери дома были наглухо заперты – апостолы опасались преследования властей. И вдруг неожиданно вошел Сам Господь и, встав посреди них, сказал: «Мир вам!»\n\n"
 + "Кстати, апостола Фомы в воскресенье в том иерусалимском доме не было. И когда другие апостолы рассказали ему о чуде, Фома не поверил – за что, собственно, и был прозван неверующим. Фома не верил в рассказы о воскресении Иисуса до тех пор, пока собственными глазами не увидел Его. А на Его теле – раны от гвоздей, которыми Христа прибивали к Кресту, и пробитые копьем ребра Спасителя… После этого Фома, как и другие апостолы, пошел проповедовать – донести до каждого Благую весть. И мученически умер за Христа: он-то точно знал, что Христос воскрес, и даже угроза смертной казни не заставила апостола перестать говорить об этом людям.\n\n"
 + "После этого Господь являлся апостолам, и не только им, еще не один раз – до тех пор, пока на сороковой день после Своего Воскресения не вознесся на Небо. Прекрасно зная человеческую природу: мы ничему не верим, пока не убедимся в этом сами, Иисус, по сути, пожалел своих учеников. Чтобы они не мучились сомнениями, Он часто находился среди них, разговаривал с ними, подтверждая тем самым то, во что поверить на первый взгляд было невозможно – в то, что Христос воскрес!\n\n"
 + "Апостол Павел, который вообще никогда не видел Христа в Его земной жизни, но которому Он явился после Своего Воскресения, обозначил суть нашей веры: «Если Христос не воскрес, то вера ваша тщетна... то мы несчастнее всех человеков» (1Кор.15,17-19).\n\n"
 + "«Своим Воскресением Христос дал людям постигнуть истинность Своего Божества, истинность Своего высокого учения, спасительность Своей смерти. Воскресение Христа – это завершение Его жизненного подвига. Иного конца не могло быть, ибо это прямое следствие нравственного смысла Христовой жизни», – это слова из пасхальной проповеди архимандрита Иоанна (Крестьянкина).\n\n"
 + "Христос воскрес и вознесся на Небо, но Он всегда присутствует в Своей Церкви. И любой из нас может прикоснуться к Нему – на главном христианском богослужении, литургии, когда священник выходит к людям с Телом и Кровью воскресшего Христа…\n\n"
 + "И нет на земле слов радостнее, чем те, что говорят друг другу люди в Светлое воскресенье и последующие сорок дней: Христос воскресе! Воистину воскресе!";

private final String[] data_years = { "2010", "2011", "2012", "2013",
 "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024" };

private final String[] data_easters = { "04 апреля 2010г.",
 "24 апреля 2011г.", "15 апреля 2012г.", "05 мая 2013г.",
 "20 апреля 2014г.", "12 апреля 2015г.", "01 мая 2016г.",
 "16 апреля 2017г.", "08 апреля 2018г.", "28 апреля 2019г.",
 "19 апреля 2020г.", "02 мая 2021г.", "24 апреля 2022г.", 
 "16 апреля 2023г.", "05 мая 2024г." };

public static final String IMAGE_RESOURCE_ID = "iconResourceID";
public static final String ITEM_NAME = "itemName";

private final String FONT_PATH1 = "fonts/Russo_One.ttf";
MyView tvEasterTitle;
MyView tvDataEaster;
MyView tvDescriptionEaster1;
MyView tvDescriptionEaster2;
Spinner spinnerEaster;

MyCalendar cal = MyCalendar.getInstance();

String text_size="0";
float standart_text_size1=0;
float standart_text_size2=0;
float standart_text_size3=0;
float standart_text_size4=0;
MyView tvEasterSelectYear;
MyView tvEasterSunday;

public FragmentEaster() {

}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {
 View view = inflater.inflate(R.layout.fragment_layout_easter,
  container, false);

 // адаптер
 ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
  R.layout.my2_simple_spinner_item, data_years);
 adapter.setDropDownViewResource(R.layout.my2_simple_spinner_dropdown_item);

 spinnerEaster = (Spinner) view.findViewById(R.id.spinnerEaster1);
 spinnerEaster.setAdapter(adapter);
 // заголовок
 spinnerEaster.setPrompt("Title");
 // выделяем элемент
 switch (cal.getYear()) {
 case 2015:
 spinnerEaster.setSelection(5);
 break;
 case 2016:
 spinnerEaster.setSelection(6);
 break;
 case 2017:
 spinnerEaster.setSelection(7);
 break;
 case 2018:
 spinnerEaster.setSelection(8);
 break;
 case 2019:
 spinnerEaster.setSelection(9);
 break;
 case 2020:
 spinnerEaster.setSelection(10);
 break;
 case 2021:
 spinnerEaster.setSelection(11);
 break;
 case 2022:
 spinnerEaster.setSelection(12);
 break;
 default:
 spinnerEaster.setSelection(0);
 break;
 }
 // устанавливаем обработчик нажатия
 spinnerEaster.setOnItemSelectedListener(new OnItemSelectedListener() {
 @Override
 public void onItemSelected(AdapterView<?> parent, View view,
  int position, long id) {
  // показываем позиция нажатого элемента
  // Toast.makeText(getBaseContext(), "Position = " +
  // position,Toast.LENGTH_SHORT).show();
  tvDataEaster.setText(cal.getEasterDate(data_years[position]));
 }

 @Override
 public void onNothingSelected(AdapterView<?> arg0) {

 }
 });

 tvEasterSelectYear=(MyView) view.findViewById(R.id.MyViewEaster2);
 tvEasterSunday=(MyView) view.findViewById(R.id.MyViewEaster3);
 ViewCompat.setAccessibilityHeading(tvEasterSunday,true);
 tvEasterTitle=(MyView) view.findViewById(R.id.MyViewEaster1);
 ViewCompat.setAccessibilityHeading(tvEasterTitle,true);
 tvEasterTitle.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH1));
 tvDataEaster = (MyView) view.findViewById(R.id.MyViewEaster4);
 tvDataEaster.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH1));
 tvDataEaster.setText(data_easters[4]);
 
 tvDescriptionEaster1 = (MyView) view
  .findViewById(R.id.MyViewEaster5);
 tvDescriptionEaster2 = (MyView) view
  .findViewById(R.id.MyViewEaster6);
 tvDescriptionEaster1.setText(description_easter1);
 tvDescriptionEaster2.setText(description_easter2);
 return view;
}

@Override
public void onStart() {
 // TODO Auto-generated method stub
 super.onStart();
 
 if(standart_text_size1==0) standart_text_size1=tvEasterTitle.getTextSize();//22
 if(standart_text_size2==0) standart_text_size2=tvEasterSelectYear.getTextSize();//17
 if(standart_text_size3==0) standart_text_size3=tvDataEaster.getTextSize();//21
 if(standart_text_size4==0) standart_text_size4=tvDescriptionEaster2.getTextSize();//19
 
 String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_text_size", "0");
 if(!text_size.equals(tmp)){
 text_size=tmp;
 //int int_text_size=Integer.valueOf(text_size);
 /*Toast.makeText(getActivity(), text_size+"="+int_text_size,
  Toast.LENGTH_SHORT).show();*/
 //float size=MyView_holiday.getTextSize();
 if(text_size.equals("-5")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-10);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-10);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-10);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-10);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-10);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-10);
 }
 if(text_size.equals("-4")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-8);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-8);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-8);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-8);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-8);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-8);
 }
 if(text_size.equals("-3")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-6);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-6);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-6);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-6);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-6);
 }
 if(text_size.equals("-2")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-4);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-4);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-4);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-4);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-4);
 }
 if(text_size.equals("-1")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-2);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-2);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-2);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-2);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4-2);
 }
 if(text_size.equals("0")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4);
 }
 if(text_size.equals("+1")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+2);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+2);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+2);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+2);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+2);
 }
 if(text_size.equals("+2")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+4);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+4);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+4);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+4);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+4);
 }
 if(text_size.equals("+3")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+6);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+6);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+6);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+6);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+6);
 }
 if(text_size.equals("+4")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+8);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+8);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+8);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+8);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+8);
 }
 if(text_size.equals("+5")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+10);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+10);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+10);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+10);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+10);
 }
 if(text_size.equals("+6")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+12);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+12);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+12);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+12);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+12);
 }
 if(text_size.equals("+7")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+14);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+14);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+14);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+14);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+14);
 }
 if(text_size.equals("+8")){
  tvEasterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+16);
  tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+16);
  tvDataEaster.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
  tvEasterSunday.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+16);
  tvDescriptionEaster1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+16);
  tvDescriptionEaster2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4+16);
 }
 }
}


}
