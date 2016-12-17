package daniel.broride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserManage extends AppCompatActivity {

    ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        lista = (ListView) findViewById(R.id.ListaUsers) ;

        loadSpinnerData();


    }

    private void loadSpinnerData() {
        // database handler
        DbHelper myDb = DbHelper.getsInstance(this);

        // Spinner Drop down elements
        List<String> lables = myDb.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        lista.setAdapter(dataAdapter);
    }
}
