/*
 * DataManager.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 4:46 PM
 */

package vn.homecredit.hcvn.data;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.ApiResponse;
import vn.homecredit.hcvn.data.remote.RestService;

public interface DataManager extends RestService {
    Single<ApiResponse> CheckUpdateAsync();
}
