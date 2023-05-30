package com.example.plateplanner.startactivity.splash.view;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.plateplanner.R;
import com.example.plateplanner.datebase.ConcreteLocalSource;
import com.example.plateplanner.homeactivity.view.HomeActivity;
import com.example.plateplanner.model.AuthSharedPreferences;
import com.example.plateplanner.model.Repository;
import com.example.plateplanner.network.ApiClient;
import com.example.plateplanner.network.FirebaseCalls;
import com.example.plateplanner.startactivity.splash.presenter.SplashPresenter;

public class SplashFragment extends Fragment implements SplashViewInterface {

    LottieAnimationView splashAnimation;
    SplashPresenter splashPresenter;
    Intent intent;

    public SplashFragment() {
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
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        splashPresenter = new SplashPresenter(this, Repository.getInstance(AuthSharedPreferences.getInstance(getContext()), FirebaseCalls.getInstance(), ApiClient.getInstance(), ConcreteLocalSource.getInstance(getContext())));
        splashAnimation = view.findViewById(R.id.splashAnimation);
        splashAnimation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (getLogStatus()) {
                    intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Navigation.findNavController(view).navigate(SplashFragmentDirections.actionSplashFragmentToSignInOptionsFragment());
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public boolean getLogStatus() {
        return splashPresenter.getLogInStatus();
    }
}