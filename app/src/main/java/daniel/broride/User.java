package daniel.broride;


public class User {
    private int id;
    private String name;
    private int age;
    private double debit;
    private int isDriver = 0;

    //Getters and setters
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public int getIsDriver() {
        return isDriver;
    }

    public void setDriver(int isDriver) {
        this.isDriver = isDriver;
    }
}
