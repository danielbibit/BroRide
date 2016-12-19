package daniel.broride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    Button buttonAction;

    String message;

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

        buttonAction = (Button) findViewById(R.id.btnAction);

        loadSpinnerCar();
        fillUsersArrayId();

        spCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idVehicle = arrayVehicleId[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                idVehicle = arrayVehicleId[0];
            }
        });

        switch (message){
            case "create":
                buttonAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createNewRide();
                    }
                });
                break;

            default:
                //never reach
                break;
        }
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
        Vehicle vehicle;
        vehicle = data.getVehicleById(idVehicle);

        ride.setName(etNome.getText().toString());
        ride.setDescription(etDescription.getText().toString());
        ride.insertVehicle(vehicle);
        ride.setDistance(Double.parseDouble(etDistance.getText().toString()));
        ride.setGasPrice(Double.parseDouble(etGas.getText().toString()));

        try {
            int id = myDb.insertRide(ride);
            ride.setId(id);
            //data.insertUser(user);
            Toast.makeText(RideEditor.this, "Data inserted", Toast.LENGTH_LONG).show();
            data.fillRide(this);
            finish();
        } catch (SqlException e) {
            e.printStackTrace();
            Toast.makeText(RideEditor.this, "Data not inserted", Toast.LENGTH_LONG).show();
        }
    }
    private void fillUsersArrayId(){
        Data data = Data.getInstance();
        arrayVehicleId = data.getAllVehicleId();
    }

}
