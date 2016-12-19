package daniel.broride;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class Data {
    private static Data mInstance;

    private User[] users = new User[20];
    private Vehicle[] vehicles = new Vehicle[20];
    private Ride[] rides = new Ride[20];
    private int countUsers,countVehicles,countRide;


    public static synchronized Data getInstance(){
        if(mInstance == null){
            mInstance = new Data();
        }

        return mInstance;
    }


    //Preenche o Data base User
    public void fillUser(Context context){
        DbHelper myDb = DbHelper.getsInstance(context.getApplicationContext());
        Cursor res = myDb.getAllUsersData();

        if(res.getCount()==0){
            //show message
            //trowh error
        }
        int i = 0;
        countUsers = 0;

        while(res.moveToNext()){
            users[i] = new User();
            users[i].setId(res.getInt(0));
            users[i].setName(res.getString(1));
            users[i].setDriver(res.getInt(2));
            users[i].setAge(res.getInt(3));
            users[i].setDebit(res.getDouble(4));
            countUsers++;
            i++;
        }
    }

    public User getUserById(int id){
        User user = null;
        for(int i = 0; i< countUsers; i++){
            if(users[i].getId()== id){
                return users[i];
            }
        }
       return user;
    }

    public User getUser(int i){
        return users[i];
    }

    public int getCountUsers(){
        return countUsers;
    }

    public ArrayList<String> getAllUsersData() {

        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i< countUsers; i++){

            labels.add(users[i].getName());
        }

        return labels;
    }

    public int[] getAllUsersId(){
        int[] array = new int[countUsers];
        for (int i = 0; i < countUsers; i++) {
            array[i] = users[i].getId();
        }
        return  array;
    }

    //----------------------------------------------------------------------------------------------


    //Preenche o Data base Vehicle
    public void fillVehicle(Context context){
        DbHelper myDb = DbHelper.getsInstance(context.getApplicationContext());
        Cursor res = myDb.getAllVehicleData();

        if(res.getCount()==0){
            //show message
            //trowh error
        }
        int i = 0;
        countVehicles = 0;

        while(res.moveToNext()){
            vehicles[i] = new Vehicle();
            vehicles[i].setId(res.getInt(0));
            vehicles[i].setModel(res.getString(1));
            vehicles[i].setName(res.getString(2));
            vehicles[i].setCapacity(res.getInt(3));
            vehicles[i].setConsumption(res.getDouble(4));
            countVehicles++;
            i++;
        }
    }

    public Vehicle getVehicleById(int id){
        Vehicle vehicle = null;
        for(int i = 0; i< countVehicles; i++){
            if(vehicles[i].getId()== id){
                return vehicles[i];
            }
        }
        return vehicle;
    }

    public Vehicle getVehicle(int i){
        return vehicles[i];
    }

    public int getCountVehicle(){
        return countVehicles;
    }

    //Metodos usados pelos Managers

    public ArrayList<String> getAllVehiclesData() {
        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i< countVehicles; i++){/////////////////////////

            labels.add(vehicles[i].getName());
        }

        return labels;
    }

    public int[] getAllVehicleId(){
        int[] array = new int[countVehicles];
        for (int i = 0; i < countVehicles; i++) {
            array[i] = vehicles[i].getId();
        }
        return  array;
    }
    //---------------------------------------------------------------

    //Preenche o Data base Ride
    public void fillRide(Context context) {
        DbHelper myDb = DbHelper.getsInstance(context.getApplicationContext());
        Cursor res = myDb.getAllVehicleData();

        if (res.getCount() == 0) {
            //show message
            //trowh error
        }
        int i = 0;
        countRide = 0;

        fillVehicle(context.getApplicationContext());

        while (res.moveToNext()) {

            rides[i] = new Ride();
            rides[i].setId(res.getInt(0));
            rides[i].setName(res.getString(1));
            rides[i].setDescription(res.getString(2));
            rides[i].insertVehicle(getVehicleById(res.getInt(3)));
            rides[i].setGasPrice(res.getDouble(4));
            rides[i].setDistance(res.getDouble(5));
            //rides[i].setUsers[]
        }
    }

    public Ride getRideById(int id){
        Ride ride = null;
        for(int i = 0; i<countRide ; i++){
            if(rides[i].getId()== id){
                return rides[i];
            }
        }
        return ride;
    }


    public Ride getRide(int i){
        return rides[i];
    }

    public int getCountRide(){
        return countVehicles;
    }

    ///Metodos usados pelos Managers
    public ArrayList<String> getAllRideData() {

        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i< countUsers; i++){

            labels.add(users[i].getName());
        }

        return labels;
    }

    public int[] getAllRideId(){
        int[] array = new int[countUsers];
        for (int i = 0; i < countUsers; i++) {
            array[i] = users[i].getId();
        }
        return  array;
    }

}
