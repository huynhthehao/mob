package vn.homecredit.hcvn.data.model.api.support

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse

class SupportResp : BaseApiResponse() {
    @SerializedName("Total")
    @Expose
    var total: Int = 0
}