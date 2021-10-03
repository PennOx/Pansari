package project.pansari.LoginAndSignupPackage.StartActivityPackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import project.pansari.Auth.Auth;
import project.pansari.Database.Database;
import project.pansari.LoginAndSignupPackage.LoginPackage.LoginActivity;
import project.pansari.R;
import project.pansari.ShopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainActivity;
import project.pansari.WholesalerPackage.WholesalerMainActivityPackage.WholesalerMainActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (Auth.isSignedIn()) {

            if (Auth.getCurrentUser().isEmailVerified()) {

                Database.getShopkeeperRef().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(Auth.getCurrentUser().getUid())) {
                            startShopkeeperMainActivity();
                        } else {
                            startWholesalerMainActivity();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(StartActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setMessage("Please verify your email and login again!");
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Auth.getInstance().signOut();
                        sendToLogin();
                        dialog.cancel();
                        finish();
                    }
                }).setNegativeButton("Resend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Auth.getCurrentUser().sendEmailVerification();

                        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                        builder.setMessage("E-mail verification mail has been sent.\nPlease verify and login again!");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Auth.getInstance().signOut();
                                sendToLogin();
                                dialog.cancel();
                                finish();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }

        } else {
            sendToLogin();
        }

    }

    private void startWholesalerMainActivity() {
        Intent intent = new Intent(StartActivity.this, WholesalerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void startShopkeeperMainActivity() {
        Intent intent = new Intent(StartActivity.this, ShopkeeperMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void sendToLogin() {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}