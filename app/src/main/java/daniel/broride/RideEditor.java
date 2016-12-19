package daniel.broride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RideEditor extends AppCompatActivity  {

    Ride ride = new Ride();
    int arrayVehicleId[];
    int idVehicle;

    EditText etNome,etDescription,etGas,etDistance;
    Spinner spUser,spCar;
    TextView mode;
    CheckBox cbIsMotorista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_editor);

        //EditText
        etNome = (EditText) findViewById(R.id.etNome);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etGas = (EditText) findViewById(R.id.etGas);
        etDistance = (EditText) findViewById(R.id.etDistance);
        //Spinner
        spUser = (Spinner) findViewById(R.id.spUser);
        spCar = (Spinner) findViewById(R.id.spCar);
        //TextView
        mode = (TextView) findViewById(R.id.mode);
        //CheckBox
        cbIsMotorista = (CheckBox) findViewById(R.id.cbIsMotorista);

        loadSpinnerCar();

        spCar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idVehicle = arrayVehicleId[position];
            }
        });

    }

    private void loadSpinnerCar() {
        Data data = Data.getInstance();

        // Spinner Drop down elements
        List<String> lables = data.getAllVehiclesData();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spCar.setAdapter(dataAdapter);
    }

    public void createNewRide(){
        DbHelper myDb = DbHelper.getsInstance(this);
        Data data = Data.getInstance();

        ride.setName(etNome.getText().toString());
        ride.setDescription(etDescription.getText().toString());
        user.setAge(Integer.parseInt(editAge.getText().toString()));

        try {
            int id = myDb.insertUser(user);
            user.setId(id);
            //data.insertUser(user);
            Toast.makeText(UserEditor.this, "Data inserted", Toast.LENGTH_LONG).show();
            data.fillUser(this);
            finish();
        } catch (SqlException e) {
            e.printStackTrace();
            Toast.makeText(UserEditor.this, "Data not inserted", Toast.LENGTH_LONG).show();
        }
    }
    private void fillUsersArrayId(){
        Data data = Data.getInstance();
        arrayVehicleId = data.getAllVehicleId();
    }

}
