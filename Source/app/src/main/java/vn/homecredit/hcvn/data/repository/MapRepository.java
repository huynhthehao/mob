package vn.homecredit.hcvn.data.repository;

import java.util.List;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.clw.ClwModel;
import vn.homecredit.hcvn.data.model.mapdata.model.disbursement.DisbursementModel;
import vn.homecredit.hcvn.data.model.mapdata.model.payment.PaymentModel;
import vn.homecredit.hcvn.data.model.mapdata.model.payoo.PayooData;
import vn.homecredit.hcvn.data.model.mapdata.model.pos.PosModel;

public interface MapRepository {

    Single<List<PayooData>> getPayooNear(Double lat, Double lon);

    Single<PosModel> getPosNear(Double lat, Double lon);

    Single<PosModel> getVnPosNear(Double lat, Double lon);

    Single<ClwModel> getClwModelNear(Double lat, Double lon);

    Single<DisbursementModel> getDisbursementModelNear(Double lat, Double lon);

    Single<PaymentModel> getPaymentModelNear(Double lat, Double lon);

}
