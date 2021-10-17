package project.pansari.loginAndSignupPackage.SignupPackage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import project.pansari.R;
import project.pansari.databinding.ActivitySignupBinding;
import project.pansari.models.SignupCredential;

public class SignupActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;

    private ActivitySignupBinding binding;
    private SignupActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        setupAccountTypeLayout();
        setupProgressDialog();

        setSupportActionBar(binding.signupToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignupActivityViewModel(getApplication());
            }
        }).get(SignupActivityViewModel.class);

        binding.signupCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClick();
            }
        });

        viewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showProgressDialog();
                } else {
                    hideProgressDialog();
                }
            }
        });

        viewModel.isSignedUp().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showAlertDialog();
                }
            }
        });
    }

    private void setupAccountTypeLayout() {
        List<String> accountTypes = new ArrayList<>();
        accountTypes.add("Wholesaler");
        accountTypes.add("Retailer");
        ArrayAdapter<String> a = new ArrayAdapter<>(this, R.layout.account_type_item, accountTypes);
        ((AutoCompleteTextView) binding.signupBusinessType.getEditText()).setText(getString(R.string.select_account_type), false);
        ((AutoCompleteTextView) binding.signupBusinessType.getEditText()).setAdapter(a);
    }

    private void showAlertDialog() {
        alertDialog.show();
    }

    public void onRegisterClick() {

        clearErrors();

        String name = binding.signupOwnerName.getEditableText().toString().trim();
        String shopName = binding.signupBusinessName.getEditableText().toString().trim();
        String email = binding.signupEmail.getEditableText().toString().trim();
        String contact = binding.signupBusinessContact.getEditableText().toString().trim();
        String shopAddress = binding.signupBusinessAddress.getEditableText().toString().trim();
        String pinCode = binding.signupBusinessPin.getEditableText().toString().trim();
        String password = binding.signupPassword.getEditableText().toString().trim();
        String confirmPassword = binding.signupConfirmPassword.getEditableText().toString().trim();
        char type;

        switch (binding.signupBusinessType.getEditText().getText().toString()) {
            case "Wholesaler":
                type = 'w';
                break;
            case "Retailer":
                type = 's';
                break;
            default:
                type = 'a';
        }

        SignupCredential signupCredential = new SignupCredential(name, shopName, email, contact, shopAddress, pinCode, password, confirmPassword, type);

        switch (signupCredential.getValidStatus()) {
            case 0:
                viewModel.signupUser(signupCredential);
                break;
            case 1:
                binding.signupOwnerName.setError(signupCredential.getMessage());
                break;
            case 2:
                binding.signupBusinessName.setError(signupCredential.getMessage());
                break;
            case 3:
                binding.signupEmail.setError(signupCredential.getMessage());
                break;
            case 4:
                binding.signupBusinessContact.setError(signupCredential.getMessage());
                break;
            case 5:
                binding.signupBusinessAddress.setError(signupCredential.getMessage());
                break;
            case 6:
                binding.signupBusinessPin.setError(signupCredential.getMessage());
                break;
            case 7:
                binding.signupPassword.setError(signupCredential.getMessage());
                break;
            case 8:
                binding.signupConfirmPassword.setError(signupCredential.getMessage());
                break;
            case 9:
                binding.signupBusinessType.setError(signupCredential.getMessage());
        }

    }

    private void clearErrors() {
        binding.signupOwnerName.setError(null);
        binding.signupBusinessName.setError(null);
        binding.signupEmail.setError(null);
        binding.signupBusinessContact.setError(null);
        binding.signupBusinessAddress.setError(null);
        binding.signupBusinessPin.setError(null);
        binding.signupPassword.setError(null);
        binding.signupConfirmPassword.setError(null);
    }

    private void setupProgressDialog() {
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
    }

    private void showProgressDialog() {
        progressDialog.show();
    }

    private void hideProgressDialog() {
        progressDialog.hide();
    }
}