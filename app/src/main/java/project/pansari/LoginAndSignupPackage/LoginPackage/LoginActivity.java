package project.pansari.LoginAndSignupPackage.LoginPackage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import project.pansari.LoginAndSignupPackage.SignupPackage.SignupActivity;
import project.pansari.LoginAndSignupPackage.StartActivityPackage.StartActivity;
import project.pansari.Models.LoginCredential;
import project.pansari.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;
    private ProgressDialog progressDialog;

    private LoginActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        char type = getIntent().getCharExtra("type", 'd');

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        emailField = ((TextInputLayout) findViewById(R.id.login_email_field)).getEditText();
        passwordField = ((TextInputLayout) findViewById(R.id.login_password_field)).getEditText();

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return ((T) new LoginActivityViewModel(getApplication(), type));
            }
        }).get(LoginActivityViewModel.class);

        viewModel.getIsLoggedIn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    loginSuccess();
                }
            }
        });

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
    }

    public void registerButtonClick(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        intent.putExtra("type", viewModel.getType());
        startActivity(intent);
    }

    public void loginButtonClick(View view) {
        LoginCredential loginCredential = getLoginCredentials();

        switch (loginCredential.getValidStatus()) {
            case 0:
                viewModel.login(loginCredential);
                break;
            case 1:
                emailField.setError(loginCredential.getMessage());
                break;
            case 2:
                passwordField.setError(loginCredential.getMessage());
                break;
        }
    }

    private LoginCredential getLoginCredentials() {
        String email = emailField.getEditableText().toString().trim();
        String pass = passwordField.getEditableText().toString().trim();

        return new LoginCredential(email, pass);

    }

    private void loginSuccess() {
        hideProgressDialog();
        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra("type", viewModel.getType());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showProgressDialog() {
        progressDialog.show();
    }

    private void hideProgressDialog() {
        progressDialog.hide();
        progressDialog.dismiss();
    }

}