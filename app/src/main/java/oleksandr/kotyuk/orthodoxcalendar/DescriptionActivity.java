package oleksandr.kotyuk.orthodoxcalendar;

import java.util.ArrayList;

import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import androidx.appcompat.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import oleksandr.kotyuk.orthodoxcalendar.MyView;
import android.widget.Toast;

public class DescriptionActivity extends MenuActivity {

static final String TAG = "myLogs";
int id_description = 1;

private DatabaseHelper db;
Cursor cursor;
String sql = "";

LinearLayout llDescriptionActivity;
MyView tvDescriptionActivity;

//private final String FONT_PATH1 = "fonts/TRIOD35.TTF";
//private final String FONT_PATH2 = "fonts/IrmUcs.ttf";

private final String FONT_PATH_RU1 = "fonts/Arial.ttf";
private final String FONT_PATH_RU2 = "fonts/Calibri.ttf";
private final String FONT_PATH_RU3 = "fonts/Cambria.ttf";
private final String FONT_PATH_RU4 = "fonts/DroidSans.ttf";
private final String FONT_PATH_RU5 = "fonts/DroidSerif.ttf";
private final String FONT_PATH_RU6 = "fonts/Times.ttf";
private final String FONT_PATH_RU7 = "fonts/Verdana.ttf";

private final String FONT_PATH_CS1 = "fonts/Canonic.ttf";
private final String FONT_PATH_CS2 = "fonts/Orthodox.ttf";
private final String FONT_PATH_CS3 = "fonts/Triodion.ttf";

String prayers_language;
String black_fon_color;

float size_default_MyView = 0;
float size = 0;

boolean description_style=false;

int orientation=0;

//String[] list_bookmarks;
ArrayList <String> arr_bookmark;
int arr_bookmark_id=-1;
int prayers_id;
int prayers_id_2;
String prayers_type="";

boolean flag_5_kanon=false;

private String tipikon = "<FONT COLOR=#aa2c2c><b>«Ќј » “»ѕ» ќЌј</b></FONT><br><br>"
 + "ѕравославный календарь снабжен специальными знаками, которые проставл¤ютс¤ обычно на пол¤х около праздничного событи¤ или пам¤ти св¤того. ¬ литургическом смысле эти знаки указывают, с какой торжественностью следует совершать Ѕогослужение данного праздника. ¬ техническо-богослужебном смысле знак содержит информацию о наличии/отсутствии тех или иных корпусов Ѕогослужени¤ (напр.: чтение ≈вангели¤ на утрени или пение \"Ѕлажен муж\"), наличии/отсутствии или кол-ве стихир, седальнов, антифонов, канонов и т.д. ќбъ¤снение знаков содержитс¤ в 47 главе “ипикона \"ќ знамени¤х праздников великих, и средних, и малых\".<br><br>"
 + " оличество знаков, примен¤емых в “ипиконе Ц 5, а количество служб, раздел¤емых по рангу Ц 6 (сама¤ \"непразднична¤\" служба не обозначаетс¤ никаким знаком). ƒл¤ изображени¤ знаков используютс¤ два цвета Ц черный и красный.<br><br>"
 + "¬ ѕравославном календаре, ввиду отсутстви¤ в шрифте таких специальных знаков, мы использовали обозначени¤, которые замен¤ют собой оригинальные знаки “ипикона:<br><br>"
 + "<FONT COLOR=RED>(+)</FONT> Ц \"крест окружен\" (красный знак), бдение на великие праздники;<br>"
 + "наиболее празднична¤ служба, где все измен¤емые песнопени¤ посв¤щены ѕразднику.<br><br>"
 + "<FONT COLOR=RED>+)</FONT>  Ц \"крест полуокружен\", \"бдение\" (красный знак)<br>"
 + "такому св¤тому совершаетс¤ ¬сенощное бдение. ≈го устав близок к полиелейной службе, отличи¤ заключаютс¤ в том, что по¤вл¤етс¤ мала¤ вечерн¤, велика¤ вечерн¤ и утрен¤ объедин¤ютс¤ (отсюда бдение), бывает осв¤щение хлебов и елеопомазание в конце утрени.<br><br>"
 + "<FONT COLOR=RED>+</FONT>   Ц \"крест\", \"полиелей\", \"с полиелеем\" (красный знак);<br>"
 + "полиелейна¤ служба, т.е. така¤ служба, на которой на утрени исполн¤етс¤ полиелей (торжественное пение псалмов 134 и 135 с припевами); кроме того, на такой службе бывает чтение ≈вангели¤, прокимен, степенные антифоны, канон на 8, поютс¤ хвалитны и ¬еликое славословие, а на вечерни по¤вл¤етс¤ \"Ѕлажен муж\" (перва¤ —лава 1-й кафисмы), бывает ¬ход, читаютс¤ паремии, а также все стихиры поютс¤ св¤тому, может служитьс¤ лити¤.<br><br>"
 + "<FONT COLOR=RED>(:Ј</FONT> Ц \"славословный\", \"со славословием\" (красный цвет);<br>"
 + "такому св¤тому полагаетс¤ петь ¬еликое славословие в конце утрени (в службах рангом ниже оно читаетс¤), кроме того, на службе поютс¤ уже некоторые воскресные богородичны, седальны по кафизмам Ц св¤тому, на каноне бывает катаваси¤, а в конце утрени поютс¤ стихиры на хвалитех, ¬еликое славословие, и все окончание утрени бывает по праздничному.<br><br>"
 + "(:Ј Ц \"шестерик\", \"на шесть\" (черный цвет);<br>"
 + "св¤тому положены все шесть стихир на \"√осподи воззвах\", бывает —лава на вечерней и утреней стиховне, тропарь, утренний канон св¤того на шесть.<br><br>"
 + "    Ц \"без знака\"; сама¤ р¤дова¤ повседневна¤ служба св¤тому,<br>"
 + "которому положено только три стихиры на \"√осподи воззвах\" и канон на утрени на 4 тропар¤. “ропар¤ св¤того может не быть.<br>";

private String abbreviation = "<FONT COLOR=#aa2c2c><b>—ќ –јў≈Ќ»я ¬  јЋ≈Ќƒј–≈:</b></FONT><br><br>"
 + "ап. Ч апостол;<br>" + "апп. Ч апостолы;<br>"
 + "архиеп. Ч архиепископ;<br>" + "архиепп. Ч архиепископы;<br>"
 + "архим. Ч архимандрит;<br>" + "архимм. Ч архимандриты;<br>"
 + "бесср. Ч бессребреник, бессребреники;<br>"
 + "блгв. Ч благоверный (благоверна¤);<br>"
 + "блгвв. Ч благоверные;<br>"
 + "блж. (блаж.) Ч блаженна¤, блаженный;<br>"
 + "блжж. Ч блаженные;<br>" + "вел. Ч великий, велика¤;<br>"
 + "вмц. (влкмц.) Ч великомученица;<br>"
 + "вмцц. (влкмцц.) Ч великомученицы;<br>"
 + "вмч. (влкмч.) Ч великомученик;<br>"
 + "вмчч. (влкмчч.) Ч великомученики;<br>" + "диак. Ч диакон;<br>"
 + "ев. Ч евангелист;<br>" + "еп. Ч епископ;<br>"
 + "епп. Ч епископы;<br>" + "игум. Ч игумен;<br>"
 + "иером. Ч иеромонах;<br>" + "иеросхим. Ч иеросхимонах;<br>"
 + "имп. Ч император;<br>"
 + "исп. (испов.) Ч исповедник, исповедница;<br>"
 + "кн. Ч кн¤зь;<br>" + "кнн. Ч кн¤зь¤;<br>" + "кнг. Ч кн¤гин¤;<br>"
 + "кнж. Ч кн¤жна;<br>" + "митр. Ч митрополит;<br>"
 + "митрр. Ч митрополиты;<br>" + "мч. Ч мученик;<br>"
 + "мчч. Ч мученики;<br>" + "мц. Ч мученица;<br>"
 + "мцц. (мчцц.) Ч мученицы;<br>"
 + "новмч. (новомуч.) Ч новомученик;<br>"
 + "новосвщмч. Ч новосв¤щенномученик;<br>" + "патр. Ч патриарх;<br>"
 + "патрр. Ч патриархи;<br>" + "прав. Ч праведный;<br>"
 + "правв. Ч праведные;<br>" + "пресвит. Ч пресвитер;<br>"
 + "прор. Ч пророк;<br>" + "прорр. Ч пророки;<br>"
 + "пророчц. Ч пророчица;<br>"
 + "просвет. Ч просветитель, просветительница;<br>"
 + "прот. Ч протоиерей;<br>" + "протопресв. Ч протопресвитер;<br>"
 + "прмч. Ч преподобномученик;<br>"
 + "прмчч. Ч преподобномученики;<br>"
 + "прмц. Ч преподобномученица;<br>"
 + "прмцц. Ч преподобномученицы;<br>"
 + "прп. (преп.) Ч преподобный;<br>" + "прпп. Ч преподобные;<br>"
 + "равноап. Ч равноапостольный, равноапостольна¤;<br>"
 + "равноапп. Ч равноапостольные;<br>" + "св. Ч св¤той, св¤та¤;<br>"
 + "свв. Ч св¤тые;<br>" + "свт. Ч св¤титель;<br>"
 + "свтт. Ч св¤тители;<br>" + "св¤щ. Ч св¤щенник;<br>"
 + "сщмч. Ч св¤щенномученик;<br>" + "сщмчч. Ч св¤щенномученики;<br>"
 + "столпн. Ч столпник;<br>" + "страст. Ч страстотерпец;<br>"
 + "схим. Ч схимонах;<br>" + "чудотв. Ч чудотворец;<br>"
 + "юрод. Ч юродивый;<br><br>" + "утр. Ц утрен¤;<br>"
 + "ћф. Ц ≈вангелие от ћатфе¤;<br>"
 + "ћк. Ц ≈вангелие от ћарка;<br>" + "Ћк. Ц ≈вангелие от Ћуки;<br>"
 + "»н. Ц ≈вангелие от »оанна;<br>"
 + "ƒе¤н. Ц ƒе¤ни¤ св¤тых апостолов;<br>"
 + "»ак. Ц ѕослание »акова;<br>"
 + "1 ѕет. Ц 1-е послание ѕетра;<br>"
 + "2 ѕет. Ц 2-е послание ѕетра;<br>"
 + "1 »н. Ц 1-е послание »оанна;<br>"
 + "2 »н. Ц 2-е послание »оанна;<br>"
 + "« »н. Ц «-е послание »оанна;<br>" + "»уд. Ц ѕослание »уды;<br>"
 + "–им. Ц ѕослание к римл¤нам;<br>"
 + "1  ор. Ц 1-е послание к коринф¤нам;<br>"
 + "2  ор. Ц 2-е послание к коринф¤нам;<br>"
 + "√ал. Ц ѕослание к галатам;<br>"
 + "≈ф. Ц ѕослание к ефес¤нам;<br>"
 + "‘лп. Ц ѕослание к филиппийцам;<br>"
 + " ол. Ц ѕослание к колосс¤нам;<br>"
 + "1 —ол. Ц 1-е послание к солун¤нам;<br>"
 + "2 —ол. Ц 2-е послание к солун¤нам;<br>"
 + "1 “им. Ц 1-е послание к “имофею;<br>"
 + "2 “им. Ц 2-е послание к “имофею;<br>"
 + "“ит. Ц ѕослание к “иту;<br>" + "‘лм. Ц ѕослание к ‘илимону;<br>"
 + "≈вр. Ц ѕослание к евре¤м;<br>" + "Ѕыт. Ц Ѕытие;<br>"
 + "»сх. Ц »сход;<br>" + "ѕритч. Ц ѕритчи —оломона;<br>"
 + "ѕрем. —олом. Ц книга ѕремудрости —оломона;<br>"
 + "»с. Ц книга »саии;<br>" + "»оил. Ц книга »оил¤;<br>"
 + "«ах. Ц книга «ахарии;<br>" + "ћал. Ц книга ћалахии.<br>";

String info="";
String text_br="<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>";

public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);

 Log.d(TAG, "DescriptionActivity onCreate");

 ///////////////////////////////////////
 //Toast.makeText(this, "DescriptionActivity...onCreate", Toast.LENGTH_LONG).show();
 /////////////////////////////////////////

 // получаем ID выбраного раздела(подпункта)
 Intent intent = getIntent();
 id_description = intent.getIntExtra("id", 1);

 //orientation = getResources().getConfiguration().orientation;
 Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
         .getDefaultDisplay();
 orientation = display.getRotation();



 Log.d(TAG, "DescriptionActivity onCreate_orientation="+orientation);

 int data = intent.getIntExtra("data", 1);
 int month = intent.getIntExtra("month", 0);
 int year = intent.getIntExtra("year", 2014);
 prayers_id = intent.getIntExtra("prayers_id", 1);
 int id_termin = intent.getIntExtra("id_termin", 1);
 String prayers_name = intent.getStringExtra("prayers_name");
 String post_info=intent.getStringExtra("post_info");
 String sedmitsa_info=intent.getStringExtra("sedmitsa_info");
 int directory_id = intent.getIntExtra("directory_id", 1);
 prayers_type=intent.getStringExtra("prayers_type");


 prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_prayers_language", "ru");
 if(id_description==5){
 if (prayers_language.equals("ru")) {
  size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_prayers_text_size_ru", 0);
  arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_ru");
 }
 if (prayers_language.equals("cs")) {
  size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_prayers_text_size_cs", 0);
  arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_cs");
 }
 description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style1", false);
 }else{
 size_default_MyView = PreferencesActivity.MyPreferenceFragment
  .ReadFloat(this, "pref_description_text_size", 0);
 description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style2", false);
 }

 ///////////////////////////////////////////////////////////////////////
 //SelectBookmarksOnOff();
 ///////////////////////////////////////////////////////////

 if (prayers_name == null) {
 if (prayers_language.equals("ru"))
  prayers_name = "ћолитвы утренние";
 if(prayers_language.equals("cs"))
  prayers_name = "ћlтвы ЭтрЇнн≥z";
 }

 ActionBar actionBar = getSupportActionBar();

 // Enabling Up / Back navigation
 actionBar.setDisplayHomeAsUpEnabled(true);

 switch (id_description) {
 case 1:
 setTitle("ћногодневные посты");
 sql = "select description from multiday_post_years";
 break;
 case 2:
 setTitle("ќднодневные посты");
 sql = "select description from oneday_post_years";
 break;
 case 3:
 setTitle("—плошные седмицы");
 sql = "select description from sedmitsa_post_years";
 break;
 case 4:
 setTitle("ќписание праздников");
 break;
 case 5:
 setTitle("ћолитвослов");
 //setTitle(prayers_name);
 if (prayers_language.equals("ru")){
  if(prayers_type.equals("pr"))
  sql = "select id2, text_prayers from prayers_ru_pr where _id=" + prayers_id;
  else{
  sql = "select id2, text_prayers from prayers_ru_ak where _id=" + prayers_id;
  if(prayers_id==63) flag_5_kanon=true;
  }
 }
 else{
  if(prayers_type.equals("pr"))
  sql = "select id2, text_prayers from prayers_cs_pr where _id=" + prayers_id;
  else
  sql = "select id2, text_prayers from prayers_cs_ak where _id=" + prayers_id;
 }
 break;
 case 6:
 setTitle("—правочник пользовател¤");
 break;
 case 7:
 setTitle(" раткий словарь терминов");
 sql = "select description from termin_description where _id="+id_termin;
 break;
 case 8:
 ///////////////////////////////////////
 //Toast.makeText(this, "DescriptionActivity...switch-case8", Toast.LENGTH_LONG).show();
 /////////////////////////////////////////
 setTitle("ќбновлени¤");
 break;
 case 10:
 setTitle("ќ посте");
 break;
 case 11:
 setTitle("ќ седмице");
 break;
 default:
 setTitle("ћногодневные посты");
 sql = "select description from multiday_post_years";
 break;
 }

 setContentView(R.layout.activity_description2);

 llDescriptionActivity=(LinearLayout)this.findViewById(R.id.llViewDescription);

 tvDescriptionActivity = (MyView) this
  .findViewById(R.id.MyViewDescription1);

 if(flag_5_kanon){
 tvDescriptionActivity.setLinksClickable(true);
 tvDescriptionActivity.setMovementMethod(new LinkMovementMethod());
 }

 black_fon_color = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_black_fon_color", "black");

 if(description_style){
 if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
 if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
 if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
 if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
 tvDescriptionActivity.setTextColor(getResources().getColor(R.color.WHITE2));
 }

 db = DatabaseHelper.getInstance(this);

 if (id_description != 4) {
 if (id_description == 6 || id_description == 8 || id_description==10 || id_description==11) {
  if(id_description == 6){
  size = tvDescriptionActivity.getTextSize();
  // Log.d(TAG, "size =" + size);
  size = size + size_default_MyView;

  String prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(this, "pref_prayers_fonts_ru", "1");
  if(prayers_fonts_ru.equals("1"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU1));
  if(prayers_fonts_ru.equals("2"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU2));
  if(prayers_fonts_ru.equals("3"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU3));
  if(prayers_fonts_ru.equals("4"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU4));
  if(prayers_fonts_ru.equals("5"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU5));
  if(prayers_fonts_ru.equals("6"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU6));
  if(prayers_fonts_ru.equals("7"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU7));

  tvDescriptionActivity.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, size);
  if(directory_id==1)
   tvDescriptionActivity.setText(Html.fromHtml(abbreviation + text_br));
  else
   tvDescriptionActivity.setText(Html.fromHtml(tipikon + text_br));
  //tvDescriptionActivity.setText(Html.fromHtml(tipikon + "<br><br>" + abbreviation + "<br>"));
  }
  if(id_description == 8){
  ///////////////////////////////////////
  //Toast.makeText(this, "DescriptionActivity...id_description-1", Toast.LENGTH_LONG).show();
  /////////////////////////////////////////
  size = tvDescriptionActivity.getTextSize();
  // Log.d(TAG, "size =" + size);
  size = size + size_default_MyView;
  tvDescriptionActivity.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, size);
  ///////////////////////////////////////
  //Toast.makeText(this, "DescriptionActivity...id_description-2", Toast.LENGTH_LONG).show();
  /////////////////////////////////////////
  tvDescriptionActivity.setText(Html.fromHtml(info +"<b>ќбновлени¤</b><br><br>"+ getResources().getString(R.string.text_update_news_list).replace("\n", "<br>") + text_br));
  ///////////////////////////////////////
  //Toast.makeText(this, "DescriptionActivity...id_description-3", Toast.LENGTH_LONG).show();
  /////////////////////////////////////////
  }
  if(id_description == 10){
  if(post_info.length()<2 || post_info.equals("#")){
   post_info="ƒополнительна¤ информаци¤ о посте отсутствует.";
  }

  String prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(this, "pref_prayers_fonts_ru", "1");
  if(prayers_fonts_ru.equals("1"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU1));
  if(prayers_fonts_ru.equals("2"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU2));
  if(prayers_fonts_ru.equals("3"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU3));
  if(prayers_fonts_ru.equals("4"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU4));
  if(prayers_fonts_ru.equals("5"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU5));
  if(prayers_fonts_ru.equals("6"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU6));
  if(prayers_fonts_ru.equals("7"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU7));

  size = tvDescriptionActivity.getTextSize();
  // Log.d(TAG, "size =" + size);
  size = size + size_default_MyView;
  tvDescriptionActivity.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, size);

  tvDescriptionActivity.setText(Html.fromHtml(post_info + text_br));
  }
  if(id_description == 11){
  if(sedmitsa_info.length()<2 || sedmitsa_info.equals("#")){
   sedmitsa_info="ƒополнительна¤ информаци¤ о седмице отсутствует.";
  }

  String prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(this, "pref_prayers_fonts_ru", "1");
  if(prayers_fonts_ru.equals("1"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU1));
  if(prayers_fonts_ru.equals("2"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU2));
  if(prayers_fonts_ru.equals("3"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU3));
  if(prayers_fonts_ru.equals("4"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU4));
  if(prayers_fonts_ru.equals("5"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU5));
  if(prayers_fonts_ru.equals("6"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU6));
  if(prayers_fonts_ru.equals("7"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU7));

  size = tvDescriptionActivity.getTextSize();
  // Log.d(TAG, "size =" + size);
  size = size + size_default_MyView;
  tvDescriptionActivity.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, size);

  tvDescriptionActivity.setText(Html.fromHtml(sedmitsa_info + text_br));
  }
 } else {
  db = DatabaseHelper.getInstance(this);
  cursor = db.executeQuery(sql);
  StringBuilder text = new StringBuilder();
  //if (id_description == 5) text.append("<br>");
  if (cursor != null) {
  if (cursor.moveToFirst()) {
   do {
   try {
    if (id_description == 5) {

    if (prayers_language.equals("ru"))
     text.append("<FONT COLOR=#aa2c2c><b>"+prayers_name+"</b></FONT><br>"+cursor.getString(cursor
      .getColumnIndex("text_prayers"))
      + text_br);
    else
     text.append("<FONT COLOR=#aa2c2c><b>"+prayers_name+"</b></FONT><br>"+cursor.getString(cursor
      .getColumnIndex("text_prayers"))
      + text_br);
    prayers_id_2=cursor.getInt(cursor.getColumnIndex("id2"));
    } else {

    text.append(cursor.getString(cursor
     .getColumnIndex("description"))
     + text_br);
    }
   } catch (NumberFormatException e) {
    // Log.d(TAG, "ERROR=" + e.toString());
   }
   } while (cursor.moveToNext());

  }
  }

  if(id_description==7){
  String prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(this, "pref_prayers_fonts_ru", "1");
  if(prayers_fonts_ru.equals("1"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU1));
  if(prayers_fonts_ru.equals("2"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU2));
  if(prayers_fonts_ru.equals("3"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU3));
  if(prayers_fonts_ru.equals("4"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU4));
  if(prayers_fonts_ru.equals("5"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU5));
  if(prayers_fonts_ru.equals("6"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU6));
  if(prayers_fonts_ru.equals("7"))
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU7));
  }
  if (id_description == 5) {
  /*size = tvDescriptionActivity.getTextSize();
  // Log.d(TAG, "size =" + size);
  size = size + size_default_MyView;
  tvDescriptionActivity.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, size);*/

  if (prayers_language.equals("ru")){
   String prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(this, "pref_prayers_fonts_ru", "1");
   if(prayers_fonts_ru.equals("1")){
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU1));
   tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
   }
   if(prayers_fonts_ru.equals("2")){
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU2));
   tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
   }
   if(prayers_fonts_ru.equals("3")){
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU3));
   tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
   }
   if(prayers_fonts_ru.equals("4")){
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU4));
   tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
   }
   if(prayers_fonts_ru.equals("5")){
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU5));
   tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
   }
   if(prayers_fonts_ru.equals("6")){
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU6));
   tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
   }
   if(prayers_fonts_ru.equals("7")){
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU7));
   tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
   }

   //tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH1));
  }
  else{
   String prayers_fonts_cs=PreferencesActivity.MyPreferenceFragment.ReadString(this, "pref_prayers_fonts_cs", "1");
   if(prayers_fonts_cs.equals("1")){
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_CS1));
   tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -5.0f,  getResources().getDisplayMetrics()), 1.0f);
   }
   if(prayers_fonts_cs.equals("2")){
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_CS2));
   tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -8.0f,  getResources().getDisplayMetrics()), 1.0f);
   }
   if(prayers_fonts_cs.equals("3")){
   tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_CS3));
   tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -10.0f,  getResources().getDisplayMetrics()), 1.0f);
   }

   //tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH2));
  }
  }

  size = tvDescriptionActivity.getTextSize();
  // Log.d(TAG, "size =" + size);
  size = size + size_default_MyView;
  tvDescriptionActivity.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, size);
  //String ffff=text.toString().replace("\r\n", "<br>");
  //String ffff2=ffff.substring(25386, 28614);
  //Spanned ffff3=Html.fromHtml(ffff2);
  //**************************************************
  String ffff=text.toString().replace("\r\n", "<br>");
  if(flag_5_kanon){
  ffff=ffff.replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST());
  }
  tvDescriptionActivity.setText(Html.fromHtml(ffff));
  //****************************************************

  //tvDescriptionActivity.setText(Html.fromHtml(text.toString().replace("\r\n", "<br>")));

 }
 } else {
 StringBuilder text = new StringBuilder();
 sql = "select ru_detailed_description from gr_unmovable_holiday where m"
  + year + "=" + month + " and d" + year + "=" + data;
 // Log.d(TAG, "sql1=" + sql);
 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  do {
   try {
   if (!(cursor.getString(cursor
    .getColumnIndex("ru_detailed_description")))
    .equals("#")) {
    text.append(cursor.getString(cursor
     .getColumnIndex("ru_detailed_description"))
     + "<br><br>");
   }
   } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
   }
  } while (cursor.moveToNext());

  }
 }

 cursor = null;
 sql = "select ru_detailed_description from big_unmovable_holiday where m"
  + year + "=" + month + " and d" + year + "=" + data;
 // Log.d(TAG, "sql2=" + sql);
 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  do {
   try {
   if (!(cursor.getString(cursor
    .getColumnIndex("ru_detailed_description")))
    .equals("#")) {
    text.append(cursor.getString(cursor
     .getColumnIndex("ru_detailed_description"))
     + "<br><br>");
   }
   } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
   }
  } while (cursor.moveToNext());

  }
 }

 cursor = null;
 sql = "select ru_detailed_description from data_calendar where month="
  + month + " and day=" + data;
 // Log.d(TAG, "sql3=" + sql);
 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  do {
   try {
   if (!(cursor.getString(cursor
    .getColumnIndex("ru_detailed_description")))
    .equals("#")) {
    text.append(cursor.getString(cursor
     .getColumnIndex("ru_detailed_description"))
     + "<br><br>");
   }
   } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
   }
  } while (cursor.moveToNext());

  }
 }

 //---------------------------------------------

 cursor = null;
 sql = "select ru_detailed_description from unmovable_holiday where m"
  + year + "=" + month + " and d" + year + "=" + data;
 // Log.d(TAG, "sql2=" + sql);
 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  do {
   try {
   if (!(cursor.getString(cursor
    .getColumnIndex("ru_detailed_description")))
    .equals("#")) {
    text.append(cursor.getString(cursor
     .getColumnIndex("ru_detailed_description"))
     + "<br><br><br>");
   }
   } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
   }
  } while (cursor.moveToNext());

  }
 }

 //---------------------------------------------

 size = tvDescriptionActivity.getTextSize();
 // Log.d(TAG, "size =" + size);
 size = size + size_default_MyView;
 tvDescriptionActivity.setTextSize(
  TypedValue.COMPLEX_UNIT_PX, size);

 if (text.toString().equals("#")) {
  tvDescriptionActivity.setText(" ");
 } else {
  // text.append(tipikon);
  tvDescriptionActivity.setText(Html.fromHtml(text.toString()

  .replace("\r\n", "<br>")));
 }
 // tvDescriptionActivity.setText("data="+data+"\nmonth="+month+"\nyear="+year);
 }

 SelectBookmarksOnOff();

}



/*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }*/

public void SelectBookmarksOnOff(){
 if(arr_bookmark!=null){
 for(int i=0; i<arr_bookmark.size();i++){
  String [] text_line_bookmark=arr_bookmark.get(i).split("###");
     int id_prayers_group_bookmarks=Integer.parseInt(text_line_bookmark[0]);
     int id_prayers_bookmarks=Integer.parseInt(text_line_bookmark[1]);
     if(id_prayers_group_bookmarks==1  && id_prayers_bookmarks==prayers_id_2 && prayers_type.equals("pr")){
      flag_bookmarks_on=true;
      arr_bookmark_id=i;
      return;
     }
     if(id_prayers_group_bookmarks==2 && id_prayers_bookmarks==prayers_id_2 && prayers_type.equals("ak")){
      flag_bookmarks_on=true;
      arr_bookmark_id=i;
      return;
     }
 }

 }
}

public void BookmarksAdd(){
 String text="";
 int prayers_id2=1;
 int prayers_group=1;
 if (prayers_language.equals("ru")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_ru");
 if(arr_bookmark==null){
  arr_bookmark=new ArrayList<String>();
 }

 cursor = null;
 if(prayers_type.equals("pr")){
  sql = "select id2, name_prayers from prayers_ru_pr where _id=" + prayers_id;
  prayers_group=1;
 }else{
  sql = "select id2, name_prayers from prayers_ru_ak where _id=" + prayers_id;
  prayers_group=2;
 }
 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  do {
   try {
    text=cursor.getString(cursor.getColumnIndex("name_prayers"));
    prayers_id2=cursor.getInt(cursor.getColumnIndex("id2"));
   } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
   }
  } while (cursor.moveToNext());

  }
 }

 arr_bookmark.add(prayers_group+"###"+prayers_id2+"###"+text);
 arr_bookmark_id=arr_bookmark.size()-1;
 PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "new_prayers_bookmarks_ru", arr_bookmark);
 }
 if (prayers_language.equals("cs")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_cs");
 if(arr_bookmark==null){
  arr_bookmark=new ArrayList<String>();
 }
cursor.close();
 cursor = null;
 if(prayers_type.equals("pr")){
  sql = "select id2, name_prayers from prayers_cs_pr where _id=" + prayers_id;
  prayers_group=1;
 }else{
  sql = "select id2, name_prayers from prayers_cs_ak where _id=" + prayers_id;
  prayers_group=2;
 }
 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  do {
   try {
    text=cursor.getString(cursor.getColumnIndex("name_prayers"));
    prayers_id2=cursor.getInt(cursor.getColumnIndex("id2"));
   } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
   }
  } while (cursor.moveToNext());
  }
}
cursor.close();
cursor=null;
 arr_bookmark.add(prayers_group+"###"+prayers_id2+"###"+text);
 arr_bookmark_id=arr_bookmark.size()-1;
 PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "new_prayers_bookmarks_cs", arr_bookmark);
 }

}

/*public void BookmarksAdd(){
 String text="";
 if (prayers_language.equals("ru")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "prayers_bookmarks_ru");
 if(arr_bookmark==null){
  arr_bookmark=new ArrayList<String>();
 }

 cursor = null;
 sql = "SELECT name_ru FROM prayers_ru where _id="+prayers_id;
 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  do {
   try {
    text=cursor.getString(cursor.getColumnIndex("name_ru"));
   } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
   }
  } while (cursor.moveToNext());

  }
 }

 arr_bookmark.add("1###"+prayers_id+"###"+text);
 arr_bookmark_id=arr_bookmark.size()-1;
 PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "prayers_bookmarks_ru", arr_bookmark);
 }
 if (prayers_language.equals("cs")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "prayers_bookmarks_cs");
 if(arr_bookmark==null){
  arr_bookmark=new ArrayList<String>();
 }

 cursor = null;
 sql = "SELECT name_cs FROM prayers_cs where _id="+prayers_id;
 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  do {
   try {
    text=cursor.getString(cursor.getColumnIndex("name_cs"));
   } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
   }
  } while (cursor.moveToNext());

  }
 }

 arr_bookmark.add("1###"+prayers_id+"###"+text);
 arr_bookmark_id=arr_bookmark.size()-1;
 PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "prayers_bookmarks_cs", arr_bookmark);
 }


}*/

public void BookmarksDelete(){

 if (prayers_language.equals("ru")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_ru");
 arr_bookmark.remove(arr_bookmark_id);
 PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "new_prayers_bookmarks_ru", arr_bookmark);
 }
 if (prayers_language.equals("cs")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_cs");
 arr_bookmark.remove(arr_bookmark_id);
 PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "new_prayers_bookmarks_cs", arr_bookmark);
 }
}

@Override
protected void onDestroy() {
 // TODO Auto-generated method stub
 super.onDestroy();
 if (cursor != null) cursor.close();
 //if (db != null) db.closeConnecion();
}

@Override
public void onBackPressed() {
 // TODO Auto-generated method stub
 // cursor.close();
 super.onBackPressed();
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
 Log.d(TAG, "onOptionsItemSelected");
 switch (item.getItemId()) {
 case android.R.id.home:
 // NavUtils.navigateUpFromSameTask(this);
 onBackPressed();
 return true;
 case R.id.item1_prayers_menu:
 // увеличиваем размер шрифта

 if(id_description==5){
  if (prayers_language.equals("ru")) {
  size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_prayers_text_size_ru", 0);
  }
  if (prayers_language.equals("cs")) {
  size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_prayers_text_size_cs", 0);
  }
  size = tvDescriptionActivity.getTextSize();
  if(size<120){
  // Log.d(TAG, "size =" + size);
  size = size + 3;
  tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
  if (prayers_language.equals("ru")) {
   PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
    "pref_prayers_text_size_ru", size_default_MyView + 3);
  } else {
   PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
    "pref_prayers_text_size_cs", size_default_MyView + 3);
  }
  }else Toast.makeText(this, "–азмер шрифта максимальный!!!", Toast.LENGTH_SHORT)
  .show();
 }else{
  size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_description_text_size", 0);

  size = tvDescriptionActivity.getTextSize();
  if(size<120){
  // Log.d(TAG, "size =" + size);
  size = size + 3;
  tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
    "pref_description_text_size", size_default_MyView + 3);

  }else Toast.makeText(this, "–азмер шрифта максимальный!!!", Toast.LENGTH_SHORT)
  .show();
 }
 return true;
 case R.id.item2_prayers_menu:
 // уменьшаем размер шрифта
 if(id_description==5){
  if (prayers_language.equals("ru")) {
  size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_prayers_text_size_ru", 0);
  }
  if (prayers_language.equals("cs")) {
  size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_prayers_text_size_cs", 0);
  }
  size = tvDescriptionActivity.getTextSize();
  if(size>7){
  //Log.d(TAG, "size2 =" + size);
  size = size - 3;
  tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
  if (prayers_language.equals("ru")) {
   PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
    "pref_prayers_text_size_ru", size_default_MyView - 3);
  } else {
   PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
    "pref_prayers_text_size_cs", size_default_MyView - 3);
  }
  }else Toast.makeText(this, "–азмер шрифта минимальный!!!", Toast.LENGTH_SHORT)
  .show();
 }else{
  size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_description_text_size", 0);

  size = tvDescriptionActivity.getTextSize();
  if(size>7){
  //Log.d(TAG, "size2 =" + size);
  size = size - 3;
  tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
    "pref_description_text_size", size_default_MyView - 3);
  }else Toast.makeText(this, "–азмер шрифта минимальный!!!", Toast.LENGTH_SHORT)
  .show();
 }
 return true;
 case R.id.item3_prayers_menu:
 if(id_description==5)
  description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style1", false);
 else
  description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style2", false);

 if(!description_style){
  if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
  if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
  if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
  if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
  tvDescriptionActivity.setTextColor(getResources().getColor(R.color.WHITE2));
  if(id_description==5)
  PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style1", true);
  else
  PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style2", true);
 }else{
  llDescriptionActivity.setBackgroundResource(R.drawable.rx1);
  tvDescriptionActivity.setTextColor(getResources().getColor(R.color.BLACK));
  if(id_description==5)
  PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style1", false);
  else
  PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style2", false);
 }
 return true;
 case R.id.item4_prayers_menu:
 if(flag_bookmarks_on){
  flag_bookmarks_on=false;
  BookmarksDelete();
 }
 else {
  flag_bookmarks_on=true;
  BookmarksAdd();
}
 invalidateOptionsMenu();
 return true;
 default:
 return super.onOptionsItemSelected(item);
 }
}
@Override
  protected void onStart() {
    super.onStart();

    Log.d(TAG, "DescriptionActivity onStart");

    if(id_description==5){
 if(!PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_rotate_screen_setting", true)){
  //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

  if (Build.VERSION.SDK_INT >= 18)
  {
  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
  }else{

  int orientation = getResources().getConfiguration().orientation;
      int rotation = getWindowManager().getDefaultDisplay().getOrientation();

  int SCREEN_ORIENTATION_REVERSE_LANDSCAPE = 8;
      int SCREEN_ORIENTATION_REVERSE_PORTRAIT = 9;

      if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90) {
          if (orientation == Configuration.ORIENTATION_PORTRAIT) {
              setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
          } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
              setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
          }
      } else if (rotation == Surface.ROTATION_180
              || rotation == Surface.ROTATION_270) {
          if (orientation == Configuration.ORIENTATION_PORTRAIT) {
              setRequestedOrientation(SCREEN_ORIENTATION_REVERSE_PORTRAIT);
          } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
              setRequestedOrientation(SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
          }
      }
  /*int orientation = getResources().getConfiguration().orientation;
  int rotation = getWindowManager().getDefaultDisplay().getRotation();

  if (orientation == Configuration.ORIENTATION_PORTRAIT)
  {
   if (rotation == Surface.ROTATION_270)
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
   else
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }
  else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
  {
   if (rotation == Surface.ROTATION_180)
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
   else
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
  }*/
  }
 }

 }
  }

@Override
protected void onResume() {
 super.onResume();


}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
 // Inflate the menu; this adds items to the action bar if it is present.
 Log.d(TAG, "onCreateOptionsMenu");
 if(id_description==5){
 getMenuInflater().inflate(R.menu.main_prayers_bookmarks, menu);
 menu_item1 = (MenuItem) menu.findItem(R.id.item1_prayers_menu);
 menu_item2 = (MenuItem) menu.findItem(R.id.item2_prayers_menu);
 menu_item3 = (MenuItem) menu.findItem(R.id.item3_prayers_menu);
 menu_item4 = (MenuItem) menu.findItem(R.id.item4_prayers_menu);
 }else{
 getMenuInflater().inflate(R.menu.main_prayers, menu);
 menu_item1 = (MenuItem) menu.findItem(R.id.item1_prayers_menu);
 menu_item2 = (MenuItem) menu.findItem(R.id.item2_prayers_menu);
 menu_item3 = (MenuItem) menu.findItem(R.id.item3_prayers_menu);
 }

 return super.onCreateOptionsMenu(menu);
}

// вызываетс¤ каждый раз перед отображением меню
@Override
public boolean onPrepareOptionsMenu(Menu menu) {
 // TODO Auto-generated method stub
 Log.d(TAG, "onPrepareOptionsMenu");
if(id_description==5) {
workWithFavouriteItem(menu);
 }
 /*if (id_description == 5) {
 menu_item1.setVisible(true);
 menu_item2.setVisible(true);
 } else {
 menu_item1.setVisible(false);
 menu_item2.setVisible(false);
 }*/
 return super.onPrepareOptionsMenu(menu);
}

// позвол¤ет востановить данные
/*protected void onRestoreInstanceState(Bundle savedInstanceState) {
 //Log.d(LOG_TAG, "onRestoreInstanceState");
 super.onRestoreInstanceState(savedInstanceState);
 //Log.d(LOG_TAG, "onRestoreInstanceState");
}

// позвол¤ет сохранить данные
protected void onSaveInstanceState(Bundle outState) {
 //Log.d(LOG_TAG, "onSaveInstanceState");
 super.onSaveInstanceState(outState);
 //Log.d(LOG_TAG, "onSaveInstanceState");
}*/

// позвол¤ет востановить данные
protected void onRestoreInstanceState(Bundle savedInstanceState) {

 super.onRestoreInstanceState(savedInstanceState);
 Log.d(TAG, "DescriptionActivity onRestoreInstanceState");
 ////////////////////////////////////////////////////////////////
 final int firstVisableCharacterOffset = savedInstanceState.getInt("scrollY");

    final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollViewDescription1);
    scrollView.post(new Runnable() {
        public void run() {
            final MyView MyView = (MyView) scrollView.getChildAt(0);
            final int firstVisableLineOffset = MyView.getLayout().getLineForOffset(firstVisableCharacterOffset);
            final int pixelOffset = MyView.getLayout().getLineTop(firstVisableLineOffset);
            scrollView.scrollTo(0, pixelOffset);
        }
    });
 //////////////////////////////////////////////////////////////////////////////

 /*if (savedInstanceState != null) {
 final ScrollView sv = (ScrollView) findViewById(R.id.scrollViewDescription1);
 final int posY = savedInstanceState.getInt("scrollY");
 final int posX = savedInstanceState.getInt("scrollX");
 Log.d(TAG, "onRestoreInstanceState y=" + posY);
 // sv.scrollTo(posX, posY);

 sv.post(new Runnable() {
  @Override
  public void run() {
  sv.scrollTo(posX, posY);
  }
 });
 }*/
}

// позвол¤ет сохранить данные
protected void onSaveInstanceState(Bundle outState) {

 super.onSaveInstanceState(outState);

 Log.d(TAG, "DescriptionActivity onSaveInstanceState");
 //////////////////////////////////////////////////////////////////

 final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollViewDescription1);
    final MyView MyView = (MyView) scrollView.getChildAt(0);
    final int firstVisableLineOffset = MyView.getLayout().getLineForVertical(scrollView.getScrollY());
    final int firstVisableCharacterOffset = MyView.getLayout().getLineStart(firstVisableLineOffset);
    outState.putInt("scrollY", firstVisableCharacterOffset);

 /////////////////////////////////////////////////////////////////////

 /*ScrollView sv = (ScrollView) findViewById(R.id.scrollViewDescription1);
 int posY = sv.getScrollY();
 int posX = sv.getScrollX();
 Log.d(TAG, "onSaveInstanceState y=" + posY);
 outState.putInt("scrollY", posY);
 outState.putInt("scrollX", posX);*/

}

@Override
public boolean dispatchKeyEvent(KeyEvent event) {
 // TODO Auto-generated method stub
 DisplayMetrics dm = new DisplayMetrics();
 getWindowManager().getDefaultDisplay().getMetrics(dm);
 final int height=dm.heightPixels;

 final ScrollView scrollViewKey = (ScrollView) findViewById(R.id.scrollViewDescription1);
 if (event.getAction() == KeyEvent.ACTION_DOWN) {
        switch (event.getKeyCode()) {
        case KeyEvent.KEYCODE_VOLUME_UP:
        scrollViewKey.post(new Runnable() {
            public void run() {
            //scrollViewKey.scrollBy(0, -(height/8));
            scrollViewKey.pageScroll(View.FOCUS_UP);

            scrollViewKey.computeScroll();
            }
        });
        //scrollViewKey.pageScroll(View.FOCUS_UP);
        //scrollToPrevious();
            return true;
        case KeyEvent.KEYCODE_VOLUME_DOWN:
        scrollViewKey.post(new Runnable() {
            public void run() {
            //scrollViewKey.scrollBy(0, +(height/8));
            scrollViewKey.pageScroll(View.FOCUS_DOWN);

            scrollViewKey.computeScroll();
            }
        });
        //scrollViewKey.pageScroll(View.FOCUS_DOWN);
           //scrollToNext();
            return true;
        }
    }
    if (event.getAction() == KeyEvent.ACTION_UP
        && (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
            || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)) {
        return true;
    }
 return super.dispatchKeyEvent(event);
}



// перерисовка меню/ActionBar
/*
 * @Override public void invalidateOptionsMenu() { // TODO Auto-generated
 * method stub super.invalidateOptionsMenu(); }
 */
}
