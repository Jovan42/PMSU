package jovan.sf62_2017.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import jovan.sf62_2017.R;
import jovan.sf62_2017.model.Comment;
import jovan.sf62_2017.service.ServiceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment> comments;


    public CommentListAdapter(Context context, List<Comment> comments) {
        mContext = context;
        this.comments = comments;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sort = sharedPreferences.getString("comment_sort", "Date");
        Log.d("TAG", sort);

        if(sort.equals("Date"   )) {
            Collections.sort(comments, Comment.dateComparator);
        } else {
            Collections.sort(comments, Comment.rateComparator);
        }
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            view = inflater.inflate(R.layout.comment_list_item, null);
        } else {
            view = convertView;
        }
        SharedPreferences sp =  PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        String loggedUser = sp.getString("logged_user", "");
        TextView author = view.findViewById(R.id.tvCommentAuthor);
        TextView title = view.findViewById(R.id.tvCommentTitle);
        TextView desc = view.findViewById(R.id.tvCommentDesc);
        TextView likes = view.findViewById(R.id.tvCommentLikes);
        TextView dislikes = view.findViewById(R.id.tvCommentDislikes);
        String user = "By: " + comments.get(position).getUser();

        author.setText(user);
        title.setText(comments.get(position).getTitle());
        desc.setText(comments.get(position).getDescription());

        likes.setText(Integer.toString(comments.get(position).getLikes()));
        dislikes.setText(Integer.toString(comments.get(position).getDislikes()));

        view.findViewById(R.id.btnLike).setOnClickListener(v -> like(comments.get(position).getId(), loggedUser, comments.get(position).getUser(), view));
        view.findViewById(R.id.btnDislike).setOnClickListener(v -> dislike(comments.get(position).getId(), loggedUser, comments.get(position).getUser(), view));



        return view;
    }

    private void like(Integer id, String loggedUser, String author, View view) {
        if (loggedUser.equals(author)) {
            Toast.makeText(mContext, "You can not like your own comments", Toast.LENGTH_LONG).show();
        } else {

            Call<Integer> callPosts = ServiceUtils.service.likeComment(id);

            callPosts.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                    if (response.body() != null) {
                        Integer likes = response.body();
                        ((TextView) view.findViewById(R.id.tvCommentLikes)).setText(String.valueOf(likes));
                        Toast.makeText(mContext.getApplicationContext(), "Liked",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(mContext.getApplicationContext(), "Request error",
                                Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                    Toast.makeText(mContext.getApplicationContext(), "Server error",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void dislike(Integer id, String loggedUser, String author, View view) {
        if (loggedUser.equals(author)) {
            Toast.makeText(mContext.getApplicationContext(), "You can not dislike your own post", Toast.LENGTH_LONG).show();
        } else {

            Call<Integer> callPosts = ServiceUtils.service.dislikeComment(id);

            callPosts.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                    if (response.body() != null) {
                        Integer dislike = response.body();
                        ((TextView) view.findViewById(R.id.tvCommentDislikes)).setText(String.valueOf(dislike));
                        Toast.makeText(mContext.getApplicationContext(), "Disliked",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(mContext.getApplicationContext(), "Request error",
                                Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                    Toast.makeText(mContext.getApplicationContext(), "Server error",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
