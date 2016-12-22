package daniel.broride;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView rideName,rideDistance;
    Button irParaRide;
    int idRide;

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rideName = (TextView) findViewById(R.id.rideName);
        rideDistance = (TextView) findViewById(R.id.rideDistance);
        irParaRide = (Button) findViewById(R.id.btnIrRide);

        /*---------------------------------------------------------------------------------------*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*---------------------------------------------------------------------------------------*/
        showLastRide();
        try{
            data = Data.getInstance();
            data.syncWithDb(this);
        }catch (Exception e){
            //this.deleteDatabase("main.db"); //CAUTION ! UNCOMENT FOR DELETING THE WHOLE DB !!!
            //finish();
        }

        irParaRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRideEditor(idRide);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            showLastRide();
        }catch (Exception e ){
            e.printStackTrace();
        }
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
            openUserManager();
        } else if (id == R.id.nav_vehicles) {
            openVehicleManager();
        } else if (id == R.id.nav_rides) {
            openRideManager();
        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openVehicleManager(){
        Intent intent = new Intent(this, VehicleManager.class);
        intent.putExtra(EXTRA_MESSAGE, "test");
        startActivity(intent);
    }

    private void openUserManager(){
        Intent intent = new Intent(this, UserManager.class);
        intent.putExtra(EXTRA_MESSAGE, "test");
        startActivity(intent);
    }

    private void openRideManager(){
        Intent intent = new Intent(this, RideManager.class);
        intent.putExtra(EXTRA_MESSAGE, "test");
        startActivity(intent);
    }

    private void showAllData(){
        /*StringBuffer buffer = new StringBuffer();
        Log.d("DEBUG", ""+data.getCountRide());
        data.fillRide(this);

        for(int i = 0; i<data.getCountRide(); i++){
            Ride ride = data.getRide(i);

            buffer.append("Id : "+ ride.getDriverPays()+"\n");
            buffer.append("Name : "+ride.getName()+"\n");
            buffer.append("Description : "+ride.getDescription()+"\n");
            buffer.append("Distance : "+ride.getDistance()+"\n");
            buffer.append("Debit: "+ride.getGasPrice()+"\n\n");
        }

        showMessage("Data", buffer.toString());
        */
    }

    private void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void showLastRide(){
        Data data = Data.getInstance();
        Ride ride;

        if(!(Utils.readCache(this).equals("null"))){
            irParaRide.setVisibility(View.VISIBLE);
            idRide = Integer.parseInt(Utils.readCache(this));
            ride = data.getRideById(idRide);
            rideName.setText(ride.getName());
            rideDistance.setText(ride.getDistance().toString());
        }else{
            irParaRide.setVisibility(View.INVISIBLE);
            rideName.setVisibility(View.INVISIBLE);
            rideDistance.setVisibility(View.INVISIBLE);
        }
    }

    private void openRideEditor(int n){
        Intent intent = new Intent(this, RideEditor.class);

        intent.putExtra(EXTRA_MESSAGE, "commit");
        intent.putExtra("id", n);
        startActivity(intent);

    }
}
