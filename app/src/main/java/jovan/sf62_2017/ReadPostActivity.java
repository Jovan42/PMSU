package jovan.sf62_2017;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jovan.sf62_2017.adapters.DrawerListAdapter;
import jovan.sf62_2017.adapters.PostsListAdapter;
import jovan.sf62_2017.model.Comment;
import jovan.sf62_2017.model.Post;
import jovan.sf62_2017.model.Tag;
import jovan.sf62_2017.model.User;
import jovan.sf62_2017.service.ServiceUtils;
import jovan.sf62_2017.tools.ReusableObjects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadPostActivity extends AppCompatActivity {

    Boolean editable = false;
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

        mDrawerLayout.addDrawerListener(ReusableObjects.getCustomActionBar(mDrawerLayout,
                toolbar, this, mTitle));
        StringBuilder tags = new StringBuilder();

        Integer id = intent.getIntExtra("post_id", -1);
        SharedPreferences sp =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String loggedUser = sp.getString("logged_user", "");

        setPost(id, loggedUser);
        //((ImageView)findViewById(R.id.ivPostPhoto)).setImageBitmap(posts.getPhoto());

        findViewById(R.id.btnComments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id == -1) return;
                Intent intent = new Intent(ReadPostActivity.this, CommentsActivity.class);
                intent.putExtra("post_id", id);
                startActivity(intent);
            }
        });
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
        if(editable) {
            int menuItem_EditId = 5;
            MenuItem edit_item = menu.add(0, menuItem_EditId, 0, R.string.edit);
            edit_item.setIcon(R.drawable.edit_icon);
            edit_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(ReadPostActivity.this, CreatePostActivity.class));
        }
        if(item.getItemId() == 5) {
            Intent intent = new Intent(ReadPostActivity.this, CreatePostActivity.class);
            Integer id = getIntent().getIntExtra("post_id", -1);
            intent.putExtra("post_id", id);
            startActivity(intent);
        }
        return true;
    }
    private void setPost(int id, String loggedUser) {
        if(id == -1) return;
        Call<Post> callPosts = ServiceUtils.service.getPost(id);

        callPosts.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if(response.body() != null) {
                    Post post = response.body();
                    ((TextView)findViewById(R.id.tvPostTitle)).setText(post.getTitle());
                    ((TextView)findViewById(R.id.tvPostTags)).setText("Tags : " + post.getTagIds().toString());
                    ((TextView)findViewById(R.id.tvPostDesc)).setText(post.getDescription());
                    ((TextView)findViewById(R.id.tvPostAuthor)).setText(post.getUser());
                    ((TextView)findViewById(R.id.tvPostDate)).setText(post.getDate().toString());
                    ((TextView)findViewById(R.id.tvPostLocation)).setText("Location");
                    ((TextView)findViewById(R.id.tvPostLikes)).setText(String.valueOf(post.getLikes()));
                    ((TextView)findViewById(R.id.tvPostDislikes)).setText(String.valueOf(post.getDislikes()));

                    Log.d("TAGG", loggedUser);
                    if(loggedUser != null && loggedUser.equals(post.getUser())) {
                        editable = true;
                        invalidateOptionsMenu();
                    } else editable = false;


                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong username and password",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Server error",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}
