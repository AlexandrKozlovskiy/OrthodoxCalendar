package oleksandr.kotyuk.orthodoxcalendar.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MyService extends RemoteViewsService {

// создает адаптер, передает ему Context и Intent, и возвращает этот
// созданный адаптер системе.
@Override
public RemoteViewsFactory onGetViewFactory(Intent intent) {
 /*int appWidgetId = intent.getIntExtra(
  AppWidgetManager.EXTRA_APPWIDGET_ID,
  AppWidgetManager.INVALID_APPWIDGET_ID);*/

 
 return new MyFactory(this.getApplicationContext(), intent);
}

}
