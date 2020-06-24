package kodz.org.muhit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kodz.org.muhit.Models.PoiTypeModel;
import kodz.org.muhit.R;

public class PoiTypeAdapter extends ArrayAdapter<PoiTypeModel> {

    public PoiTypeAdapter(@NonNull Context context, ArrayList<PoiTypeModel> poiList) {
        super(context, 0, poiList);
    }

    @NotNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.poi_type_item, parent, false);
        }

        PoiTypeModel item = getItem(position);
        ImageView imgPoiIcon = convertView.findViewById(R.id.imgPoiIcon);
        TextView txtPoiName = convertView.findViewById(R.id.txtPoiName);

        if (item != null) {
            imgPoiIcon.setImageResource(item.getIcon());
            txtPoiName.setText(item.getName());
        }

        return convertView;
    }


    @NotNull
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.poi_type_item, parent, false);
        }

        PoiTypeModel item = getItem(position);
        ImageView imgPoiIcon = convertView.findViewById(R.id.imgPoiIcon);
        TextView txtPoiName = convertView.findViewById(R.id.txtPoiName);

        if (item != null) {
            imgPoiIcon.setImageResource(item.getIcon());
            txtPoiName.setText(item.getName());

            if (item.getType() == null) {
                imgPoiIcon.setVisibility(View.GONE);
                txtPoiName.setVisibility(View.GONE);
                convertView.setVisibility(View.GONE);
            } else {
                imgPoiIcon.setVisibility(View.VISIBLE);
                txtPoiName.setVisibility(View.VISIBLE);
                convertView.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }
}
