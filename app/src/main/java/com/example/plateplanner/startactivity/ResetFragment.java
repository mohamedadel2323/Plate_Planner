package com.example.plateplanner.startactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.plateplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


public class ResetFragment extends Fragment {
    TextInputEditText emailEt;
    AppCompatButton doneBtn;
    ImageButton backBtn;
    LottieAnimationView resetLoading;

    public ResetFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEt = view.findViewById(R.id.resetEmailEt);
        doneBtn = view.findViewById(R.id.doneResetButton);
        resetLoading = view.findViewById(R.id.resetLoadingAnimation);
        backBtn = view.findViewById(R.id.resetBackButton);

        doneBtn.setOnClickListener(v -> {
            if (emailEt.getText().toString().equals("")) {
                Toast.makeText(getContext(), "Enter your email please", Toast.LENGTH_SHORT).show();
            } else {
                resetLoading.setVisibility(View.VISIBLE);
                resetLoading.playAnimation();
                String email = emailEt.getText().toString().trim();
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Reset email has been sent", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(v).navigateUp();
                        } else {
                            Toast.makeText(getContext(), "Make sure your email and try again", Toast.LENGTH_SHORT).show();
                        }
                        resetLoading.pauseAnimation();
                        resetLoading.setVisibility(View.GONE);
                    }
                });
            }
        });

        backBtn.setOnClickListener(v -> {
            Navigation.findNavController(getView()).navigateUp();
        });

    }
}