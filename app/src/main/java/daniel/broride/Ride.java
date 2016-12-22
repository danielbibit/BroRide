package daniel.broride;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class Ride {
    private int id;
    private String name, description;
    private Vehicle vehicle;

    private ArrayList<User> usersList = new ArrayList<>();
    private ArrayList<String> usersIds = new ArrayList<>();

    private Double distance, gasPrice;
    private int driverPays;
    private double totalValue;

    public Ride(){
    }

    public boolean userExists(User user){
        if(usersList.contains(user)){
            return true;
        }else{
            return false;
        }
    }

    public void commitRide(ArrayList<Integer> ids, boolean mode, Context context){
        Data data = Data.getInstance();
        DbHelper myDb = DbHelper.getsInstance(context.getApplicationContext());

        totalValue = (distance/vehicle.getConsumption())*gasPrice*2.0;
        double valuePerUser;

        //True = user Pays
        if(mode){
            valuePerUser = totalValue/(ids.size()+1);
            Log.d("User set",usersList.get(0).getName());
            Log.d("User before debit: ", "" + usersList.get(0).getDebit());
            usersList.get(0).setDebit(usersList.get(0).getDebit()+valuePerUser);
            Log.d("User after debit: ", "" + usersList.get(0).getDebit());

            try {
                myDb.updateUser(usersList.get(0));
            } catch (SqlException e) {
                e.printStackTrace();
            }

            //Charge from driver
            for (int i = 0; i<ids.size(); i++){
                User user;
                user = data.getUserById(ids.get(i));

                Log.d("User set",user.getName());
                Log.d("User before debit: ", "" + user.getDebit());
                user.setDebit(user.getDebit()+valuePerUser);
                Log.d("User after debit: ", "" + user.getDebit());

                try {
                    myDb.updateUser(user);
                } catch (SqlException e) {
                    e.printStackTrace();
                }
            }

            return;
        }else{
            valuePerUser = totalValue/ids.size();

            for(int i = 0; i<ids.size(); i++){
                User user;
                user = data.getUserById(ids.get(i));

                Log.d("User set",user.getName());
                Log.d("User before debit: ", "" + user.getDebit());
                user.setDebit(user.getDebit()+valuePerUser);
                Log.d("User after debit: ", "" + user.getDebit());

                user.setDebit(user.getDebit()+valuePerUser);

                try {
                    myDb.updateUser(user);
                } catch (SqlException e) {
                    e.printStackTrace();
                }
            }

            return;
        }
    }
    //Gets and Setter

    public void insertUser(User user){
        usersList.add(user);
    }

    public ArrayList<String> getUsersIdsList(){
        usersIds.clear();
        for(int i=0; i<usersList.size(); i++){
            usersIds.add(String.valueOf(usersList.get(i).getId()));
        }

        return usersIds;
    }

    public User getUser(int i){
        return usersList.get(i);
    }

    public String[] getUsersArrayString(){
        String[] array = new String[usersList.size()];

        for(int i=0; i<usersList.size(); i++){
            array[i] = usersList.get(i).getName();
        }

        return array;
    }

    public String[] getUsersArrayStringWithoutDriver(){
        String[] array = new String[usersList.size()-1];

        for(int i=1; i<usersList.size(); i++){
            array[i-1] = usersList.get(i).getName();
        }

        return array;
    }

    public int[] getUsersIdWithDriver(){
        int[] ids = new int[usersList.size()];
        for(int i=0; i<usersList.size(); i++){
             ids[i] = usersList.get(i).getId();
        }
        return ids;
    }

    public int[] getUsersIdWithOutDriver(){
        int[] ids = new int[usersList.size()];
        for(int i=1; i<usersList.size(); i++){
            ids[i-1] = usersList.get(i).getId();
        }
        return ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    public Double getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(Double gasPrice) {
        this.gasPrice = gasPrice;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getPrice(){
        return gasPrice*distance;
    }

    public int getDriverPays() {
        return driverPays;
    }

    public void setDriverPays(int driverPays) {
        this.driverPays = driverPays;
    }
}
