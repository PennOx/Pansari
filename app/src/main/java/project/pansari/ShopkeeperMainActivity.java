package project.pansari;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import project.pansari.Auth.Auth;

public class ShopkeeperMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void logOut(View view) {
        Auth.getInstance().signOut();
    }
}