package daniel.broride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class RideEditor extends AppCompatActivity  {

    EditText etNome,etDescription,etGas,etDistance;
    Spinner spUser,spCar;
    TextView mode;
    CheckBox cbIsMotorista;

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

        loadSpinnerCar();

        switch(message){
            case "create":
                break;
            case "edit":
                break;
            case "delete":
                break;
            case "commit":
                break;
            default:
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
}
