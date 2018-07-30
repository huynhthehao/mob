package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class NextPayment {

    @SerializedName("date_next_due")
    @Expose
    private String dateNextDue;

    @SerializedName("next_instalment")
    @Expose
    private int nextInstalment;

    @SerializedName("out_debt")
    @Expose
    private int outDebt;

    @SerializedName("penalty")
    @Expose
    private int penalty;

    @SerializedName("total")
    @Expose
    private int total;


    public String getDateNextDue() {
        return dateNextDue;
    }

    public void setDateNextDue(String dateNextDue) {
        this.dateNextDue = dateNextDue;
    }

    public int getNextInstalment() {
        return nextInstalment;
    }

    public void setNextInstalment(int nextInstalment) {
        this.nextInstalment = nextInstalment;
    }

    public int getOutDebt() {
        return outDebt;
    }

    public void setOutDebt(int outDebt) {
        this.outDebt = outDebt;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
