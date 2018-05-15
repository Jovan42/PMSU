package jovan.sf62_2017.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import jovan.sf62_2017.R;
import jovan.sf62_2017.ReadPostActivity;
import jovan.sf62_2017.model.Comment;

/**
 * Created by jovan on 12-May-18.
 */

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

    @SuppressLint("InflateParams")
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
        TextView author = view.findViewById(R.id.tvCommentAuthor);
        TextView title = view.findViewById(R.id.tvCommentTitle);
        TextView desc = view.findViewById(R.id.tvCommentDesc);
        TextView likes = view.findViewById(R.id.tvCommentLikes);
        TextView dislikes = view.findViewById(R.id.tvCommentDislikes);

        author.setText("By: " + comments.get(position).getUser());
        title.setText(comments.get(position).getTitle());
        desc.setText(comments.get(position).getDescription());

        likes.setText(Integer.toString(comments.get(position).getLikes()));
        dislikes.setText(Integer.toString(comments.get(position).getDislikes()));



        return view;
    }
}
