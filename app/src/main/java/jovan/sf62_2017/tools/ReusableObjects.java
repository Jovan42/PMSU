package jovan.sf62_2017.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import jovan.sf62_2017.LoginActivity;
import jovan.sf62_2017.R;

/**
 * Created by jovan on 11-May-18.
 */

public class ReusableObjects {
    public static  ActionBarDrawerToggle getCustomActionBar(DrawerLayout mDrawerLayout,
                                                            Toolbar toolbar, AppCompatActivity app, CharSequence mTitle) {
        ActionBarDrawerToggle customActionBar = new ActionBarDrawerToggle(
                app,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.pass  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                app.getSupportActionBar().setTitle(mTitle);
                app.getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_icon);
                app.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                app.getSupportActionBar().setTitle("Project");
                app.getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_back_icon);
                app.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                SharedPreferences sp =  PreferenceManager.getDefaultSharedPreferences(app.getApplicationContext());
                String loggedUser = sp.getString("logged_user", "");

                ((TextView)app.findViewById(R.id.etUsername)).setText(loggedUser);
                ((Button)app.findViewById(R.id.btnLogOut)).setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ApplySharedPref")
                    @Override
                    public void onClick(View view) {
                        sp.edit().putString("logged_user", "").commit();

                        app.startActivity(new Intent(app, LoginActivity.class));
                        app.finish();
                    }
                });
            }
        };

        return customActionBar;
    }


}
