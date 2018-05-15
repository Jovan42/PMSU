package jovan.sf62_2017;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import jovan.sf62_2017.service.ServiceUtils;
import jovan.sf62_2017.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sp =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String loggedUser = sp.getString("logged_user", "");
        if(loggedUser != null && !loggedUser.equals("")) {
            startActivity(new Intent(LoginActivity.this, PostsActivity.class));
            finish();
        }


        (findViewById(R.id.btnLogIn)).setOnClickListener(v -> logIn());
    }

    private void logIn() {
//        startActivity(new Intent(LoginActivity.this, PostsActivity.class));
//        SharedPreferences sp1 =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        sp1.edit().putString("logged_user", "b").apply();
//        finish();

        final String userName = ((EditText)findViewById(R.id.etUsername)).getText().toString();
        String password = ((EditText)findViewById(R.id.etPassword)).getText().toString();

        Call<Boolean> listCall= ServiceUtils.service.logInUser(new User(userName, password));

        listCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if(response.body() != null && response.body()) {
                    Log.d("TAGG", call.request().body().toString());
                    SharedPreferences sp1 =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    sp1.edit().putString("logged_user", userName).apply();
                    startActivity(new Intent(LoginActivity.this, PostsActivity.class));
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
