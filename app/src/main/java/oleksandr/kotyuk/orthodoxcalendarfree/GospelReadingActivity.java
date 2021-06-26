package oleksandr.kotyuk.orthodoxcalendarfree;

import java.util.LinkedHashMap;
import java.util.Map;

import oleksandr.kotyuk.orthodoxcalendarfree.db.DatabaseHelper;
import androidx.appcompat.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import oleksandr.kotyuk.orthodoxcalendarfree.accessibility.MyView;
import android.widget.Toast;

public class GospelReadingActivity extends AppCompatActivity {

	private DatabaseHelper db;
	Cursor cursor;
	String sql = "";

	LinearLayout llDescriptionActivity;
	MyView tvDescriptionActivity;
	float size_default_MyView = 0;
	float size = 0;
	
	private final String FONT_PATH_RU1 = "fonts/Arial.ttf";
	private final String FONT_PATH_RU2 = "fonts/Calibri.ttf";
	private final String FONT_PATH_RU3 = "fonts/Cambria.ttf";
	private final String FONT_PATH_RU4 = "fonts/DroidSans.ttf";
	private final String FONT_PATH_RU5 = "fonts/DroidSerif.ttf";
	private final String FONT_PATH_RU6 = "fonts/Times.ttf";
	private final String FONT_PATH_RU7 = "fonts/Verdana.ttf";
	String prayers_fonts_ru;
	
	MenuItem menu_item1;
	MenuItem menu_item2;
	
	boolean description_style=false;
	
	String black_fon_color;
	
	String text_name_gr = "";
	String tmp = "";
	String[] mas_bible_book = { "", "Быт", "Исх", "Лев", "Чис", "Втор", "Нав",
			"Суд", "Руфь", "1 Цар", "2 Цар", "3 Цар", "4 Цар", "1 Пар",
			"2 Пар", "1 Езд", "Неем", "Есф", "Иов", "Пс", "Притч", "Еккл",
			"Песн", "Ис", "Иер", "Плач", "Иез", "Дан", "Ос", "Иоил", "Амос",
			"Авд", "Иона", "Мих", "Наум", "Авв", "Соф", "Агг", "Зах", "Мал",
			"2 Езд", "Тов", "Иудифь", "Прем. Солом", "Сир", "Посл Иер", "Вар",
			"1 Мак", "2 Мак", "3 Мак", "3 Езд", "Мф", "Мк", "Лк", "Ин", "Деян",
			"Иак", "1 Пет", "2 Пет", "1 Ин", "2 Ин", "3 Ин", "Иуд", "Рим",
			"1 Кор", "2 Кор", "Гал", "Еф", "Флп", "Кол", "1 Сол", "2 Сол",
			"1 Тим", "2 Тим", "Тит", "Флм", "Евр" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gospel_reading);

		ActionBar actionBar = getSupportActionBar();
		// Enabling Up / Back navigation
		actionBar.setDisplayHomeAsUpEnabled(true);

		Uri uri = getIntent().getData();
		try {
			text_name_gr = uri.getQueryParameter("name");
		} catch (Exception e) {
			// TODO: handle exception
		}

		////////////////////////////////
		//text_name_gr="Евр.,X:1-36;XI:1-XI:10";
		////////////////////////////////////
		prayers_fonts_ru = PreferencesActivity.MyPreferenceFragment.ReadString(this, "pref_prayers_fonts_ru", "1");
		
		description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style3", false);
		llDescriptionActivity=(LinearLayout)this.findViewById(R.id.llViewGospelReading);
		tvDescriptionActivity = (MyView) this
				.findViewById(R.id.MyViewGospelReading1);
		
		black_fon_color = PreferencesActivity.MyPreferenceFragment.ReadString(
				this, "pref_black_fon_color", "black");
		
		if(description_style){
			if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
			if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
			if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
			if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
			tvDescriptionActivity.setTextColor(getResources().getColor(R.color.WHITE2));
		}
		
		if(prayers_fonts_ru.equals("1")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
				FONT_PATH_RU1));
		if(prayers_fonts_ru.equals("2")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
				FONT_PATH_RU2));
		if(prayers_fonts_ru.equals("3")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
				FONT_PATH_RU3));
		if(prayers_fonts_ru.equals("4")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
				FONT_PATH_RU4));
		if(prayers_fonts_ru.equals("5")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
				FONT_PATH_RU5));
		if(prayers_fonts_ru.equals("6")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
				FONT_PATH_RU6));
		if(prayers_fonts_ru.equals("7")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
				FONT_PATH_RU7));
		
		size_default_MyView = PreferencesActivity.MyPreferenceFragment
				.ReadFloat(this, "pref_prayers_text_gr_size", 0);
		
		size = tvDescriptionActivity.getTextSize();
		// Log.d(TAG, "size =" + size);
		size = size + size_default_MyView;
		tvDescriptionActivity.setTextSize(
				TypedValue.COMPLEX_UNIT_PX, size);

		/*
		 * for(int i=0;mas_bible_book.length>i;i++){
		 * tmp=tmp+"№"+i+"  "+mas_bible_book[i]+"<br>"; }
		 */
		db = DatabaseHelper.getInstance(this);
		try{
		tvDescriptionActivity.setText(Html.fromHtml(ParseShortNameGR(text_name_gr)));
		}catch(Exception e){
			tvDescriptionActivity.setText("Произошла ошибка!!!");
		}
	}

	public String ParseShortNameGR(String name) {
		String text_gr = "";
		String tmp;
		String tmp2;
		String bible_name="";
		int id_bible = 0;
		// int start=0;
		int end = 0;
		String str = name;
		if (str.equals("")) {
			// str="Произошла ошибка!!!";
			return "Произошла ошибка!!!";
		}

		end = str.indexOf(".,");
		tmp = str.substring(0, end);
		for (int i = 0; i < mas_bible_book.length; i++) {
			// вычисляем индекс книги
			if (mas_bible_book[i].equals(tmp)) {
				id_bible = i;
				break;
			}
		}
		
		sql = "select name_book from bible where _id="+ id_bible;
		db = DatabaseHelper.getInstance(this);
		cursor = db.executeQuery(sql);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				try {
					bible_name = cursor.getString(cursor
							.getColumnIndex("name_book"));
					//bible_name=bible_name.toUpperCase();
				} catch (NumberFormatException e) {
					// Log.d(TAG, "ERROR=" + e.toString());
				}
			}
		}
		
		tmp2 = str.replace(tmp + ".,", "");
		if (tmp2.contains(";")) {
			String[] mas = tmp2.split(";");
			for (int i = 0; i < mas.length; i++) {
				text_gr += ParseTextGR(id_bible, mas[i]) + "<br>";
			}

		} else {
			text_gr = ParseTextGR(id_bible, tmp2);
		}

		return "<b> "+bible_name+" </b> <br>"+"( "+name+" )<br><br>" + text_gr;
	}

	public String ParseTextGR(int id_bible, String name) {
		String tmp = "";
		String tmp2 = "";
		String tmp3="";
		String text="";
		String chapter_name_start="";
		String chapter_name_end="";
		
		int start=0;
		int end = 0;
		
		int count = 0;
		
		int chapter_id_start = 0;
		int chapter_id_finish = 0;
		int chapter_arabic = 0;
		
		int p_id_start=0;
		int p_id_end=0;
		
		int start_defis=0;

		end = name.indexOf(":");
		if (end != -1)
			count = 1;
		end = name.indexOf(":", end + 1);
		if (end != -1)
			count = 2;
		
		
		switch (count) {
		case 1:
			// X:10-25 або X:10,15-25...
			end = name.indexOf(":");
			tmp = name.substring(0, end);
			chapter_name_start=tmp;
			chapter_id_start = RomanToArabic(chapter_name_start);
			chapter_arabic=chapter_id_start;

			sql = "select chapter_text from bible_book where id_bible="
					+ id_bible + " and chapter_id=" + chapter_id_start;
			db = DatabaseHelper.getInstance(this);
			cursor = db.executeQuery(sql);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					try {
						tmp2 = cursor.getString(cursor
								.getColumnIndex("chapter_text"));
					} catch (NumberFormatException e) {
						// Log.d(TAG, "ERROR=" + e.toString());
					}
				}
			}
			tmp2=tmp2.replace("\r\n", "<br>");
			
			tmp = name.replace(tmp + ":", "");
			if (tmp.contains(",")) {
				// 9-12,16,20-30...
				String[] mas = tmp.split(",");
				for (int i = 0; i < mas.length; i++) {
					//15 или 9-15...
					if(mas[i].contains("-")){
						//9-15
						String[] mas2 = mas[i].split("-");
						p_id_start=Integer.parseInt(mas2[0]);
						p_id_end=Integer.parseInt(mas2[1]);
						start = tmp2.indexOf("<p>"+(p_id_start));
						end = tmp2.indexOf("<p>"+(p_id_end+1));
						if(end!=-1){
							text+=tmp2.substring(start, end)+"<br>";
						}else{
							text+=tmp2.substring(start, tmp2.length())+"<br>";
						}
						
					}else{
						//15
						p_id_start=Integer.parseInt(mas[i]);
						start = tmp2.indexOf("<p>"+(p_id_start));
						end = tmp2.indexOf("<p>"+(p_id_start+1));
						if(end!=-1){
							text+=tmp2.substring(start, end)+"<br>";
						}else{
							text+=tmp2.substring(start, tmp2.length())+"<br>";
						}
					}
				}
			} else {
				//15 или 9-15...
				if(tmp.contains("-")){
					//9-15
					String[] mas2 = tmp.split("-");
					p_id_start=Integer.parseInt(mas2[0]);
					p_id_end=Integer.parseInt(mas2[1]);
					start = tmp2.indexOf("<p>"+(p_id_start));
					end = tmp2.indexOf("<p>"+(p_id_end+1));
					if(end!=-1){
						text+=tmp2.substring(start, end)+"<br>";
					}else{
						text+=tmp2.substring(start, tmp2.length())+"<br>";
					}
					
				}else{
					//15
					p_id_start=Integer.parseInt(tmp);
					start = tmp2.indexOf("<p>"+(p_id_start));
					end = tmp2.indexOf("<p>"+(p_id_start+1));
					if(end!=-1){
						text+=tmp2.substring(start, end)+"<br>";
					}else{
						text+=tmp2.substring(start, tmp2.length())+"<br>";
					}
				}
			}
			//////text=text.replace("<p>", chapter_name_start+".");
			text=text.replace("<p>", chapter_arabic+".");
			break;
		case 2:
			// X:23-XI:20...
			if (!name.contains(",")) {
				//X:23-XI:20 или X:23-XI:20-25 или X:23-XII:20
				start = name.indexOf(":");
				chapter_name_start = name.substring(0, start);
				chapter_id_start = RomanToArabic(chapter_name_start);
				chapter_arabic=chapter_id_start;

				sql = "select chapter_text from bible_book where id_bible="
						+ id_bible + " and chapter_id=" + chapter_id_start;
				db = DatabaseHelper.getInstance(this);
				cursor = db.executeQuery(sql);

				if (cursor != null) {
					if (cursor.moveToFirst()) {
						try {
							tmp = cursor.getString(cursor
									.getColumnIndex("chapter_text"));
						} catch (NumberFormatException e) {
							// Log.d(TAG, "ERROR=" + e.toString());
						}
					}
				}
				tmp=tmp.replace("\r\n", "<br>");
				
				end=name.indexOf(":", start+1);
				start_defis=FindDefis(end, name);
				
				chapter_name_end = name.substring(start_defis+1, end);
				chapter_id_finish = RomanToArabic(chapter_name_end);
				
				//****************************************
				String midle_tmp="";
				if(chapter_id_start+1<chapter_id_finish){
					sql = "select chapter_text from bible_book where id_bible="
							+ id_bible + " and chapter_id=" + (chapter_id_start+1);
					db = DatabaseHelper.getInstance(this);
					cursor = db.executeQuery(sql);

					if (cursor != null) {
						if (cursor.moveToFirst()) {
							try {
								midle_tmp = cursor.getString(cursor
										.getColumnIndex("chapter_text"));
							} catch (NumberFormatException e) {
								// Log.d(TAG, "ERROR=" + e.toString());
							}
						}
					}
					midle_tmp=midle_tmp.replace("\r\n", "<br>");
				}
				
				
				String midle_tmp2="";
				if(chapter_id_start+2<chapter_id_finish){
					sql = "select chapter_text from bible_book where id_bible="
							+ id_bible + " and chapter_id=" + (chapter_id_start+2);
					db = DatabaseHelper.getInstance(this);
					cursor = db.executeQuery(sql);

					if (cursor != null) {
						if (cursor.moveToFirst()) {
							try {
								midle_tmp2 = cursor.getString(cursor
										.getColumnIndex("chapter_text"));
							} catch (NumberFormatException e) {
								// Log.d(TAG, "ERROR=" + e.toString());
							}
						}
					}
					midle_tmp2=midle_tmp2.replace("\r\n", "<br>");
				}
				
				String midle_tmp3="";
				if(chapter_id_start+3<chapter_id_finish){
					sql = "select chapter_text from bible_book where id_bible="
							+ id_bible + " and chapter_id=" + (chapter_id_start+3);
					db = DatabaseHelper.getInstance(this);
					cursor = db.executeQuery(sql);

					if (cursor != null) {
						if (cursor.moveToFirst()) {
							try {
								midle_tmp3 = cursor.getString(cursor
										.getColumnIndex("chapter_text"));
							} catch (NumberFormatException e) {
								// Log.d(TAG, "ERROR=" + e.toString());
							}
						}
					}
					midle_tmp3=midle_tmp3.replace("\r\n", "<br>");
				}
				
				String midle_tmp4="";
				if(chapter_id_start+4<chapter_id_finish){
					sql = "select chapter_text from bible_book where id_bible="
							+ id_bible + " and chapter_id=" + (chapter_id_start+4);
					db = DatabaseHelper.getInstance(this);
					cursor = db.executeQuery(sql);

					if (cursor != null) {
						if (cursor.moveToFirst()) {
							try {
								midle_tmp4 = cursor.getString(cursor
										.getColumnIndex("chapter_text"));
							} catch (NumberFormatException e) {
								// Log.d(TAG, "ERROR=" + e.toString());
							}
						}
					}
					midle_tmp4=midle_tmp4.replace("\r\n", "<br>");
				}
				//*****************************************

				sql = "select chapter_text from bible_book where id_bible="
						+ id_bible + " and chapter_id=" + chapter_id_finish;
				db = DatabaseHelper.getInstance(this);
				cursor = db.executeQuery(sql);

				if (cursor != null) {
					if (cursor.moveToFirst()) {
						try {
							tmp2 = cursor.getString(cursor
									.getColumnIndex("chapter_text"));
						} catch (NumberFormatException e) {
							// Log.d(TAG, "ERROR=" + e.toString());
						}
					}
				}
				tmp2=tmp2.replace("\r\n", "<br>");
				
				tmp3=name.substring(start+1, start_defis);
				p_id_start=Integer.parseInt(tmp3);
				
				start = tmp.indexOf("<p>"+(p_id_start));
				text+=tmp.substring(start, tmp.length())+"<br>";
				//text=text.replace("<p>", chapter_name_start+".");
				text=text.replace("<p>", chapter_arabic+".");
				
				//***********************************
				if(chapter_id_start+1<chapter_id_finish){
					text+=midle_tmp+"<br>";
					//text=text.replace("<p>", RomanNumerals(chapter_id_start+1)+".");
					text=text.replace("<p>", (chapter_arabic+1)+".");
				}
				
				if(chapter_id_start+2<chapter_id_finish){
					text+=midle_tmp2+"<br>";
					//text=text.replace("<p>", RomanNumerals(chapter_id_start+2)+".");
					text=text.replace("<p>", (chapter_arabic+2)+".");
				}
				
				if(chapter_id_start+3<chapter_id_finish){
					text+=midle_tmp3+"<br>";
					//text=text.replace("<p>", RomanNumerals(chapter_id_start+3)+".");
					text=text.replace("<p>", (chapter_arabic+3)+".");
				}
				
				if(chapter_id_start+4<chapter_id_finish){
					text+=midle_tmp4+"<br>";
					//text=text.replace("<p>", RomanNumerals(chapter_id_start+4)+".");
					text=text.replace("<p>", (chapter_arabic+4)+".");
				}
				//************************************
				
				tmp3=name.substring(end+1, name.length());
				p_id_end=Integer.parseInt(tmp3);
				
				end = tmp2.indexOf("<p>"+(p_id_end+1));
				if(end!=-1){
					text+=tmp2.substring(0, end)+"<br>";
				}else{
					text+=tmp2.substring(0, tmp2.length())+"<br>";
				}
				//text=text.replace("<p>", chapter_name_end+".");
				text=text.replace("<p>", (chapter_arabic+1)+".");

			}else{
				//X:23-XI:20,25-30 или X:23-30,35-XI:20
				
			}
			break;
		default:

			break;
		}
		cursor.close();
		return text;
	}

	//поиск "-" перед X:10
	public int FindDefis(int number, String text){
		int start_defis=0;
		int tmp=0;
		for(int i=0;i<4;i++){
			tmp=text.indexOf("-", start_defis+1);
			if(tmp==-1) return start_defis;
			else{
				if(tmp<number) start_defis=tmp;
				else return start_defis;
			}
		}
		return 0;
	}
	
	// конвертация римского числа в арабское
	public int RomanToArabic(String rome) {

		String[] Rome = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X",
				"IX", "V", "IV", "I" };
		int[] Arab = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

		StringBuffer romeNumber = new StringBuffer(rome);
		int arabNumber = 0;
		int i = 0;
		// Проверяем переданную строку на наличие символов
		if (romeNumber.length() > 0) {
			while (true) {
				do {

					if (Rome[i].length() <= romeNumber.length()) {
						// Выделяем из строки подстроку и сравниваем со
						// значением из массива Arab
						if (Rome[i].equals(romeNumber.substring(0,
								Rome[i].length()))) {
							// После чего прибавляем число соответствующее
							// индексу элемента римской цифры из массива Arab
							arabNumber += Arab[i];
							// и удаляем из строки римскую цифру
							romeNumber.delete(0, Rome[i].length());
							if (romeNumber.length() == 0)
								return arabNumber;
						} else
							break;
					} else
						break;
				} while (true && romeNumber.length() != 0);
				i++;
			}
		}
		return 0;

	}
	
	// конвертация арабского числа в римское
	public String  RomanNumerals ( int  Int )  { 
	    LinkedHashMap < String ,  Integer > roman_numerals =  new  LinkedHashMap < String ,  Integer >(); 
	    roman_numerals . put ( "M" ,  1000 ); 
	    roman_numerals . put ( "CM" ,  900 ); 
	    roman_numerals . put ( "D" ,  500 ); 
	    roman_numerals . put ( "CD" ,  400 ); 
	    roman_numerals . put ( "C" ,  100 ); 
	    roman_numerals . put ( "XC" ,  90 ); 
	    roman_numerals . put ( "L" ,  50 ); 
	    roman_numerals . put ( "XL" ,  40 ); 
	    roman_numerals . put ( "X" ,  10 ); 
	    roman_numerals . put ( "IX" ,  9 ); 
	    roman_numerals . put ( "V" ,  5 ); 
	    roman_numerals . put ( "IV" ,  4 ); 
	    roman_numerals . put ( "I" ,  1 ); 
	    String res =  "" ; 
	    for ( Map . Entry < String ,  Integer > entry : roman_numerals . entrySet ()){ 
	      int matches =  Int / entry . getValue (); 
	      res += repeat ( entry . getKey (), matches ); 
	      Int  =  Int  % entry . getValue (); 
	    } 
	    return res ; 
	  } 
	  public  static  String repeat ( String s ,  int n )  { 
	    if ( s ==  null )  { 
	        return  null ; 
	    } 
	    final  StringBuilder sb =  new  StringBuilder (); 
	    for ( int i =  0 ; i < n ; i ++)  { 
	        sb . append ( s ); 
	    } 
	    return sb . toString (); 
	  }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//Log.d(TAG, "onCreateOptionsMenu");
		getMenuInflater().inflate(R.menu.main_prayers, menu);
		menu_item1 = (MenuItem) menu.findItem(R.id.item1_prayers_menu);
		menu_item2 = (MenuItem) menu.findItem(R.id.item2_prayers_menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	// позволяет востановить данные
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		//Log.d(LOG_TAG, "onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
		//Log.d(LOG_TAG, "onRestoreInstanceState");
	}

	// позволяет сохранить данные
	protected void onSaveInstanceState(Bundle outState) {
		//Log.d(LOG_TAG, "onSaveInstanceState");
		super.onSaveInstanceState(outState);
		//Log.d(LOG_TAG, "onSaveInstanceState");
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		if(!PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_rotate_screen_setting", true)){
			//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);	
			
			if (Build.VERSION.SDK_INT >= 18)
			{
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
			}
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
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// NavUtils.navigateUpFromSameTask(this);
			onBackPressed();
			return true;
		case R.id.item1_prayers_menu:
			// увеличиваем размер шрифта

			size_default_MyView = PreferencesActivity.MyPreferenceFragment
						.ReadFloat(this, "pref_prayers_text_gr_size", 0);

			size = tvDescriptionActivity.getTextSize();
			if(size<120){
				// Log.d(TAG, "size =" + size);
				size = size + 3;
				tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
				
				PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
							"pref_prayers_text_gr_size", size_default_MyView + 3);
				
			}else Toast.makeText(this, "Размер шрифта максимальный!!!", Toast.LENGTH_SHORT)
			.show();
			return true;
		case R.id.item2_prayers_menu:
			// уменьшаем размер шрифта
			
			size_default_MyView = PreferencesActivity.MyPreferenceFragment
						.ReadFloat(this, "pref_prayers_text_gr_size", 0);
			size = tvDescriptionActivity.getTextSize();
			if(size>7){
				//Log.d(TAG, "size2 =" + size);
				size = size - 3;
				tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
				
				PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
							"pref_prayers_text_gr_size", size_default_MyView - 3);
					
			}else Toast.makeText(this, "Размер шрифта минимальный!!!", Toast.LENGTH_SHORT)
			.show();
			return true;
		case R.id.item3_prayers_menu:
			description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style3", false);
			if(!description_style){
				if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
				if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
				if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
				if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
				tvDescriptionActivity.setTextColor(getResources().getColor(R.color.WHITE2));
				PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style3", true);
			}else{
				llDescriptionActivity.setBackgroundResource(R.drawable.rx1);
				tvDescriptionActivity.setTextColor(getResources().getColor(R.color.BLACK));
				PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style3", false);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		//final int height=dm.heightPixels;
		
		final ScrollView scrollViewKey = (ScrollView) findViewById(R.id.scrollViewGospelReading1);
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

}
