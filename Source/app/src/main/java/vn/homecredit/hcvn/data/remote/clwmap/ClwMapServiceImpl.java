package vn.homecredit.hcvn.data.remote.clwmap;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.clw.ClwModel;
import vn.homecredit.hcvn.data.remote.ApiEndPoint;
import vn.homecredit.hcvn.data.remote.ApiHeader;

public class ClwMapServiceImpl implements ClwMapService {

    private ApiHeader mApiHeader;

    @Inject
    public ClwMapServiceImpl(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public Single<ClwModel> getClwNear(Double lat, Double lon) {
        String url = ApiEndPoint.ENDPOINT_APP +
                "/clw/pos?" +
                "lng=" + lon +
                "&lat=" + lat;
        return Rx2AndroidNetworking.get(url)
                .addHeaders(mApiHeader.getPayooApiHeader())
                .build()
                .getObjectSingle(ClwModel.class);
    }
}
