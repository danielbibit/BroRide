package daniel.broride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static daniel.broride.MainActivity.EXTRA_MESSAGE;

public class UserManage extends AppCompatActivity {

    ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        lista = (ListView) findViewById(R.id.ListaUsers) ;

        loadSpinnerData();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),"item clicado "+position,Toast.LENGTH_SHORT).show();
                openUserManage(position);

            }
        });

    }
    private void openUserManage(int i){
        Intent intent = new Intent(this, UserEditor.class);
        intent.putExtra(EXTRA_MESSAGE, "test");
        intent.putExtra("id",i);
        startActivity(intent);
    }

    private void loadSpinnerData() {
        // database handler
        DbHelper myDb = DbHelper.getsInstance(this);

        // Spinner Drop down elements
        List<String> lables = myDb.getAllLabels();
        lables.add("New User");

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
