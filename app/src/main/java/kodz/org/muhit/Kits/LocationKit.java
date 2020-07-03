package kodz.org.muhit.Kits;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.os.Looper;
import android.util.Log;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.ResolvableApiException;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationAvailability;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.LocationSettingsRequest;
import com.huawei.hms.location.LocationSettingsResponse;
import com.huawei.hms.location.LocationSettingsStatusCodes;
import com.huawei.hms.location.SettingsClient;
import com.huawei.hms.maps.model.LatLng;

import kodz.org.muhit.Helpers.Utils;
import kodz.org.muhit.R;

public class LocationKit {

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    SettingsClient settingsClient;
    LocationCallback locationCallback;
    public LatLng currentLatLng;
    public MapKit map;
    Context context;

    public void init(Context mcontext, MapKit mmap) {

        map = mmap;
        context = mcontext;

        // LOCATION
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        settingsClient = LocationServices.getSettingsClient(context);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    public void getLastLocationWithCallback(final Activity activity) {

        Utils.toggleView(activity.findViewById(R.id.frmLoading), true);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {

                    currentLatLng = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());

                    Log.i(Utils.TAG, "LocationKit -> currentLatLng: " + currentLatLng.toString());

                    if (map == null) {
                        Log.d(Utils.TAG, "map is null");
                    } else {
                        map.huaweiMap.setMyLocationEnabled(true);
                        map.setCamera(currentLatLng);
                        Utils.toggleView(activity.findViewById(R.id.frmLoading), false);
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
                        Log.i(Utils.TAG, "getLastLocation onFail location is null");
                        return;
                    }

                    Log.i(Utils.TAG, "getLastLocation: " + location.getLatitude() + "," + location.getLongitude());
                    currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    if (map == null) {
                        Log.d(Utils.TAG, "map is null");
                    } else {
                        map.huaweiMap.setMyLocationEnabled(true);
                        map.setCamera(currentLatLng);
                    }

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

    public void checkLocationSettings(final Activity activity) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        locationRequest = new LocationRequest();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check the device location settings.
        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        // Initiate location requests when the location settings meet the requirements.
                        fusedLocationProviderClient
                                .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Processing when the API call is successful.
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Device location settings do not meet the requirements.
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    // Call startResolutionForResult to display a pop-up asking the user to enable related permission.
                                    rae.startResolutionForResult(activity, 0);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.d(Utils.TAG, "LocationKit -> " + sie.getMessage());
                                }
                                break;
                        }
                    }
                });
    }
}
