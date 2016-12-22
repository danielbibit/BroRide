package daniel.broride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static daniel.broride.MainActivity.EXTRA_MESSAGE;

public class VehicleManager extends AppCompatActivity {

    private ListView listView;
    private int[] arrayVehicleId;
    private Button btnNewVehicle;
    private Data data = Data.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_manager);

        listView = (ListView) findViewById(R.id.ListaVehicle);

        btnNewVehicle = (Button) findViewById(R.id.btn_new);

        loadSpinnerData();
        fillVehicleArrayId();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(),"item clicado "+position,Toast.LENGTH_SHORT).show();
                openVehicleEditor(arrayVehicleId[position]);
            }
        });

        btnNewVehicle.setOnClickListener(new View.OnClickListener() {
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
        // Spinner Drop down elements
        List<String> labels = data.getVehiclesLabelsList();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels){
            @Override
            public View getView(int  position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);

                return view;
            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        listView.setAdapter(dataAdapter);
    }

    private void fillVehicleArrayId(){
        Data data = Data.getInstance();
        arrayVehicleId = data.getAllVehiclesId();
    }

    //Fixme
    private void openVehicleEditor(){
        Intent intent = new Intent(VehicleManager.this, VehicleEditor.class);
        intent.putExtra(EXTRA_MESSAGE, "create");
        startActivity(intent);
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

    public void back(View view){
        finish();
    }


}
