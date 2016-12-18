package daniel.broride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class VehicleEditor extends AppCompatActivity {

    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_editor);

        switch (message){
            case "create":
                break;
            case "display":
                break;
            case "edit":
                break;
            case "delete":
                break;
            default:
                //never reach
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void displayData(int id){

    }

    public void setViewMode(){

    }

    public void createNewVehicle(Vehicle vehicle){

    }

    public void back(View view){
        finish();
    }
}
