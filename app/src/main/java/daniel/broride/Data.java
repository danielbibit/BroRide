package daniel.broride;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public class Data {
    private static Data mInstance;

    private ArrayList<User> usersList = new ArrayList<>();
    private ArrayList<Vehicle> vehicleList = new ArrayList<>();
    private ArrayList<Ride> rideList = new ArrayList<>();

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

    //Verify if a given user is in one ride
    public boolean verifyUserConflict(User user){
        for(int i=0; i<rideList.size(); i++){
            if(rideList.get(i).userExists(user)){
                return true;
            }
        }
        return false;
    }

    //Verify id a given Vehicle is registered on any ride
    public boolean verifyVehicleConflict(Vehicle vehicle) {
        for(int i=0; i<rideList.size(); i++){
            if(rideList.get(i).getVehicle() == vehicle){
                return true;
            }
        }
        return true;
    }

    //Preenche o Data base User
    public void fillUser(Context context){
        DbHelper myDb = DbHelper.getsInstance(context.getApplicationContext());
        Cursor res = myDb.getAllUsersData();

        if(res.getCount()==0){
            //show message
            //throw error
        }else {
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

        for(int i = 0; i<usersList.size(); i++){
            if(usersList.get(i).getId()== id){
                return usersList.get(i);
            }
        }

       return user;
    }

    public ArrayList<String> getUsersArrayList() {
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i< usersList.size(); i++){
            labels.add(usersList.get(i).getName());
        }

        return labels;
    }

    public String[] getUsersArrayString(){
        String[] array = new String[usersList.size()];

        for(int i=0; i<usersList.size(); i++){
            array[i] = usersList.get(i).getName();
        }

        return array;
    }

    public int[] getAllUsersId(){
        int[] array = new int[usersList.size()];

        for(int i=0; i<usersList.size(); i++){
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
        vehicleList.clear();
        while(res.moveToNext()){
            Vehicle vehicle = new Vehicle();

            vehicle.setId(res.getInt(0));
            vehicle.setModel(res.getString(1));
            vehicle.setName(res.getString(2));
            vehicle.setCapacity(res.getInt(3));
            vehicle.setConsumption(res.getDouble(4));

            vehicleList.add(vehicle);
        }
    }

    public Vehicle getVehicleById(int id){
        Vehicle vehicle = null;
        for(int i = 0; i< vehicleList.size(); i++){

            if(vehicleList.get(i).getId()==id){
                return vehicleList.get(i);
            }
        }
        return vehicle;
    }

    //Metodos usados pelos Managers

    public ArrayList<String> getAllVehiclesData() {
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i< vehicleList.size(); i++){
            labels.add(vehicleList.get(i).getName());
        }

        return labels;
    }

    public int[] getAllVehiclesId(){
        int[] array = new int[vehicleList.size()];
        for (int i = 0; i < vehicleList.size(); i++) {
            array[i] = vehicleList.get(i).getId();
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


        fillUser(context.getApplicationContext());
        fillVehicle(context.getApplicationContext());

        rideList.clear();
        while (res.moveToNext()){
            Ride ride = new Ride();

            ride.setId(res.getInt(0));
            ride.setName(res.getString(1));
            ride.setDescription(res.getString(2));
            ride.setVehicle(getVehicleById(res.getInt(3)));
            ride.setGasPrice(res.getDouble(4));
            ride.setDistance(res.getDouble(5));
            ride.setDriverPays(res.getInt(7));

            String[] usersFromDb = Utils.StringToArray(res.getString(6));

            Log.d("Peguei",res.getString(6));

            for(int i=0; i<usersFromDb.length; i++){
                if(!(usersFromDb[i].equals("") || usersFromDb[i].equals("0")
                        || usersFromDb[i].equals("null"))){
                    ride.insertUser(getUserById( Integer.parseInt(usersFromDb[i]) ));
                }
            }

            rideList.add(ride);
        }

    }

    public Ride getRideById(int id){
        Ride ride = null;

        for(int i = 0; i<rideList.size(); i++){
            if(rideList.get(i).getId()==id){
                return rideList.get(i);
            }
        }
        return ride;
    }

    ///Metodos usados pelos Managers
    public ArrayList<String> getAllRideData() {
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i< rideList.size(); i++){
            labels.add(rideList.get(i).getName()+" : "+rideList.get(i).getDescription());
        }

        return labels;
    }

    public int[] getAllRideId(){
        int[] array = new int [rideList.size()];

        for (int i = 0; i<rideList.size(); i++){
            array[i] = rideList.get(i).getId();
        }

        return  array;
    }

}
