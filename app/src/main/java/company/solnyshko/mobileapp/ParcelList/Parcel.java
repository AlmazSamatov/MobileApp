package company.solnyshko.mobileapp.ParcelList;

public class Parcel {
    // Id of icon
    private int icon;
    private String type;
    private boolean isChecked;

    public Parcel(int icon, String type, boolean isChecked) {
        this.icon = icon;
        this.type = type;
        this.isChecked = isChecked;
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
}
