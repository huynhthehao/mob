package vn.homecredit.hcvn.data.remote;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class UploadAsyncTask extends AsyncTask<Void, Integer, String> {

    HttpClient httpClient = new DefaultHttpClient();
    private Context context;
    private ProgressDialog progressDialog;
    private String url;
    private String filePath;
    private Map<String, String> bodyParams = null;
    private Map<String, String> headers = null;
    private UploadAsyncListener uploadAsyncListener = null;

    public UploadAsyncTask(Context context) {
        this.context = context;
    }

    public void init(String url, String filePath, Map<String, String> bodyParams) {
        this.url = url;
        this.filePath = filePath;
        this.bodyParams = bodyParams;
    }

    public void init(String url, String filePath, Map<String, String> bodyParams, Map<String,String> headers) {
        this.url = url;
        this.filePath = filePath;
        this.bodyParams = bodyParams;
        this.headers = headers;
    }

    public void setListener(UploadAsyncListener uploadAsyncListener){
        this.uploadAsyncListener = uploadAsyncListener;
    }

    private void addRequestBody(MultipartEntityBuilder multipartEntityBuilder) {
        if (bodyParams == null)
            return;

        for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            multipartEntityBuilder.addTextBody(key, value);
        }
    }

    private void addRequestHeaders(HttpPost httpPost) {
        if (headers == null)
            return;

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            httpPost.addHeader(key, value);
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpResponse httpResponse;
        HttpEntity httpEntity;
        String responseString = null;

        try {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

            File file = new File(filePath);
            multipartEntityBuilder.addPart("file", new FileBody(file));
            addRequestBody(multipartEntityBuilder);
            addRequestHeaders(httpPost);

            // For updates task's progress
            CustomHttpEntity.ProgressListener progressListener = progress -> publishProgress((int) progress);

            // POST
            httpPost.setEntity(new CustomHttpEntity(multipartEntityBuilder.build(), progressListener));

            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Server response
                responseString = EntityUtils.toString(httpEntity);
            } else {
                responseString = "Error occurred! Http Status Code: " + statusCode;
            }
        } catch (UnsupportedEncodingException | ClientProtocolException e) {
            e.printStackTrace();
            Log.e("UPLOAD", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseString;
    }

    @Override
    protected void onPreExecute() {
        if(uploadAsyncListener != null)
            uploadAsyncListener.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        if(uploadAsyncListener != null)
            uploadAsyncListener.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if(uploadAsyncListener != null)
            uploadAsyncListener.onProgressUpdate(progress[0]);
    }
}