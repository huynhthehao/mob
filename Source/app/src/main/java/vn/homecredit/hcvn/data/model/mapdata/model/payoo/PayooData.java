package vn.homecredit.hcvn.data.model.mapdata.model.payoo;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayooData implements Serializable {

    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("Long")
    @Expose
    private Double _long;
    @SerializedName("Lat")
    @Expose
    private Double lat;
    @SerializedName("Distance")
    @Expose
    private Double distance;
    @SerializedName("Address")
    @Expose
    private String address;
    private final static long serialVersionUID = 922941908500254842L;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Double getLong() {
        return _long;
    }

    public void setLong(Double _long) {
        this._long = _long;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}