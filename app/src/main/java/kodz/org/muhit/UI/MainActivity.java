package kodz.org.muhit.UI;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.hms.site.api.model.Coordinate;

import kodz.org.muhit.Adapters.PoiTypeAdapter;
import kodz.org.muhit.Helpers.Utils;
import kodz.org.muhit.Kits.LocationKit;
import kodz.org.muhit.Kits.MapKit;
import kodz.org.muhit.Kits.SiteKit;
import kodz.org.muhit.Models.PoiTypeModel;
import kodz.org.muhit.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Init
    Button btnClearAllMarkers;
    Spinner spnPoiTypes;
    View divider;
    Bundle savedInstanceState;

    Utils utils;
    MapKit map;
    LocationKit location;
    SiteKit site;

    // PoiTypes
    PoiTypeAdapter poiTypeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState = savedInstanceState;

        // Hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide navigation buttons
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setContentView(R.layout.activity_main);

        // Init
        init();

        // Utils
        utils = new Utils();
        utils.checkPermissions(this);

        // MAP
        map = new MapKit();
        map.init(this, savedInstanceState);

        // LOCATION
        location = new LocationKit();
        location.init(this, map);
        location.getLastLocation(); // or location.getLastLocationWithCallbak(); for tracking location changes

        // SITE
        site = new SiteKit();
        site.init(this, map);


        // POI TYPE LIST
        poiTypeAdapter = new PoiTypeAdapter(MainActivity.this, utils.getPoiTypeList(getApplicationContext()));
        spnPoiTypes.setAdapter(poiTypeAdapter);
        int initialposition = spnPoiTypes.getSelectedItemPosition();
        spnPoiTypes.setSelection(initialposition, false);
        spnPoiTypes.setOnItemSelectedListener(this);
    }

    private void init() {
        btnClearAllMarkers = findViewById(R.id.btnClearMarkers);
        btnClearAllMarkers.setVisibility(View.GONE);
        divider = findViewById(R.id.divider);
        divider.setVisibility(View.GONE);
        spnPoiTypes = findViewById(R.id.spnPoiTypes);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        PoiTypeModel poi = (PoiTypeModel) parent.getSelectedItem();

        Log.d(utils.TAG, poi.getName());

        if (poi.getType() != null) {
            site.getPoiList(
                    poi.getType(),
                    poi.getName(),
                    poi.getIcon(),
                    new Coordinate(location.currentlatLng.latitude, location.currentlatLng.longitude),
                    utils.getColor(position)
            );

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d(Utils.TAG + " requestCode", requestCode + "");
        Log.d(Utils.TAG + " permissions 0", permissions[0]);
        Log.d(Utils.TAG + " permissions 3", permissions[1]);
        Log.d(Utils.TAG + " permissions 2", permissions[2]);
        Log.d(Utils.TAG + " grantResults 0", String.valueOf(grantResults[0]));
        Log.d(Utils.TAG + " grantResults 1", String.valueOf(grantResults[1]));
        Log.d(Utils.TAG + " grantResults 2", String.valueOf(grantResults[2]));

        if (requestCode == 1) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i(Utils.TAG, "onRequestPermissionsResult: apply LOCATION PERMISSION successful");
            } else {
                Log.i(Utils.TAG, "onRequestPermissionsResult: apply LOCATION PERMISSSION  failed");
            }
        }

        if (requestCode == 2) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(Utils.TAG, "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION successful");
                }

                location.getLastLocation();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        map.huaweiMap.setMyLocationEnabled(true);
                        map.setCamera(location.currentlatLng);

                    }
                }, 1700);


            } else {
                Log.i(Utils.TAG, "onRequestPermissionsResult: Permission Denied by User!!");
            }

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        }
    }

    public void clearAllMarkers(View view) {
        map.huaweiMap.clear();
        btnClearAllMarkers.setVisibility(View.GONE);
        divider.setVisibility(View.GONE);
        spnPoiTypes.setSelection(0);
    }

}