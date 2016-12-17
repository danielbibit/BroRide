package daniel.broride;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper mInstance;
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
    public static synchronized DbHelper getsInstance(Context context){
        if(mInstance == null){
            mInstance = new DbHelper(context.getApplicationContext());
        }

        return mInstance;
    }

    private DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_USER+" ("+USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                USER_NAME+" TEXT,"+USER_DRIVER+" TEXT,"+USER_AGE+" TEXT,"+USER_DEBIT+" TEXT)");

        db.execSQL("CREATE TABLE "+TABLE_VEHICLE+" ("+VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                VEHICLE_MODEL+" TEXT,"+VEHICLE_NAME+" TEXT,"+VEHICLE_CAPACITY+" TEXT,"+VEHICLE_CONSUMPTION+" TEXT)");
        /*
        db.execSQL("CREATE TABLE "+TABLE_USER+" ("+USER_ID + "INTEGER PRIMARY KEY AUINCREMENT,"+
                USER_NAME+" TEXT,"+USER_DRIVER+" TEXT,"+USER_AGE+" TEXT,"+USER_DEBIT+" TEXT,");
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_VEHICLE);
    }

    public void insertUser(User user) throws SqlException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_NAME, user.getName());
        contentValues.put(USER_DRIVER, user.getName());
        contentValues.put(USER_AGE, user.getAge());
        contentValues.put(USER_DEBIT, user.getDebit());

        long result = db.insert(TABLE_USER, null, contentValues);

        if (result == -1){
            throw new SqlException();
        }
    }

    public void insertVehicle(Vehicle vehicle)throws SqlException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(VEHICLE_MODEL, vehicle.getModel());
        contentValues.put(VEHICLE_NAME, vehicle.getName());
        contentValues.put(VEHICLE_CAPACITY, vehicle.getCapacity());
        contentValues.put(VEHICLE_CONSUMPTION, vehicle.getConsumption());

        long result = db.insert(TABLE_VEHICLE, null, contentValues);

        if (result == -1){
            throw new SqlException();
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_USER, null);

        return res;
    }
}
