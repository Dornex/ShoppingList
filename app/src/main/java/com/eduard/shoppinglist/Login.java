package com.eduard.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class Login extends AppCompatActivity {

    private TextView info;
    private LoginButton facebookLogin;
    private Button loginButton;
    private EditText usernameInput;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences(getString(R.string.login_shared_pref), Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        usernameInput = findViewById(R.id.username_input);

        // Facebook login
        facebookLogin = findViewById(R.id.facebook_login);

        CallbackManager callbackManager = CallbackManager.Factory.create();

        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sharedPreferencesEditor.putBoolean(getString(R.string.login_state), true);
                sharedPreferencesEditor.putString(getString(R.string.login_username), loginResult.getAccessToken().getApplicationId());
                sharedPreferencesEditor.apply();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        // Normal login
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesEditor.putBoolean(getString(R.string.login_state), true);
                sharedPreferencesEditor.putString(getString(R.string.login_username), usernameInput.getText().toString());
                sharedPreferencesEditor.apply();
            }
        });

        // Check if user is logged in
        if(sharedPreferences.getBoolean(getString(R.string.login_state), false)) {
            info = findViewById(R.id.login_screen_enter_username);
            info.setText(sharedPreferences.getString(getString(R.string.login_username), ""));
            setContentView(R.layout.drawer_layout);
        }
    }
}
