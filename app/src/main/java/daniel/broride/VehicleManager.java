package daniel.broride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import static daniel.broride.MainActivity.EXTRA_MESSAGE;

public class VehicleManager extends AppCompatActivity {

    ListView lista;
    int[] arrayVehicleId;

    Button newVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_manager);

        lista = (ListView) findViewById(R.id.ListaVehicle);

        newVehicle = (Button) findViewById(R.id.newVehicle);

        loadSpinnerData();
        fillVehicleArrayId();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(),"item clicado "+position,Toast.LENGTH_SHORT).show();
                openVehicleEditor(arrayVehicleId[position]);
            }
        });

        newVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVehicleEditor();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSpinnerData();

        fillVehicleArrayId();

    }

    private void loadSpinnerData() {
        // database handler
        //DbHelper myDb = DbHelper.getsInstance(this);
        Data data = Data.getInstance();

        // Spinner Drop down elements
        List<String> lables = data.getAllVehiclesData();


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        lista.setAdapter(dataAdapter);
    }

    private void fillVehicleArrayId(){
        Data data = Data.getInstance();
        arrayVehicleId = data.getAllVehicleId();
    }

    private void openVehicleEditor(){
        Intent intent = new Intent(VehicleManager.this, VehicleEditor.class);
        intent.putExtra(EXTRA_MESSAGE, "create");
        startActivity(intent);
    }

    public void back(View view){
        finish();
    }

    private void openVehicleEditor(int n){
        Intent intent = new Intent(this, VehicleEditor.class);

        if(n==-1){
            intent.putExtra(EXTRA_MESSAGE, "create");
            startActivity(intent);
        }else{
            intent.putExtra(EXTRA_MESSAGE,"display");
            intent.putExtra("id",n);
            startActivity(intent);
        }
    }
}
