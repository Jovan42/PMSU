package jovan.sf62_2017;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jovan.sf62_2017.adapters.DrawerListAdapter;
import jovan.sf62_2017.model.Post;
import jovan.sf62_2017.model.Tag;
import jovan.sf62_2017.service.ServiceUtils;
import jovan.sf62_2017.tools.ReusableObjects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity {
    String uri = "";
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

            (findViewById(R.id.etPostTitle)).setEnabled(false);
            (findViewById(R.id.etDesc)).setEnabled(false);
            ((Button)findViewById(R.id.btnCreate)).setText(R.string.edit);
            setPost(postId);
        } else setTags(null);


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
        (findViewById(R.id.btnCreate)).setOnClickListener(v -> createPost());
        findViewById(R.id.btnPostPhoto).setOnClickListener(v -> takePicture());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    private void setPost(int id) {
        if (id == -1) {
            setTags(null);

            return;
        }
        Call<Post> callPosts = ServiceUtils.service.getPost(id);

        callPosts.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.body() != null) {
                    Post post = response.body();
                    EditText etPostTitle = findViewById(R.id.etPostTitle);
                    EditText etPostDesc = findViewById(R.id.etDesc);
                    Log.d("TAG", post.getTitle());
                    etPostTitle.setText(post.getTitle());
                    etPostDesc.setText(post.getDescription());
                    etPostTitle.setEnabled(true);
                    etPostDesc.setEnabled(true);
                    setTags(post.getTagIds());

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


        if(postId == -1) {
            Post post = new Post();
            post.setDate( new Date());
            post.setUser(loggedUser);
            Log.d("TAG", loggedUser);
            post.setTitle(title);
            post.setDescription(desc);
            post.setTagIds(new ArrayList<>());
            post.setCommentIds(new ArrayList<>());
            post.setPhotoUrl(uri);
            post.setTagIds(getTags());
            Call<Post> callPosts = ServiceUtils.service.postPost(post);
            callPosts.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                    if(response.body() != null) {
                        Toast.makeText(getApplicationContext(), "Post created.",
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
                public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "Server error",
                            Toast.LENGTH_LONG).show();
                }
            });
        } else {

            Call<Post> callPosts = ServiceUtils.service.getPost(postId);

            callPosts.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                    if(response.body() != null) {
                        Post post = response.body();
                        post.setTitle(title);
                        post.setDescription(desc);
                        post.setPhotoUrl(uri);
                        post.setTagIds(getTags());
                        Call<Post> callPosts = ServiceUtils.service.putPost(post);
                        callPosts.enqueue(new Callback<Post>() {
                            @Override
                            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                                if(response.body() != null) {
                                    Toast.makeText(getApplicationContext(), "Post edited.",
                                            Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), PostsActivity.class));
                                    finish();

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Wrong username and password",
                                            Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), PostsActivity.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                                Toast.makeText(getApplicationContext(), "Server error",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
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

    public void takePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ImageButton imageView = findViewById(R.id.btnPostPhoto);
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());

                Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(imageBitmap);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                uri = data.getData().toString();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void setTags(List<Integer> tags){
        Call<List<Tag>> callPosts = ServiceUtils.service.getTag();
        callPosts.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tag>> call, @NonNull Response<List<Tag>> response) {
                if(response.body() != null) {
                    GridLayout cbContainer = findViewById(R.id.checkboxContainer);
                    if (tags == null) {
                        for (Tag tag : response.body()) {
                            Log.d("TAG", Integer.toString(response.body().size()));
                            CheckBox checkBox = new CheckBox(getApplicationContext());
                            checkBox.setText(tag.getName());
                            checkBox.setId(tag.getId());
                            checkBox.setTextColor(getResources().getColor(R.color.white));
                            cbContainer.addView(checkBox);
                        }

                    } else {
                        for (Tag tag : response.body()) {
                            CheckBox checkBox = new CheckBox(getApplicationContext());
                            checkBox.setText(tag.getName());
                            checkBox.setId(tag.getId());
                            checkBox.setTextColor(getResources().getColor(R.color.white));

                            if(tags.contains(tag.getId()))
                                checkBox.setChecked(true);
                            cbContainer.addView(checkBox);
                            for (Integer id: tags) {
                                Log.d("TAG", "tagID: " + id);
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong username and password",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Tag>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Server error",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private List<Integer> getTags() {
        GridLayout checkboxContainer = findViewById(R.id.checkboxContainer);
        List<Integer> tags = new ArrayList<>();
        for(int i = 0; i<checkboxContainer.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) checkboxContainer.getChildAt(i);
            if (checkBox.isChecked())tags.add(checkBox.getId());
        }
        return  tags;

    }
}
