package kodz.org.muhit.Models;

import com.huawei.hms.site.api.model.LocationType;

public class PoiTypeModel {

    String name;
    LocationType type;
    int icon;

    public PoiTypeModel(String name, LocationType type, int icon) {
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}
