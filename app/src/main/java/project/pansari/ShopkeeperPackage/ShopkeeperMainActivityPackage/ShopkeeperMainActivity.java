package project.pansari.ShopkeeperPackage.ShopkeeperMainActivityPackage;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import project.pansari.R;

public class ShopkeeperMainActivity extends AppCompatActivity {

    private ShopkeeperMainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_main);

        viewModel = new ViewModelProvider(this).get(ShopkeeperMainActivityViewModel.class);

        viewModel.getActiveFragment().observe(this, new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                setFragment(fragment);
            }
        });

        BottomNavigationView navigationView = findViewById(R.id.shopkeeper_main_bottom_navigation);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                viewModel.updateMenuItemFragment(item.getItemId());
                return true;
            }
        });

    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.shopkeeper_main_fragment_view, fragment).commit();
    }
}