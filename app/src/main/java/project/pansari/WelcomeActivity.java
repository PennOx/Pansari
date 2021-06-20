package project.pansari;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import project.pansari.WelcomeFragments.Welcome1;
import project.pansari.WelcomeFragments.Welcome2;
import project.pansari.WelcomeFragments.Welcome3;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ViewPager2 vpg = findViewById(R.id.welcome_pager);
        vpg.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new Welcome1();
                    case 1:
                        return new Welcome2();
                    case 2:
                        return new Welcome3(WelcomeActivity.this);
                }
                return null;
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });

        getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}