/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/17/18 1:20 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data;

import com.rx2androidnetworking.Rx2ANRequest;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*
    Wrapper Of Rx2AndroidNetworking Class
*/
public class DefaultAndroidNetworking {
    private DefaultAndroidNetworking() {
    }


    public static Rx2ANRequest.GetRequestBuilder get(String url) {
        return new Rx2ANRequest.GetRequestBuilder(url);
    }

    public static Rx2ANRequest.HeadRequestBuilder head(String url) {
        return new Rx2ANRequest.HeadRequestBuilder(url);
    }

    public static Rx2ANRequest.OptionsRequestBuilder options(String url) {
        return new Rx2ANRequest.OptionsRequestBuilder(url);
    }

    public static <T> Single<T> post(String url, Object body, Class<T> dataType) {
        return post(url, null, body, dataType);
    }

    public static <T> Single<T> post(String url, Object header, Object body, Class<T> dataType) {
        Rx2ANRequest.PostRequestBuilder builder = new Rx2ANRequest.PostRequestBuilder(url);
        if(header != null)
            builder.addHeaders(header);

        if(body != null)
            builder.addBodyParameter(body);

        return builder.build().getObjectSingle(dataType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Rx2ANRequest.PutRequestBuilder put(String url) {
        return new Rx2ANRequest.PutRequestBuilder(url);
    }

    public static Rx2ANRequest.DeleteRequestBuilder delete(String url) {
        return new Rx2ANRequest.DeleteRequestBuilder(url);
    }

    public static Rx2ANRequest.PatchRequestBuilder patch(String url) {
        return new Rx2ANRequest.PatchRequestBuilder(url);
    }

    public static Rx2ANRequest.DownloadBuilder download(String url, String dirPath, String fileName) {
        return new Rx2ANRequest.DownloadBuilder(url, dirPath, fileName);
    }

    public static Rx2ANRequest.MultiPartBuilder upload(String url) {
        return new Rx2ANRequest.MultiPartBuilder(url);
    }

    public static Rx2ANRequest.DynamicRequestBuilder request(String url, int method) {
        return new Rx2ANRequest.DynamicRequestBuilder(url, method);
    }
}
