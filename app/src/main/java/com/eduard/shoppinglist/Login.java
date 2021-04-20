package com.eduard.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    private TextView info;
    private LoginButton facebookLogin;
    private Button loginButton;
    private EditText usernameInput;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private DrawerLayout drawer;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences(getString(R.string.login_shared_pref), Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        usernameInput = findViewById(R.id.username_input);

        // Facebook login
        facebookLogin = findViewById(R.id.facebook_login);

        callbackManager = CallbackManager.Factory.create();
        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Log.i("asd", response.getRawResponse());
                                    sharedPreferencesEditor.putBoolean(getString(R.string.login_state), true);
                                    sharedPreferencesEditor.putString(getString(R.string.login_username), response.getJSONObject().get("name").toString());
                                    sharedPreferencesEditor.apply();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i("asd", "Cancel!");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("asd", "Error!");
                Log.i("asd", error.getMessage());
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
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });

        // Check if user is logged in
        if(sharedPreferences.getBoolean(getString(R.string.login_state), false)) {
            startActivity(new Intent(Login.this, MainActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
