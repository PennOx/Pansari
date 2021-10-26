package project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import project.pansari.R;
import project.pansari.auth.Auth;
import project.pansari.databinding.FragmentProfileBinding;
import project.pansari.loginAndSignupPackage.StartActivityPackage.StartActivity;
import project.pansari.models.Shopkeeper;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainActivityViewModel;

public class ShopkeeperProfile extends Fragment {

    private ShopkeeperMainActivityViewModel viewModel;
    private FragmentProfileBinding binding;

    public ShopkeeperProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(ShopkeeperMainActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        binding.setOnClickLogoutButton(v -> signout());

        viewModel.getShopkeeper().observe(getViewLifecycleOwner(), new Observer<Shopkeeper>() {
            @Override
            public void onChanged(Shopkeeper shopkeeper) {
                setData(shopkeeper);
            }
        });

        return binding.getRoot();
    }

    private void setData(Shopkeeper s) {
        if (s.getImage() != null) {
            Glide.with(getContext()).load(s.getImage()).into(binding.profileProfileImage);
        }

        binding.setUser(s);
    }

    private void signout() {
        Auth.getInstance().signOut();
        Intent intent = new Intent(getContext(), StartActivity.class);
        startActivity(intent);
    }
}