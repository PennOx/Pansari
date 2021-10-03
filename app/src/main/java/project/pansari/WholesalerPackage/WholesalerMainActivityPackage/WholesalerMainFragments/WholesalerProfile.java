package project.pansari.WholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments;

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
import project.pansari.Auth.Auth;
import project.pansari.Database.Database;
import project.pansari.LoginAndSignupPackage.StartActivityPackage.StartActivity;
import project.pansari.R;
import project.pansari.models.Wholesaler;


public class WholesalerProfile extends Fragment {

    private CircleImageView image;
    private TextView name;
    private TextView sName;
    private TextView address;
    private TextView contactNumber;
    private TextView mail;
    private TextView areaPIN;

    private Wholesaler currentData = null;

    public WholesalerProfile() {
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

        Database.getWholesalersRef().child(Auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Wholesaler w = dataSnapshot.getValue(Wholesaler.class);

                setData(w);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

    private void setData(Wholesaler w) {
        if (w.getImage() != null) {
            Glide.with(getContext()).load(w.getImage()).into(image);
        }
        name.setText(w.getName());
        sName.setText(w.getShopName());
        address.setText(w.getAddress());
        mail.setText(w.getEmail());
        contactNumber.setText(w.getContact());
        areaPIN.setText("" + w.getPinCode());
    }

    private void signout() {
        Auth.getInstance().signOut();
        Intent intent = new Intent(getContext(), StartActivity.class);
        startActivity(intent);
    }
}