package vn.homecredit.hcvn.data.model.offer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;
import vn.homecredit.hcvn.ui.notification.model.OfferModel;

public class OfferDetailData extends BaseApiResponse {

    @SerializedName("Formula")
    @Expose
    private JsonObject jsonFormula;

    private List<Rate> rateList;
    private OfferModel offerModel;

    public OfferModel getOfferModel() {
        return offerModel;
    }

    public void setOfferModel(OfferModel offerModel) {
        this.offerModel = offerModel;
    }

    public JsonObject getJsonFormula() {
        return jsonFormula;
    }

    public void setJsonFormula(JsonObject jsonFormula) {
        this.jsonFormula = jsonFormula;
    }

    public void generateInterest() {
        rateList = new ArrayList<>();
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonFormula.entrySet()) {
            rateList.add(new Rate(stringJsonElementEntry));
        }
    }

    public static class Rate {
        String month;
        int interest;
        public Rate(Map.Entry<String, JsonElement>  map) {
            month = map.getKey();
            interest = 70;
        }

    }
}

