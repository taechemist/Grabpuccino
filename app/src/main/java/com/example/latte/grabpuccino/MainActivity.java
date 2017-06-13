package com.example.latte.grabpuccino;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private String TAG = "grabpuccino";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText emailText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupFirebaseAuth();

        emailText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);

        debugUsernamePassword();

    }

    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    // Testing Intent
                    //Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    private void debugUsernamePassword() {
        if(BuildConfig.DEBUG) {
            emailText.setText("test@g.com");
            passwordText.setText("qwerty");
        }
    }

    public void createAccount(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, R.string.regis_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, R.string.regis_successed, Toast.LENGTH_SHORT).show();
                            emailText.setText("");
                            passwordText.setText("");
                        }
                    }
                });
    }

    public void signIn(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        // Show loading dialog
        if (validateEmailAndPassword(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // Hide loading dialog
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(MainActivity.this, R.string.auth_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, R.string.please_insert_email_and_password, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, R.string.you_are_here, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateEmailAndPassword(String email, String password) {
        return email != null && !email.isEmpty() && password != null && !password.isEmpty();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
