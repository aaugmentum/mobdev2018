package uz.avt9812.mobdev2018.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.avt9812.mobdev2018.App;
import uz.avt9812.mobdev2018.R;
import uz.avt9812.mobdev2018.Utils;
import uz.avt9812.mobdev2018.dataObjects.getPhotosModel.Photo;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind main components
        Toolbar toolbar = findViewById(R.id.toolbar);

        mViewPager = findViewById(R.id.vp_main);
        mTabLayout = findViewById(R.id.tl_main);


        //Set title to the Toolbar
        toolbar.setTitle(getString(R.string.app_name));
        mTabLayout.setupWithViewPager(mViewPager);

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mFragmentAdapter);
    }

}
