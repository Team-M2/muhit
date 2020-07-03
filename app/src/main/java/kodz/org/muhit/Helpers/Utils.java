package kodz.org.muhit.Helpers;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.huawei.hms.site.api.model.LocationType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import kodz.org.muhit.Models.PoiTypeModel;
import kodz.org.muhit.R;
import kodz.org.muhit.UI.MainActivity;

public class Utils {

    public static final String TAG = "muhit";
    ArrayList<PoiTypeModel> poiTypeList;
    public Boolean isLocationPermissionGranted = false;


    public static String getApiKey(Context context) {

        String apiKey = context.getString(R.string.apikey);
        try {
            String encodedApiKey = URLEncoder.encode(apiKey, "utf-8");
            return encodedApiKey;
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "encode apikey error");
            return null;
        }
    }

    public ArrayList<PoiTypeModel> getPoiTypeList(Context context) {
        poiTypeList = new ArrayList<>();
        poiTypeList.clear();

        poiTypeList.add(new PoiTypeModel(context.getString(R.string.select_one), null, R.drawable.icon_place));

        poiTypeList.add(new PoiTypeModel(context.getString(R.string.hospital), LocationType.HOSPITAL, R.drawable.icon_hospital));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.movie_theater), LocationType.MOVIE_THEATER, R.drawable.icon_cinema));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.cafe), LocationType.CAFE, R.drawable.icon_cafe));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.super_market), LocationType.SUPERMARKET, R.drawable.icon_market));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.atm), LocationType.ATM, R.drawable.icon_atm));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.bank), LocationType.BANK, R.drawable.icon_bank));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.bus_station), LocationType.BUS_STATION, R.drawable.icon_bus));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.park), LocationType.PARK, R.drawable.icon_park));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.parking), LocationType.PARKING, R.drawable.icon_parking));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.bakery), LocationType.BAKERY, R.drawable.icon_bakery));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.school), LocationType.SCHOOL, R.drawable.icon_school));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.food), LocationType.FOOD, R.drawable.icon_restaurant));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.police_station), LocationType.POLICE, R.drawable.icon_police));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.mosque), LocationType.MOSQUE, R.drawable.icon_mosque));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.church), LocationType.CHURCH, R.drawable.icon_church));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.gym), LocationType.GYM, R.drawable.icon_gym));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.gas_station), LocationType.GAS_STATION, R.drawable.icon_gas_station));
        poiTypeList.add(new PoiTypeModel(context.getString(R.string.beauty_salon), LocationType.BEAUTY_SALON, R.drawable.icon_beauty));

        return poiTypeList;
    }


    public Integer getColor(int id) {
        Stack recycle = new Stack<>();
        recycle.addAll(Arrays.asList(
                0xfff44336, 0xffe91e63, 0xff9c27b0, 0xff673ab7,
                0xff3f51b5, 0xff2196f3, 0xff03a9f4, 0xff00bcd4,
                0xff009688, 0xff4caf50, 0xff8bc34a, 0xffcddc39,
                0xffffeb3b, 0xffffc107, 0xffff9800, 0xffff5722,
                0xff795548, 0xff9e9e9e, 0xff607d8b, 0xff333333
                )
        );

        return (Integer) recycle.get(id);
    }

    public static class RandomColors {
        private Stack<Integer> recycle, colors;

        public RandomColors() {
            colors = new Stack<>();
            recycle = new Stack<>();
            recycle.addAll(Arrays.asList(
                    0xfff44336, 0xffe91e63, 0xff9c27b0, 0xff673ab7,
                    0xff3f51b5, 0xff2196f3, 0xff03a9f4, 0xff00bcd4,
                    0xff009688, 0xff4caf50, 0xff8bc34a, 0xffcddc39,
                    0xffffeb3b, 0xffffc107, 0xffff9800, 0xffff5722,
                    0xff795548, 0xff9e9e9e, 0xff607d8b, 0xff333333
                    )
            );
        }

        public int getColor(int id) {
            if (colors.size() == 0) {
                while (!recycle.isEmpty())
                    colors.push(recycle.pop());
                Collections.shuffle(colors);
            }
            Integer c = colors.pop();
            recycle.push(c);
            return c;
        }
    }

    public Boolean checkLocationServiceAvailability(final Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            Log.d(TAG,"Util -> checkLocationServiceAvailability: False");
            return false;
        }
        Log.d(TAG,"Util -> checkLocationServiceAvailability: True");
        return true;
    }

    public void checkPermissions(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions((Activity) context, strings, 1);
                Log.d(TAG,"Util -> checkPermissions: False");
            }else{
                isLocationPermissionGranted = true;
                Log.d(TAG,"Util -> checkPermissions: True");
            }
        } else {
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context,
                    "android.permission.ACCESS_BACKGROUND_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        "android.permission.ACCESS_BACKGROUND_LOCATION"
                };
                ActivityCompat.requestPermissions((Activity) context, strings, 2);
                Log.d(TAG,"Util -> checkPermissions: False");
            }else{
                isLocationPermissionGranted = true;
                Log.d(TAG,"Util -> checkPermissions: True");
            }
        }
    }




    public void openAppSettings(Context context, Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static void toggleView(View view, Boolean show){
        if(show){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }
    }
}

