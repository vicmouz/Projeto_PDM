package br.edu.ifpe.tads.pdm.urbano.auth;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifpe.tads.pdm.urbano.HomeActivity;
import br.edu.ifpe.tads.pdm.urbano.MapsActivity;

public class FirebaseAuthListener implements FirebaseAuth.AuthStateListener {

    private final Activity activity;

    public  FirebaseAuthListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Intent intent = null;
        if((user != null) && !(activity instanceof MapsActivity)) {
            intent = new Intent(activity, MapsActivity.class);
        }

        if ((user == null) && (activity instanceof MapsActivity)) {
            intent = new Intent(activity, SignInActivity.class);
        }

        if(intent != null) {
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
