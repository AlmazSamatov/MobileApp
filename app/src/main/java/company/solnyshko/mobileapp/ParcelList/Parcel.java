package company.solnyshko.mobileapp.ParcelList;

public class Parcel {
    // Id of icon
    private int icon;
    private String type;
    private boolean isChecked;
    private String description;

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
