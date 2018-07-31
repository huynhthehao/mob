package vn.homecredit.hcvn.data.remote.payoo;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.payoo.PayooData;
import vn.homecredit.hcvn.data.remote.ApiEndPoint;
import vn.homecredit.hcvn.data.remote.ApiHeader;

@Singleton
public class PayooRestServiceImpl implements PayooRestService {
    private ApiHeader mApiHeader;

    @Inject
    public PayooRestServiceImpl(ApiHeader apiHeader){
        this.mApiHeader = apiHeader;
    }

    @Override
    public Single<List<PayooData>> getPayooNear(Double lat, Double lon) {
        String url = ApiEndPoint.ENDPOINT_PAYOO +
                "long=" + lon +
                "&lat=" + lat +
                "&verify=Verify&limit=10000";
        return Rx2AndroidNetworking.get(url)
                .addHeaders(mApiHeader.getmPayooApiHeader())
                .build()
                .getObjectListSingle(PayooData.class);
    }
}
