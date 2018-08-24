package vn.homecredit.hcvn.data.repository;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.clw.ClwModel;
import vn.homecredit.hcvn.data.model.mapdata.model.disbursement.DisbursementModel;
import vn.homecredit.hcvn.data.model.mapdata.model.payment.PaymentModel;

public interface MapRepository {

    Single<ClwModel> getClwModelNear(Double lat, Double lon);

    Single<DisbursementModel> getDisbursementModelNear(Double lat, Double lon);

    Single<PaymentModel> getPaymentModelNear(Double lat, Double lon);

}
