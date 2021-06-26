package oleksandr.kotyuk.orthodoxcalendarfree;

import oleksandr.kotyuk.orthodoxcalendarfree.db.DatabaseHelper;
import androidx.appcompat.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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

public class DescriptionOtherActivityCS  extends AppCompatActivity{

	private DatabaseHelper db;
	Cursor cursor;
	String sql = "";

	LinearLayout llDescriptionActivity;
	MyView tvDescriptionActivity;
	float size_default_MyView = 0;
	float size = 0;
	
	private final String FONT_PATH_CS1 = "fonts/Canonic.ttf";
	private final String FONT_PATH_CS2 = "fonts/Orthodox.ttf";
	private final String FONT_PATH_CS3 = "fonts/Triodion.ttf";
	
	String prayers_fonts_cs;
	
	MenuItem menu_item1;
	MenuItem menu_item2;
	

	String description_id_link_akafist="";

	String id="";

	boolean description_style=false;
	
	String black_fon_color;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_description);

		ActionBar actionBar = getSupportActionBar();
		// Enabling Up / Back navigation
		actionBar.setDisplayHomeAsUpEnabled(true);

		Uri uri = getIntent().getData();
		try {
			description_id_link_akafist=uri.getQueryParameter("id_akafist");
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		prayers_fonts_cs = PreferencesActivity.MyPreferenceFragment.ReadString(this, "pref_prayers_fonts_cs", "1");
		
		description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style2", false);
		llDescriptionActivity=(LinearLayout)this.findViewById(R.id.llViewDescription);
		tvDescriptionActivity = (MyView) this.findViewById(R.id.MyViewDescription1);
		
		if(description_id_link_akafist!=null){
			tvDescriptionActivity.setLinksClickable(true);
			tvDescriptionActivity.setMovementMethod(new LinkMovementMethod());
		}
		
		if(prayers_fonts_cs.equals("1")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
				FONT_PATH_CS1));
		if(prayers_fonts_cs.equals("2")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
				FONT_PATH_CS2));
		if(prayers_fonts_cs.equals("3")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
				FONT_PATH_CS3));

		
		black_fon_color = PreferencesActivity.MyPreferenceFragment.ReadString(
				this, "pref_black_fon_color", "black");
		
		if(description_style){
			if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
			if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
			if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
			if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
			tvDescriptionActivity.setTextColor(getResources().getColor(R.color.WHITE2));
		}
		
		size_default_MyView = PreferencesActivity.MyPreferenceFragment
				.ReadFloat(this, "pref_prayers_text_size_cs", 0);
		
		size = tvDescriptionActivity.getTextSize();
		// Log.d(TAG, "size =" + size);
		size = size + size_default_MyView;
		tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

		db = DatabaseHelper.getInstance(this);
		
		if(description_id_link_akafist!=null && !description_id_link_akafist.equals("")){
			try{
				tvDescriptionActivity.setText(Html.fromHtml(textDescriptionOther().replace("\r\n", "<br>")));
			}catch(Exception e){
				tvDescriptionActivity.setText("Произошла ошибка!!!");
			}
		}
	}
	
	public String textDescriptionOther(){
		
		String column_index="";
		
		if(description_id_link_akafist!=null){
			sql="select text_prayers, name_prayers from prayers_cs_ak where id2="+description_id_link_akafist;
			column_index="text_prayers";
		}
		
		String text="";
		String top_prayer="";
		
		cursor = db.executeQuery(sql);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					try {
						if(description_id_link_akafist!=null){
							top_prayer="<FONT COLOR=#aa2c2c> <b>"+cursor.getString(cursor.getColumnIndex("name_prayers"))+"</b> </FONT> <br>";
						}
						text=cursor.getString(cursor.getColumnIndex(column_index))+ "<br><br>";
					} catch (NumberFormatException e) {
						// Log.d(TAG, "ERROR=" + e.toString());
						text="Error!!!";
					}
				} while (cursor.moveToNext());
			}
		}

		return top_prayer+text;
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (cursor != null)
			cursor.close();
		if (db != null)
			db.closeConnecion();
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
						.ReadFloat(this, "pref_prayers_text_size_cs", 0);

			size = tvDescriptionActivity.getTextSize();
			if(size<120){
				// Log.d(TAG, "size =" + size);
				size = size + 3;
				tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
				
				PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
							"pref_prayers_text_size_cs", size_default_MyView + 3);
				
			}else Toast.makeText(this, "Размер шрифта максимальный!!!", Toast.LENGTH_SHORT)
			.show();
			return true;
		case R.id.item2_prayers_menu:
			// уменьшаем размер шрифта
			
			size_default_MyView = PreferencesActivity.MyPreferenceFragment
						.ReadFloat(this, "pref_prayers_text_size_cs", 0);
			size = tvDescriptionActivity.getTextSize();
			if(size>7){
				//Log.d(TAG, "size2 =" + size);
				size = size - 3;
				tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
				
				PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
							"pref_prayers_text_size_cs", size_default_MyView - 3);
					
			}else Toast.makeText(this, "Размер шрифта минимальный!!!", Toast.LENGTH_SHORT)
			.show();
			return true;
		case R.id.item3_prayers_menu:
			description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style2", false);
			if(!description_style){
				if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
				if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
				if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
				if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
				tvDescriptionActivity.setTextColor(getResources().getColor(R.color.WHITE2));
				PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style2", true);
			}else{
				llDescriptionActivity.setBackgroundResource(R.drawable.rx1);
				tvDescriptionActivity.setTextColor(getResources().getColor(R.color.BLACK));
				PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style2", false);
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
	            return true;  
	        case KeyEvent.KEYCODE_VOLUME_DOWN:
	        	scrollViewKey.post(new Runnable() {
	    	        public void run() {
	    	        	//scrollViewKey.scrollBy(0, +(height/8));
	    	        	scrollViewKey.pageScroll(View.FOCUS_DOWN);
	    	        	
	    	        	scrollViewKey.computeScroll();
	    	        }
	    	    }); 
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
