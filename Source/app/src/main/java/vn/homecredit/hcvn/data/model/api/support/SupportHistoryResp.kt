package vn.homecredit.hcvn.data.model.api.support

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse

class SupportHistoryResp : BaseApiResponse() {
    @SerializedName("data")
    @Expose
    var data: List<Support> = emptyList()
}