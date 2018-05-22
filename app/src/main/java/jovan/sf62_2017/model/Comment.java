package jovan.sf62_2017.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.Date;

public class Comment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("post")
    @Expose
    private Integer post;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("dislikes")
    @Expose
    private Integer dislikes;

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
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

    public static Comparator<Comment> dateComparator = (c1, c2) -> c1.getDate().compareTo(c2.getDate());

    public static Comparator<Comment> rateComparator = (p1, p2) -> {
        Integer r1 = p1.getLikes() - p1.getDislikes();
        Integer r2 = p2.getLikes() - p2.getDislikes();
        return r1.compareTo(r2);
    };

}