package uz.avt9812.mobdev2018;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by azamat on 5/5/2018.
 */

public class DrawableSaver implements ActivityCompat.OnRequestPermissionsResultCallback {
    private Activity mActivity;
    private final int STORAGE_ACCESS_CODE = 11;
    private Drawable mDrawable;
    private String mFileName;

    public DrawableSaver(Activity activity) {
        this.mActivity = activity;
    }

    public void saveDrawable (final Drawable drawable, final String fileName) {
        //Saving file in another thread
        Runnable saveFileTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bm = ((BitmapDrawable) drawable).getBitmap();
                String path = Environment.getExternalStorageDirectory() + File.separator + "Backsplash";
                File file = new File(path, fileName + ".png");
                FileOutputStream fos;
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                        file.mkdir();
                    }

                    fos = new FileOutputStream(file);

                    bm.compress(Bitmap.CompressFormat.PNG, 100, fos);

                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        };


        if (checkPermission()) {
            Thread thread = new Thread(saveFileTask);
            thread.start();
            Toast.makeText(mActivity, R.string.saved, Toast.LENGTH_SHORT).show();
        }
        else {
            mDrawable = drawable;
            mFileName = fileName;

            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_ACCESS_CODE);


        }
    }

    private boolean checkPermission () {
        return ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_ACCESS_CODE)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                saveDrawable(mDrawable, mFileName);
            else
                Toast.makeText(mActivity, R.string.permission_denied, Toast.LENGTH_SHORT).show();
    }
}
