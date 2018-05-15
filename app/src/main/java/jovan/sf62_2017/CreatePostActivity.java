package jovan.sf62_2017;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jovan.sf62_2017.adapters.DrawerListAdapter;
import jovan.sf62_2017.adapters.PostsListAdapter;
import jovan.sf62_2017.model.Post;
import jovan.sf62_2017.service.ServiceUtils;
import jovan.sf62_2017.tools.ReusableObjects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        final CharSequence mTitle;
        mTitle = getTitle();
        DrawerLayout mDrawerLayout = findViewById(R.id.drawerLayout);
        ListView mDrawerList = findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this);

        Integer postId = getIntent().getIntExtra("post_id", -1);
        if(postId != -1) {

            ((EditText) findViewById(R.id.etPostTitle)).setEnabled(false);
            ((EditText) findViewById(R.id.etDesc)).setEnabled(false);
            ((Button)findViewById(R.id.btnCreate)).setText("Edit");
            setPost(postId);
        }


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

        ((Button) findViewById(R.id.btnCreate)).setOnClickListener(v -> createPost());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        return true;
    }


    private void setPost(int id) {
        if (id == -1) return;
        Call<Post> callPosts = ServiceUtils.service.getPost(id);

        callPosts.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.body() != null) {
                    Post post = response.body();
                    Log.d("TAG", post.getTitle());
                    ((EditText) findViewById(R.id.etPostTitle)).setText(post.getTitle());
                    ((EditText) findViewById(R.id.etDesc)).setText(post.getDescription());
                    ((EditText) findViewById(R.id.etPostTitle)).setEnabled(true);
                    ((EditText) findViewById(R.id.etDesc)).setEnabled(true);

                } else {
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

    private void createPost() {
        String title = ((EditText) findViewById(R.id.etPostTitle)).getText().toString();
        String desc = ((EditText) findViewById(R.id.etDesc)).getText().toString();
        SharedPreferences sp =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String loggedUser = sp.getString("logged_user", "");
        Integer postId = getIntent().getIntExtra("post_id", -1);
        Post post = new Post();
        //post.setDate(new Date());
        post.setUser(loggedUser);
        post.setTitle(title);
        post.setDescription(desc);
        post.setTagIds(new ArrayList<>());
        post.setCommentIds(new ArrayList<>());
        if(postId != -1) {
            Call<Post> callPosts = ServiceUtils.service.postPost(post);
            callPosts.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                    if(response.body() != null) {
                        Toast.makeText(getApplicationContext(), "Post created.",
                                Toast.LENGTH_LONG).show();

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
        } else {
            Call<Post> callPosts = ServiceUtils.service.putPost(post);
            callPosts.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                    if(response.body() != null) {
                        Toast.makeText(getApplicationContext(), "Post edited.",
                                Toast.LENGTH_LONG).show();

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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    Intent intent = new Intent(CreatePostActivity.this, PostsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case 1:
                    startActivity(new Intent(CreatePostActivity.this, SettingsActivity.class));

                    break;
                default:
                    break;
            }
        }
    }
}
