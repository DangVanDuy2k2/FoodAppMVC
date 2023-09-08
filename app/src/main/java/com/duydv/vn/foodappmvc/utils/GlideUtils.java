package com.duydv.vn.foodappmvc.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.duydv.vn.foodappmvc.R;

public class GlideUtils {
    public static void loadUrl(String url, ImageView imageView){
        if(StringUtils.isEmty(url)){
            imageView.setImageResource(R.drawable.no_image);
            return;
        }

        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.no_image)
                .into(imageView);
    }
}
