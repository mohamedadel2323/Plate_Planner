package com.example.plateplanner.homeactivity.profile.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.plateplanner.R;
import com.example.plateplanner.datebase.ConcreteLocalSource;
import com.example.plateplanner.homeactivity.calender.view.CalenderFragment;
import com.example.plateplanner.homeactivity.favorites.view.FavoritesFragment;
import com.example.plateplanner.homeactivity.profile.presenter.ProfileFragmentPresenter;
import com.example.plateplanner.model.AuthSharedPreferences;
import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.PlanMeal;
import com.example.plateplanner.model.Repository;
import com.example.plateplanner.network.ApiClient;
import com.example.plateplanner.network.FirebaseCalls;
import com.example.plateplanner.startactivity.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class ProfileFragment extends Fragment implements ProfileFragmentViewInterface {

    ImageView logoutBtn;
    ImageView profileImg;
    TextView profileName;
    TextView profileEmail;
    AppCompatButton signUpBtn;
    AppCompatButton uploadDataBtn;
    ProfileFragmentPresenter profileFragmentPresenter;
    List<MealPojo> favMeals;
    List<PlanMeal> mPlanMeals;
    LottieAnimationView loadingAnimationView;

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

        initUi(view);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            profileFragmentPresenter = new ProfileFragmentPresenter(this, Repository.getInstance(AuthSharedPreferences.getInstance(getContext()), FirebaseCalls.getInstance(), ApiClient.getInstance(), ConcreteLocalSource.getInstance(getContext())));
            profileFragmentPresenter.getCashedFavMeals().observe(getViewLifecycleOwner(), new Observer<List<MealPojo>>() {
                @Override
                public void onChanged(List<MealPojo> mealPojos) {
                    favMeals = mealPojos;
                }
            });
            profileFragmentPresenter.getCashedPlanMeals().observe(getViewLifecycleOwner(), new Observer<List<PlanMeal>>() {
                @Override
                public void onChanged(List<PlanMeal> planMeals) {
                    mPlanMeals = planMeals;
                }
            });
            signUpBtn.setVisibility(View.GONE);
            profileName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            profileEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            Glide.with(getContext()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                    .apply(new RequestOptions().override(300, 300)
                            .placeholder(R.drawable.person)
                            .error(R.drawable.person)).into(profileImg);
        } else {
            uploadDataBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.GONE);
            profileImg.setImageResource(R.drawable.person);
        }


    }


    @Override
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        AuthSharedPreferences.getInstance(getContext()).setLoginStatus(false);
        loadingAnimationView.setVisibility(View.GONE);
        loadingAnimationView.pauseAnimation();
        FavoritesFragment.favMealsDownloadIndicator = false;
        CalenderFragment.planMealsDownloadIndicator = false;
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void stopAnimation(){
        loadingAnimationView.setVisibility(View.GONE);
        loadingAnimationView.pauseAnimation();
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    void initUi(View view) {
        logoutBtn = view.findViewById(R.id.logoutBtn);
        profileImg = view.findViewById(R.id.profileImage);
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        signUpBtn = view.findViewById(R.id.signUpButton);
        uploadDataBtn = view.findViewById(R.id.uploadDataBtn);
        loadingAnimationView = view.findViewById(R.id.logoutLoading);
        listeners();
    }

    void listeners() {
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAnimationView.setVisibility(View.VISIBLE);
                loadingAnimationView.playAnimation();
                profileFragmentPresenter.uploadMeals(FirebaseAuth.getInstance().getCurrentUser().getEmail(), mPlanMeals, favMeals , 1);
            }
        });
        signUpBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        });
        uploadDataBtn.setOnClickListener(v -> {
            loadingAnimationView.setVisibility(View.VISIBLE);
            loadingAnimationView.playAnimation();
            profileFragmentPresenter.uploadMeals(FirebaseAuth.getInstance().getCurrentUser().getEmail(), mPlanMeals, favMeals , 0);
        });
    }
}