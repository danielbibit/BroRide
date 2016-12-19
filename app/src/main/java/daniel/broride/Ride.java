package daniel.broride;

public class Ride {
    private int id;
    private String name, description;
    private Vehicle vehicle;
    private User[] users = new User[10];
    private String[] usersId = new String[10];
    private int userCount;
    private Double distance, gasPrice;

    public Ride(){};

    public Ride(Vehicle vehicle){
        this.vehicle = vehicle;
        users = new User[this.vehicle.getCapacity()];
    }

    public void insertUser(User user){
        users[userCount] = new User();
        userCount++;
    }

    //Gets and Setter

    public int getVehicleId(){
        int i = vehicle.getId();
        return i;
    }

    public String[] getUsersId() {
        return usersId;
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

    public void insertVehicle (Vehicle vehicle){
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

    public Double getPricePerUser(int i){
        return getPrice()/i;
    }
}
