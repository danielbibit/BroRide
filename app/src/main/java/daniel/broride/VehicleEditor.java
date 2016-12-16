package daniel.broride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class VehicleEditor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_editor);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
