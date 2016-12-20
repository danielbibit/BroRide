package daniel.broride;

import android.content.Intent;
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

import static daniel.broride.MainActivity.EXTRA_MESSAGE;

public class RideEditor extends AppCompatActivity  {
    int arrayVehicleId[];
    int idVehicle;
    EditText etNome,etDescription,etGas,etDistance;
    Spinner spUser,spCar;
    TextView mode;
    CheckBox cbIsMotorista;
    Button btnAction, btnDelete;
    private String message;
    private Ride ride = new Ride();
    private Data data = Data.getInstance();
    private int id;
    private int arrayUsersId[];
    private int idUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_editor);

        Intent intent = getIntent();
        message = intent.getStringExtra(EXTRA_MESSAGE);

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

        btnAction = (Button) findViewById(R.id.btnAction);
        btnDelete  = (Button) findViewById(R.id.btnDelete);

        loadSpinnerCar();
        loadSpinnerUser();
        //fillUsersArrayId();

        id = intent.getIntExtra("id", 0);

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
                mode.setText("Criar");
                btnAction.setText("Criar!");
                btnDelete.setVisibility(View.INVISIBLE);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createNewRide();
                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                    }
                });
                break;

            case "commit":
                mode.setText("Commit");
                viewMode();
                displayRides();

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nIntent = getIntent();
                        nIntent.putExtra(EXTRA_MESSAGE, "edit");
                        nIntent.putExtra("id",id);
                        startActivity(nIntent);
                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nIntent = getIntent();
                        nIntent.putExtra(EXTRA_MESSAGE, "delete");
                        nIntent.putExtra("id",id);
                        startActivity(nIntent);
                    }
                });

                break;

            case "delete":
                mode.setText("Deletar");
                btnDelete.setVisibility(View.INVISIBLE);
                btnAction.setText("Confirmar");
                viewMode();
                displayRides();

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteRide();
                    }
                });

                break;
            case "edit":
                mode.setText("Editar");
                btnDelete.setVisibility(View.INVISIBLE);

                displayRides();
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateRide(id);
                        finish();
                    }
                });

                break;

            default:
                //never reach (i hope...)
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void loadSpinnerCar() {

        // Spinner Drop down elements
        List<String> lables = data.getAllVehiclesData();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spCar.setAdapter(dataAdapter);

        arrayVehicleId = data.getAllVehicleId();
    }

    private void loadSpinnerUser(){
        List<String> labels = data.getAllUsersData();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spUser.setAdapter(dataAdapter);

        arrayUsersId = data.getAllUsersId();
    }

    private void viewMode(){
        etNome.setFocusable(false);
        etDescription.setFocusable(false);
        etDistance.setFocusable(false);
        etGas.setFocusable(false);
        spCar.setFocusable(false);
        spUser.setFocusable(false);
        cbIsMotorista.setClickable(false);
    }

    private void displayRides(){
        ride = data.getRideById(id);
        etNome.setText(ride.getName());
        etDescription.setText(ride.getDescription());
        etGas.setText(String.valueOf(ride.getGasPrice()));
        etDistance.setText(String.valueOf(ride.getDistance()));
        cbIsMotorista.setChecked(ride.getDriverPays() == 1);
    }

    private void createNewRide(){
        DbHelper myDb = DbHelper.getsInstance(this);

        Vehicle vehicle;
        vehicle = data.getVehicleById(idVehicle);

        ride.setName(etNome.getText().toString());
        ride.setDescription(etDescription.getText().toString());
        ride.insertVehicle(vehicle);
        ride.setDistance(Double.parseDouble(etDistance.getText().toString()));
        ride.setGasPrice(Double.parseDouble(etGas.getText().toString()));
        ride.setDriverPays(cbIsMotorista.isChecked() ? 1 : 0);

        Log.d("Log",ride.getName());
        Log.d("Log",ride.getDescription());
        Log.d("Log",""+ride.getDistance());
        Log.d("Log",""+ride.getGasPrice());

        try {
            int id = myDb.insertRide(ride);
            ride.setId(id);
            Toast.makeText(RideEditor.this, "Data inserted", Toast.LENGTH_LONG).show();
            data.fillRide(this);
            finish();
        } catch (SqlException e) {
            e.printStackTrace();
            Toast.makeText(RideEditor.this, "Data not inserted", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void updateRide(int id){
        DbHelper myDb = DbHelper.getsInstance(this);
        //Data data = Data.getInstance();

        ride.setId(id);
        ride.setName(etNome.getText().toString());
        ride.setDescription(etDescription.getText().toString());
        ride.setDistance(Double.valueOf(etDistance.getText().toString()));
        ride.setGasPrice(Double.valueOf(etGas.getText().toString()));
        ride.setDriverPays(cbIsMotorista.isChecked() ? 1:0);

        try {
            myDb.updateRide(ride);
            Toast.makeText(this, "Data atualizada!", Toast.LENGTH_LONG).show();
            data.fillRide(this);
            finish();
        } catch (SqlException e) {
            Toast.makeText(this, "ERRO: Data não atualizada!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            finish();
        }

    }

    private void deleteRide(){
        DbHelper myDb = DbHelper.getsInstance(this);
        //Data data = Data.getInstance();

        try{
            myDb.deleteRide(data.getRideById(ride.getId()));
            data.fillRide(this);
            Toast.makeText(RideEditor.this, "Data deletada", Toast.LENGTH_LONG).show();
            finish();
        }catch(SqlException e){
            e.printStackTrace();
            Toast.makeText(RideEditor.this, "ERRO: Data não deletada", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void back(View view){
        finish();
    }

}
