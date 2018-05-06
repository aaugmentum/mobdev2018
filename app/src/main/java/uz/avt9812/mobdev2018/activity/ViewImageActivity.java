package uz.avt9812.mobdev2018.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.otaliastudios.zoom.ZoomImageView;

import uz.avt9812.mobdev2018.DrawableSaver;
import uz.avt9812.mobdev2018.R;

public class ViewImageActivity extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private ZoomImageView mZoomImageView;
    private Toolbar mToolbar;
    private View mBackBtn;
    private DrawableSaver mDrawableSaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        mZoomImageView = findViewById(R.id.iv_photo);
        mToolbar = findViewById(R.id.toolbar);
        mBackBtn = mToolbar.findViewById(R.id.back_button);

        mToolbar.inflateMenu(R.menu.options);
        mToolbar.setOnMenuItemClickListener(this);
        mBackBtn.setOnClickListener(this);

       mDrawableSaver =  new DrawableSaver(this);

        String url = getIntent().getExtras().getString("url");

        Glide.with(this)
                .load(url)
                .into(mZoomImageView);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_button) {
            onBackPressed();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.d("a", "Clicked");


        String name = getIntent().getExtras().getString("name");

        Drawable drawable = mZoomImageView.getDrawable();

        if (drawable != null)
            mDrawableSaver.saveDrawable(drawable, name);
        else
            Toast.makeText(this, R.string.wait_download, Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mDrawableSaver.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
