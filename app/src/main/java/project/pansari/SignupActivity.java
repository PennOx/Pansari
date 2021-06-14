package project.pansari;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import project.pansari.Auth.Auth;
import project.pansari.Database.Database;
import project.pansari.Models.Shopkeeper;
import project.pansari.Models.Wholesaler;

public class SignupActivity extends AppCompatActivity {

    private char type;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        type = getIntent().getCharExtra("type", 'd');
        registerButton = findViewById(R.id.signup_register_button);
    }

    public void registerButtonClick(View view) {
        EditText nameField = findViewById(R.id.signup_name_edittext);
        EditText emailField = findViewById(R.id.signup_email_edittext);
        EditText contactField = findViewById(R.id.signup_contact_edittext);
        EditText shopNameField = findViewById(R.id.signup_shopname_edittext);
        EditText shopAddressField = findViewById(R.id.signup_address_edittext);
        EditText pinCodeField = findViewById(R.id.signup_pincode_edittext);
        EditText passwordField = findViewById(R.id.signup_password_edittext);
        EditText confirmPasswordField = findViewById(R.id.signup_confirmpassword_edittext);

        String name = nameField.getEditableText().toString();
        String email = emailField.getEditableText().toString();
        String contact = contactField.getEditableText().toString();
        String shopName = shopNameField.getEditableText().toString();
        String shopAddress = shopAddressField.getEditableText().toString();
        int pinCode = 0;
        try {
            pinCode = Integer.parseInt(pinCodeField.getEditableText().toString());
            if (pinCode < 100000 || pinCode > 999999) {
                throw new Exception();
            }
        } catch (Exception e) {
            pinCode = 0;
        }
        String password = passwordField.getEditableText().toString();
        String confirmPassword = confirmPasswordField.getEditableText().toString();

        if (name.isEmpty()) {
            nameField.setError("Name required.");
        } else if (email.isEmpty()) {
            emailField.setError("E-mail required.");
        } else if(contact.isEmpty() || contact.length() < 10){
          contactField.setError("Enter valid contact number.");
        } else if (shopName.isEmpty()) {
            shopNameField.setError("Shop Name required.");
        } else if (pinCode == 0) {
            pinCodeField.setError("Enter valid pin code.");
        } else if (password.isEmpty()){
            passwordField.setError("Password required.");
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordField.setError("Password mismatched");
        } else {
            disableButtons();
            int finalPinCode = pinCode;
            Auth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        if (type == 's'){
                            Shopkeeper currentUser = new Shopkeeper();
                            currentUser.setAddress(shopAddress);
                            currentUser.setContact(contact);
                            currentUser.setEmail(email);
                            currentUser.setName(name);
                            currentUser.setPinCode(finalPinCode);
                            currentUser.setShopName(shopName);

                            Database.getShopkeeperRef().child(Auth.getCurrentUser().getUid()).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Auth.getCurrentUser().sendEmailVerification();
                                        Auth.getInstance().signOut();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                        builder.setMessage("E-mail verification mail has been sent.\nPlease verify and login again!");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startWelcomeActivity();
                                                dialog.cancel();
                                                finish();
                                            }
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    } else {
                                        enableButtons();
                                        Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Wholesaler currentUser = new Wholesaler();
                            currentUser.setAddress(shopAddress);
                            currentUser.setContact(contact);
                            currentUser.setEmail(email);
                            currentUser.setName(name);
                            currentUser.setPinCode(finalPinCode);
                            currentUser.setShopName(shopName);

                            Database.getWholesalerRef().child(Auth.getCurrentUser().getUid()).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Auth.getCurrentUser().sendEmailVerification();
                                        Auth.getInstance().signOut();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                        builder.setMessage("E-mail verification mail has been sent.\nPlease verify and login again!");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startWelcomeActivity();
                                                dialog.cancel();
                                                finish();
                                            }
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    } else {
                                        enableButtons();
                                        Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    } else {
                        enableButtons();
                        Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void startWelcomeActivity() {
        Intent intent = new Intent(SignupActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    private void disableButtons() {
        registerButton.setEnabled(false);
    }

    private void enableButtons() {
        registerButton.setEnabled(true);
    }
}