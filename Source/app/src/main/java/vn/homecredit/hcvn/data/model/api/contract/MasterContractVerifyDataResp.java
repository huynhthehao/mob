package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class MasterContractVerifyDataResp {

    @SerializedName("TimeOut")
    @Expose
    private int timeOut;
    @SerializedName("LoadInterval")
    @Expose
    private int loadInterval;
    @SerializedName("DisbursementVisible")
    @Expose
    private boolean disbursementVisible;

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getLoadInterval() {
        return loadInterval;
    }

    public void setLoadInterval(int loadInterval) {
        this.loadInterval = loadInterval;
    }

    public boolean isDisbursementVisible() {
        return disbursementVisible;
    }

    public void setDisbursementVisible(boolean disbursementVisible) {
        this.disbursementVisible = disbursementVisible;
    }
}
