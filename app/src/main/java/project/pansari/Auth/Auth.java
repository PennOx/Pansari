package project.pansari.Auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Auth {

    private static FirebaseAuth auth;

    private static void init(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
    }

    public static boolean isSignedIn(){
        init();
        return getCurrentUser() != null;
    }

    public static FirebaseUser getCurrentUser(){
        init();
        return auth.getCurrentUser();
    }
}
