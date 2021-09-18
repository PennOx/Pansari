package project.pansari.WholesalerPackage.WholesalerMainActivityPackage;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import project.pansari.R;

public class WholesalerMainActivity extends AppCompatActivity {

    private WholesalerMainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholesaler_main);

        BottomNavigationView bottomNavigation = findViewById(R.id.wholesaler_main_bottom_navigation);

        viewModel = new ViewModelProvider(this).get(WholesalerMainActivityViewModel.class);

        viewModel.getActiveFragment().observe(this, new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                setFragment(fragment);
            }
        });

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                viewModel.updateMenuItemFragment(item.getItemId());

                return true;
            }
        });

    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.wholesaler_main_fragment_view, fragment).commit();
    }
}