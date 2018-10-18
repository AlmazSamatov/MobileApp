package company.solnyshko.mobileapp.ParcelList;

public class Parcel {
    // Id of icon
    private int icon;
    private String name;
    private boolean isChecked;

    public Parcel(int icon, String name, boolean isChecked) {
        this.icon = icon;
        this.name = name;
        this.isChecked = isChecked;
    }

    public int getIcon() {

        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
