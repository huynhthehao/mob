package vn.homecredit.hcvn.data.remote.disbursement;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.clw.ClwModel;
import vn.homecredit.hcvn.data.model.mapdata.model.disbursement.DisbursementModel;
import vn.homecredit.hcvn.data.remote.ApiEndPoint;
import vn.homecredit.hcvn.data.remote.ApiHeader;

public class DisbursementServiceImpl implements DisbursementService {
    private ApiHeader mApiHeader;

    @Inject
    public DisbursementServiceImpl(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public Single<DisbursementModel> getDisbursementNear(Double lat, Double lon) {
        String url = ApiEndPoint.ENDPOINT_APP +
                "/pos/disbursement?" +
                "lng=" + lon +
                "&lat=" + lat;
        return Rx2AndroidNetworking.get(url)
                .addHeaders(mApiHeader.getDisbursementApiHeader())
                .build()
                .getObjectSingle(DisbursementModel.class);
    }
}
