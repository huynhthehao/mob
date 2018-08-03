package vn.homecredit.hcvn.data.model.mapdata.model.pos;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PosModel implements Serializable {

    @SerializedName("data")
    @Expose
    private List<PosData> data = null;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    private final static long serialVersionUID = 422065583703361656L;

    public List<PosData> getData() {
        return data;
    }

    public void setData(List<PosData> data) {
        this.data = data;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

}