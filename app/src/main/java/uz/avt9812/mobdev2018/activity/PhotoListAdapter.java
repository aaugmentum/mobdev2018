package uz.avt9812.mobdev2018.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import uz.avt9812.mobdev2018.ImageViewResizer;
import uz.avt9812.mobdev2018.R;
import uz.avt9812.mobdev2018.dataObjects.getPhotosModel.Photo;

/**
 * Created by azamat on 4/18/2018.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder>{
    private ArrayList<Photo> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private ImageViewResizer mIvResizer;
    private RequestOptions mRequestOptions;
    private ColorDrawable mColorDrawable;

    PhotoListAdapter(ArrayList<Photo> data, Context context) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mIvResizer = new ImageViewResizer(context);
        this.mRequestOptions = new RequestOptions();
        this.mColorDrawable = new ColorDrawable();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Get mData from position
        final Photo item = mData.get(position);
        String url = item.getUrls().getRegular();

        //Set placeholder
        mColorDrawable.setColor(Color.parseColor(item.getColor()));
        mRequestOptions = mRequestOptions.placeholder(mColorDrawable);
        mRequestOptions = mRequestOptions.centerCrop();

        //Resize Image View according to image's ratio
        mIvResizer.resizeImageView(holder.iv, item.getHeight(), item.getWidth());

        //Load the image with Glide
        Glide.with(mContext)
                .load(url)
                .apply(mRequestOptions)
                .into(holder.iv);

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewImageActivity.class);


                intent.putExtra(ViewImageActivity.URL, item.getUrls().getRaw());
                intent.putExtra(ViewImageActivity.NAME, item.getId());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.iv.setImageBitmap(null);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_photo);

        }
    }
}
