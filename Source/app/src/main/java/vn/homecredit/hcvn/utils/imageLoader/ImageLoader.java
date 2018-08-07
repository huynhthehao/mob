package vn.homecredit.hcvn.utils.imageLoader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoader {
    public static void loadImage(Context context, ImageView iv, String url) {
        Glide.with(context).load(url).into(iv);
    }
}
