package kodz.org.muhit.Kits;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationAvailability;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.SettingsClient;
import com.huawei.hms.maps.model.LatLng;

import java.util.List;

import kodz.org.muhit.Helpers.Utils;

public class LocationKit {

    FusedLocationProviderClient fusedLocationProviderClient;
    SettingsClient settingsClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    public LatLng currentlatLng;
    public MapKit map;

    public void init(Context context, MapKit mmap) {

        map = mmap;

        // LOCATION
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        settingsClient = LocationServices.getSettingsClient(context);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    public void getLastLocationWithCallbak() {

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    List<android.location.Location> locations = locationResult.getLocations();
                    if (!locations.isEmpty()) {
                        for (android.location.Location location : locations) {

                            Log.i(Utils.TAG, "getLastLocationWithCallBack: " + location.getLatitude() + "," + location.getLongitude());

                            currentlatLng = new LatLng(location.getLatitude(), location.getLongitude());

                            if (map == null) {
                                Log.d(Utils.TAG, "map is null");
                            } else {
                                map.setCamera(currentlatLng);
                            }

                        }
                    }
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                if (locationAvailability != null) {
                    boolean flag = locationAvailability.isLocationAvailable();
                    Log.i(Utils.TAG, "GeoFence onLocationAvailability isLocationAvailable:" + flag);
                }
            }
        };
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }


    public void getLastLocation() {
        try {
            Task<android.location.Location> lastLocation = fusedLocationProviderClient.getLastLocation();

            lastLocation.addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
                @Override
                public void onSuccess(android.location.Location location) {

                    if (location == null) {
                        Log.i(Utils.TAG, "getLastLocation onSuccess location is null");
                        return;
                    }

                    Log.i(Utils.TAG, "getLastLocation: " + location.getLatitude() + "," + location.getLongitude());
                    currentlatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    if (map == null) {
                        Log.d(Utils.TAG, "map is null");
                    } else {
                        map.setCamera(currentlatLng);
                    }

                    return;

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Log.e(Utils.TAG, "getLastLocation onFailure:" + e.getMessage());
                }
            });

        } catch (Exception e) {

            Log.e(Utils.TAG, "getLastLocation exception:" + e.getMessage());

        }
    }
}
