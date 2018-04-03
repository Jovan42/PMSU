package jovan.sf62_2017;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    private Post newPost() {
        String desc = "Lorem ipsum dolor sit amet, at mel causae partiendo, usu et splendide intellegat forensibus, fierent adipisci cu vim. No mea temporibus contentiones, latine blandit appetere per ex. Tota sonet invenire ius ne. Tempor prompta recteque sea eu.";
        User user = new User(0, "Marko", null, "Marko", "password123", null, null);
        Location location = new Location("");
        location.setLatitude(0);
        location.setLongitude(0);
        Post post =  new Post(0, "Novi Post", "desc", null, user, new Date(),
                new Location(""), new ArrayList<Tag>(), new ArrayList<Comment>(), 10, 10);

    }
}
