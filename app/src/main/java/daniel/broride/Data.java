package daniel.broride;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Data {
    private static Data mInstance;

   // private User[] users = new User[20];
    private ArrayList<User> usersList = new ArrayList<>();
    private Vehicle[] vehicles = new Vehicle[20];
    private Ride[] rides = new Ride[20];
    private int countUsers = 0, countVehicles = 0, countRide = 0;

    private Context context;

    //private Data(Context context){this.context = context;}

    public static synchronized Data getInstance(){
        if(mInstance == null){
            mInstance = new Data();
        }

        return mInstance;
    }

    public void syncWithDb(Context context){
        context = context.getApplicationContext();
        fillUser(context);
        fillVehicle(context);
        fillRide(context);
    }

    //Preenche o Data base User
    public void fillUser(Context context){
        DbHelper myDb = DbHelper.getsInstance(context.getApplicationContext());
        Cursor res = myDb.getAllUsersData();

        if(res.getCount()==0){
            //show message
            //throw error
        }else {
            int i = 0;
            /*countUsers = 0;

            while (res.moveToNext()) {
                users[i] = new User();
                users[i].setId(res.getInt(0));
                users[i].setName(res.getString(1));
                users[i].setDriver(res.getInt(2));
                users[i].setAge(res.getInt(3));
                users[i].setDebit(res.getDouble(4));
                countUsers++;
                i++;
            }*/
            usersList.clear();
            while(res.moveToNext()){
                User user = new User();
                user.setId(res.getInt(0));
                user.setName(res.getString(1));
                user.setDriver(res.getInt(2));
                user.setAge(res.getInt(3));
                user.setDebit(res.getDouble(4));
                usersList.add(user);
            }
        }
    }

    public User getUserById(int id){
        User user = null;
        /*for(int i = 0; i< countUsers; i++){
            if(users[i].getId()== id){
                return users[i];
            }
        }*/
        for(int i = 0; i<usersList.size(); i++){
            if(usersList.get(i).getId()== id){
                return usersList.get(i);
            }
        }
       return user;
    }

    public ArrayList<String> getUsersArrayList() {

        ArrayList<String> labels = new ArrayList<String>();

        //for (int i = 0; i< countUsers; i++){
        for (int i = 0; i< usersList.size(); i++){

            //labels.add(users[i].getName());
            labels.add(usersList.get(i).getName());
        }

        return labels;
    }

    public String[] getUsersArrayString(){
        String[] array = new String[/*countUsers*/usersList.size()];

        //for(int i=0; i<countUsers; i++){
        for(int i=0; i<usersList.size(); i++){
            //array[i] = users[i].getName();
            array[i] = usersList.get(i).getName();
        }

        return array;
    }

    public int[] getAllUsersId(){
        int[] array = new int[/*countUsers*/usersList.size()];
        //for (int i = 0; i < countUsers; i++) {
        for(int i=0; i<usersList.size(); i++){
            //array[i] = users[i].getId();
            array[i] = usersList.get(i).getId();
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

    //Metodos usados pelos Managers

    public ArrayList<String> getAllVehiclesData() {
        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i< countVehicles; i++){/////////////////////////

            labels.add(vehicles[i].getName());
        }

        return labels;
    }

    public int[] getAllVehiclesId(){
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
        Cursor res = myDb.getAllRides();

        if (res.getCount() == 0) {
        }

        int i = 0;
        countRide = 0;

        fillVehicle(context.getApplicationContext());
        fillUser(context.getApplicationContext());

        while (res.moveToNext()) {

            rides[i] = new Ride();
            rides[i].setId(res.getInt(0));
            rides[i].setName(res.getString(1));
            rides[i].setDescription(res.getString(2));
            rides[i].insertVehicle(getVehicleById(res.getInt(3)));
            rides[i].setGasPrice(res.getDouble(4));
            rides[i].setDistance(res.getDouble(5));
            rides[i].setDriverPays(res.getInt(7));

            String[] usersFromDb = Utils.StringToArray(res.getString(6));
            Log.d("Peguei",res.getString(6));
            Log.d("Pegui2", usersFromDb[0]);
            Log.d("Pegui2", usersFromDb[1]);
            Log.d("Pegui2", usersFromDb[2]);
            Log.d("Pegui2", usersFromDb[3]);
            for(int j=0; j<usersFromDb.length; j++){
                Log.d("Fiz",""+j);
                if(!(usersFromDb[j].equals("") || usersFromDb[j].equals("0") || usersFromDb[j].equals("null"))){
                    Log.d("Fiz1",usersFromDb[j]+j);
                    rides[i].insertUser(getUserById( Integer.parseInt(usersFromDb[j]) ));
                }
            }

            countRide++;
            i++;
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

    ///Metodos usados pelos Managers
    public ArrayList<String> getAllRideData() {

        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i< countRide; i++){
            labels.add(rides[i].getName()+" : "+rides[i].getDescription());
        }

        return labels;
    }

    public int[] getAllRideId(){
        int[] array = new int[countRide];
        for (int i = 0; i < countRide; i++) {
            array[i] = rides[i].getId();
        }
        return  array;
    }

}
