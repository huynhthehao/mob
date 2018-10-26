package vn.homecredit.hcvn.data.remote;

import static vn.homecredit.hcvn.data.remote.ApiEndPoint.ENDPOINT_APP;

/**
 * Defines all api urls
 */
public final class RestUrl {
    private RestUrl() {}
    public static final String SUPPORT_HISTORY = ENDPOINT_APP + "/customer/tickets";
    public static final String CREDO_UPLOAD = ENDPOINT_APP + "/analytic/consent";


    public static String getRequestUrl(String url) {
        // TODO: will be update for multi-language
        final String languageCode = "vi";

        if (url == null) return url;
        if (url.contains("?")) {
            url += "&lang=" + languageCode;
        } else {
            url += "?lang=" + languageCode;
        }
        // platform = 2: Android
        url += "&platform=2";
        return url;
    }
}
