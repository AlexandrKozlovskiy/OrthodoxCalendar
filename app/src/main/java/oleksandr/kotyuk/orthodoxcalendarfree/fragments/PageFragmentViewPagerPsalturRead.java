package oleksandr.kotyuk.orthodoxcalendarfree.fragments;


import oleksandr.kotyuk.orthodoxcalendarfree.FontsHelper;
import oleksandr.kotyuk.orthodoxcalendarfree.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.R;
import oleksandr.kotyuk.orthodoxcalendarfree.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendarfree.GlobalData;
import android.database.Cursor;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import oleksandr.kotyuk.orthodoxcalendarfree.accessibility.MyView;

import oleksandr.kotyuk.orthodoxcalendarfree.accessibility.MyView;

public class PageFragmentViewPagerPsalturRead extends Fragment {

	static final String ARGUMENT_PAGE_NUMBER = "arg_page_number_psaltur_read";
	public int pageNumber;

	private DatabaseHelper db;
	Cursor cursor;
	String sql = "";
	
	float size_default_MyView = 0;
	float size = 0;
	
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

	static final String TAG = "my_log";
	
	LinearLayout llDescriptionActivity;
	boolean description_style=false;
	
	String text_br="<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>";
	
	public static PageFragmentViewPagerPsalturRead newInstance(int page) {
		PageFragmentViewPagerPsalturRead pageFragment = new PageFragmentViewPagerPsalturRead();
	    Bundle arguments = new Bundle();
	    arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
	    pageFragment.setArguments(arguments);
	    return pageFragment;
	  }
	  
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
	    
	  }
	  
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.activity_description, null);
	    
	    description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(getActivity(), "pref_description_style1", false);
		llDescriptionActivity=(LinearLayout)view.findViewById(R.id.llViewDescription);
		
	    MyView tvPage = (MyView) view.findViewById(R.id.MyViewDescription1);
	    black_fon_color = PreferencesActivity.MyPreferenceFragment.ReadString(
	    		getActivity(), "pref_black_fon_color", "black");
	    
	    if(description_style){
	    	if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
			if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
			if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
			if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
			tvPage.setTextColor(getResources().getColor(R.color.WHITE2));
		}else{
			llDescriptionActivity.setBackgroundResource(R.drawable.rx1);
			tvPage.setTextColor(getResources().getColor(R.color.BLACK));
		}
	    
	    prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
				getActivity(), "pref_prayers_language", "ru");
	    if (prayers_language.equals("ru")) {
			db = DatabaseHelper.getInstance(getActivity()
					.getApplicationContext());
			//SELECT p.psalom_text_ru, (select kafisma_ru from psaltur_group where _id = p.id_kafist) as kaf_name FROM psaltur p where p._id=150
			cursor = db.executeQuery("SELECT p.psalom_text_ru, (select kafisma_ru from psaltur_group where _id = p.id_kafist) as kaf_name FROM psaltur p where p._id="+(pageNumber+1)+";");
			//cursor = db.executeQuery("SELECT psalom_text_ru FROM psaltur where _id="+(pageNumber+1)+";");
		} else {
			db = DatabaseHelper.getInstance(getActivity()
					.getApplicationContext());
			//SELECT p.psalom_text_sc, (select kafisma_sc from psaltur_group where _id = p.id_kafist) as kaf_name FROM psaltur p where p._id=150
			cursor = db.executeQuery("SELECT p.psalom_text_sc, (select kafisma_sc from psaltur_group where _id = p.id_kafist) as kaf_name FROM psaltur p where p._id="+(pageNumber+1)+";");
			//cursor = db.executeQuery("SELECT psalom_text_sc FROM psaltur where _id="+(pageNumber+1)+";");
		}
	    String text="";
	    String text_title="";
	    int p=(pageNumber+1);
	    if (cursor != null) {
			if (cursor.moveToFirst()) {
				try {
					if(prayers_language.equals("ru")){
						text_title="<FONT COLOR=#aa2c2c><b>"+cursor.getString(cursor.getColumnIndex("kaf_name"))+"</b></FONT><br>";
						if(p==1 || p==10 || p==19 || p==27 || p==36 || p==42 || p==52 || p==62 || p==72 || p==79 || p==87 || p==96 || p==103 || p==114 || p==119 || p==124 || p==134 || p==136 || p==152 || p==162 || p>171)
							text_title="";
						text = text_title+cursor.getString(cursor.getColumnIndex("psalom_text_ru"));
					}else{
						text_title="<FONT COLOR=#aa2c2c><b>"+cursor.getString(cursor.getColumnIndex("kaf_name"))+"</b></FONT><br>";
						if(p==1 || p==10 || p==19 || p==27 || p==36 || p==42 || p==52 || p==62 || p==72 || p==79 || p==87 || p==96 || p==103 || p==114 || p==119 || p==124 || p==134 || p==136 || p==152 || p==162 || p>171)
							text_title="";
						text = text_title+cursor.getString(cursor.getColumnIndex("psalom_text_sc"));
					}
				} catch (NumberFormatException e) {
					text="";
				}
			}
		}
	    if (prayers_language.equals("ru")){
	    	String prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity().getApplicationContext(), "pref_prayers_fonts_ru", "1");
			if(prayers_fonts_ru.equals("1")){
				tvPage.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH_RU1));
				tvPage.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
			}
			if(prayers_fonts_ru.equals("2")){
				tvPage.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH_RU2));
				tvPage.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
			}
			if(prayers_fonts_ru.equals("3")){
				tvPage.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH_RU3));
				tvPage.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
			}
			if(prayers_fonts_ru.equals("4")){
				tvPage.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH_RU4));
				tvPage.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
			}
			if(prayers_fonts_ru.equals("5")){
				tvPage.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH_RU5));
				tvPage.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
			}
			if(prayers_fonts_ru.equals("6")){
				tvPage.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH_RU6));
				tvPage.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
			}
			if(prayers_fonts_ru.equals("7")){
				tvPage.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH_RU7));
				tvPage.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
			}
	    	
	    	//tvPage.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH1));
	    }
		else{
			String prayers_fonts_cs=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity().getApplicationContext(), "pref_prayers_fonts_cs", "1");
			if(prayers_fonts_cs.equals("1")){
				tvPage.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH_CS1));
				tvPage.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -5.0f,  getResources().getDisplayMetrics()), 1.0f);
			}
			if(prayers_fonts_cs.equals("2")){
				tvPage.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH_CS2));
				tvPage.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -8.0f,  getResources().getDisplayMetrics()), 1.0f);
			}
			if(prayers_fonts_cs.equals("3")){
				tvPage.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH_CS3));
				tvPage.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -10.0f,  getResources().getDisplayMetrics()), 1.0f);
			}
			
			//tvPage.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH2));
		}
	    
	    if (prayers_language.equals("ru")) {
			size_default_MyView = PreferencesActivity.MyPreferenceFragment.ReadFloat(getActivity().getApplicationContext(), "pref_prayers_text_size_ru", 0);
		} 
		if (prayers_language.equals("cs")) {
			size_default_MyView = PreferencesActivity.MyPreferenceFragment.ReadFloat(getActivity().getApplicationContext(), "pref_prayers_text_size_cs", 0);
		}
		size = tvPage.getTextSize();
		// Log.d(TAG, "size =" + size);
		size = size + size_default_MyView;
		tvPage.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
	    
	    text=text.replace("\r\n", "<br>");
	    text=textPsalturEdit(text, p, prayers_language, tvPage);
	    tvPage.setText(Html.fromHtml(text+text_br));
	    
	    return view;
	  }
	  
	  public String textPsalturEdit(String text, int id, String prayers_language, MyView tvPage){
		  String text_edit=text;
		  
		  String text_add_link_start="<FONT COLOR=#aa2c2c> <i>Чтение Псалтири за упокой предваряется чтением канона за умерших многих или за единоумершего, "+
				  "после которого начинается чтение Псалтири. По прочтении всех псалмов опять прочитывается заупокойный канон, "+
				  "после которого снова начинается чтение первой кафизмы. "+
				  "Такой порядок продолжается во все время чтения Псалтири за упокой.</i> </FONT><br><br>"+
		  "<b><FONT COLOR=#aa2c2c><a href=\"activity-run://DescriptionOtherActivityHost?id_other=46\">О чтении Псалтири за умерших</a></FONT></b><br><br>"+
		  "<b><FONT COLOR=#aa2c2c><a href=\"activity-run://DescriptionOtherActivityHost?id_akafist=111\">Канон за единоумершего</a></FONT></b><br><br>"+
		  "<b><FONT COLOR=#aa2c2c><a href=\"activity-run://DescriptionOtherActivityHost?id_akafist=203\">Канон о усопших</a></FONT></b><br><br>";
		  
		  String text_add_link_finish="<b><FONT COLOR=#aa2c2c><a href=\"activity-run://DescriptionOtherActivityHost?id_akafist=111\">Канон за единоумершего</a></FONT></b><br><br>"+
				  "<b><FONT COLOR=#aa2c2c><a href=\"activity-run://DescriptionOtherActivityHost?id_akafist=203\">Канон о усопших</a></FONT></b><br><br><br>";
		  
		  
		  String text_add_link_start_cs="<b><FONT COLOR=#aa2c2c><a href=\"activity-run://DescriptionOtherActivityHost?id_other=46\">О чтении Псалтири за умерших</a></FONT></b><br><br>";
		  text_add_link_start_cs=text_add_link_start_cs.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
		  text_add_link_start_cs=text_add_link_start_cs+"<b><FONT COLOR=#aa2c2c><a href=\"activity-run://DescriptionOtherActivityHostCS?id_akafist=18\">Кан0нъ за преста1вльшагосz</a></FONT></b><br><br>"+
				  "<b><FONT COLOR=#aa2c2c><a href=\"activity-run://DescriptionOtherActivityHostCS?id_akafist=17\">Кан0нъ о3 мн0гихъ ўс0пшихъ</a></FONT></b><br><br>";
				  
		  String text_add_link_finish_cs="<b><FONT COLOR=#aa2c2c><a href=\"activity-run://DescriptionOtherActivityHostCS?id_akafist=18\">Кан0нъ за преста1вльшагосz</a></FONT></b><br><br>"+
						  "<b><FONT COLOR=#aa2c2c><a href=\"activity-run://DescriptionOtherActivityHostCS?id_akafist=17\">Кан0нъ о3 мн0гихъ ўс0пшихъ</a></FONT></b><br><br><br>";
		  
		  text_add_link_start=text_add_link_start.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
		  text_add_link_finish=text_add_link_finish.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
		  text_add_link_start_cs=text_add_link_start_cs.replace("DescriptionOtherActivityHostCS", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST_CS());
		  text_add_link_finish_cs=text_add_link_finish_cs.replace("DescriptionOtherActivityHostCS", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST_CS());
		  
		  //На Славах:
		  String text_replacement1="";
		  //На Славах (за усопших):
		  String text_replacement2="";
		  //По кафисме (за усопших):
		  String text_replacement3="";
		  
		  String  psaltur_view = PreferencesActivity.MyPreferenceFragment.ReadString(getActivity().getApplicationContext(), "pref_psaltur_view", "ps");
		  
		  if (prayers_language.equals("ru")) {
			  db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
			  cursor = db.executeQuery("SELECT _id, psalom_text_ru FROM psaltur_description;");
			  
			  if (cursor != null) {
					if (cursor.moveToFirst()) {
						do {
							try {
								int _id=cursor.getInt(cursor.getColumnIndex("_id"));
								if(_id==1) text_replacement1="<br>"+cursor.getString(cursor.getColumnIndex("psalom_text_ru")).replace("\r\n", "<br>");;
								if(_id==2) text_replacement2="<br>"+cursor.getString(cursor.getColumnIndex("psalom_text_ru")).replace("\r\n", "<br>");;
								if(_id==3) text_replacement3=cursor.getString(cursor.getColumnIndex("psalom_text_ru")).replace("\r\n", "<br>");;
							} catch (NumberFormatException e) {
								// Log.d(TAG, "ERROR=" + e.toString());
							}
						} while (cursor.moveToNext());
					}
				}
			  if (psaltur_view.equals("ys")) {
				  if(id==1){
					  text_edit=text_add_link_start+text_edit;
					  tvPage.setLinksClickable(true);
					  tvPage.setMovementMethod(new LinkMovementMethod());
				  }
				  
				  if(id==173){
					  text_edit=text_edit+text_add_link_finish;
					  tvPage.setLinksClickable(true);
					  tvPage.setMovementMethod(new LinkMovementMethod());
				  }
				  
				  text_edit=text_edit.replace("<b>Слава:</b><br>", text_replacement2);
				  
				  switch(id){
				  case 10:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 1-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 19:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 2-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 27:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 3-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 36:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 4-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 42:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 5-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 52:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 6-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 62:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 7-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 72:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 8-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 79:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 9-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 87:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 10-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 96:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 11-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 103:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 12-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 114:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 13-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 119:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 14-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 124:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 15-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 134:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 16-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 136:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 17-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 152:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 18-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 162:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 19-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 172:
					  text_edit="<FONT COLOR=#aa2c2c><b>По 20-й кафисме</b></FONT><br><br>"+text_replacement3;
					  break;
				  }

			  }
			  if (psaltur_view.equals("ps")) {
				  text_edit=text_edit.replace("<b>Слава:</b><br>", text_replacement1);
			  }
		  } 
		  if (prayers_language.equals("cs")) {
			  db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
			  cursor = db.executeQuery("SELECT _id, psalom_text_sc FROM psaltur_description;");
			  
			  if (cursor != null) {
					if (cursor.moveToFirst()) {
						do {
							try {
								int _id=cursor.getInt(cursor.getColumnIndex("_id"));
								if(_id==1) text_replacement1="<br>"+cursor.getString(cursor.getColumnIndex("psalom_text_sc")).replace("\r\n", "<br>");;
								if(_id==2) text_replacement2="<br>"+cursor.getString(cursor.getColumnIndex("psalom_text_sc")).replace("\r\n", "<br>");;
								if(_id==3) text_replacement3=cursor.getString(cursor.getColumnIndex("psalom_text_sc")).replace("\r\n", "<br>");;
							} catch (NumberFormatException e) {
								// Log.d(TAG, "ERROR=" + e.toString());
							}
						} while (cursor.moveToNext());
					}
				}
			  if (psaltur_view.equals("ys")) {
				  if(id==1){
					  text_edit=text_add_link_start_cs+text_edit;
					  tvPage.setLinksClickable(true);
					  tvPage.setMovementMethod(new LinkMovementMethod());
				  }
				  
				  if(id==173){
					  text_edit=text_edit+text_add_link_finish_cs;
					  tvPage.setLinksClickable(true);
					  tvPage.setMovementMethod(new LinkMovementMethod());
				  }
				  
				  text_edit=text_edit.replace("<b>Слaва:</b><br>", text_replacement2);
				  
				  switch(id){
				  case 10:
					  text_edit="<FONT COLOR=#aa2c2c><b>По №-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 19:
					  text_edit="<FONT COLOR=#aa2c2c><b>По в7-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 27:
					  text_edit="<FONT COLOR=#aa2c2c><b>По G-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 36:
					  text_edit="<FONT COLOR=#aa2c2c><b>По д7-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 42:
					  text_edit="<FONT COLOR=#aa2c2c><b>По є7-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 52:
					  text_edit="<FONT COLOR=#aa2c2c><b>По ѕ7-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 62:
					  text_edit="<FONT COLOR=#aa2c2c><b>По з7-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 72:
					  text_edit="<FONT COLOR=#aa2c2c><b>По }-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 79:
					  text_edit="<FONT COLOR=#aa2c2c><b>По f7-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 87:
					  text_edit="<FONT COLOR=#aa2c2c><b>По ‹-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 96:
					  text_edit="<FONT COLOR=#aa2c2c><b>По №i-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 103:
					  text_edit="<FONT COLOR=#aa2c2c><b>По в7i-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 114:
					  text_edit="<FONT COLOR=#aa2c2c><b>По Gi-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 119:
					  text_edit="<FONT COLOR=#aa2c2c><b>По д7i-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 124:
					  text_edit="<FONT COLOR=#aa2c2c><b>По є7i-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 134:
					  text_edit="<FONT COLOR=#aa2c2c><b>По ѕ7i-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 136:
					  text_edit="<FONT COLOR=#aa2c2c><b>По з7i-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 152:
					  text_edit="<FONT COLOR=#aa2c2c><b>По }i-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 162:
					  text_edit="<FONT COLOR=#aa2c2c><b>По f7i-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  case 172:
					  text_edit="<FONT COLOR=#aa2c2c><b>По к7-й каfjсмэ</b></FONT><br><br>"+text_replacement3;
					  break;
				  }

			  }
			  if (psaltur_view.equals("ps")) {
				  text_edit=text_edit.replace("<b>Слaва:</b><br>", text_replacement1);
			  }
		  }
		  
		  return text_edit;
	  }
	  
		@Override
		public void onDestroyView() {
			// TODO Auto-generated method stub
			super.onDestroyView();
			//Log.d(TAG, "FragmentPrayers===onDestroyView()");
			if(cursor!=null)cursor.close();
			if(db!=null)db.closeConnecion();
		}
}
