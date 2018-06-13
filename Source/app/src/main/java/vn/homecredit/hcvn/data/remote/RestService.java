/*
 * RestService.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 1:11 PM
 */

package vn.homecredit.hcvn.data.remote;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.ApiResponse;

public interface RestService {
    Single<ApiResponse> CheckUpdateAsync();
}
