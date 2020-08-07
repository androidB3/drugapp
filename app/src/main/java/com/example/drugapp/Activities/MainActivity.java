package com.example.drugapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drugapp.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    CardView cv;

    EditText edtUserName;
    EditText edtPassword;
    TextView txvRegist;
    ImageView imgv4;
    LoginButton btnloginfacebook;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;

    private static final String TAG="FacebookAuthentication";
    //==============================================onCreate=====================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        callbackManager=CallbackManager.Factory.create();
        AnhXa();

       // btnloginfacebook.setReadPermissions("email");



//===========================================
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();



//============================================
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateUserName() | !validatePassWord()) {

                    Toast.makeText(MainActivity.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    DangNhap();
                }
            }
        });

        txvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SingupActivity.class);

                startActivity(intent);

            }
        });
//============================================================ cái này để test user current
        imgv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //  Block of code to try
                    Toast.makeText(MainActivity.this, mUser.getEmail(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();
                    //  Block of code to handle errors
                }
            }
        });

        //=====================================================================
        btnloginfacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG,"on success"+loginResult.getAccessToken());
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
               // handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG,"on cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG,"on error"+error);
            }
        });

        accessTokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    mAuth.signOut();
                }
            }
        };


    }

    //=================================================onCreate=============================================================


//    private void handleFacebookToken(AccessToken token){
//        Log.d(TAG,"handle token"+token);
//        AuthCredential credential= FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Log.d(TAG,"sign in with credential:success");
//                  //  updateUI(mUser);
//                }else {
//                    Log.d(TAG,"sign in with credential:fail",task.getException());
//                  //  updateUI(null);
//                }
//            }
//        });
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

//    private void updateUI(FirebaseUser user)
//    {
//        if(user!=null){
//            Toast.makeText(MainActivity.this,user.getDisplayName(),Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_SHORT).show();
//
//        }
//    }

    private void AnhXa()
    {
        cv = (CardView) findViewById(R.id.cardView);
        edtUserName=findViewById(R.id.editTextUsername);
        edtPassword=findViewById(R.id.editTextPassword);
        txvRegist=findViewById(R.id.txvRegist);
        imgv4=findViewById(R.id.imageView4);
        btnloginfacebook= findViewById(R.id.login_button);

    }


    private boolean validateUserName(){
        String usernameInput = edtUserName.getText().toString().trim();
        if(usernameInput.isEmpty()){
            edtUserName.setError("Field can't be empty!");
            return false;
        }else {
//            textInputUserName.setError(null);
            edtUserName.setError(null);
            return true;
        }
    }
    // Kiểm tra PassWord
    private boolean validatePassWord(){
        String passwordInput = edtPassword.getText().toString().trim();
        if(passwordInput.isEmpty()) {
            edtPassword.setError("Field can't be empty!");
            return false;
        }
        else
        {
            edtPassword.setError(null);
            return true;
        }
    }

    //============================================================================================

    private void DangNhap()
    {
        String email=edtUserName.getText().toString();
        String password=edtPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this,"Sai Tên Đăng Nhập Hoặc Mật Khẩu!!!",Toast.LENGTH_SHORT).show();
                            // ...
                        }

                        // ...
                    }
                });

    }








}