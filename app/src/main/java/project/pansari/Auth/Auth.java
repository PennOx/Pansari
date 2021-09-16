package project.pansari.Auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Auth {

    private static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static FirebaseAuth getInstance() {
        return auth;
    }

    public static FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public static String getCurrentUserUid() {
        return auth.getCurrentUser().getUid();
    }

    public static boolean isSignedIn() {
        return getCurrentUser() != null;
    }


}
