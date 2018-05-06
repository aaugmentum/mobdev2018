package uz.avt9812.mobdev2018.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import uz.avt9812.mobdev2018.R;

/**
 * Created by azamat on 4/21/2018.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private Context mContext;

    FragmentAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return ContentFragment.newInstance(ContentFragment.TYPE_FEATURED);
        else
            return ContentFragment.newInstance(ContentFragment.TYPE_NEW);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return mContext.getString(R.string.tab_featured);
        else
            return mContext.getString(R.string.tab_new);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
