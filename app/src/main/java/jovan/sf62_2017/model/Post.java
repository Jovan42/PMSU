package jovan.sf62_2017.model;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("photoUrl")
    @Expose
    private String photoUrl;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("locationLong")
    @Expose
    private Double locationLong;
    @SerializedName("locationLat")
    @Expose
    private Double locationLat;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("dislikes")
    @Expose
    private Integer dislikes;
    @SerializedName("tagIds")
    @Expose
    private List<Integer> tagIds = null;
    @SerializedName("commentIds")
    @Expose
    private List<Integer> commentIds = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-mm-dd");
        return dt1.format(date);
    }

    public void setDate(Date date) {
        this.date  = date;
    }

    public Double getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(Double locationLong) {
        this.locationLong = locationLong;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }

    /* public List<Integer> getTagIds() {
       List<Integer> ids = new ArrayList<>();
        for (Tag tag:tagIds) {
            ids.add(tag.getId());
        }
        return ids;
    }

    public void setTagIds(List<Integer> tagIds) {
        Call<List<Tag>> tagsCall = ServiceUtils.service.getTag();
        this.tagIds = new ArrayList<>();
        ArrayList<Tag> tmp = new ArrayList<>();
        tagsCall.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tag>> call, @NonNull Response<List<Tag>> response) {
                if (response.body() != null) {
                    tmp.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
            }
        });
        this.tagIds = tmp;
    }*/

    public List<Integer> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(List<Integer> commentIds) {
        this.commentIds = commentIds;
    }

    public static Comparator<Post> dateComparator = (p1, p2) -> p1.getDate().compareTo(p2.getDate());

    public static Comparator<Post> rateComparator = (p1, p2) -> {
        Integer r1 = p1.getLikes() - p1.getDislikes();
        Integer r2 = p2.getLikes() - p2.getDislikes();
        return r1.compareTo(r2);
    };



}