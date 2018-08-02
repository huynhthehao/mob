package vn.homecredit.hcvn.data.remote.pos;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.pos.PosModel;
import vn.homecredit.hcvn.data.remote.ApiEndPoint;
import vn.homecredit.hcvn.data.remote.ApiHeader;

public class PosRestServiceImpl implements PosRestService {
    private ApiHeader mApiHeader;

    @Inject
    public PosRestServiceImpl(ApiHeader apiHeader) {
        this.mApiHeader = apiHeader;
    }

    @Override
    public Single<PosModel> getPosNear(Double lat, Double log) {
        String url = ApiEndPoint.ENDPOINT_APP +
                "/pos/pos?" +
                "lng=" + log +
                "&lat=" + lat;

        return Rx2AndroidNetworking.get(url)
                .addHeaders(mApiHeader.getPosApiHeader())
                .build()
                .getObjectSingle(PosModel.class);
    }

    @Override
    public Single<PosModel> getVnPosNear(Double lat, Double log) {
        String url = ApiEndPoint.ENDPOINT_APP +
                "/pos/vnpos?" +
                "lng=" + log +
                "&lat=" + lat;

        return Rx2AndroidNetworking.get(url)
                .addHeaders(mApiHeader.getPosApiHeader())
                .build()
                .getObjectSingle(PosModel.class);
    }
}
