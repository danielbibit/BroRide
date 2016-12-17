package daniel.broride;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper mInstance;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "main.db";

    private static final String TABLE_USER = "USER_TABLE";
    private static final String USER_ID = "ID";
    private static final String USER_NAME = "NAME";
    private static final String USER_DRIVER = "DRIVER";
    private static final String USER_AGE = "AGE";
    private static final String USER_DEBIT = "DEBIT";

    private static final String TABLE_VEHICLE = "USER_VEHICLE";
    private static final String VEHICLE_ID = "ID";
    private static final String VEHICLE_MODEL = "MODEL";
    private static final String VEHICLE_NAME = "NAME";
    private static final String VEHICLE_CAPACITY = "CAPACITY";
    private static final String VEHICLE_CONSUMPTION = "CONSUMPTION";

    /*
    public static final String TABLE_RIDE = "RIDE_TABLE";
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
                USER_NAME+" TEXT,"+USER_DRIVER+" INTEGER,"+USER_AGE+" INTEGER,"+USER_DEBIT+" REAL)");

        db.execSQL("CREATE TABLE "+TABLE_VEHICLE+" ("+VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                VEHICLE_MODEL+" TEXT,"+VEHICLE_NAME+" TEXT,"+VEHICLE_CAPACITY+" INTEGER,"+VEHICLE_CONSUMPTION+" REAL)");
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

    /*------------------------------------------------------------------------------------------------------------------------------*/

    public void insertUser(User user) throws SqlException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_NAME, user.getName());
        contentValues.put(USER_DRIVER, user.getIsDriver());
        contentValues.put(USER_AGE, user.getAge());
        contentValues.put(USER_DEBIT, user.getDebit());

        long result = db.insert(TABLE_USER, null, contentValues);

        if (result == -1){
            throw new SqlException();
        }
    }

    public void deleteUser(User user) throws SqlException{
        SQLiteDatabase db = this.getWritableDatabase();
        String id = String.valueOf(user.getId());

        int i = db.delete(TABLE_USER, "ID = ?", new String[]{id});

        if(i == 0){
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

    public void deleteVehicle(int id){

    }


    public void deleteEverything(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.rawQuery("DELETE FROM "+TABLE_USER, null);
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_USER, null);

        return res;
    }

    // Creater a String array object and return
    public ArrayList<String> getAllLabels(){
        ArrayList<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

}
