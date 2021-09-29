package project.pansari.LoginAndSignupPackage.SignupPackage;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import project.pansari.Auth.Auth;
import project.pansari.Database.Database;
import project.pansari.Models.Shopkeeper;
import project.pansari.Models.SignupCredential;
import project.pansari.Models.Wholesaler;

public class SignupActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isSignedup;

    public SignupActivityViewModel(@NonNull Application application) {
        super(application);

        isLoading = new MutableLiveData<>(false);
        isSignedup = new MutableLiveData<>(false);
    }

    public LiveData<Boolean> getIsSignedup() {
        return isSignedup;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private void showLoading() {
        isLoading.setValue(true);
    }

    private void hideLoading() {
        isLoading.setValue(false);
    }

    public void signupUser(SignupCredential signupCredential) {
        showLoading();

        Auth.getInstance().createUserWithEmailAndPassword(signupCredential.getEmail(), signupCredential.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    if (signupCredential.getAccountType() == 's') {
                        Shopkeeper currentUser = new Shopkeeper();
                        currentUser.setAddress(signupCredential.getShopAddress());
                        currentUser.setContact(signupCredential.getContactNumber());
                        currentUser.setEmail(signupCredential.getEmail());
                        currentUser.setName(signupCredential.getName());
                        currentUser.setPinCode(signupCredential.getPinCode());
                        currentUser.setShopName(signupCredential.getShopName());
                        currentUser.setSid(Auth.getCurrentUserUid());

                        Database.getShopkeeperRef().child(Auth.getCurrentUser().getUid()).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                processTask(task);
                            }
                        });
                    } else {
                        Wholesaler currentUser = new Wholesaler();
                        currentUser.setAddress(signupCredential.getShopAddress());
                        currentUser.setContact(signupCredential.getContactNumber());
                        currentUser.setEmail(signupCredential.getEmail());
                        currentUser.setName(signupCredential.getName());
                        currentUser.setPinCode(signupCredential.getPinCode());
                        currentUser.setShopName(signupCredential.getShopName());
                        currentUser.setWid(Auth.getCurrentUserUid());

                        Database.getPinRef(currentUser.getPinCode()).child(currentUser.getWid()).setValue(true);
                        Database.getWholesalersRef().child(Auth.getCurrentUser().getUid()).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                processTask(task);
                            }
                        });
                    }
                } else {
                    hideLoading();
                    Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("SignupError", task.getException().getMessage());
                }
            }
        });
    }

    private void processTask(Task<Void> task) {
        if (task.isSuccessful()) {
            Auth.getCurrentUser().sendEmailVerification();
            Auth.getInstance().signOut();
            hideLoading();
            isSignedup.setValue(true);
        } else {
            hideLoading();
            Toast.makeText(getApplication(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
