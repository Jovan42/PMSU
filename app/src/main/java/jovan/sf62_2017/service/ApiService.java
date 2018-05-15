package jovan.sf62_2017.service;

import java.util.List;

import jovan.sf62_2017.model.Comment;
import jovan.sf62_2017.model.Post;
import jovan.sf62_2017.model.Tag;
import jovan.sf62_2017.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by jovan on 10-May-18.
 */

public interface ApiService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("tags/{id}")
    Call<Tag> getTag(@Path("id") String id);
    @GET("tags/")
    Call<List<Tag>> getTag();
    @POST("tags/")
    Call<Tag> postTag(@Body Tag tag);
    @PUT("tags/")
    Call<Tag> putTag(@Body Tag tag);
    @DELETE("tags/{id}")
    Call<Boolean> deleteTag(@Path("id") String id);

    @GET("users/{id}")
    Call<User> getUser(@Path("id") String id);
    @GET("users/")
    Call<List<User>> getUser();
    @POST("users/log")
    Call<Boolean> logInUser(@Body User user);
    @POST("users/")
    Call<User> postUser(@Body User user);
    @PUT("users/")
    Call<User> putUser(@Body User user);
    @DELETE("users/{id}")
    Call<User> deleteUser(@Path("id") String id);

    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") Integer id);
    @GET("posts/")
    Call<List<Post>> getPost();
    @POST("posts/")
    Call<Post> postPost(@Body Post post);
    @PUT("posts/")
    Call<Post> putPost(@Body Post user);
    @DELETE("posts/{id}")
    Call<Post> deletePost(@Path("id") String id);

    @GET("comments/{id}")
    Call<Comment> getComment(@Path("id") String id);
    @GET("comments/")
    Call<List<Comment>> getComment();
    @POST("comments/")
    Call<Comment> postComment(@Body Comment comment);
    @PUT("comments/")
    Call<Comment> putComment(@Body Comment comment);
    @DELETE("comments/{id}")
    Call<Boolean> deleteComment(@Path("id") String id);
}
