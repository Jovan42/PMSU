package adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jovan.sf62_2017.R;
import jovan.sf62_2017.ReadPostActivity;
import model.Comment;
import model.Post;
import model.Tag;
import model.User;

public class PostsListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Post> posts;


    public PostsListAdapter(Context context) {
        mContext = context;
        posts = new ArrayList<>();
        posts.add(newPost("1"));
        posts.add(newPost("2"));
        posts.add(newPost("3"));
        posts.add(newPost("4"));
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sort = sharedPreferences.getString("posts_sort", "Date");
        Log.d("TAG", sort);

        if(sort.equals("Date")) {
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

        author.setText("By" + posts.get(position).getAuthor().getUsername());
        title.setText(posts.get(position).getTitle());
        desc.setText(posts.get(position).getDescription());
        postImage.setImageBitmap(posts.get(position).getPhoto());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder geocoder;
                String location = "unknown";
                List<Address> addresses;
                geocoder = new Geocoder(mContext, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation( posts.get(position).getLocation().getLatitude(),  posts.get(position).getLocation().getLongitude(), 1);
                    if(addresses.size() > 0 ) {
                        String city = addresses.get(0).getLocality();
                        String country = addresses.get(0).getCountryName();
                        location = city + ", " + country;
                    }
                } catch (IOException e) {
                }


                Intent intent = new Intent(mContext, ReadPostActivity.class);
                intent.putExtra("Title", posts.get(position).getTitle());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
                intent.putExtra("Date", dateFormat.format( posts.get(position).getDate()));
                intent.putExtra("Desc", posts.get(position).getDescription());
                intent.putExtra("Dislikes", posts.get(position).getDislikes());
                intent.putExtra("Likes", posts.get(position).getLikes());
                intent.putExtra("Location", location);
                intent.putExtra("Author", posts.get(position).getAuthor().getUsername());
                mContext.startActivity(intent);

            }
        });

        return view;
    }
    private Post newPost(String id) {
        String desc = "Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu.Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu.Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu.";
        User user = new User(0, "User" + id, null, "User" + id, "password123", null, null);
        Location location = new Location("");
        location.setLatitude(45.2671);
        location.setLongitude(19.8335);
        Bitmap photo = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.nyan_cat);


        Post post =  new Post(0, "Post" + id, desc, photo, user, new Date(),
                location, new ArrayList<Tag>(), new ArrayList<Comment>(), 100-Integer.parseInt(id), 10);

        post.getComments().add(new Comment(0, "Komentar" + id, "Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim."
                , user, new Date(), post, 15, 15));
        post.getTags().add(new Tag(0, "Post", null));
        return post;
    }
}
