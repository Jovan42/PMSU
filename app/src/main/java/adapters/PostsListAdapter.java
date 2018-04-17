package adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import jovan.sf62_2017.R;
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
    public View getView(int position, View convertView, ViewGroup parent) {
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
        ImageView postImage = view.findViewById(R.id.ivPostPhoto);

        author.setText(posts.get(position).getAuthor().getUsername());
        title.setText(posts.get(position).getTitle());
        postImage.setImageBitmap(posts.get(position).getPhoto());

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
                location, new ArrayList<Tag>(), new ArrayList<Comment>(), 10, 10);

        post.getComments().add(new Comment(0, "Komentar" + id, "Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim."
                , user, new Date(), post, 15, 15));
        post.getTags().add(new Tag(0, "Post", null));
        return post;
    }
}
