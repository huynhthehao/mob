package vn.homecredit.hcvn.data.model.api.support

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel
import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse

@Parcel
class Support {
    @SerializedName("TicketId")
    @Expose
    var ticketId: String? = null
    @SerializedName("Subject")
    @Expose
    var subject: String? = null
    @SerializedName("Description")
    @Expose
    var description: String? = null
    @SerializedName("ContractNumber")
    @Expose
    var contractNumber: String? = null
    @SerializedName("Status")
    @Expose
    var status: Status? = null
    @SerializedName("Date")
    @Expose
    var date: String? = null
    @SerializedName("StatusText")
    @Expose
    var statusText: String? = null

    enum class Status {
        @SerializedName("Closed")
        @Expose
        CLOSED,
        @SerializedName("Unknown")
        @Expose
        UNKNOWN
    }
}