package jovan.sf62_2017;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import adapters.DrawerListAdapter;
import model.Comment;
import model.Post;
import model.Tag;
import model.User;

public class ReadPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        final CharSequence mTitle;
        Intent intent = getIntent();
        mTitle  = getTitle();
        DrawerLayout mDrawerLayout = findViewById(R.id.drawerLayout);
        ListView mDrawerList =  findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this);

        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.action_bar_icon);
            actionBar.setHomeButtonEnabled(true);
        }

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.app_name,
                R.string.pass
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_icon);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Project");
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_back_icon);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        StringBuilder tags = new StringBuilder();

        Post posts = newPost("1");
        for (Tag tag: posts.getTags())
            tags.append(tag.getName()).append(", ");



        ((TextView)findViewById(R.id.tvPostTitle)).setText(intent.getStringExtra("Title"));
        ((TextView)findViewById(R.id.tvPostTags)).setText("Tags : " + tags);
        ((TextView)findViewById(R.id.tvPostDesc)).setText(intent.getStringExtra("Desc"));
        ((TextView)findViewById(R.id.tvPostAuthor)).setText(intent.getStringExtra("Author"));
        ((TextView)findViewById(R.id.tvPostDate)).setText(intent.getStringExtra("Dates"));
        ((TextView)findViewById(R.id.tvPostLocation)).setText(intent.getStringExtra("Location"));
        ((TextView)findViewById(R.id.tvPostLikes)).setText(String.valueOf(intent.getIntExtra("Likes", -1)));
        ((TextView)findViewById(R.id.tvPostDislikes)).setText(String.valueOf(intent.getIntExtra("Dislikes", -1)));
        ((ImageView)findViewById(R.id.ivPostPhoto)).setImageBitmap(posts.getPhoto());


    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    Intent intent = new Intent(ReadPostActivity.this, PostsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case 1:
                    startActivity(new Intent(ReadPostActivity.this, SettingsActivity.class));

                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(ReadPostActivity.this, CreatePostActivity.class));
        }
        return true;
    }

    private Post newPost(String id) {
        String desc = "Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu.Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu.Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu. Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu.";
        User user = new User(0, "User" + id, null, "User" + id, "password123", null, null);
        Location location = new Location("");
        location.setLatitude(45.2671);
        location.setLongitude(19.8335);
        Bitmap photo = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.nyan_cat);


        Post post =  new Post(0, "Post" + id, desc, photo, user, new Date(),
                location, new ArrayList<Tag>(), new ArrayList<Comment>(), 10, 10);

        post.getComments().add(new Comment(0, "Komentar" + id, "Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim."
                , user, new Date(), post, 15, 15));
        post.getTags().add(new Tag(0, "Post", null));
        return post;
    }
}
