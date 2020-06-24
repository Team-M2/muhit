package kodz.org.muhit.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.Marker;

import kodz.org.muhit.R;

public class InfoWindowAdapter implements HuaweiMap.InfoWindowAdapter {

    private LayoutInflater inflate;

    public InfoWindowAdapter(LayoutInflater inflater) {
        this.inflate = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        View view = this.inflate.inflate(R.layout.map_info_window, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));

        //ImageView icon = (ImageView) view.findViewById(R.id.imgMarkerIcon);
        //TextView phone = (TextView) view.findViewById(R.id.txtMarkerPhone);

        TextView title = (TextView) view.findViewById(R.id.txtMarkerTitle);
        TextView snippet = (TextView) view.findViewById(R.id.txtMarkerSnippet);

        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());

        return view;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(Marker marker) {
        View view = this.inflate.inflate(R.layout.map_info_window, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));

        //ImageView icon = (ImageView) view.findViewById(R.id.imgMarkerIcon);
        //TextView phone = (TextView) view.findViewById(R.id.txtMarkerPhone);

        TextView title = (TextView) view.findViewById(R.id.txtMarkerTitle);
        TextView snippet = (TextView) view.findViewById(R.id.txtMarkerSnippet);

        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());

        return view;
    }

}