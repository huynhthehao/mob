package vn.homecredit.hcvn.data.remote.payment;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.disbursement.DisbursementModel;
import vn.homecredit.hcvn.data.model.mapdata.model.payment.PaymentModel;
import vn.homecredit.hcvn.data.remote.ApiEndPoint;
import vn.homecredit.hcvn.data.remote.ApiHeader;

public class PaymentServiceImpl implements PaymentService {
    private ApiHeader mApiHeader;

    @Inject
    public PaymentServiceImpl(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public Single<PaymentModel> getDisbursementNear(Double lat, Double lon) {
        String url = ApiEndPoint.ENDPOINT_APP +
                "/pos/payment?" +
                "lng=" + lon +
                "&lat=" + lat;
        return Rx2AndroidNetworking.get(url)
                .addHeaders(mApiHeader.getDisbursementApiHeader())
                .build()
                .getObjectSingle(PaymentModel.class);
    }
}
