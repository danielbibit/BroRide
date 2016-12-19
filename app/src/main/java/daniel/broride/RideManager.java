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

public class RideManager extends AppCompatActivity {

    ListView lista;
    Button button;
    int[] arrayRideId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_manager);

        lista = (ListView) findViewById(R.id.lista);
        button = (Button) findViewById(R.id.newVehicle);

        loadSpinnerData();
        fillRideArrayId();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(),"item clicado "+position,Toast.LENGTH_SHORT).show();
                openRideEditor(arrayRideId[position]);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRideEditor(-1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSpinnerData();
        fillRideArrayId();
    }

    private void openRideEditor(int n){
        Intent intent = new Intent(this, RideEditor.class);

        if(n==-1){
            intent.putExtra(EXTRA_MESSAGE, "create");
            startActivity(intent);
        }else{
            intent.putExtra(EXTRA_MESSAGE, "commit");
            intent.putExtra("id", n);
            startActivity(intent);
        }
    }

    private void loadSpinnerData() {
        Data data = Data.getInstance();

        // Spinner Drop down elements
        List<String> labels = data.getAllRideData();


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        lista.setAdapter(dataAdapter);
    }

    private void fillRideArrayId(){
        Data data = Data.getInstance();
        arrayRideId = data.getAllRideId();
    }

    public void back(View view){
        finish();
    }

}
