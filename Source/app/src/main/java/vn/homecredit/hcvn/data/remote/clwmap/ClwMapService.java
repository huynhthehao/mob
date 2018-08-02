package vn.homecredit.hcvn.data.remote.clwmap;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.clw.ClwModel;

public interface ClwMapService {
    Single<ClwModel> getClwNear(Double lat, Double lon);
}