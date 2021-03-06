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

public class UserManager extends AppCompatActivity {

    private ListView lista;
    private Button button;
    private int[] arrayUsersId;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        lista = (ListView) findViewById(R.id.ListaUsers) ;
        button = (Button) findViewById(R.id.addUser);

        loadSpinnerData();
        fillUsersArrayId();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(),"item clicado "+position,Toast.LENGTH_SHORT).show();
                openUserEditor(arrayUsersId[position]);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserEditor(-1);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSpinnerData();
        fillUsersArrayId();
    }

    private void openUserEditor(int n){
        Intent intent = new Intent(this, UserEditor.class);

        if(n==-1){
            intent.putExtra(EXTRA_MESSAGE, "create");
            startActivity(intent);
        }else{
            intent.putExtra(EXTRA_MESSAGE,"display");
            intent.putExtra("id",n);
            startActivity(intent);
        }
    }

    private void loadSpinnerData() {
        Data data = Data.getInstance();

        // Spinner Drop down elements
        List<String> lables = data.getUsersLabelsList();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables){
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
        lista.setAdapter(dataAdapter);
    }

    private void fillUsersArrayId(){
        Data data = Data.getInstance();
        arrayUsersId = data.getAllUsersId();
    }

    public void back(View view){
        finish();
    }

}
