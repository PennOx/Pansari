package project.pansari.loginAndSignupPackage.LoginPackage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import project.pansari.R;
import project.pansari.databinding.ActivityLoginBinding;
import project.pansari.loginAndSignupPackage.SignupPackage.SignupActivity;
import project.pansari.loginAndSignupPackage.StartActivityPackage.StartActivity;
import project.pansari.models.LoginCredential;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private LoginActivityViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return ((T) new LoginActivityViewModel(getApplication()));
            }
        }).get(LoginActivityViewModel.class);

        viewModel.getIsLoggedIn().observe(this, aBoolean -> {
            if (aBoolean) {
                onLoginSuccess();
            }
        });

        viewModel.getIsLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressDialog();
            } else {
                hideProgressDialog();
            }
        });

        binding.setLoginClickListener(v -> onLoginButtonClick());

        binding.setCreateAccountClickListener(v -> onRegisterButtonClick());
    }

    private void onRegisterButtonClick() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    private void onLoginButtonClick() {
        LoginCredential loginCredential = getLoginCredentials();

        switch (loginCredential.getValidStatus()) {
            case 0:
                viewModel.login(loginCredential);
                break;
            case 1:
                binding.emailEditText.setError(loginCredential.getMessage());
                break;
            case 2:
                binding.passwordEditText.setError(loginCredential.getMessage());
                break;
        }
    }

    private LoginCredential getLoginCredentials() {
        String email = binding.emailEditText.getText().toString().trim();
        String pass = binding.passwordEditText.getText().toString().trim();

        return new LoginCredential(email, pass);

    }

    private void onLoginSuccess() {
        hideProgressDialog();
        Intent intent = new Intent(this, StartActivity.class);
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