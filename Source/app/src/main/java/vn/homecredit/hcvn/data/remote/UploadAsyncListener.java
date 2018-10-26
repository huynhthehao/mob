package vn.homecredit.hcvn.data.remote;

public interface UploadAsyncListener {
    void onPreExecute();
    void onPostExecute(String result);
    void onProgressUpdate(Integer percentage);
}
