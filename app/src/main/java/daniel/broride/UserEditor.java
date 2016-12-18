package daniel.broride;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.List;

public class UserEditor extends AppCompatActivity{
    User user = new User();

    private boolean a = true;
    private int i;

    TextView description;
    EditText editName, editAge;
    Button btnAction;
    CheckBox isDriver;

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


        switch (message){
            case "display":
                description.setText("Visualizar");
                btnAction.setText("edit");

                int id = intent.getIntExtra("id", 0);

                Data data = Data.getInstance();
                data.fillUser(this);

                User user;
                user = data.getUserById(id);

                displayMode(0);

                editName.setText(user.getName());
                editAge.setText(String.valueOf(user.getAge()));

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


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

    public void displayMode(int mode){
        if(mode==0) {
            editName.setFocusable(false);
            editAge.setFocusable(false);
            isDriver.setFocusable(false);
            editName.setClickable(false);
            editAge.setClickable(false);
            isDriver.setClickable(false);
        }else if(mode == 1){
            editName.setFocusable(true);
            editAge.setFocusable(true);
            isDriver.setFocusable(true);
            editName.setClickable(true);
            editAge.setClickable(true);
            isDriver.setClickable(true);
        }
    }

    public void createNewUser(){
        DbHelper myDb = DbHelper.getsInstance(this);
        Data data = Data.getInstance();

        user.setName(editName.getText().toString());
        user.setDriver(isDriver.isChecked()==true ? 1:0);
        user.setAge(Integer.parseInt(editAge.getText().toString()));

        try {
            int id = myDb.insertUser(user);
            user.setId(id);
            data.insertUser(user);
            Toast.makeText(UserEditor.this, "Data inserted", Toast.LENGTH_LONG).show();
            finish();
        } catch (SqlException e) {
            e.printStackTrace();
            Toast.makeText(UserEditor.this, "Data not inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteUser(int id){
        DbHelper myDb = DbHelper.getsInstance(this);
        Data data = Data.getInstance();

        try {
            myDb.deleteUser(data.getUserById(id));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }
    public void back(View view){
        finish();
    }

}
