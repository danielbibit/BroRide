package daniel.broride;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.List;

import static daniel.broride.MainActivity.EXTRA_MESSAGE;

public class RideEditor extends AppCompatActivity  {
    //Multi Selection User Create
    String[] allUsersLabel;
    int[] allUsersId;
    ArrayList<Integer> selectedUsers = new ArrayList<Integer>();

    //btnSelect variables
    String[] usersLabelRide;
    int[] idUsersRide;
    ArrayList<Integer> usersSelected = new ArrayList<Integer>();

    //View objects
    EditText etNome,etDescription,etGas,etDistance;
    Spinner spUser,spCar;
    TextView mode;
    CheckBox cbIsMotorista;
    Button btnAction, btnAction2, btnSelect;

    //Spinner Vehicles
    int arrayVehicleId[];
    int idVehicle;

    //Spiner User
    private int arrayUsersId[];//Delete this
    private int idUser;

    //General User atributes for the activity
    private String message;
    private Ride ride = new Ride();
    private Data data = Data.getInstance();
    private int id; // id da ride que a lista devolve

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_editor);

        Intent intent = getIntent();
        message = intent.getStringExtra(EXTRA_MESSAGE);

        allUsersLabel = data.getUsersLabelsArray();
        allUsersId = data.getAllUsersId();

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
        btnAction2  = (Button) findViewById(R.id.button_delete);
        btnSelect = (Button) findViewById(R.id.button_commit);

        loadSpinnerCar();
        loadSpinnerUser();

        id = intent.getIntExtra("id", 0);

        switch (message){

            case "create":
                mode.setText("Criar");
                btnAction.setText("Criar!");
                btnAction2.setVisibility(View.INVISIBLE);

                setSpinnersOnClickListners();

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createNewRide();
                    }
                });

                btnAction2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                btnSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSelectUserDialog();
                    }
                });

                break;

            case "commit":
                mode.setText("Commit");
                viewMode();
                try{displayRides();
                }catch (Exception e){
                    e.printStackTrace();
                }

                ride = data.getRideById(id);

                if (cbIsMotorista.isChecked()){
                    idUsersRide = ride.getUsersIdWithOutDriver();
                    usersLabelRide = ride.getUsersArrayStringWithoutDriver();
                }else{
                    idUsersRide = ride.getUsersIdWithDriver();
                    usersLabelRide = ride.getUsersArrayString();
                }

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nIntent = getIntent();
                        nIntent.putExtra(EXTRA_MESSAGE, "edit");
                        nIntent.putExtra("id",id);
                        startActivity(nIntent);
                    }
                });

                btnSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSelectUserDialogCommit();
                    }
                });

                btnAction2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Utils.saveCache(Integer.toString(id),getApplicationContext());
                        if (cbIsMotorista.isChecked()){
                            ride.commitRide(usersSelected, true, RideEditor.this);
                            Toast.makeText(RideEditor.this, "Feito", Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            ride.commitRide(usersSelected, false, RideEditor.this);
                            Toast.makeText(RideEditor.this, "Feito", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });

                break;

            case "edit":
                mode.setText("Editar");
                btnAction2.setText("Deletar");

                displayRides();

                setSpinnersOnClickListners();

                btnSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSelectUserDialog();
                    }
                });

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateRide(id);
                        finish();
                    }
                });

                btnAction2.setOnClickListener(new View.OnClickListener() {
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
                btnAction2.setVisibility(View.INVISIBLE);
                btnAction.setText("Confirmar");
                viewMode();
                displayRides();

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.saveCache("",getApplicationContext());
                        deleteRide();
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
        List<String> labels = data.getVehiclesLabelsList();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spCar.setAdapter(dataAdapter);

        arrayVehicleId = data.getAllVehiclesId();
    }

    private void loadSpinnerUser(){
        List<String> labels = data.getUsersLabelsList();

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
        spCar.setEnabled(false);
        spUser.setEnabled(false);
        cbIsMotorista.setClickable(false);
    }

    private void displayRides(){
        ride = data.getRideById(id);
        etNome.setText(ride.getName());
        etDescription.setText(ride.getDescription());
        etGas.setText(String.valueOf(ride.getGasPrice()));
        etDistance.setText(String.valueOf(ride.getDistance()));
        cbIsMotorista.setChecked(ride.getDriverPays() == 1);

        int selectedCar = 0;

        for(int i=0; i<arrayVehicleId.length; i++){
            if(ride.getVehicle().getId()==arrayVehicleId[i]){
                selectedCar=i;
                break;
            }
        }

        spCar.setSelection(selectedCar);

        int selectedUser = 0;

        for(int i =0; i<allUsersId.length; i++){
            if(ride.getUser(0).getId() == allUsersId[i]){
                selectedUser = i;
                break;
            }
        }

        spUser.setSelection(selectedUser);
    }

    private void createNewRide(){
        DbHelper myDb = DbHelper.getsInstance(this);

        Vehicle vehicle;
        vehicle = data.getVehicleById(idVehicle);

        ride.setName(etNome.getText().toString());
        ride.setDescription(etDescription.getText().toString());
        ride.setVehicle(vehicle);
        ride.setDistance(Double.parseDouble(etDistance.getText().toString()));
        ride.setGasPrice(Double.parseDouble(etGas.getText().toString()));
        ride.setDriverPays(cbIsMotorista.isChecked() ? 1 : 0);

        ride.insertUser(data.getUserById(idUser));
        Log.d("Usuario InserindoM", data.getUserById(idUser).getName());

        for(int i=0; i<selectedUsers.size(); i++){
            if(selectedUsers.get(i) == idUser){
                selectedUsers.remove(i);
            }
        }

        for(int i : selectedUsers){
            ride.insertUser(data.getUserById(i));
            Log.d("Usuario Inserindo", data.getUserById(i).getName());
        }

        try {
            int id = myDb.insertRide(ride);
            ride.setId(id);
            Toast.makeText(RideEditor.this, "Data inserted", Toast.LENGTH_LONG).show();
            data.fillRidesList(this);
            finish();
        } catch (SqlException e) {
            e.printStackTrace();
            Toast.makeText(RideEditor.this, "Data not inserted", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void updateRide(int id){
        DbHelper myDb = DbHelper.getsInstance(this);

        Ride ride = new Ride();

        ride.setId(id);
        ride.setName(etNome.getText().toString());
        ride.setDescription(etDescription.getText().toString());
        ride.setVehicle(data.getVehicleById(idVehicle));
        ride.setDistance(Double.valueOf(etDistance.getText().toString()));
        ride.setGasPrice(Double.valueOf(etGas.getText().toString()));
        ride.setDriverPays(cbIsMotorista.isChecked() ? 1:0);

        ride.insertUser(data.getUserById(idUser));

        for(int i=0; i<selectedUsers.size(); i++){
            if(selectedUsers.get(i) == idUser){
                selectedUsers.remove(i);
            }
        }

        for(int i : selectedUsers){
            ride.insertUser(data.getUserById(i));
        }

        try {
            myDb.updateRide(ride);
            Toast.makeText(this, "Data atualizada!", Toast.LENGTH_LONG).show();
            data.fillRidesList(this);
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
            data.fillRidesList(this);
            Toast.makeText(RideEditor.this, "Data deletada", Toast.LENGTH_LONG).show();
            finish();
        }catch(SqlException e){
            e.printStackTrace();
            Toast.makeText(RideEditor.this, "ERRO: Data não deletada", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setSpinnersOnClickListners(){
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

        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idUser = arrayUsersId[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                idUser = arrayUsersId[0];
            }
        });
    }

    protected void showSelectUserDialog() {
        boolean[] checkedUsers = new boolean[arrayUsersId.length];

        int count = arrayUsersId.length;

        for(int i = 0; i < count; i++)
            checkedUsers[i] = selectedUsers.contains(allUsersId[i]);

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new
                DialogInterface.OnMultiChoiceClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked){
                    selectedUsers.add(allUsersId[which]);
                }else {
                    try {
                        selectedUsers.remove(allUsersId[which]);
                    } catch (Exception e) {

                    }
                }
            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione Usuarios");
        builder.setMultiChoiceItems(allUsersLabel, checkedUsers, coloursDialogListener);

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    protected void showSelectUserDialogCommit() {
        boolean[] checkedUsers = new boolean[idUsersRide.length];

        int count = idUsersRide.length;

        for(int i = 0; i < count; i++)
            checkedUsers[i] = usersSelected.contains(idUsersRide[i]);

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new
                DialogInterface.OnMultiChoiceClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            usersSelected.add(idUsersRide[which]);
                        }else {
                            try {
                                usersSelected.remove(idUsersRide[which]);
                            } catch (Exception e) {

                            }
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione Usuarios");
        builder.setMultiChoiceItems(usersLabelRide, checkedUsers, coloursDialogListener);

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void back(View view){
        finish();
    }

}
