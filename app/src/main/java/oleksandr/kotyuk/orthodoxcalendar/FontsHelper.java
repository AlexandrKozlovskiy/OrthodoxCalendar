package oleksandr.kotyuk.orthodoxcalendar;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;

public class FontsHelper {

private static Map<String, Typeface> fonts = new HashMap<String, Typeface>();

public static Typeface getTypeFace(Context context, String fontPath) {

 if (!fonts.containsKey(fontPath)) {

 Typeface font = Typeface.createFromAsset(context.getAssets(),
  fontPath);
 fonts.put(fontPath, font);
 }

 return fonts.get(fontPath);
}

}
