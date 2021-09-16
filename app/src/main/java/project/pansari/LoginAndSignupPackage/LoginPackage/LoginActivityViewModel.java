package project.pansari.LoginAndSignupPackage.LoginPackage;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import project.pansari.Auth.Auth;
import project.pansari.Models.LoginCredential;

public class LoginActivityViewModel extends AndroidViewModel {

    private char type;
    private MutableLiveData<Boolean> isLoggedIn;
    private MutableLiveData<Boolean> isLoading;

    LoginActivityViewModel(Application app, char type) {
        super(app);
        this.type = type;
        isLoggedIn = new MutableLiveData<>(false);
        isLoading = new MutableLiveData<>(false);
    }

    public LiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void login(LoginCredential loginCredential) {
        //TODO move to Repo..

        showLoading();

        Auth.getInstance().signInWithEmailAndPassword(loginCredential.getEmail(), loginCredential.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    hideLoading();
                    isLoggedIn.setValue(true);
                } else {
                    Toast.makeText(getApplication(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    hideLoading();
                }
            }
        });
    }

    public char getType() {
        return type;
    }

    private void showLoading() {
        isLoading.setValue(true);
    }

    private void hideLoading() {
        isLoading.setValue(false);
    }

}
