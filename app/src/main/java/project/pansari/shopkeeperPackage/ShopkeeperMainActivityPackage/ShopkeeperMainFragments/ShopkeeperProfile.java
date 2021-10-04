package project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import project.pansari.R;
import project.pansari.auth.Auth;
import project.pansari.database.Database;
import project.pansari.loginAndSignupPackage.StartActivityPackage.StartActivity;
import project.pansari.models.Shopkeeper;

public class ShopkeeperProfile extends Fragment {

    private CircleImageView image;
    private TextView name;
    private TextView sName;
    private TextView address;
    private TextView contactNumber;
    private TextView mail;
    private TextView areaPIN;


    private Shopkeeper currentData = null;

    public ShopkeeperProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        image = v.findViewById(R.id.profile_image);
        name = v.findViewById(R.id.profile_name);
        sName = v.findViewById(R.id.profile_sname);
        address = v.findViewById(R.id.profile_address);
        contactNumber = v.findViewById(R.id.profile_contact_number);
        mail = v.findViewById(R.id.profile_mail);
        areaPIN = v.findViewById(R.id.profile_area_pin);

        Button logoutButton = v.findViewById(R.id.profile_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });

        Database.getShopkeeperRef().child(Auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Shopkeeper s = dataSnapshot.getValue(Shopkeeper.class);

                setData(s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

    private void setData(Shopkeeper s) {
        if (s.getImage() != null) {
            Glide.with(getContext()).load(s.getImage()).into(image);
        }
        name.setText(s.getName());
        sName.setText(s.getShopName());
        address.setText(s.getAddress());
        mail.setText(s.getEmail());
        contactNumber.setText(s.getContact());
        areaPIN.setText("" + s.getPinCode());
    }

    private void signout() {
        Auth.getInstance().signOut();
        Intent intent = new Intent(getContext(), StartActivity.class);
        startActivity(intent);
    }
}