package oleksandr.kotyuk.orthodoxcalendar;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MyApp extends Application {
class ExceptionCatcher implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler h;
    ExceptionCatcher(Thread.UncaughtExceptionHandler h) {
this.h=h;
    }
        @Override
        public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
String info="version: "+BuildConfig.VERSION_NAME+" version code: "+BuildConfig.VERSION_CODE+" product flavor: "+BuildConfig.FLAVOR+"\nversion of android: "+ Build.VERSION.SDK_INT+" brand: "+Build.BRAND+" device: "+Build.DEVICE+" model: "+Build.MODEL+" and exception stack trace is:\n";
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(outputStream));
            info+=outputStream.toString();
            Intent i=new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
            i.putExtra(Intent.EXTRA_EMAIL,new String[] {"oleksandr.kotyuk@gmail.com","k.sasha1994@yandex.ru"});
            i.putExtra(Intent.EXTRA_SUBJECT,"Crash of orthodox calendar");
            i.putExtra(Intent.EXTRA_TEXT,info);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(i);
            }
            catch (Exception ex) {
                //h.uncaughtException(t,ex);
            }
h.uncaughtException(t,e);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(!(Thread.getDefaultUncaughtExceptionHandler() instanceof ExceptionCatcher)) Thread.setDefaultUncaughtExceptionHandler(new ExceptionCatcher(Thread.getDefaultUncaughtExceptionHandler()));
}

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}

