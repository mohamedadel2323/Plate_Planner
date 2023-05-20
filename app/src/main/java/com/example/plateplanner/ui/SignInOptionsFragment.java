package com.example.plateplanner.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.plateplanner.R;


public class SignInOptionsFragment extends Fragment {

    AppCompatButton signInWithEmailBtn;
    ImageButton googleBtn;
    TextView skipTv;
    TextView signInTv;
    AppCompatButton signUp;
    NavController navController;

    public SignInOptionsFragment() {
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
        return inflater.inflate(R.layout.fragment_sign_in_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signInWithEmailBtn = view.findViewById(R.id.emailSignBtn);
        googleBtn = view.findViewById(R.id.googleBtn);
        skipTv = view.findViewById(R.id.skipTv);
        signInTv = view.findViewById(R.id.signInTv);
        signUp = view.findViewById(R.id.emailSignBtn);
        navController = Navigation.findNavController(view);

        signInWithEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        skipTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enter as guest
            }
        });
        signInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to sign in screen
                navController.navigate(SignInOptionsFragmentDirections
                        .actionSignInOptionsFragmentToSignInFragment());
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(SignInOptionsFragmentDirections
                        .actionSignInOptionsFragmentToSignUpFragment());
            }
        });


    }
}