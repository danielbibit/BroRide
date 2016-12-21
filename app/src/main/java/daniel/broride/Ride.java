package daniel.broride;

import java.util.ArrayList;

public class Ride {
    private int id;
    private String name, description;
    private Vehicle vehicle;

    //private User[] users = new User[10];
    private ArrayList<User> usersList = new ArrayList<User>();
    private String[] usersId = new String[20];
    private int userCount = 0;

    private Double distance, gasPrice;
    private int driverPays;

    public Ride(){
        for(int i=0; i<10; i++){
            usersId[i] = new String();
            usersId[i] = "0";
        }
    }

    public boolean userExists(User user){
        if(usersList.contains(user)){
            return true;
        }else{
            return false;
        }
    }

    //Gets and Setter

    public void insertUser(User user){
        /*users[userCount] = new User();
        users[userCount] = user;
        userCount++;*/
        usersList.add(user);
    }

    public String[] getUsersId() {
        for(int i=0; i</*userCount*/usersList.size(); i++){
            //usersId[i] = String.valueOf(users[i].getId());
            usersId[i] = String.valueOf(usersList.get(i).getId());
        }
        return usersId;
    }

    public int getUsersId(int n){
        getUsersId();
        return Integer.valueOf(usersId[n]);
    }

    public User getUser(int i){
        //return users[i];
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
    /*public User[] getUsers() {
        return users;
    }*/

    /*public ArrayList<String> getUsersLabels(){
        ArrayList<String> list = new ArrayList<String>();
        for(int i=0; i<userCount; i++){
            list.add(users[i].getName());
        }

        return list;
    }*/

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


    public void commitRide(ArrayList<Integer> ids){
        //Calcular quanto cada um deve pagar
        //Levar em consideração driverPays
        //Debitar em cada Usuario
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
