package uz.avt9812.mobdev2018.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.avt9812.mobdev2018.App;
import uz.avt9812.mobdev2018.R;
import uz.avt9812.mobdev2018.Utils;
import uz.avt9812.mobdev2018.api.API;
import uz.avt9812.mobdev2018.dataObjects.getPhotosModel.Photo;

/**
 * Created by azamat on 4/21/2018.
 */

public class ContentFragment extends Fragment implements Callback<List<Photo>>, SwipeRefreshLayout.OnRefreshListener {
    public static final String TYPE_FEATURED = "featured";
    public static final String TYPE_NEW = "new";
    private static final String TYPE = "type";
    private static final String TAG = "ContentFragment";

    private String mCurrentType;
    private RecyclerView mRecyclerView;
    private ArrayList<Photo> mData = new ArrayList<>();
    private PhotoListAdapter mAdapter;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean mIsLoading = false;
    private static int mPageNumber = 1;

    public ContentFragment() {

    }

    public static ContentFragment newInstance(String type) {
        ContentFragment contentFragment = new ContentFragment();

        Bundle args = new Bundle();
        args.putCharSequence(TYPE, type);

        contentFragment.setArguments(args);
        return contentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentType = getArguments().getString(TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycler_view, container, false);

        //Bind main components
        mRecyclerView = root.findViewById(R.id.rv_main);
        mProgressBar = root.findViewById(R.id.pb_load);
        mRefreshLayout = root.findViewById(R.id.swipe_main);

        //Set LinearLayout manager to the Recycler view
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(lm);

        MyOnScrollListener listener = new MyOnScrollListener();
        mRecyclerView.addOnScrollListener(listener);

        mAdapter = new PhotoListAdapter(mData, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPhotos(mPageNumber);
    }

    private void getPhotos(int pageNumber) {
        //Set swipe refresh listener
        mRefreshLayout.setOnRefreshListener(this);

        //Asynchronously get list of photo urls from Unsplash API
        if (mCurrentType.equals(TYPE_FEATURED))
            App.getApi().getCuratedPhotos(Utils.KEY, pageNumber).enqueue(this);
        else if (mCurrentType.equals(TYPE_NEW))
            App.getApi().getPhotos(Utils.KEY, pageNumber).enqueue(this);

        mIsLoading = true;
    }

    @Override
    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
        mIsLoading = false;
        Log.d(TAG, "in onResponse");

        if (response.code() == 200) {
            ArrayList<Photo> newData = (ArrayList<Photo>) response.body();

            int startPos = mData.size();

            if (mPageNumber == 1)
                mData.clear();

            mData.addAll(newData);
            mAdapter.notifyItemRangeInserted(startPos, newData.size());

            mProgressBar.setVisibility(View.GONE);
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onFailure(Call<List<Photo>> call, Throwable t) {
        mProgressBar.setVisibility(View.GONE);
        mRefreshLayout.setRefreshing(false);

        if (t.getClass() == UnknownHostException.class)
            Toast.makeText(App.getContext(), R.string.no_internet_error, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(App.getContext(), R.string.unknown_error, Toast.LENGTH_SHORT).show();

        t.printStackTrace();
    }

    @Override
    public void onRefresh() {
        mPageNumber = 1;
        Log.d(TAG, String.valueOf(mPageNumber));
        getPhotos(mPageNumber);
    }

    class MyOnScrollListener extends RecyclerView.OnScrollListener {
        MyOnScrollListener() {
            super();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            //Pagination, load more if in the end of the screen
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!mIsLoading) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= API.PAGE_SIZE) {
                    mPageNumber++;
                    Log.i(TAG, String.valueOf(mPageNumber));
                    mRefreshLayout.setRefreshing(true);
                    getPhotos(mPageNumber);
                }
            }

        }
    }
}
