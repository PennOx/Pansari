package project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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

        binding.setOnClickProfileImage(v -> changeProfilePic());

        viewModel.getWholesaler().observe(getViewLifecycleOwner(), this::setData);

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 999) {
            if (resultCode == RESULT_OK) {
                Uri inputUri = data.getData();
                viewModel.setProfilePicture(inputUri);

            } else {

                Toast.makeText(getContext(), "Working bad", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Working terribly", Toast.LENGTH_LONG).show();
        }
    }

    private void changeProfilePic() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 999);
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