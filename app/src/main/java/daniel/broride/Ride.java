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

    //Gets and Setter
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
}
