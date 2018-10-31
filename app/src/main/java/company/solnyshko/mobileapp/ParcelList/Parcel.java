package company.solnyshko.mobileapp.ParcelList;

import io.realm.RealmObject;

public class Parcel extends RealmObject {
    // Id of icon
    private int icon = 0;
    private String type = "";
    private boolean isChecked = false;
    private String description = "";
    private String parcelId = "";

    public Parcel(){}

    public Parcel(String id, String type, String remark) {
        this.parcelId = id;
        this.type = type;
        this.description = remark;
    }

    public Parcel(int icon, String type, boolean isChecked) {
        this.icon = icon;
        this.type = type;
        this.isChecked = isChecked;
        this.description = "";
    }

    public Parcel(int icon, String type, boolean isChecked, String description) {
        this.icon = icon;
        this.type = type;
        this.isChecked = isChecked;
        this.description = description;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String name) {
        this.type = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
