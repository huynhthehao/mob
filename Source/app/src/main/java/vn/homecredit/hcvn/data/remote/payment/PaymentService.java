package vn.homecredit.hcvn.data.remote.payment;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.payment.PaymentModel;

public interface PaymentService {
    Single<PaymentModel> getDisbursementNear(Double lat, Double lon);
}
