package project.pansari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void shopkeeperButtonClick(View view){
        startLoginActivity('s');
    }

    public void wholesalerButtonClick(View view){
        startLoginActivity('w');
    }

    private void startLoginActivity(char type) {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}