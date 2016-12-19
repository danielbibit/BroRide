package daniel.broride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class RideManager extends AppCompatActivity {

    ListView lista;
    int[] arrayRideId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_manager);

        lista = (ListView) findViewById(R.id.lista);
    }

    /*private void loadSpinnerData() {
        // database handler
        //DbHelper myDb = DbHelper.getsInstance(this);
        Data data = Data.getInstance();

        // Spinner Drop down elements
        List<String> lables = data.getAllRideData();


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        lista.setAdapter(dataAdapter);
    }

    private void fillUsersArrayId(){
        Data data = Data.getInstance();
       // arrayRideId = data.getAllRideId();
    }
    */
    public void back(View view){
        finish();
    }

}
