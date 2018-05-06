package uz.avt9812.mobdev2018;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uz.avt9812.mobdev2018.api.API;

/**
 * Created by azamat on 4/15/2018.
 */

public class App extends Application {

    private static API sUnsplashApi;
    private static Gson sGson;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("INFO: ", "App opened!");
        sGson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(sGson))
                .build();

        sUnsplashApi = retrofit.create(API.class);
    }

    public static API getApi() {
        return sUnsplashApi;
    }

    public static Gson getGson() {
        return sGson;
    }

    public static Context getContext() {
        return getContext();
    }
}
