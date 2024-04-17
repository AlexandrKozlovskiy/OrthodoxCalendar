package oleksandr.kotyuk.orthodoxcalendar.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    static boolean update_flag = false;
    static final String TAG = "myLogs";
    private final static String[] DB_NAME_ZIP_MAS = {"create1_1.zip",
            "create1_2.zip",
            "create1_3.zip",
            "create1_3_1.zip",
            "create1_3_2.zip",
            "create1_3_3.zip",
            "create1_3_4.zip",
            "create1_3_5.zip",
            "create1_3_6.zip",
            "create1_3_7.zip",
            "create1_3_8.zip",
            "create1_3_9.zip",
            "create1_3_10.zip",
            "create1_3_11.zip",
            "create1_3_12.zip",
            "create1_3_13.zip",
            "create1_3_14.zip",
            "create1_3_15.zip",
            "create2_1.zip",
            "create2_2.zip",
            "create2_3.zip",
            "create2_4.zip",
            "create2_5.zip",
            "create3_0_1.zip",
            "create3_0_2.zip",
            "create3_0_3.zip",
            "create3_1_1.zip",
            "create3_1_2.zip",
            "create3_1_3.zip",
            "create3_1_4.zip",
            "create3_1_5.zip",
            "create3_1_6.zip",
            "create3_1_7.zip",
            "create3_1_8.zip",
            "create3_1_9.zip",
            "create3_1_10.zip",
            "create3_1_11.zip",
            "create3_1_12.zip",
            "create3_1_13.zip",
            "create3_1_14.zip",
            "create3_1_15.zip",
            "create3_1_16.zip",
            "create3_1_17.zip"};

    //Путь к папке с базами на устройстве
    public static String DB_PATH;

    // db name for this application
    private static final String DATABASE_NAME = "calendar.db";

    // Номер версии этой БД
    public static final int DATABASE_VERSION = 98;
    public static String DBVersion = "db_version";
    private static DatabaseHelper dbHelper = null;

    private static SQLiteDatabase db = null;

    private final Context db_context;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db_context = context;
        // storing the object of this class to dbHelper
        dbHelper = this;

        String packageName = context.getPackageName();
        DB_PATH = String.format("//data//data//%s//databases//", packageName);

        Log.d(TAG, "DB DatabaseHelper()");
    }

    public static byte shouldUpdateDb(SharedPreferences sp) {
        int num = sp.getInt(DBVersion, 0);
        return num == 0 ? (byte) -1 : num != DATABASE_VERSION ? (byte) 0 : (byte) 1;
    }


    public static void saveDBVersion(SharedPreferences sp) {
        if (sp.getInt(DBVersion, 0) != DATABASE_VERSION)
            sp.edit().putInt(DBVersion, DATABASE_VERSION).commit();
    }

    public static synchronized DatabaseHelper getInstance(Context context, SharedPreferences sp) {
        Log.d(TAG, "DatabaseHelper getInstance()");
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context);
            openConnecion();
            if (sp != null) saveDBVersion(sp);
        }

        Log.d(TAG, "DatabaseHelper getInstance() SLEEP!!!");

        return dbHelper;
    }

    public static DatabaseHelper getInstance(Context context) {
        return getInstance(context, null);
    }

    // будет вызван только один раз, когда Синглтон создается
    private static void openConnecion() {
        Log.d(TAG, "openConnecion()");
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
    }

    // не забыть вызвать метод:
    // DatabaseHelper.getInstance.closeConnecion() когда программа закрывается
    public synchronized void closeConnecion() {
        Log.d(TAG, "closeConnecion()");
        if (dbHelper != null) {
            dbHelper.close();
            db.close();
            dbHelper = null;
            db = null;
        }
    }

    private void executeScripts(SQLiteDatabase database) {
        for (int i = 0; i < DB_NAME_ZIP_MAS.length; i++) {
            executeSQLScriptZIP(database, "db/" + DB_NAME_ZIP_MAS[i]);
            Log.d("myLogs", "executeSQLScrptZIPMAS-" + i);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // DBTable.onCreate(database);
        Log.d(TAG, "DB onCreate()");
        //executeSQLScriptZIP(database, DB_NAME_ZIP);
        executeScripts(database);
    }

    // Метод вызывается во время обновления базы данных ,
    // Например, если увеличить версию базы данных

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        Log.d(TAG, "DB onUpgrade()-oldVersion=" + oldVersion);
        //Toast.makeText(db_context, "Helper", Toast.LENGTH_SHORT).show();
        //executeUpdateSQLScriptZIP(database, DB_NAME_ZIP, oldVersion);
        update_flag = true;

        if (oldVersion > 1 && oldVersion < 17) {
            database.execSQL("DROP TABLE prayers;");//old
        }
        if (oldVersion > 4) {
            database.execSQL("DROP TABLE think_feofan;");
        }
        if (oldVersion > 10) {
            database.execSQL("DROP TABLE bible;");
            database.execSQL("DROP TABLE bible_book;");
        }
        if (oldVersion > 11) {
            database.execSQL("DROP TABLE termin_list;");
            database.execSQL("DROP TABLE termin_description;");
        }
        if (oldVersion > 12) {
            database.execSQL("DROP TABLE psaltur;");
            database.execSQL("DROP TABLE psaltur_group;");
        }
        if (oldVersion > 15) {
            database.execSQL("DROP TABLE description_holiday;");
        }
        if (oldVersion > 16) {
            database.execSQL("DROP TABLE description_other;");
        }
        if (oldVersion > 16 && oldVersion < 36) {
            database.execSQL("DROP TABLE prayers_ru;");//old
            database.execSQL("DROP TABLE prayers_cs;");//old
        }
        if (oldVersion > 22) {
            database.execSQL("DROP TABLE tropari_kondaki_day_sedmits;");
        }
        if (oldVersion > 29) {
            database.execSQL("DROP TABLE citations;");
            database.execSQL("DROP TABLE data_calendar_leap_year;");
        }
        if (oldVersion > 35) {
            database.execSQL("DROP TABLE prayers_cs_ak;");
            database.execSQL("DROP TABLE prayers_cs_ak_group;");
            database.execSQL("DROP TABLE prayers_cs_pr;");
            database.execSQL("DROP TABLE prayers_cs_pr_group;");
            database.execSQL("DROP TABLE prayers_ru_ak;");
            database.execSQL("DROP TABLE prayers_ru_ak_group;");
            database.execSQL("DROP TABLE prayers_ru_pr;");
            database.execSQL("DROP TABLE prayers_ru_pr_group;");
        }
        if (oldVersion > 42) {
            database.execSQL("DROP TABLE tropari_kondaki_holiday_multi;");
            database.execSQL("DROP TABLE tropari_kondaki_holiday_one;");
        }
        if (oldVersion > 53) {
            database.execSQL("DROP TABLE prayers_day_month;");
            database.execSQL("DROP TABLE prayers_day_sedmits;");
        }
        if (oldVersion > 64) {
            database.execSQL("DROP TABLE psaltur_description;");
        }
        if (oldVersion > 66) {
            database.execSQL("DROP TABLE holiday_lessons;");
        }
        database.execSQL("DROP TABLE big_unmovable_holiday;");
        database.execSQL("DROP TABLE data_calendar;");
        database.execSQL("DROP TABLE days_souls_years;");
        database.execSQL("DROP TABLE easter_years;");
        database.execSQL("DROP TABLE gr_unmovable_holiday;");
        database.execSQL("DROP TABLE holiday_years;");
        database.execSQL("DROP TABLE multiday_post_years;");
        database.execSQL("DROP TABLE oneday_post_years;");
        database.execSQL("DROP TABLE sedmitsa_post_years;");
        database.execSQL("DROP TABLE unmovable_holiday;");
        onCreate(database);
        update_flag = false;
    }

    // открытие заархивированого файла create.zip
    private void executeSQLScriptZIP(SQLiteDatabase database, String dbname) {
        InputStream zipFileStream = null;
        ZipInputStream zis = null;
        ByteArrayOutputStream outputStream = null;
        try {
            zipFileStream = this.db_context.getAssets().open(dbname);
            zis = new ZipInputStream(zipFileStream);
            outputStream = new ByteArrayOutputStream();

            @SuppressWarnings("unused")
            ZipEntry ze = null;
            while ((ze = zis.getNextEntry()) != null) {
                byte[] buf = new byte[1024];
                int count;
                while ((count = zis.read(buf)) != -1) {
                    outputStream.write(buf, 0, count);
                }

                database.beginTransaction();
                String[] createScript = outputStream.toString().split("\\);;");
                for (int i = 0; i < createScript.length; i++) {
                    String sqlStatement = createScript[i].trim();
                    if (sqlStatement.length() > 0) {
                        database.execSQL(sqlStatement + ");");
                    }
                }
                database.setTransactionSuccessful();
            }
        } catch (IOException e) {
            Log.d("myLogs", "Zip InputStream-" + e.toString());
        } finally {
            try {
                zis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                outputStream.flush();
                outputStream.close();
                zipFileStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            database.endTransaction();
        }
    }


    //Проверка существования базы данных
    @SuppressWarnings("unused")
    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try {
            String path = DB_PATH + DATABASE_NAME;
            checkDb = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Error while checking db");
        }
        //Андроид не любит утечки ресурсов, все должно закрываться
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null ? true : false;
    }


    public Cursor executeQuery(String sql) {
        Cursor mCursor = db.rawQuery(sql, null);
        return mCursor;
    }


    public boolean executeDMLQuery(String sql) {
        try {
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

}
