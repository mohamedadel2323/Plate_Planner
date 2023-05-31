package com.example.plateplanner.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plateplanner.model.AuthModel;
import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.PlanMeal;
import com.example.plateplanner.model.UploadPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FirebaseCalls implements FirebaseSource {

    private static FirebaseCalls firebaseCalls = null;

    private FirebaseCalls() {

    }

    public static synchronized FirebaseCalls getInstance() {
        if (firebaseCalls == null) {
            firebaseCalls = new FirebaseCalls();
        }
        return firebaseCalls;
    }

    @Override
    public void signIn(AuthModel authModel, FirebaseDelegate firebaseDelegate) {
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
    public void signup(AuthModel authModel, FirebaseDelegate firebaseDelegate) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(authModel.getEmail(), authModel.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(authModel.getName())
                                    .build();
                            user.updateProfile(profileUpdates);
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
    public void uploadMeals(String email, List<PlanMeal> planMeals, List<MealPojo> favMeals, FirebaseDelegate firebaseDelegate) {
        UploadPojo uploadPojo = new UploadPojo(planMeals , favMeals);
        FirebaseFirestore.getInstance().collection("Meals").document(email).set(uploadPojo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firebaseDelegate.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        firebaseDelegate.onFail(e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void downloadMeals(String email, FirebaseDelegate firebaseDelegate) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Meals").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UploadPojo uploadPojo = document.toObject(UploadPojo.class);
                        // Process mealsList here
                        firebaseDelegate.onDownloadMealsSuccess(uploadPojo.getFavMeals() , uploadPojo.getPlanMeals());
                    } else {
                        Log.d("FirebaseCalls", "No such document");
                    }
                } else {
                    firebaseDelegate.onFail(task.toString());
                }
            }
        });
    }

}
