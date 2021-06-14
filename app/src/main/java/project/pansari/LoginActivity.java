package project.pansari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import project.pansari.Auth.Auth;

public class LoginActivity extends AppCompatActivity {

    private char type;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        type = getIntent().getCharExtra("type", 'd');

        loginButton = findViewById(R.id.login_signin_btn);
        registerButton = findViewById(R.id.login_register_btn);

    }

    public void registerButtonClick(View view) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void loginButtonClick(View view) {

        disableButtons();

        EditText emailField = findViewById(R.id.login_email_edittext);
        EditText passwordField = findViewById(R.id.login_password_edittext);

        String email = emailField.getEditableText().toString();
        String password = passwordField.getEditableText().toString();

        if (email.isEmpty()) {
            emailField.setError("Email is required.");
            enableButtons();

        }

        if (password.length() < 6) {
            passwordField.setError("Password should be at least 6 characters.");
            enableButtons();

        }

        if (!email.isEmpty() && password.length() >= 6) {

            Auth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        startMainActivity();
                    } else {
                        Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        enableButtons();
                    }
                }
            });
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void enableButtons() {
        loginButton.setEnabled(true);
        registerButton.setEnabled(true);
    }

    private void disableButtons() {
        loginButton.setEnabled(false);
        registerButton.setEnabled(false);
    }
}