package project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import project.pansari.R;
import project.pansari.databinding.ActivityShopkeeperMainBinding;

public class ShopkeeperMainActivity extends AppCompatActivity {

    private ShopkeeperMainActivityViewModel viewModel;
    private ActivityShopkeeperMainBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopkeeper_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ShopkeeperMainActivityViewModel(getApplication());
            }
        }).get(ShopkeeperMainActivityViewModel.class);

        viewModel.getActiveFragment().observe(this, new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                setFragment(fragment);
            }
        });

        viewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showProgressDialog();
                } else {
                    hideProgressDialog();
                }
            }
        });

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                viewModel.updateMenuItemFragment(item.getItemId());
                return true;
            }
        });

    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(binding.mainFragment.getId(), fragment).commit();
    }

    private void showProgressDialog() {
        progressDialog.show();
    }

    private void hideProgressDialog() {
        progressDialog.hide();
        progressDialog.dismiss();
    }

}