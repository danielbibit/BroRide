package daniel.broride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UserEditor extends AppCompatActivity {
    User user = new User();
    DbHelper myDb;
    EditText editName, editAge;
    Button insert;
    CheckBox isDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_editor);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        switch (message){
            case "edit":
                break;
            case "delete":
                break;
            case "create":
                break;
            default:

        }

        myDb = new DbHelper(this);

        editName = (EditText) findViewById(R.id.edit_name);
        editAge = (EditText) findViewById(R.id.edit_age);
        insert = (Button) findViewById(R.id.button);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDb();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void insertDb(){
        user.setName(editName.getText().toString());
        user.setAge(editAge.getInputType());

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

}
