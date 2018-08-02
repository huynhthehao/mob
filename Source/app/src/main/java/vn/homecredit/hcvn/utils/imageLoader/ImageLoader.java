package vn.homecredit.hcvn.utils.imageLoader;

import android.content.Context;
import android.widget.ImageView;

public interface ImageLoader {
    void loadImage(Context context, ImageView iv, String url);
}
