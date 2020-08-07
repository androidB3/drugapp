package com.example.drugapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drugapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class SingupActivity extends AppCompatActivity {

    EditText edtEmail,edtUsername,edtPassword,edtEntirePassword;
    Button btnRegist;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


        private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
//                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +          //no white spaces
                    ".{4,10}" +               //at least 4 characters
                    "$");
//==========================================================Create=========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        AnhXa();




        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if( !validateUserName() |  !validatePassWord() | !validateEmail() | !validateEntirePassword()){

                    Toast.makeText(SingupActivity.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
//                    if(edtEntirePassword.getText()!=edtPassword.getText()){
//                        Toast.makeText(SingupActivity.this, "Mật Khẩu Nhập Lại Sai", Toast.LENGTH_SHORT).show();
//
//                    }
                }
                else {
                    DangKy();

                }
            }
        });
    }


//==============================================================Create =============================================================

    private void AnhXa(){
        edtEmail=findViewById(R.id.editTextEmail);
        edtUsername=findViewById(R.id.editTextUsername);
        edtPassword=findViewById(R.id.editTextPassword);
        edtEntirePassword=findViewById(R.id.editTextEntirePassword);
        btnRegist=findViewById(R.id.btnRegist);

        // khoi tao authen firebase
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
    }

    //================================================================
    private void DangKy(){
        String email=edtEmail.getText().toString();
        String password=edtPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            // Sign in success, update UI with the signed-in user's information
                            if(task.isSuccessful())
                            {
                                Toast.makeText(SingupActivity.this,"Dang Ky Thanh Cong",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SingupActivity.this, MainActivity.class);
                                startActivity(intent);
                                mAuth.signOut();
                            }else {
                                Toast.makeText(SingupActivity.this,"Email Wrong !!!",Toast.LENGTH_SHORT).show();
                            }
                        }

                });
    }

    //=========================================================================

    //kiem tra email
    private boolean validateEmail(){
        String emailInput=edtEmail.getText().toString().trim();
        if(emailInput.isEmpty()){
            edtEmail.setError("Field can't be empty!");
            return false;
        }
        else {
            edtEmail.setError(null);
            return true;
        }
    }

    private boolean validateUserName(){
        String usernameInput = edtUsername.getText().toString().trim();
        if(usernameInput.isEmpty()){
            edtUsername.setError("Field can't be empty!");
            return false;
        }else {

            edtUsername.setError(null);
            return true;
        }
    }
    // Kiểm tra PassWord

//    public boolean isNullOrEmpty(String s) {
//        return s == null || s.trim().isEmpty();
//    }

    private boolean validatePassWord(){
        String passwordInput = edtPassword.getText().toString();
        if(passwordInput.equals("") && !passwordInput.matches("\\S")) {
            edtPassword.setError("Field can't be empty!");
            return false;
        } else if(!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            edtPassword.setError("Password too weak!,space not allowed");
            return false;
        }
        else
        {
            edtPassword.setError(null);
            return true;
        }
    }
//
    //kiem tra xac nhan password
    private boolean validateEntirePassword(){
        String EntirePasswordInput = edtEntirePassword.getText().toString().trim();
        String Password=edtPassword.getText().toString().trim();
        if(!EntirePasswordInput.equals(Password)){
            edtEntirePassword.setError("Entire Wrong !!!");

            return false;
        }else if(EntirePasswordInput.isEmpty()){
            edtEntirePassword.setError("Field can't be empty!");
            return false;

        }
        else {

            edtEntirePassword.setError(null);
            return true;
        }
    }
    //=============================================================================

}