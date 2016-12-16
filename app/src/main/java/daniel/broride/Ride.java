package daniel.broride;

public class Ride {
    private Vehicle vehicle;
    private User[] users;
    private Double gasPrice;
    private Double distance;

    public Ride(){};

    public Ride(Vehicle vehicle){
        this.vehicle = vehicle;
        users = new User[this.vehicle.getCapacity()];
    }
}
