package daniel.broride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static daniel.broride.MainActivity.EXTRA_MESSAGE;

public class VehicleEditor extends AppCompatActivity {

    private String message;
    private EditText editNome,editConsumo,editCapacidade,editModelo;
    private TextView description;
    private Button btnAction,btnDelete;
    private Vehicle vehicle = new Vehicle();
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_editor);

        final Intent intent = getIntent();
        message = getIntent().getStringExtra(EXTRA_MESSAGE);

        editNome = (EditText) findViewById(R.id.editNome);
        editConsumo = (EditText) findViewById(R.id.editConsumo);
        editCapacidade = (EditText) findViewById(R.id.editCapacidade);
        editModelo = (EditText) findViewById(R.id.editModelo);
        description = (TextView) findViewById(R.id.description);

        btnAction = (Button) findViewById(R.id.btnAction);
        btnDelete = (Button) findViewById(R.id.button_delete);
        //btnVoltar = (Button) findViewById(R.id.btnVoltar);

        data = Data.getInstance();
        data.syncWithDb(this);

        final int id = intent.getIntExtra("id", 0);

        switch (message){
            case "create":
                description.setText("Criar");
                btnAction.setText("Criar!");
                btnDelete.setVisibility(View.INVISIBLE);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createNewVehicle();
                    }
                });
                break;

            case "display":
                setViewMode(0);
                description.setText("Visualizar");
                btnAction.setText("Editar");
                btnDelete.setVisibility(View.INVISIBLE);

                displayData(id);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nIntent = getIntent();
                        nIntent.putExtra(EXTRA_MESSAGE, "edit");
                        nIntent.putExtra("id",id);
                        startActivity(nIntent);
                    }
                });
                break;

            case "edit":
                description.setText("Editar");
                btnAction.setText("Confirmar");

                displayData(id);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateVehicle(vehicle);
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
                setViewMode(0);
                description.setText("Deletar");
                btnDelete.setVisibility(View.INVISIBLE);
                btnAction.setText("Confirmar");

                displayData(id);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    if(data.verifyVehicleConflict(data.getVehicleById(id))){
                        Toast.makeText(VehicleEditor.this, "O veiculo esta sendo utilizado ",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        deleteVehicle(id);
                    }
                    }
                });
                break;

            default:
                //never reach
                break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void displayData(int id){
        vehicle = data.getVehicleById(id);

        editNome.setText(vehicle.getName());
        editConsumo.setText(String.valueOf(vehicle.getConsumption()));
        editCapacidade.setText(String.valueOf(vehicle.getCapacity()));
        editModelo.setText(vehicle.getModel());
    }

    public void setViewMode(int mode){

        if(mode==0) {
            editNome.setFocusable(false);
            editConsumo.setFocusable(false);
            editCapacidade.setFocusable(false);
            editModelo.setFocusable(false);

            editNome.setClickable(false);
            editConsumo.setClickable(false);
            editCapacidade.setClickable(false);
            editModelo.setClickable(false);

        }
    }

    public void createNewVehicle(){
        DbHelper myDb = DbHelper.getsInstance(this);
        Data data = Data.getInstance();

        vehicle.setName(editNome.getText().toString());
        vehicle.setConsumption(Double.parseDouble(editConsumo.getText().toString()));
        vehicle.setCapacity(Integer.parseInt(editCapacidade.getText().toString()));
        vehicle.setModel(editModelo.getText().toString());


        try {
            int id = myDb.insertVehicle(vehicle);
            vehicle.setId(id);
            Toast.makeText(VehicleEditor.this, "Data inserted", Toast.LENGTH_LONG).show();
            data.fillVehiclesList(this);
            finish();
        } catch (SqlException e) {
            e.printStackTrace();
            Toast.makeText(VehicleEditor.this, "Data not inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void updateVehicle(Vehicle vehicle){
        DbHelper myDb = DbHelper.getsInstance(this);
        Data data = Data.getInstance();

        vehicle.setName(editNome.getText().toString());
        vehicle.setConsumption(Double.parseDouble(editConsumo.getText().toString()));
        vehicle.setCapacity(Integer.parseInt(editCapacidade.getText().toString()));
        vehicle.setModel(editModelo.getText().toString());

        try {
            myDb.updateVehicle(vehicle);
            data.fillVehiclesList(this);
            Toast.makeText(getBaseContext(),"Vehicle Atualizado",Toast.LENGTH_SHORT).show();
            finish();
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void deleteVehicle(int id){
        DbHelper myDb = DbHelper.getsInstance(this);
        Data data = Data.getInstance();

        try {
            myDb.deleteVehicle(data.getVehicleById(id));
            data.fillVehiclesList(this);
            Toast.makeText(getBaseContext(),"Veiculo Deletado",Toast.LENGTH_SHORT).show();
            finish();
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void back(View view){
        finish();
    }
}
