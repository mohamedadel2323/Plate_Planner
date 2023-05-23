package com.example.plateplanner.startactivity.network;

import androidx.annotation.NonNull;

import com.example.plateplanner.startactivity.model.AuthModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseCalls implements FirebaseSource{

    private static FirebaseCalls firebaseCalls = null;

    private FirebaseCalls(){

    }

    public static synchronized FirebaseCalls getInstance(){
        if (firebaseCalls == null){
            firebaseCalls = new FirebaseCalls();
        }
        return firebaseCalls;
    }

    @Override
    public void signIn(AuthModel authModel ,  FirebaseDelegate firebaseDelegate) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(authModel.getEmail(), authModel.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseDelegate.onSuccess();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                firebaseDelegate.onFail(e.getLocalizedMessage());
            }
        });
    }

    @Override
    public void signup(AuthModel authModel , FirebaseDelegate firebaseDelegate) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(authModel.getEmail(), authModel.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseDelegate.onSuccess();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        firebaseDelegate.onFail(e.getLocalizedMessage());
                    }
                });
    }
}
