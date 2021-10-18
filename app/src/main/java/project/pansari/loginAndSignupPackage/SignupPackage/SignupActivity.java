package project.pansari.loginAndSignupPackage.SignupPackage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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

        setSupportActionBar(binding.toolBar);
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

        binding.setCreateAccountClickListener(v -> onRegisterClick());

        viewModel.isLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressDialog();
            } else {
                hideProgressDialog();
            }
        });

        viewModel.isSignedUp().observe(this, aBoolean -> {
            if (aBoolean) {
                showAlertDialog();
            }
        });
    }

    private void setupAccountTypeLayout() {
        List<String> accountTypes = new ArrayList<>();
        accountTypes.add("Wholesaler");
        accountTypes.add("Retailer");
        ArrayAdapter<String> a = new ArrayAdapter<>(this, R.layout.account_type_item, accountTypes);
        ((AutoCompleteTextView) binding.businessType.getEditText()).setText(getString(R.string.select_account_type), false);
        ((AutoCompleteTextView) binding.businessType.getEditText()).setAdapter(a);
    }

    private void showAlertDialog() {
        alertDialog.show();
    }

    public void onRegisterClick() {

        clearErrors();

        String name = binding.ownerName.getEditableText().toString().trim();
        String shopName = binding.businessName.getEditableText().toString().trim();
        String email = binding.email.getEditableText().toString().trim();
        String contact = binding.contact.getEditableText().toString().trim();
        String shopAddress = binding.address.getEditableText().toString().trim();
        String pinCode = binding.pin.getEditableText().toString().trim();
        String password = binding.password.getEditableText().toString().trim();
        String confirmPassword = binding.confirmPassword.getEditableText().toString().trim();
        char type;

        switch (binding.businessType.getEditText().getText().toString()) {
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
                binding.ownerName.setError(signupCredential.getMessage());
                break;
            case 2:
                binding.businessName.setError(signupCredential.getMessage());
                break;
            case 3:
                binding.email.setError(signupCredential.getMessage());
                break;
            case 4:
                binding.contact.setError(signupCredential.getMessage());
                break;
            case 5:
                binding.address.setError(signupCredential.getMessage());
                break;
            case 6:
                binding.pin.setError(signupCredential.getMessage());
                break;
            case 7:
                binding.password.setError(signupCredential.getMessage());
                break;
            case 8:
                binding.confirmPassword.setError(signupCredential.getMessage());
                break;
            case 9:
                binding.businessType.setError(signupCredential.getMessage());
        }

    }

    private void clearErrors() {
        binding.ownerName.setError(null);
        binding.businessName.setError(null);
        binding.email.setError(null);
        binding.contact.setError(null);
        binding.address.setError(null);
        binding.pin.setError(null);
        binding.password.setError(null);
        binding.confirmPassword.setError(null);
    }

    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
        builder.setMessage(R.string.email_verification_alert);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", (dialog, which) -> {
            dialog.cancel();
            finish();
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