package oleksandr.kotyuk.orthodoxcalendarfree.fragments;

import oleksandr.kotyuk.orthodoxcalendarfree.FontsHelper;
import oleksandr.kotyuk.orthodoxcalendarfree.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.R;
import oleksandr.kotyuk.orthodoxcalendarfree.models.MyCalendar;
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
import oleksandr.kotyuk.orthodoxcalendarfree.accessibility.MyView;
import android.widget.AdapterView.OnItemSelectedListener;

public class FragmentSouls  extends Fragment {

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";
	
	private final String[] data_years_souls = { "2014", "2015", "2016",
	"2017", "2018", "2019", "2020", "2021", "2022"};
	
	private final String[] text_years_souls={"09.02.2014 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>"+
		"22.02.2014 – Вселенская родительская мясопустная суббота<br>"+ 
		"15.03.2014 – Родительская суббота 2-й седмицы Великого поста<br>"+
		"22.03.2014 – Родительская суббота 3-й седмицы Великого поста<br>"+
		"29.03.2014 – Родительская суббота 4-й седмицы Великого поста<br>"+
		"29.04.2014 – Радоница<br>"+
		"09.05.2014 – Поминовение усопших воинов<br>"+
		"07.06.2014 – Троицкая родительская суббота<br>"+
		"01.11.2014 – Суббота Димитриевская",
			
		"08.02.2015 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>"+
		"14.02.2015 – Вселенская родительская мясопустная суббота<br>"+ 
		"07.03.2015 – Родительская суббота 2-й седмицы Великого поста<br>"+
		"14.03.2015 – Родительская суббота 3-й седмицы Великого поста<br>"+
		"21.03.2015 – Родительская суббота 4-й седмицы Великого поста<br>"+
		"21.04.2015 – Радоница<br>"+
		"09.05.2015 – Поминовение усопших воинов<br>"+
		"30.05.2015 – Троицкая родительская суббота<br>"+
		"07.11.2015 – Суббота Димитриевская",
					
		"07.02.2016 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>"+
		"05.03.2016 – Вселенская родительская мясопустная суббота<br>"+ 
		"26.03.2016 – Родительская суббота 2-й седмицы Великого поста<br>"+
		"02.04.2016 – Родительская суббота 3-й седмицы Великого поста<br>"+
		"09.04.2016 – Родительская суббота 4-й седмицы Великого поста<br>"+
		"10.05.2016 – Радоница<br>"+
		"09.05.2016 – Поминовение усопших воинов<br>"+
		"18.06.2016 – Троицкая родительская суббота<br>"+
		"05.11.2016 – Суббота Димитриевская",
			
		"05.02.2017 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>"+
		"18.02.2017 – Вселенская родительская мясопустная суббота<br>"+ 
		"11.03.2017 – Родительская суббота 2-й седмицы Великого поста<br>"+
		"18.03.2017 – Родительская суббота 3-й седмицы Великого поста<br>"+
		"25.03.2017 – Родительская суббота 4-й седмицы Великого поста<br>"+
		"25.04.2017 – Радоница<br>"+
		"09.05.2017 – Поминовение усопших воинов<br>"+
		"03.06.2017 – Троицкая родительская суббота<br>"+
		"28.10.2017 – Суббота Димитриевская",
			
		"04.02.2018 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>"+ 
		"10.02.2018 – Вселенская родительская мясопустная суббота<br>"+
		"03.03.2018 – Родительская суббота 2-й седмицы Великого поста<br>"+
		"10.03.2018 – Родительская суббота 3-й седмицы Великого поста<br>"+
		"17.03.2018 – Родительская суббота 4-й седмицы Великого поста<br>"+
		"17.04.2018 – Радоница<br>"+
		"09.05.2018 – Поминовение усопших воинов<br>"+
		"26.05.2018 – Троицкая родительская суббота<br>"+
		"03.11.2018 – Суббота Димитриевская",
				
		"10.02.2019 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>"+
		"02.03.2019 – Вселенская родительская мясопустная суббота<br>"+ 
		"23.03.2019 – Родительская суббота 2-й седмицы Великого поста<br>"+
		"30.03.2019 – Родительская суббота 3-й седмицы Великого поста<br>"+
		"06.04.2019 – Родительская суббота 4-й седмицы Великого поста<br>"+
		"07.05.2019 – Радоница<br>"+
		"09.05.2019 – Поминовение усопших воинов<br>"+
		"15.06.2019 – Троицкая родительская суббота<br>"+
		"02.11.2019 – Суббота Димитриевская",
				
		"09.02.2020 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>"+
		"22.02.2020 – Вселенская родительская мясопустная суббота<br>"+ 
		"14.03.2020 – Родительская суббота 2-й седмицы Великого поста<br>"+
		"21.03.2020 – Родительская суббота 3-й седмицы Великого поста<br>"+
		"28.03.2020 – Родительская суббота 4-й седмицы Великого поста<br>"+
		"28.04.2020 – Радоница<br>"+
		"09.05.2020 – Поминовение усопших воинов<br>"+
		"06.06.2020 – Троицкая родительская суббота<br>"+
		"07.11.2020 – Суббота Димитриевская",
		
		"07.02.2021 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>"+
		"06.03.2021 – Вселенская родительская мясопустная суббота<br>"+ 
		"27.03.2021 – Родительская суббота 2-й седмицы Великого поста<br>"+
		"03.04.2021 – Родительская суббота 3-й седмицы Великого поста<br>"+
		"10.04.2021 – Родительская суббота 4-й седмицы Великого поста<br>"+
		"11.05.2021 – Радоница<br>"+
		"09.05.2021 – Поминовение усопших воинов<br>"+
		"19.06.2021 – Троицкая родительская суббота<br>"+
		"06.11.2021 – Суббота Димитриевская",
		
		"06.02.2022 – Поминовение всех усопших, пострадавших в годину гонений за веру Христову<br>"+
		"26.02.2022 – Вселенская родительская мясопустная суббота<br>"+ 
		"19.03.2022 – Родительская суббота 2-й седмицы Великого поста<br>"+
		"26.03.2022 – Родительская суббота 3-й седмицы Великого поста<br>"+
		"02.04.2022 – Родительская суббота 4-й седмицы Великого поста<br>"+
		"03.05.2022 – Радоница<br>"+
		"09.05.2022 – Поминовение усопших воинов<br>"+
		"11.06.2022 – Троицкая родительская суббота<br>"+
		"05.11.2022 – Суббота Димитриевская"};
	
	/*private final String text_years_souls1=
			"1. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=12\">О воздушных мытарствах</a><br>"+
			"2. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=13\">Поминовение усопших в Церкви</a><br>"+
			"3. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=14\">3-й, 9-й, 40-й дни, годовщина</a><br>"+
			"4. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=15\">Поминовение усопших на Божественной литургии</a><br>"+
			"5. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=16\">Сорокоуст</a><br>"+
			"6. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=17\">Вселенская родительская мясопустная суббота</a><br>"+
			"7. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=18\">Троицкая вселенская родительская суббота</a><br>"+
			"8. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=19\">Субботы Великого поста</a><br>"+
			"9. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=20\">Радоница</a><br>"+
			"10. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=21\">Димитриевская родительская суббота</a><br>"+
			"11. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=22\">Другие дни поминовения воинов</a><br>"+
			"12. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=23\">Панихида</a><br>"+
			"13. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=24\">Лития</a><br>"+
			"14. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=25\">В какие дни не совершается заупокойное богослужение</a><br>"+
			"15. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=26\">Значение записок «об упокоении»</a><br>"+
			"16. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=27\">Поминовение усопших на домашней молитве</a><br>"+
			"17. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=28\">Чтение Псалтири по усопшем</a><br>"+
			"18. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=29\">Чтение Святого Евангелия в память усопших</a><br>"+
			"19. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=30\">Поминовение на домашней молитве неправославных</a><br>"+
			"20. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=31\">О каноне мученику Уару об избавлении от муки в иноверии умерших</a><br>"+
			"21. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=32\">О каноне преподобному Паисию Великому, поемом за избавление от муки без покаяния умерших</a><br>"+
			"22. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=33\">О молитве за некрещеных и мертворожденных младенцев</a><br>"+
			"23. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=34\">Поминальная трапеза</a><br>"+
			"24. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=35\">Поминальная трапеза во дни поста</a><br>"+
			"25. <a href=\"activity-run://DescriptionOtherActivityHost?id_other=36\">Кутия в память усопших</a>";*/
	
	MyCalendar cal = MyCalendar.getInstance();
	
	private final String FONT_PATH1 = "fonts/Russo_One.ttf";
	MyView tvSoulsTitle;
	MyView tvDescriptionSouls;
	//MyView tvDescriptionSouls1;
	//MyView tvDescriptionSouls2;
	Spinner spinnerSouls;
	
	String text_size="0";
	float standart_text_size1=0;
	float standart_text_size2=0;
	float standart_text_size3=0;
	MyView tvEasterSelectYear;
	
	public FragmentSouls() {

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_souls,
				container, false);
		
		tvEasterSelectYear=(MyView) view.findViewById(R.id.MyViewSouls3);
		tvSoulsTitle=(MyView) view.findViewById(R.id.MyViewSouls1);
		ViewCompat.setAccessibilityHeading(tvSoulsTitle,true);
		tvSoulsTitle.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH1));
		tvDescriptionSouls=(MyView) view.findViewById(R.id.MyViewSouls2);
		/*tvDescriptionSouls1=(MyView) view.findViewById(R.id.MyViewSouls4);
		tvDescriptionSouls2=(MyView) view.findViewById(R.id.MyViewSouls5);
		tvDescriptionSouls2.setLinksClickable(true);
		tvDescriptionSouls2.setMovementMethod(new LinkMovementMethod());
		tvDescriptionSouls2.setText(Html.fromHtml(text_years_souls1));*/
		
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
				case 2014:
					spinnerSouls.setSelection(0);
					tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[0]));
					break;
				case 2015:
					spinnerSouls.setSelection(1);
					tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[1]));
					break;
				case 2016:
					spinnerSouls.setSelection(2);
					tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[2]));
					break;
				case 2017:
					spinnerSouls.setSelection(3);
					tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[3]));
					break;
				case 2018:
					spinnerSouls.setSelection(4);
					tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[4]));
					break;
				case 2019:
					spinnerSouls.setSelection(5);
					tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[5]));
					break;
				case 2020:
					spinnerSouls.setSelection(6);
					tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[6]));
					break;
				case 2021:
					spinnerSouls.setSelection(7);
					tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[7]));
					break;
				case 2022:
					spinnerSouls.setSelection(8);
					tvDescriptionSouls.setText(Html.fromHtml(text_years_souls[8]));
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
		if(standart_text_size1==0) standart_text_size1=tvSoulsTitle.getTextSize();//22
		if(standart_text_size2==0) standart_text_size2=tvEasterSelectYear.getTextSize();//17
		if(standart_text_size3==0) standart_text_size3=tvDescriptionSouls.getTextSize();//19
		String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_text_size", "0");
		if(!text_size.equals(tmp)){
			text_size=tmp;
			//int int_text_size=Integer.valueOf(text_size);
			/*Toast.makeText(getActivity(), text_size+"="+int_text_size,
					Toast.LENGTH_SHORT).show();*/
			//float size=MyView_holiday.getTextSize();
			if(text_size.equals("-5")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-10);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-10);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-10);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
			}
			if(text_size.equals("-4")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-8);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-8);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-8);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
			}
			if(text_size.equals("-3")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-6);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-6);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
			}
			if(text_size.equals("-2")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-4);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-4);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
			}
			if(text_size.equals("-1")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-2);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-2);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
			}
			if(text_size.equals("0")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
			}
			if(text_size.equals("+1")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+2);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+2);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
			}
			if(text_size.equals("+2")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+4);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+4);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
			}
			if(text_size.equals("+3")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+6);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+6);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
			}
			if(text_size.equals("+4")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+8);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+8);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
			}
			if(text_size.equals("+5")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+10);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+10);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
			}
			if(text_size.equals("+6")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+12);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+12);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
			}
			if(text_size.equals("+7")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+14);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+14);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
			}
			if(text_size.equals("+8")){
				tvSoulsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+16);
				tvEasterSelectYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+16);
				tvDescriptionSouls.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
				//tvDescriptionSouls1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
				//tvDescriptionSouls2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
			}
				
		}
	}

}
