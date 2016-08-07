package com.example.lalytto.sadora;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.lalytto.sadora.Controllers.AppCtrl;
import com.example.lalytto.sadora.Controllers.SessionCtrl;
import com.example.lalytto.sadora.Models.User;
import com.example.lalytto.sadora.Services.HttpService;
import com.example.lalytto.sadora.Views.RegistreActivity;
import com.example.lalytto.sadora.Views.SessionActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonArray;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.Manifest.permission.READ_CONTACTS;
import static com.google.android.gms.common.api.GoogleApiClient.*;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnConnectionFailedListener {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final String MySession = "Lalytto";

    // UI references.
    private AutoCompleteTextView inputUser;
    private EditText inputPass;
    private AppCtrl ctrl;


    // Session Manager Class
    SessionCtrl session;


    //Signin button
    private SignInButton signInButton;
    //Signing Options
    private GoogleSignInOptions gso;
    //google api client
    private GoogleApiClient mGoogleApiClient;
    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctrl = new AppCtrl(this);
        getSession();

        // Set up the login form.
        inputUser = (AutoCompleteTextView) findViewById(R.id.usuario);
        inputPass = (EditText) findViewById(R.id.password);

        //Initializing google signin option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing google signin option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signinbutton
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Setting onclick listener to signing button
        signInButton.setOnClickListener(this);

        Button submitBtn = (Button) findViewById(R.id.submit_login);
        submitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                submitLogin();
            }
        });

        Button btnRegistre = (Button) findViewById(R.id.submit_registre);
        btnRegistre.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                ctrl.activitiesCtrl.changeActivity(LoginActivity.this, RegistreActivity.class);
            }
        });
    }

    //This function will option signing intent
    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }


    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            ctrl.elementsService.displayToast(acct.getDisplayName());

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();

            editor.putBoolean("isLogged", true); // Storing boolean - true/false
            editor.putString("name", acct.getDisplayName()); // Storing string
            editor.putString("email", acct.getEmail()); // Storing integer
            editor.putString("personId", acct.getId()); // Storing floatg
            editor.putString("personPhoto", String.valueOf(acct.getPhotoUrl()));

            editor.commit(); // commit changes

            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

/*
            //Initializing image loader
            imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                    .getImageLoader();

            imageLoader.get(acct.getPhotoUrl().toString(),
                    ImageLoader.getImageListener(profilePhoto,
                            R.mipmap.ic_launcher,
                            R.mipmap.ic_launcher));

            //Loading image
            profilePhoto.setImageUrl(acct.getPhotoUrl().toString(), imageLoader);
*/
        } else {
            ctrl.elementsService.displayToast("Login Failed");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == signInButton) {
            //Calling signin
            signIn();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void submitLogin(){
        // Check if username, password is filled
        if((inputUser.getText().toString()).trim().length() > 0 && (inputPass.getText().toString()).trim().length() > 0){
            RequestParams params = new RequestParams();
            params.put("usuario_login", inputUser.getText());
            params.put("usuario_password", inputPass.getText());

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(HttpService.uriLogin, params, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    ctrl.elementsService.displayToast("Ok array");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    if(statusCode==200) {
                        try {
                            ctrl.elementsService.displayToast(response.getString("mensaje"));
                            setSession(response.getBoolean("estado"));
                        } catch (JSONException e) {
                            ctrl.elementsService.displayToast("Fallo jsonObject!");
                            e.printStackTrace();
                        }
                    } else {
                        ctrl.elementsService.displayToast("Connect to server failed!");
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    ctrl.elementsService.displayToast("We got an error");
                }

            });
        }else{
            // user didn't entered username or password
            // Show alert asking him to enter the details
            ctrl.elementsService.displayToast("Login failed.., Please enter username and password");
        }

    }

    private void setSession(Boolean state){
        SharedPreferences session = getSharedPreferences(MySession, 0);
        SharedPreferences.Editor editor = session.edit();
        editor.putBoolean("isLogged", state);
        editor.commit();
        if(state){
            ctrl.activitiesCtrl.changeActivity(LoginActivity.this, SessionActivity.class);
            finish();
        }
    }

    private void getSession(){
        SharedPreferences session = getSharedPreferences(MySession, 0);
        boolean isLogged = session.getBoolean("isLogged", false);
        if(isLogged){
            ctrl.activitiesCtrl.changeActivity(LoginActivity.this, SessionActivity.class);
            finish();
            ctrl.elementsService.displayToast("Is logged");
        } else {
            ctrl.elementsService.displayToast("No Is logged");
        }
    }

}