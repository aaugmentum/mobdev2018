package uz.avt9812.mobdev2018;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by azamat on 4/20/2018.
 */

public class ImageViewResizer {
    private float mDisplayWidth;
    private DisplayMetrics mDisplayMetrics;
    private final String TAG = "IVResizer";


    public ImageViewResizer(Context context) {
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        this.mDisplayWidth = mDisplayMetrics.widthPixels;
    }

    public void resizeImageView(ImageView iv, Integer height, Integer width) {
        float ratio = (height*1.0f) / width;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int) mDisplayWidth, (int) (mDisplayWidth *ratio));
        iv.setLayoutParams(lp);
    }
}
