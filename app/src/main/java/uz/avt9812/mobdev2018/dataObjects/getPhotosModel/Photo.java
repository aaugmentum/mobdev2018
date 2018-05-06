package uz.avt9812.mobdev2018.dataObjects.getPhotosModel;

import com.google.gson.annotations.SerializedName;

import uz.avt9812.mobdev2018.App;

/**
 * Created by azamat on 4/15/2018.
 */


public class Photo {
    private String id;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    private Integer width;
    private Integer height;
    private Urls urls;

    @SerializedName("likes")
    private Integer likesCount;

    @SerializedName("sponsored")
    private Boolean isSponsored;

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Boolean getSponsored() {
        return isSponsored;
    }

    public void setSponsored(Boolean sponsored) {
        isSponsored = sponsored;
    }

    @Override
    public String toString() {
        return App.getGson().toJson(this);
    }
}
