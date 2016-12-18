package daniel.broride;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DbHelper myDb = DbHelper.getsInstance(this);
        Cursor res = myDb.getAllData();
        data = Data.getInstance();
        data.fillUser(res);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_users) {
            openUserManage();
        } else if (id == R.id.nav_vehicles) {
           openVehicleEditor();
        } else if (id == R.id.nav_rides) {

        } else if (id == R.id.nav_manage) {
            showAllData();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openUserEditor(){
        Intent intent = new Intent(this, UserEditor.class);
        intent.putExtra(EXTRA_MESSAGE, "create");
        startActivity(intent);
    }

    public void openVehicleEditor(){
        Intent intent = new Intent(this, VehicleEditor.class);
        intent.putExtra(EXTRA_MESSAGE, "test");
        startActivity(intent);
    }

    public void openUserManage(){
        Intent intent = new Intent(this, UserManage.class);
        intent.putExtra(EXTRA_MESSAGE, "test");
        startActivity(intent);
    }


    public void showAllData(){
        StringBuffer buffer = new StringBuffer();


        for(int i=0; i<data.getCount(); i++){
            User user = data.getUser(i);

            buffer.append("Id : "+ user.getId()+"\n");
            buffer.append("Name : "+user.getName()+"\n");
            buffer.append("Driver : "+user.getIsDriver()+"\n");
            buffer.append("Age : "+user.getAge()+"\n");
            buffer.append("Debit: "+user.getDebit()+"\n\n");
        }

        showMessage("Data", buffer.toString());
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
