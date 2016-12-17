package daniel.broride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UserEditor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    User user = new User();

    TextView description;
    EditText editName, editAge;
    Button btnAction;
    CheckBox isDriver;
    Spinner spinnerUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_editor);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        editName = (EditText) findViewById(R.id.edit_name);
        editAge = (EditText) findViewById(R.id.edit_age);
        btnAction = (Button) findViewById(R.id.button);
        isDriver = (CheckBox) findViewById(R.id.driver_checkBox);
        description = (TextView)findViewById(R.id.textView_description);

        spinnerUser = (Spinner) findViewById(R.id.spinnerUser);

       // ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.UserManager, android.R.layout.simple_spinner_item );

        //spinnerUser.setAdapter(adapter);

        spinnerUser.setOnItemSelectedListener(this);

        switch (message){
            case "edit":
                break;
            case "delete":
                break;
            case "create":
                description.setText("Criar");
                btnAction.setText("Criar!");

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createNewUser();
                    }
                });
                break;
            default:

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void createNewUser(){
        DbHelper myDb = DbHelper.getsInstance(this);

        user.setName(editName.getText().toString());
        user.setDriver(isDriver.isChecked()==true ? 1:0);
        user.setAge(Integer.parseInt(editAge.getText().toString()));

        try {
            myDb.insertUser(user);
            Toast.makeText(UserEditor.this, "Data inserted", Toast.LENGTH_LONG).show();
            finish();
        } catch (SqlException e) {
            e.printStackTrace();
            Toast.makeText(UserEditor.this, "Data not inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void back(View view){
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // test

    /*private void loadSpinnerData() {
        // database handler
        DbHelper db = new DbHelper(getApplicationContext());

        // Spinner Drop down elements
        List<String> lables = db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    */
}
