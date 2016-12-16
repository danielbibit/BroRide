package daniel.broride;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "main.db";

    public static final String TABLE_USER = "USER_TABLE";
    public static final String USER_ID = "ID";
    public static final String USER_NAME = "NAME";
    public static final String USER_DRIVER = "DRIVER";
    public static final String USER_AGE = "AGE";
    public static final String USER_DEBIT = "DEBIT";

    public static final String TABLE_VEHICLE = "USER_VEHICLE";
    public static final String VEHICLE_ID = "ID";
    public static final String VEHICLE_MODEL = "MODEL";
    public static final String VEHICLE_NAME = "NAME";
    public static final String VEHICLE_CAPACITY = "CAPACITY";
    public static final String VEHICLE_CONSUMPTION = "CONSUMPTION";

    /*
    public static final String TABLE_RIDE = "USER_TABLE";
    public static final String RIDE_ID = "ID";
    public static final String RIDE_VEHICLE = "NAME";
    public static final String RIDE_GAS = "DRIVER";
    public static final String RIDE_DISTANCE = "AGE";
    */

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
