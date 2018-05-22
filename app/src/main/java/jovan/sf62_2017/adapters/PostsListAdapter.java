package jovan.sf62_2017.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import jovan.sf62_2017.R;
import jovan.sf62_2017.ReadPostActivity;
import jovan.sf62_2017.model.Post;

public class PostsListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Post> posts;

    public PostsListAdapter(Context context, List<Post> posts) {
        mContext = context;
        this.posts = posts;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sort = sharedPreferences.getString("posts_sort", "Date");
        Log.d("TAG", sort);

        if(sort.equals("Date"   )) {
            Collections.sort(posts, Post.dateComparator);
        } else {
            Collections.sort(posts, Post.rateComparator);
        }
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
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
            view = inflater.inflate(R.layout.posts_list_item, null);
        } else {
            view = convertView;
        }
        TextView author = view.findViewById(R.id.tvPostAuthor);
        TextView title = view.findViewById(R.id.tvPostTitle);
        TextView desc = view.findViewById(R.id.tvPostDesc);
        ImageView postImage = view.findViewById(R.id.ivPostPhoto);

        String authorS = "By: " + posts.get(position).getUser();
        author.setText(authorS);
        title.setText(posts.get(position).getTitle());
        desc.setText(posts.get(position).getDescription());

        postImage.setImageDrawable(mContext.getDrawable(R.drawable.nyan_cat));

        view.setOnClickListener(view1 -> {
            Intent intent = new Intent(mContext, ReadPostActivity.class);
            intent.putExtra("post_id", posts.get(position).getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });
        return view;
    }
}
