package vn.homecredit.hcvn.data.model.offer;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;
import vn.homecredit.hcvn.ui.notification.model.OfferModel;

public class OfferDetailData extends BaseApiResponse {
    // TODO: Refactor to remove JsonObject in this model

    @SerializedName("Formula")
    @Expose
    private JsonObject jsonFormula;

    private List<Rate> rateList;
    private OfferModel offerModel;
    private Rate rateMaxMonth;

    public JsonObject getJsonFormula() {
        return jsonFormula;
    }

    public void setJsonFormula(JsonObject jsonFormula) {
        this.jsonFormula = jsonFormula;
    }

    public List<Rate> getRateList() {
        return rateList;
    }

    public void setRateList(List<Rate> rateList) {
        this.rateList = rateList;
    }

    public OfferModel getOfferModel() {
        return offerModel;
    }

    public void setOfferModel(OfferModel offerModel) {
        this.offerModel = offerModel;
    }

    public Rate getRateMaxMonth() {
        return rateMaxMonth;
    }

    public void generateInterest() {
        rateList = new ArrayList<>();
        Iterator<String> keys = jsonFormula.keySet().iterator();
        int maxMonth = 0;
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                int month = Integer.parseInt(key);
                Rate rate = new Rate(month, jsonFormula.get(key).getAsJsonObject().get("Interest").getAsDouble());
                if (maxMonth < month) {
                    maxMonth = month;
                    rateMaxMonth = rate;
                }
                rateList.add(rate);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Rate {
        private int month;
        private double interest;

        Rate(int month, double interest) {
            this.month = month;
            this.interest = interest;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public double getInterest() {
            return interest;
        }

        public void setInterest(double interest) {
            this.interest = interest;
        }
    }
}

