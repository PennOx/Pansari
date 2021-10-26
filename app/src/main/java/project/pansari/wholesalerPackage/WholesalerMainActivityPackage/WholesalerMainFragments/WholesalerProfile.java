package project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments;

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
import project.pansari.models.Wholesaler;
import project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainActivityViewModel;


public class WholesalerProfile extends Fragment {

    private WholesalerMainActivityViewModel viewModel;
    private FragmentProfileBinding binding;

    public WholesalerProfile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(WholesalerMainActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        binding.setOnClickLogoutButton(v -> signout());

        viewModel.getWholesaler().observe(getViewLifecycleOwner(), new Observer<Wholesaler>() {
            @Override
            public void onChanged(Wholesaler wholesaler) {
                setData(wholesaler);
            }
        });

        return binding.getRoot();
    }

    private void setData(Wholesaler w) {
        if (w.getImage() != null) {
            Glide.with(getContext()).load(w.getImage()).into(binding.profileProfileImage);
        }

        binding.setUser(w);
    }

    private void signout() {
        Auth.getInstance().signOut();
        Intent intent = new Intent(getContext(), StartActivity.class);
        startActivity(intent);
    }
}