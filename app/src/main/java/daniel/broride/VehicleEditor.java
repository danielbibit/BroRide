package daniel.broride;

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

    String message;
    private Vehicle vehicle = new Vehicle();
    private Data data;

    EditText editNome,editConsumo,editCapacidade,editModelo;
    TextView description;
    Button btnAction,btnDelete,btnVoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_editor);

        editNome = (EditText) findViewById(R.id.editNome);
        editConsumo = (EditText) findViewById(R.id.editConsumo);
        editCapacidade = (EditText) findViewById(R.id.editCapacidade);
        editModelo = (EditText) findViewById(R.id.editModelo);
        description = (TextView) findViewById(R.id.description);

        btnAction = (Button) findViewById(R.id.btnAction);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        message = getIntent().getStringExtra(EXTRA_MESSAGE);


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
        vehicle = data.getVehicleById(id);

        //editName.setText(vehicle.getName());
       // editAge.setText(String.valueOf(vehicle.getCapacity()));
      //  isDriver.setChecked(user.getIsDriver() == 1);
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
            //data.insertUser(user);
            Toast.makeText(VehicleEditor.this, "Data inserted", Toast.LENGTH_LONG).show();
            data.fillVehicle(this);
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
            data.fillVehicle(this);
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
            data.fillVehicle(this);
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
