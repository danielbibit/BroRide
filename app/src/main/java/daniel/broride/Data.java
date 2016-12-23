package daniel.broride;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public class Data {
    private static Data instance;

    private ArrayList<User> usersList = new ArrayList<>();
    private ArrayList<Vehicle> vehiclesList = new ArrayList<>();
    private ArrayList<Ride> ridesList = new ArrayList<>();

    public static synchronized Data getInstance(){
        if(instance == null){
            instance = new Data();
        }

        return instance;
    }

    public void syncWithDb(Context context){
        context = context.getApplicationContext();
        fillUsersList(context);
        fillVehiclesList(context);
        fillRidesList(context);
    }

    //Verify if a given user is in one ride
    public boolean verifyUserConflict(User user){
        for(int i = 0; i< ridesList.size(); i++){
            if(ridesList.get(i).userExists(user)){
                return true;
            }
        }
        return false;
    }

    //Verify id a given Vehicle is registered on any ride
    public boolean verifyVehicleConflict(Vehicle vehicle) {
        for(int i = 0; i< ridesList.size(); i++){
            if(ridesList.get(i).getVehicle().equals(vehicle)){
                return true;
            }
        }
        return false;
    }

    //Preenche o Data base User
    public void fillUsersList(Context context){
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

    public ArrayList<String> getUsersLabelsList() {
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i< usersList.size(); i++){
            labels.add(usersList.get(i).getName());
        }

        return labels;
    }

    public String[] getUsersLabelsArray(){
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
    public void fillVehiclesList(Context context){
        DbHelper myDb = DbHelper.getsInstance(context.getApplicationContext());
        Cursor res = myDb.getAllVehicleData();

        if(res.getCount()==0){
            //show message
            //trowh error
        }
        vehiclesList.clear();
        while(res.moveToNext()){
            Vehicle vehicle = new Vehicle();

            vehicle.setId(res.getInt(0));
            vehicle.setModel(res.getString(1));
            vehicle.setName(res.getString(2));
            vehicle.setCapacity(res.getInt(3));
            vehicle.setConsumption(res.getDouble(4));

            vehiclesList.add(vehicle);
        }
    }

    public Vehicle getVehicleById(int id){
        Vehicle vehicle = null;
        for(int i = 0; i< vehiclesList.size(); i++){

            if(vehiclesList.get(i).getId()==id){
                return vehiclesList.get(i);
            }
        }
        return vehicle;
    }

    //Metodos usados pelos Managers

    public ArrayList<String> getVehiclesLabelsList() {
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i< vehiclesList.size(); i++){
            labels.add(vehiclesList.get(i).getName());
        }

        return labels;
    }

    public int[] getAllVehiclesId(){
        int[] array = new int[vehiclesList.size()];
        for (int i = 0; i < vehiclesList.size(); i++) {
            array[i] = vehiclesList.get(i).getId();
        }
        return  array;
    }

    //----------------------------------------------------------------------------------------------

    //Preenche o Data base Ride
    public void fillRidesList(Context context) {
        DbHelper myDb = DbHelper.getsInstance(context.getApplicationContext());
        Cursor res = myDb.getAllRides();

        if (res.getCount() == 0) {
        }


        fillUsersList(context.getApplicationContext());
        fillVehiclesList(context.getApplicationContext());

        ridesList.clear();
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

            ridesList.add(ride);
        }

    }

    public Ride getRideById(int id){
        Ride ride = null;

        for(int i = 0; i< ridesList.size(); i++){
            if(ridesList.get(i).getId()==id){
                return ridesList.get(i);
            }
        }
        return ride;
    }

    ///Metodos usados pelos Managers
    public ArrayList<String> getRidesLabelsList() {
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i< ridesList.size(); i++){
            labels.add(ridesList.get(i).getName()+" : "+ ridesList.get(i).getDescription());
        }

        return labels;
    }

    public int[] getAllRideId(){
        int[] array = new int [ridesList.size()];

        for (int i = 0; i< ridesList.size(); i++){
            array[i] = ridesList.get(i).getId();
        }

        return  array;
    }

}
