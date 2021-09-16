package project.pansari.LoginAndSignupPackage.SignupPackage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import project.pansari.Models.SignupCredential;
import project.pansari.R;

public class SignupActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText emailField;
    private EditText contactField;
    private EditText shopNameField;
    private EditText shopAddressField;
    private EditText pinCodeField;
    private EditText passwordField;
    private EditText confirmPasswordField;

    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;

    private SignupActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        char type = getIntent().getCharExtra("type", 'd');

        nameField = findViewById(R.id.signup_name_edittext);
        emailField = findViewById(R.id.signup_email_edittext);
        contactField = findViewById(R.id.signup_contact_edittext);
        shopNameField = findViewById(R.id.signup_shopname_edittext);
        shopAddressField = findViewById(R.id.signup_address_edittext);
        pinCodeField = findViewById(R.id.signup_pincode_edittext);
        passwordField = findViewById(R.id.signup_password_edittext);
        confirmPasswordField = findViewById(R.id.signup_confirmpassword_edittext);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
        builder.setMessage("E-mail verification mail has been sent.\nPlease verify and login again!");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        alertDialog = builder.create();

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignupActivityViewModel(getApplication(), type);
            }
        }).get(SignupActivityViewModel.class);

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showProgressDialog();
                } else {
                    hideProgressDialog();
                }
            }
        });

        viewModel.getIsSignedup().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showAlertDialog();
                }
            }
        });
    }

    private void showAlertDialog() {
        alertDialog.show();
    }

    public void registerButtonClick(View view) {

        clearErrors();

        String name = nameField.getEditableText().toString().trim();
        String shopName = shopNameField.getEditableText().toString().trim();
        String email = emailField.getEditableText().toString().trim();
        String contact = contactField.getEditableText().toString().trim();
        String shopAddress = shopAddressField.getEditableText().toString().trim();
        String pinCode = pinCodeField.getEditableText().toString().trim();
        String password = passwordField.getEditableText().toString().trim();
        String confirmPassword = confirmPasswordField.getEditableText().toString().trim();

        SignupCredential signupCredential = new SignupCredential(name, shopName, email, contact, shopAddress, pinCode, password, confirmPassword);

        switch (signupCredential.getValidStatus()) {
            case 0:
                viewModel.signupUser(signupCredential);
                break;
            case 1:
                nameField.setError(signupCredential.getMessage());
                break;
            case 2:
                shopNameField.setError(signupCredential.getMessage());
                break;
            case 3:
                emailField.setError(signupCredential.getMessage());
                break;
            case 4:
                contactField.setError(signupCredential.getMessage());
                break;
            case 5:
                shopAddressField.setError(signupCredential.getMessage());
                break;
            case 6:
                pinCodeField.setError(signupCredential.getMessage());
                break;
            case 7:
                passwordField.setError(signupCredential.getMessage());
                break;
            case 8:
                confirmPasswordField.setError(signupCredential.getMessage());
                break;
        }

    }

    private void clearErrors() {
        nameField.setError(null);
        shopNameField.setError(null);
        emailField.setError(null);
        contactField.setError(null);
        shopAddressField.setError(null);
        pinCodeField.setError(null);
        passwordField.setError(null);
        confirmPasswordField.setError(null);
    }

    private void showProgressDialog() {
        progressDialog.show();
    }

    private void hideProgressDialog() {
        progressDialog.hide();
    }
}