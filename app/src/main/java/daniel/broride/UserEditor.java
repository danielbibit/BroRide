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

import static daniel.broride.MainActivity.EXTRA_MESSAGE;

public class UserEditor extends AppCompatActivity{
    User user = new User();

    private boolean a = true;
    private int i, pass = 0;
    private String message;

    TextView description;
    EditText editName, editAge;
    Button btnAction, btnDelete;
    CheckBox isDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_editor);

        Intent intent = getIntent();
        message = intent.getStringExtra(EXTRA_MESSAGE);

        editName = (EditText) findViewById(R.id.edit_name);
        editAge = (EditText) findViewById(R.id.edit_age);
        btnAction = (Button) findViewById(R.id.button);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        isDriver = (CheckBox) findViewById(R.id.driver_checkBox);
        description = (TextView)findViewById(R.id.textView_description);

        final int id = intent.getIntExtra("id", 0);
        description.setText("Visualizar");
        btnAction.setText("Editar");
        Data data = Data.getInstance();
        data.fillUser(this);

        final User user;
        user = data.getUserById(id);

        //setViewMode(1);
        editName.setText(user.getName());
        editAge.setText(String.valueOf(user.getAge()));
        isDriver.setChecked(user.getIsDriver()==1?true:false);

        switch (message){
            case "display":

                //Listener for the action button
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nIntent = getIntent();
                        nIntent.putExtra(EXTRA_MESSAGE, "edit");
                        nIntent.putExtra("id",id);
                        startActivity(nIntent);
                    }
                });

                //Listener for the delete button
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

            case "create":
                description.setText("Criar");
                btnAction.setText("Criar!");
                btnDelete.setVisibility(View.INVISIBLE);
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createNewUser();
                    }
                });
                break;

            case "delete":
                description.setText("Deletar");
                btnDelete.setVisibility(View.INVISIBLE);
                btnAction.setText("Confirmar");
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteUser(id);
                    }
                });
                break;

            case "edit":
                description.setText("Editar");
                btnDelete.setVisibility(View.INVISIBLE);
                btnAction.setText("Confirmar");

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateUser(user);
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

    public void displayData(){
        description.setText("Visualizar");
        btnAction.setText("edit");


        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setViewMode(int mode){
        if(mode==0) {
            editName.setFocusable(false);
            editAge.setFocusable(false);
            isDriver.setFocusable(false);
            editName.setClickable(false);
            editAge.setClickable(false);
            isDriver.setClickable(false);
        }else if(mode ==1){
            editName.setFocusable(true);
            editName.setCursorVisible(true);
            editName.setSelected(true);
            editName.setLongClickable(true);

            editAge.setFocusable(true);
            isDriver.setFocusable(true);
            editName.setClickable(true);
            editAge.setClickable(true);
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
            //data.insertUser(user);
            Toast.makeText(UserEditor.this, "Data inserted", Toast.LENGTH_LONG).show();
            data.fillUser(this);
            finish();
        } catch (SqlException e) {
            e.printStackTrace();
            Toast.makeText(UserEditor.this, "Data not inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void updateUser(User user){
        DbHelper myDb = DbHelper.getsInstance(this);
        Data data = Data.getInstance();

        user.setName(editName.getText().toString());
        user.setDriver(isDriver.isChecked()==true ? 1:0);
        user.setAge(Integer.parseInt(editAge.getText().toString()));

        try {
            myDb.updateUser(user);
            data.fillUser(this);
            Toast.makeText(getBaseContext(),"Usuario Atualizado",Toast.LENGTH_SHORT).show();
            finish();
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id){
        DbHelper myDb = DbHelper.getsInstance(this);
        Data data = Data.getInstance();

        try {
            myDb.deleteUser(data.getUserById(id));
            data.fillUser(this);
            Toast.makeText(getBaseContext(),"Usuario Deletado",Toast.LENGTH_SHORT).show();
            finish();
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public void back(View view){
        finish();
    }

}