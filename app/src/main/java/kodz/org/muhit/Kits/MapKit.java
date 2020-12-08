package kodz.org.muhit.Kits;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.site.api.model.LocationType;
import com.huawei.hms.site.api.model.Site;

import java.text.DecimalFormat;

import kodz.org.muhit.Adapters.InfoWindowAdapter;
import kodz.org.muhit.Helpers.Utils;
import kodz.org.muhit.R;

public class MapKit extends AppCompatActivity implements OnMapReadyCallback, HuaweiMap.OnMarkerClickListener {

    Context context;
    public HuaweiMap huaweiMap;
    public MapView mapView;
    CameraUpdate cameraUpdate;
    float zoom = 14.0f;
    static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    LatLng lastlatLng;

    public void init(Context mcontext, Bundle savedInstanceState) {

        context = mcontext;

        // MAP
        mapView = (MapView) ((Activity) context).findViewById(R.id.mapView);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);

        MapsInitializer.setApiKey("CV8Bi0AC7BMYpuAPzEU894pZJvwlzO8bIgR7z+aMSaZ+Dm6+CMNci6tZHJAeF0fHiAYvpFWy4iMxbGBFHCSSHwPyhTqs");
        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        Log.d(Utils.TAG, "huaweiMap is ready");

        huaweiMap = map;
        //huaweiMap.setMyLocationEnabled(true);
        huaweiMap.setOnMarkerClickListener(this);
        huaweiMap.setInfoWindowAdapter(new InfoWindowAdapter(((Activity) context).getLayoutInflater()));
        if (lastlatLng != null) {
            setCamera(lastlatLng);
        }
    }


    public void setCamera(LatLng latLng) {

        Log.d(Utils.TAG, "setCamera Latlng: " + latLng);
        lastlatLng = latLng;

        if(lastlatLng != null){
            if (huaweiMap == null) {
                Log.d(Utils.TAG, "huaweiMap is null");
            } else {
                cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
                huaweiMap.animateCamera(cameraUpdate);
            }

        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    public void addMarker(Site site, int icon, LocationType type, Integer color) {

        String snippet;
        String title = site.getName();
        String address = site.getFormatAddress();
        String phoneNumber = site.getPoi().getPhone();
        double distance = site.getDistance();
        String distanceSuffix = "m";

        if (distance >= 1000) {
            distance = distance / 1000;
            distanceSuffix = "km";
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String d = df.format(distance);

        if (phoneNumber != null) {
            snippet = String.format("%s\n%s\n%s %s", address, phoneNumber, d, distanceSuffix);
        } else {
            snippet = String.format("%s\n%s %s", address, d, distanceSuffix);
        }

        LatLng position = new LatLng(site.getLocation().getLat(), site.getLocation().getLng());

        BitmapDescriptor mi = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            Drawable m_icon = context.getDrawable(icon);
            m_icon.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

            Drawable m_circle = context.getDrawable(R.drawable.circle);
            m_circle.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

            Bitmap m = drawableToBitmap(m_circle);
            Bitmap i = drawableToBitmap(m_icon);
            i = Bitmap.createScaledBitmap(i, 30, 30, false);

            Bitmap o = overlay(m, i);
            mi = BitmapDescriptorFactory.fromBitmap(o);
        }

        MarkerOptions markerOptions = new MarkerOptions()
                .title(title)
                .snippet(snippet)
                .position(position)
                .icon(mi);

        huaweiMap.addMarker(markerOptions);

        ((Activity) context).findViewById(R.id.btnClearMarkers).setVisibility(View.VISIBLE);
        ((Activity) context).findViewById(R.id.divider).setVisibility(View.VISIBLE);
    }

    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        float l = (bmp1.getWidth() / 2) - (bmp2.getWidth() / 2);
        float t = (bmp1.getHeight() / 2) - (bmp2.getHeight() / 2);

        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, l, t, null);
        return bmOverlay;
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
