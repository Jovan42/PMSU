package jovan.sf62_2017;

import android.content.Intent;
import android.content.SharedPreferences;

import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import jovan.sf62_2017.adapters.DrawerListAdapter;
import jovan.sf62_2017.fragments.SettingsFragment;
import jovan.sf62_2017.tools.ReusableObjects;

//Morao sam da vratim na AppCompatActivity zato sto  PreferenceActivity ne nasledjuje AppCompactActivity
// i nema metode get i SupportActionBar.

// Get i Set ActionBar nije radilo zato sto PreferenceActivity nema ActionBar i javlja se null pointer exception

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);


        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new SettingsFragment())
                .commit();

        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Log.d("TAG On Crate",sharedPreferences.getString("posts_sort", "0000"));
        } catch (Exception e) {
            throw e;
        }



        final CharSequence mTitle;
        mTitle = getTitle();
        DrawerLayout mDrawerLayout = findViewById(R.id.drawerLayout);
        ListView mDrawerList = findViewById(R.id.navList);
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    Intent intent = new Intent(SettingsActivity.this, PostsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
    }
}
