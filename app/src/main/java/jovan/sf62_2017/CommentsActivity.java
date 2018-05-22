package jovan.sf62_2017;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jovan.sf62_2017.adapters.CommentListAdapter;
import jovan.sf62_2017.adapters.DrawerListAdapter;
import jovan.sf62_2017.model.Comment;
import jovan.sf62_2017.model.Post;
import jovan.sf62_2017.service.ServiceUtils;
import jovan.sf62_2017.tools.ReusableObjects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity {
    //TODO: Dodavanje novog komentara
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        final CharSequence mTitle;
        mTitle = getTitle();
        DrawerLayout mDrawerLayout = findViewById(R.id.drawerLayout);
        ListView mDrawerList = findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this);

        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new CommentsActivity.DrawerItemClickListener());

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

        Integer id = getIntent().getIntExtra("post_id", -1);
        setComments(id);
        findViewById(R.id.btnAddComment).setOnClickListener(v -> addComment());

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    startActivity(new Intent(CommentsActivity.this, SettingsActivity.class));

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
            startActivity(new Intent(CommentsActivity.this, CreatePostActivity.class));
        }
        return true;
    }

    private void setComments(int id) {
        Log.d("TAGG id", Integer.toString(id));
        if (id == -1) return;
        Call<List<Comment>> callPosts = ServiceUtils.service.getComment();
        List<Comment> comments = new ArrayList<>();
        callPosts.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Comment>> call, @NonNull Response<List<Comment>> response) {
                if(response.body() != null) {
                    Log.d("TAGG", Integer.toString(response.body().size()));
                    for (Comment com: response.body()) {
                        if(com.getPost() != null && com.getPost() == id) comments.add(com);
                    }

                    CommentListAdapter commentListAdapterAdapter = new CommentListAdapter(getApplicationContext(), comments);
                    ListView commentsList = findViewById(R.id.commentsList);
                    commentsList.setAdapter(commentListAdapterAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong username and password",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Comment>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Server error",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addComment(){
        SharedPreferences sp =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String loggedUser = sp.getString("logged_user", "");
        Integer postId = getIntent().getIntExtra("post_id", -1);


        if(postId != -1) {
            Comment comment = new Comment();
            comment.setDate(new Date());
            EditText et = findViewById(R.id.etComment);
            EditText etTitle = findViewById(R.id.etCommentTitle);
            comment.setDescription(et.getText().toString());
            comment.setTitle(etTitle.getText().toString());
            comment.setUser(loggedUser);
            comment.setPost(postId);
            Call<Comment> callPosts = ServiceUtils.service.postComment(comment);
            callPosts.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(@NonNull Call<Comment> call, @NonNull Response<Comment> response) {
                    if(response.body() != null) {
                        Toast.makeText(getApplicationContext(), "Comment created.",
                                Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), PostsActivity.class));
                        finish();

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Wrong username and password",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Comment> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "Server error",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
