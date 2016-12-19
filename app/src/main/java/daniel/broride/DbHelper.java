package daniel.broride;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
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


    private static final String TABLE_RIDE = "RIDE_TABLE";
    private static final String RIDE_ID = "ID";
    private static final String RIDE_NAME = "NAME";
    private static final String RIDE_DESCRIPTION = "DESCRIPTION";
    private static final String RIDE_VEHICLE = "VEHICLE";
    private static final String RIDE_GAS = "GAS";
    private static final String RIDE_DISTANCE = "DISTANCE";
    private static final String RIDE_USERS = "USERS";


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

        db.execSQL("CREATE TABLE "+TABLE_RIDE+" ("+RIDE_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"+
                RIDE_NAME+" TEXT"+ RIDE_DESCRIPTION+" TEXT"+ RIDE_VEHICLE+" INTEGER,"+RIDE_GAS+
                " REAL,"+RIDE_DISTANCE+" REAL,"+ RIDE_USERS+" TEXT");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_VEHICLE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_RIDE);
    }

    /*------------------------------------------------------------------------------------------------------------------------------*/

    public int insertUser(User user) throws SqlException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_NAME, user.getName());
        contentValues.put(USER_DRIVER, user.getIsDriver());
        contentValues.put(USER_AGE, user.getAge());
        contentValues.put(USER_DEBIT, user.getDebit());

        long result = db.insert(TABLE_USER, null, contentValues);

        if (result == -1){
            throw new SqlException();
        }else{
            return (int) result;
        }
    }

    public void updateUser(User user) throws SqlException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID, user.getId());
        contentValues.put(USER_NAME, user.getName());
        contentValues.put(USER_DRIVER, user.getIsDriver());
        contentValues.put(USER_AGE, user.getAge());
        contentValues.put(USER_DEBIT, user.getDebit());

        int result = db.update(TABLE_USER, contentValues, "ID = ?", new String[]{String.valueOf(user.getId())});

        if (result == 0){
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

    public Cursor getAllUsersData(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_USER, null);

        return res;
    }





    public int insertVehicle(Vehicle vehicle)throws SqlException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(VEHICLE_MODEL, vehicle.getModel());
        contentValues.put(VEHICLE_NAME, vehicle.getName());
        contentValues.put(VEHICLE_CAPACITY, vehicle.getCapacity());
        contentValues.put(VEHICLE_CONSUMPTION, vehicle.getConsumption());

        long result = db.insert(TABLE_VEHICLE, null, contentValues);

        if (result == -1){
            throw new SqlException();
        }else{
            return (int) result;
        }
    }

    public void updateVehicle(Vehicle vehicle) throws SqlException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(VEHICLE_MODEL, vehicle.getModel());
        contentValues.put(VEHICLE_NAME, vehicle.getName());
        contentValues.put(VEHICLE_CAPACITY, vehicle.getCapacity());
        contentValues.put(VEHICLE_CONSUMPTION, vehicle.getConsumption());

        int result = db.update(TABLE_VEHICLE, contentValues, "ID = ?",
                new String[]{String.valueOf(vehicle.getId())});

        if(result == 0){
            throw new SqlException();
        }
    }

    public void deleteVehicle(Vehicle vehicle) throws SqlException{
        SQLiteDatabase db = this.getWritableDatabase();
        String id = String.valueOf(vehicle.getId());

        int i = db.delete(TABLE_VEHICLE, "ID = ?", new String[]{id});

        if(i == 0){
            throw new SqlException();
        }
    }

    public Cursor getAllVehicleData(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_VEHICLE, null);

        return res;
    }

    public int insertRide(Ride ride) throws SqlException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RIDE_NAME, ride.getName());
        contentValues.put(RIDE_DESCRIPTION, ride.getDescription());
        contentValues.put(RIDE_VEHICLE, ride.getVehicleId());
        contentValues.put(RIDE_GAS, ride.getGasPrice());
        contentValues.put(RIDE_USERS, Arrays.toString(ride.getUsersId()));


        long result = db.insert(TABLE_RIDE, null, contentValues);

        if(result == 1){
            return (int)result;
        }else{
            throw new SqlException();
        }
    }

    public void updateRide(Ride ride){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RIDE_NAME, ride.getName());
        contentValues.put(RIDE_DESCRIPTION, ride.getDescription());
        //contentValues.put(RIDE_VEHICLE, ride.getV());
        contentValues.put(RIDE_GAS, ride.getGasPrice());
        //contentValues.put(RIDE_USERS, ride.getName());
    }

    public void deleteRide(Ride ride) throws SqlException{
        SQLiteDatabase db = this.getWritableDatabase();
        String id = String.valueOf(ride.getId());

        int i = db.delete(TABLE_VEHICLE, "ID = ?", new String[]{id});

        if(i == 0){
            throw new SqlException();
        }
    }

    public Cursor getAllRides(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_RIDE, null);
        return res;
    }
}
