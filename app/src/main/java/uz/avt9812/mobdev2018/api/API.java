package uz.avt9812.mobdev2018.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import uz.avt9812.mobdev2018.dataObjects.getPhotosModel.Photo;

/**
 * Created by azamat on 4/15/2018.
 */

public interface API {
    int PAGE_SIZE = 15;

    @GET("/photos?per_page=15")
    Call<List<Photo>> getPhotos(@Query("client_id") String clientId, @Query("page") int page);

    @GET("/photos/curated?per_page=15")
    Call<List<Photo>> getCuratedPhotos(@Query("client_id") String clientId, @Query("page") int page);
}
