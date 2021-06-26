package oleksandr.kotyuk.orthodoxcalendarfree.fragments;

import oleksandr.kotyuk.orthodoxcalendarfree.DescriptionActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.FontsHelper;
import oleksandr.kotyuk.orthodoxcalendarfree.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.R;
import oleksandr.kotyuk.orthodoxcalendarfree.GlobalData;
import android.content.Intent;
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
import oleksandr.kotyuk.orthodoxcalendarfree.accessibility.MyView;

public class FragmentDirectory extends Fragment implements OnClickListener{

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";
	
	private final String text_directory1="Канонические правила";
	
	private String text_directory2=
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=37\">1. Канонические правила Православной Церкви о мирянах в богослужении</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=38\">2. Канонические правила относительно участия женщин в богослужении</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=39\">3. Богослужебный Устав для мирян</a>";
	
	private final String text_directory3="Сокращения в календаре";
	
	private final String text_directory4="Знаки Типикона";
	
	private final String text_directory5="Устав о посте по Типикону";
	
	private String text_directory6=
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=1\">1. Вступление</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=2\">2. Общие положения православного устава о трапезе</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=3\">3. Порядок трапезы вне длительных постов</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=4\">4. Трапеза во время Поста</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=5\">5. Великий Пост</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=6\">6. Пост свв. Апостолов</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=7\">7. Успенский Пост</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=8\">8. Рождественский Пост</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=9\">9. Трапеза в Пятидесятницу</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=10\">10. Заключение</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=11\">11. Приложение</a>";
	
	private final String text_directory7="О поминовении усопших";
	
	private String text_directory8=
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=12\">1. О воздушных мытарствах</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=13\">2. Поминовение усопших в Церкви</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=14\">3. 3-й, 9-й, 40-й дни, годовщина</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=15\">4. Поминовение усопших на Божественной литургии</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=16\">5. Сорокоуст</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=17\">6. Вселенская родительская мясопустная суббота</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=18\">7. Троицкая вселенская родительская суббота</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=19\">8. Субботы Великого поста</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=20\">9. Радоница</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=21\">10. Димитриевская родительская суббота</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=22\">11. Другие дни поминовения воинов</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=23\">12. Панихида</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=24\">13. Лития</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=25\">14. В какие дни не совершается заупокойное богослужение</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=26\">15. Значение записок «об упокоении»</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=27\">16. Поминовение усопших на домашней молитве</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=28\">17. Чтение Псалтири по усопшем</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=29\">18. Чтение Святого Евангелия в память усопших</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=30\">19. Поминовение на домашней молитве неправославных</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=31\">20. О каноне мученику Уару об избавлении от муки в иноверии умерших</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=32\">21. О каноне преподобному Паисию Великому, поемом за избавление от муки без покаяния умерших</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=33\">22. О молитве за некрещеных и мертворожденных младенцев</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=34\">23. Поминальная трапеза</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=35\">24. Поминальная трапеза во дни поста</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=36\">25. Кутия в память усопших</a>";
	
	private final String text_directory9="Порядок чтения Евангелия в Великий пост";
	
	private String text_directory10=
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=40\">1. О чтении Евангелия на часах Великим постом</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=41\">2. Порядок чтения Евангелия на великопостных часах в среду и пятницу в течение 2-й–6-й седмиц</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=42\">3. Порядок чтения Евангелия на великопостных часах ежедневно в течение 2-й–6-й седмиц</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=43\">4. Порядок чтения Евангелия на великопостных часах только на 6-й седмице</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=44\">5. Особенности часов в первые три дня Страстной седмицы</a><br>"+
			"<a href=\"activity-run://DescriptionOtherActivityHost?id_other=45\">6. Порядок чтения Евангелия в первые три дня Страстной седмицы</a>";
	
	private int flag_visible_directory1 = 0;
	private int flag_visible_directory2 = 0;
	private int flag_visible_directory3 = 0;
	private int flag_visible_directory4 = 0;
	
	
	//MyCalendar cal = MyCalendar.getInstance();
	
	private final String FONT_PATH1 = "fonts/Russo_One.ttf";
	MyView tvDirectoryTitle;
	/*MyView tvDescriptionDirectory1;
	MyView tvDescriptionDirectory2;
	
	String text_size="0";
	float standart_text_size1=0;
	float standart_text_size3=0;*/
	
	MyView tvDescriptionDirectory1;
	MyView tvDescriptionDirectory2;
	MyView tvDescriptionDirectory3;
	MyView tvDescriptionDirectory4;
	MyView tvDescriptionDirectory5;
	MyView tvDescriptionDirectory6;
	MyView tvDescriptionDirectory7;
	MyView tvDescriptionDirectory8;
	MyView tvDescriptionDirectory9;
	MyView tvDescriptionDirectory10;
	String text_size="0";
	float standart_text_size1=0;
	float standart_text_size2=0;
	float standart_text_size3=0;
	
	public FragmentDirectory() {

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*View view = inflater.inflate(R.layout.fragment_layout_directory,
				container, false);

		tvDirectoryTitle=(MyView) view.findViewById(R.id.MyViewDirectory1);
tvDirectoryTitle.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH1));
		
		tvDescriptionDirectory1=(MyView) view.findViewById(R.id.MyViewDirectory2);
		tvDescriptionDirectory2=(MyView) view.findViewById(R.id.MyViewDirectory3);
		tvDescriptionDirectory2.setLinksClickable(true);
		tvDescriptionDirectory2.setMovementMethod(new LinkMovementMethod());
		tvDescriptionDirectory2.setText(Html.fromHtml(text_directory));*/

		View view = inflater.inflate(R.layout.fragment_layout_directory,
				container, false);
		

tvDirectoryTitle=(MyView) view.findViewById(R.id.MyViewDirectory1);
ViewCompat.setAccessibilityHeading(tvDirectoryTitle,true);
		tvDirectoryTitle.setTypeface(FontsHelper.getTypeFace(getActivity().getApplicationContext(), FONT_PATH1));
		
		tvDescriptionDirectory1=(MyView) view.findViewById(R.id.MyViewDirectory2);
		tvDescriptionDirectory2=(MyView) view.findViewById(R.id.MyViewDirectory3);
		tvDescriptionDirectory3=(MyView) view.findViewById(R.id.MyViewDirectory4);
		tvDescriptionDirectory4=(MyView) view.findViewById(R.id.MyViewDirectory5);
		tvDescriptionDirectory5=(MyView) view.findViewById(R.id.MyViewDirectory6);
		tvDescriptionDirectory6=(MyView) view.findViewById(R.id.MyViewDirectory7);
		tvDescriptionDirectory7=(MyView) view.findViewById(R.id.MyViewDirectory8);
		tvDescriptionDirectory8=(MyView) view.findViewById(R.id.MyViewDirectory9);
		tvDescriptionDirectory9=(MyView) view.findViewById(R.id.MyViewDirectory10);
		tvDescriptionDirectory10=(MyView) view.findViewById(R.id.MyViewDirectory11);
		
		tvDescriptionDirectory1.setOnClickListener(this);
		tvDescriptionDirectory1.setText(text_directory1);
		
		tvDescriptionDirectory2.setLinksClickable(true);
		tvDescriptionDirectory2.setMovementMethod(new LinkMovementMethod());
		text_directory2=text_directory2.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
		tvDescriptionDirectory2.setText(Html.fromHtml(text_directory2));
		
		tvDescriptionDirectory3.setOnClickListener(this);
		tvDescriptionDirectory3.setText(text_directory3);
		
		tvDescriptionDirectory4.setOnClickListener(this);
		tvDescriptionDirectory4.setText(text_directory4);
		
		tvDescriptionDirectory5.setOnClickListener(this);
		tvDescriptionDirectory5.setText(text_directory5);
		
		tvDescriptionDirectory6.setLinksClickable(true);
		tvDescriptionDirectory6.setMovementMethod(new LinkMovementMethod());
		text_directory6=text_directory6.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
		tvDescriptionDirectory6.setText(Html.fromHtml(text_directory6));
		
		tvDescriptionDirectory7.setOnClickListener(this);
		tvDescriptionDirectory7.setText(text_directory7);
		
		tvDescriptionDirectory8.setLinksClickable(true);
		tvDescriptionDirectory8.setMovementMethod(new LinkMovementMethod());
		text_directory8=text_directory8.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
		tvDescriptionDirectory8.setText(Html.fromHtml(text_directory8));
		
		tvDescriptionDirectory9.setOnClickListener(this);
		tvDescriptionDirectory9.setText(text_directory9);
		
		tvDescriptionDirectory10.setLinksClickable(true);
		tvDescriptionDirectory10.setMovementMethod(new LinkMovementMethod());
		text_directory10=text_directory10.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
		tvDescriptionDirectory10.setText(Html.fromHtml(text_directory10));
		
		return view;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(standart_text_size1==0) standart_text_size1=tvDirectoryTitle.getTextSize();//22
		if(standart_text_size2==0) standart_text_size2=tvDescriptionDirectory1.getTextSize();//19
		if(standart_text_size3==0) standart_text_size3=tvDescriptionDirectory2.getTextSize();//18
				String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_text_size", "0");
				if(!text_size.equals(tmp)){
					text_size=tmp;
					if(text_size.equals("-5")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-6);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-10);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-10);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-10);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-10);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-10);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-10);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-10);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-10);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-10);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-10);
					}
					if(text_size.equals("-4")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-6);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-8);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-8);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-8);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-8);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-8);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-8);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-8);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-8);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-8);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-8);
					}
					if(text_size.equals("-3")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-6);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-6);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-6);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-6);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-6);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-6);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-6);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-6);
					}
					if(text_size.equals("-2")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-4);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-4);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-4);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-4);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-4);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-4);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-4);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-4);
					}
					if(text_size.equals("-1")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1-2);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-2);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-2);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-2);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-2);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-2);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2-2);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3-2);
					}
					if(text_size.equals("0")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
					}
					if(text_size.equals("+1")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+2);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+2);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+2);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+2);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+2);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+2);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+2);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+2);
					}
					if(text_size.equals("+2")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+4);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+4);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+4);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+4);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+4);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+4);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+4);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+4);
					}
					if(text_size.equals("+3")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+6);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+6);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+6);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+6);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+6);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+6);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+6);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+6);
					}
					if(text_size.equals("+4")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+8);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+8);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+8);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+8);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+8);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+8);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+8);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+8);
					}
					if(text_size.equals("+5")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+10);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+10);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+10);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+10);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+10);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+10);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+10);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+10);
					}
					if(text_size.equals("+6")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+12);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+12);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+12);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+12);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+12);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+12);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+12);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+12);
					}
					if(text_size.equals("+7")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+14);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+14);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+14);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+14);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+14);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+14);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+14);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+14);
					}
					if(text_size.equals("+8")){
						tvDirectoryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1+16);
						tvDescriptionDirectory1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+16);
						tvDescriptionDirectory2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
						tvDescriptionDirectory3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+16);
						tvDescriptionDirectory4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+16);
						tvDescriptionDirectory5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+16);
						tvDescriptionDirectory6.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
						tvDescriptionDirectory7.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+16);
						tvDescriptionDirectory8.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
						tvDescriptionDirectory9.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2+16);
						tvDescriptionDirectory10.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3+16);
					}
						
				}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.MyViewDirectory2:
			if (flag_visible_directory1 == 0) {
				tvDescriptionDirectory2.setVisibility(View.VISIBLE);
				flag_visible_directory1 = 1;
			} else {
				tvDescriptionDirectory2.setVisibility(View.GONE);
				flag_visible_directory1 = 0;
			}
			break;

		case R.id.MyViewDirectory4:
			Intent intent_directory1 = new Intent(getActivity(),
					DescriptionActivity.class);
			intent_directory1.putExtra("directory_id", 1);
			intent_directory1.putExtra("id", 6);
			startActivity(intent_directory1);
			break;
		case R.id.MyViewDirectory5:
			Intent intent_directory2 = new Intent(getActivity(),
					DescriptionActivity.class);
			intent_directory2.putExtra("directory_id", 2);
			intent_directory2.putExtra("id", 6);
			startActivity(intent_directory2);
			break;
		case R.id.MyViewDirectory6:
			if (flag_visible_directory2 == 0) {
				tvDescriptionDirectory6.setVisibility(View.VISIBLE);
				flag_visible_directory2 = 1;
			} else {
				tvDescriptionDirectory6.setVisibility(View.GONE);
				flag_visible_directory2 = 0;
			}
			break;
		case R.id.MyViewDirectory8:
			if (flag_visible_directory3 == 0) {
				tvDescriptionDirectory8.setVisibility(View.VISIBLE);
				flag_visible_directory3 = 1;
			} else {
				tvDescriptionDirectory8.setVisibility(View.GONE);
				flag_visible_directory3 = 0;
			}
			break;
		case R.id.MyViewDirectory10:
			if (flag_visible_directory4 == 0) {
				tvDescriptionDirectory10.setVisibility(View.VISIBLE);
				flag_visible_directory4 = 1;
			} else {
				tvDescriptionDirectory10.setVisibility(View.GONE);
				flag_visible_directory4 = 0;
			}
			break;
		default:
			break;
		}
	}

}

