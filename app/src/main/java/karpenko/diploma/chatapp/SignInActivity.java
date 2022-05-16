package karpenko.diploma.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference usersDatabaseReference;
    private EditText userPasswordEditText;
    private EditText userEmailEditText;
    private EditText userNameEditText;
    private TextView changeLoginSignIn;
    private Button loginSignInButton;

    private boolean loginModeActive;

    public static final String TAG = "SignInActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        database = FirebaseDatabase.getInstance();
        usersDatabaseReference = database.getReference().child("users");

        userPasswordEditText = findViewById(R.id.passwordEditText);
        userEmailEditText = findViewById(R.id.emailEditText);
        userNameEditText = findViewById(R.id.nameEditText);
        changeLoginSignIn = findViewById(R.id.tapToLoginSignIn);
        loginSignInButton = findViewById(R.id.loginSignUpButton);

        mAuth = FirebaseAuth.getInstance();

        loginSignInButton.setOnClickListener(view -> loginSignUpUser(userEmailEditText.getText().toString().trim(),
                userPasswordEditText.getText().toString().trim()));

        changeLoginSignIn.setOnClickListener(view -> toggleLoginMode());

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(SignInActivity.this, UsersListActivity.class));
        }
    }

    private void loginSignUpUser(String email, String password) {

        if (loginModeActive){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent intent = new Intent(SignInActivity.this, UsersListActivity.class);
                            intent.putExtra("userName", userNameEditText.getText().toString().trim());
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    });
        }else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            createUser(user);
                            //updateUI(user);
                            Intent intent = new Intent(SignInActivity.this, UsersListActivity.class);
                            intent.putExtra("userName", userNameEditText.getText().toString().trim());
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    });
        }
    }

    private void createUser(FirebaseUser firebaseUser) {
        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(userNameEditText.getText().toString().trim());
        usersDatabaseReference.push().setValue(user);
    }


    public void toggleLoginMode(){
        if (loginModeActive){
            loginModeActive = false;
            loginSignInButton.setText("Sign up");
            changeLoginSignIn.setText("Or login");
            userNameEditText.setEnabled(true);
        }else{
            loginModeActive = true;
            loginSignInButton.setText("Login");
            changeLoginSignIn.setText("Or sign in");
            userNameEditText.setEnabled(false);
        }
    }

}