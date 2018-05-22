package jovan.sf62_2017;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import jovan.sf62_2017.adapters.DrawerListAdapter;
import jovan.sf62_2017.model.Post;
import jovan.sf62_2017.model.Tag;
import jovan.sf62_2017.service.ServiceUtils;
import jovan.sf62_2017.tools.ReusableObjects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadPostActivity extends AppCompatActivity {

    //TODO: Ucitavanje slike
    //TODO: Ispis toasta za svaku akciju korisnika
    Boolean editable = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        final CharSequence mTitle;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("post_id", -1);
        SharedPreferences sp =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String loggedUser = sp.getString("logged_user", "");

        setPost(id, loggedUser);

        //((ImageView)findViewById(R.id.ivPostPhoto)).setImageBitmap(posts.getPhoto());

        findViewById(R.id.btnComments).setOnClickListener(view -> {
            if(id == -1) return;
            Intent intent1 = new Intent(ReadPostActivity.this, CommentsActivity.class);
            intent1.putExtra("post_id", id);
            startActivity(intent1);
        });

        findViewById(R.id.btnLike).setOnClickListener(view -> like(id, loggedUser,  ((TextView)findViewById(R.id.tvPostAuthor)).getText().toString()));
        findViewById(R.id.btnDislike).setOnClickListener(view -> dislike(id, loggedUser, ((TextView)findViewById(R.id.tvPostAuthor)).getText().toString()));

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
            int menuItem_Delete = 6;
            MenuItem delete_item = menu.add(0, menuItem_Delete, 0, R.string.delete);
            delete_item.setIcon(R.drawable.delete_icon);
            delete_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);

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
        if(item.getItemId() == 6) {
            Integer id = getIntent().getIntExtra("post_id", -1);
            deletePost(id);
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
                    ((TextView)findViewById(R.id.tvPostDesc)).setText(post.getDescription());
                    ((TextView)findViewById(R.id.tvPostAuthor)).setText(post.getUser());
                    ((TextView)findViewById(R.id.tvPostDate)).setText(post.getDate());
                    ((TextView)findViewById(R.id.tvPostLocation)).setText(R.string.location);
                    ((TextView)findViewById(R.id.tvPostLikes)).setText(String.valueOf(post.getLikes()));
                    ((TextView)findViewById(R.id.tvPostDislikes)).setText(String.valueOf(post.getDislikes()));
                    try {
                        ImageView imageView = (ImageView) findViewById(R.id.ivPostPhoto);
                        //InputStream inputStream = getContentResolver().openInputStream(Uri.parse(post.getPhotoUrl()));
                        Log.d("TAGG", Uri.parse(post.getPhotoUrl()).toString());
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(post.getPhotoUrl()))
                                ;

                        imageView.setImageBitmap(bitmap);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    } catch (Exception e) {
                        Log.d("TAGG", e.getMessage());
                    }
                    getTags(post.getTagIds());
                    /*StringBuilder tags = new StringBuilder();
                    for (Tag tag:post.getTags()) {
                        tags.append(" ").append(tag.getName());
                    }
                    ((TextView)findViewById(R.id.tvPostTags)).setText(tags.toString());*/
                    getLocation(post.getLocationLong(), post.getLocationLat());
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

    private void like(Integer id, String loggedUser, String author) {
        if (loggedUser.equals(author)) {
            Toast.makeText(getApplicationContext(), "You can not like your own post", Toast.LENGTH_LONG).show();
        } else {

            Call<Integer> callPosts = ServiceUtils.service.likePost(id);

            callPosts.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                    if (response.body() != null) {
                        Integer likes = response.body();
                        ((TextView) findViewById(R.id.tvPostLikes)).setText(String.valueOf(likes));
                        Toast.makeText(getApplicationContext(), "Liked",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Request error",
                                Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "Server error",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void dislike(Integer id, String loggedUser, String author) {
        if (loggedUser.equals(author)) {
            Toast.makeText(getApplicationContext(), "You can not dislike your own post", Toast.LENGTH_LONG).show();
        } else {

            Call<Integer> callPosts = ServiceUtils.service.dislikePost(id);

            callPosts.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                    if (response.body() != null) {
                        Integer dislike = response.body();
                        ((TextView) findViewById(R.id.tvPostDislikes)).setText(String.valueOf(dislike));
                        Toast.makeText(getApplicationContext(), "Disliked",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Request error",
                                Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "Server error",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void getLocation(Double lo, Double lat) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        Log.d("TAGG", lo.toString());
        try {
            addresses = geocoder.getFromLocation( lat, lo, 1);
            if(addresses.size() > 0 ) {
                String city = addresses.get(0).getLocality();
                String country = addresses.get(0).getCountryName();
                Log.d("TAGG", addresses.get(0).getCountryName());
                TextView tvLocation = findViewById(R.id.tvPostLocation);
                String location = city + ", " + country;
                tvLocation.setText(location);
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Unknown location", Toast.LENGTH_LONG).show();
        }
    }

    private void getTags(List<Integer> ids) {
        TextView tv = findViewById(R.id.tvPostTags);
        for (Integer id: ids) {
            Call<Tag> tagsCall = ServiceUtils.service.getTag(id);

            tagsCall.enqueue(new Callback<Tag>() {
                @Override
                public void onResponse(@NonNull Call<Tag> call, @NonNull Response<Tag> response) {
                    if (response.body() != null) {
                        String s = tv.getText().toString();
                        s += " " + response.body().getName();
                        tv.setText(s);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Tag> call, @NonNull Throwable t) {
                    Log.d("TAG", "onFailure: ");
                }
            });

        }
    }

    private void deletePost(Integer id) {
        Call<Boolean> callPosts = ServiceUtils.service.deletePost(id);

        callPosts.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if(response.body() != null) {
                    Toast.makeText(getApplicationContext(), "Post deleted",
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
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Server error",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}
