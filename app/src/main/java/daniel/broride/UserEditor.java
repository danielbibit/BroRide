package daniel.broride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static daniel.broride.MainActivity.EXTRA_MESSAGE;

public class UserEditor extends AppCompatActivity{
    TextView description, debitValue;
    EditText editName, editAge;
    Button btnAction, btnDelete;
    CheckBox isDriver;
    private User user = new User();
    private Data data;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_editor);

        Intent intent = getIntent();
        message = intent.getStringExtra(EXTRA_MESSAGE);

        description = (TextView)findViewById(R.id.textView_description);
        debitValue = (TextView)findViewById(R.id.textViewDebitValue);
        editName = (EditText) findViewById(R.id.edit_name);
        editAge = (EditText) findViewById(R.id.edit_age);
        isDriver = (CheckBox) findViewById(R.id.driver_checkBox);
        btnAction = (Button) findViewById(R.id.button);
        btnDelete = (Button) findViewById(R.id.btnAction2);

        data = Data.getInstance();
        data.fillUser(this);

        final int id = intent.getIntExtra("id", 0);

        switch (message){
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

            case "display":
                setViewMode(0);
                description.setText("Visualizar");
                btnAction.setText("Editar");

                displayData(id);

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

            case "delete":
                setViewMode(0);
                description.setText("Deletar");
                btnDelete.setVisibility(View.INVISIBLE);
                btnAction.setText("Confirmar");

                displayData(id);

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

                displayData(id);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateUser(user);
                    }
                });
                break;

            default:
                //Never reach
                description.setText("ERROR");

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void displayData(int id){
        user = data.getUserById(id);
        debitValue.setText(String.valueOf(user.getDebit()));
        editName.setText(user.getName());
        editAge.setText(String.valueOf(user.getAge()));
        isDriver.setChecked(user.getIsDriver() == 1);
    }

    public void setViewMode(int mode){
        if(mode==0) {
            editName.setFocusable(false);
            editAge.setFocusable(false);
            isDriver.setFocusable(false);
            editName.setClickable(false);
            editAge.setClickable(false);
            isDriver.setClickable(false);
        }
    }

    public void createNewUser(){
        DbHelper myDb = DbHelper.getsInstance(this);
        Data data = Data.getInstance();

        user.setName(editName.getText().toString());
        user.setDriver(isDriver.isChecked() ? 1:0);
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
        user.setDriver(isDriver.isChecked() ? 1:0);
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