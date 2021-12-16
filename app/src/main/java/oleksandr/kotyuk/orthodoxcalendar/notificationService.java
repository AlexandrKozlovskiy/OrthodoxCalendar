package oleksandr.kotyuk.orthodoxcalendar;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;

public class notificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyScheduledReceiver.l=new MyScheduledReceiver.StopServiceListener() {
            @Override
            public void stopService() {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) stopForeground(0);
                stopSelf();
            }
        };
        MyScheduledReceiver.onAlarm(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1,MyScheduledReceiver.sendNotif(this,"",getString(R.string.app_name),"","",true));
    }
}
