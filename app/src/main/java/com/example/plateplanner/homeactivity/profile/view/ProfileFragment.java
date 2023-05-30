package com.example.plateplanner.homeactivity.profile.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.plateplanner.R;
import com.example.plateplanner.startactivity.model.AuthSharedPreferences;
import com.example.plateplanner.startactivity.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    ImageView logoutBtn;
    ImageView profileImg;
    TextView profileName;
    TextView profileEmail;
    AppCompatButton signUpBtn;

    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        profileImg = view.findViewById(R.id.profileImage);
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        signUpBtn = view.findViewById(R.id.signUpButton);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            signUpBtn.setVisibility(View.GONE);
            profileName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            profileEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            Glide.with(getContext()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                    .apply(new RequestOptions().override(300, 300)
                            .placeholder(R.drawable.person)
                            .error(R.drawable.person)).into(profileImg);
        }else {
            logoutBtn.setVisibility(View.GONE);
            profileImg.setImageResource(R.drawable.person);
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                AuthSharedPreferences.getInstance(getContext()).setLoginStatus(false);
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        signUpBtn.setOnClickListener(v ->{
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        });

    }
}