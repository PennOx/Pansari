package project.pansari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import project.pansari.Auth.Auth;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Internal

        setContentView(R.layout.activity_start);

        if(Auth.isSignedIn()){

            startMainActivity();

        }else{

            startWelcomeActivity();

        }
        finish();
    }

    private void startWelcomeActivity() {
        Intent intent = new Intent(StartActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    private void startMainActivity() {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
    }
}