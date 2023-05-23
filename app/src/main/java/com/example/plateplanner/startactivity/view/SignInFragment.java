package com.example.plateplanner.startactivity.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.example.plateplanner.R;
import com.example.plateplanner.homeactivity.HomeActivity;
import com.example.plateplanner.startactivity.model.AuthModel;
import com.example.plateplanner.startactivity.model.AuthSharedPreferences;
import com.example.plateplanner.startactivity.model.Repository;
import com.example.plateplanner.startactivity.network.FirebaseCalls;
import com.example.plateplanner.startactivity.presenter.SignInPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import java.util.regex.Pattern;

public class SignInFragment extends Fragment implements SignInViewInterface{

    public final String TAG = "SignInFragment";

    NavController navController;
    ImageButton backBtn;
    AppCompatButton doneBtn;
    TextInputEditText emailEt;
    TextInputEditText passwordEt;
    TextView errorTv;
    LottieAnimationView loadingLottie;
    Intent intent;
    SignInPresenter signInPresenter;


    public SignInFragment() {
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
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signInPresenter = new SignInPresenter(this , Repository.getInstance(AuthSharedPreferences.getInstance(getContext()) , FirebaseCalls.getInstance()));

        initUi(view);

        clickListeners();



        intent = new Intent(getActivity(), HomeActivity.class);
    }

    private String validate() {
        String resultMessage = "";

        String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailRegex);
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        if (email.isEmpty()) {
            resultMessage = "Email can't be empty.";
        } else if (!pattern.matcher(email).matches()) {
            resultMessage = "Enter valid email please.";
        }
        if (password.isEmpty()) {
            resultMessage = "Password field can't be empty.";
        }

        return resultMessage;
    }

    private void initUi(View view) {
        navController = Navigation.findNavController(view);
        backBtn = view.findViewById(R.id.signInBackButton);
        emailEt = view.findViewById(R.id.emailSignInEt);
        passwordEt = view.findViewById(R.id.passwordSignInEt);
        doneBtn = view.findViewById(R.id.doneSignInButton);
        errorTv = view.findViewById(R.id.signInResultTv);
        loadingLottie = view.findViewById(R.id.signInLoadingAnimation);
    }

    private void clickListeners() {
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = validate();
                if (result.isEmpty()) {
                    loadingLottie.setVisibility(View.VISIBLE);
                    loadingLottie.playAnimation();
                    AuthModel authModel = new AuthModel( emailEt.getText().toString() , passwordEt.getText().toString());
                    signInPresenter.signIn(authModel);
                } else {
                    errorTv.setText(result);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });
    }

//    @Override
//    public void signIn(String email, String password) {
//        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    startActivity(intent);
//                    getActivity().finish();
//                }
//                loadingLottie.setVisibility(View.GONE);
//                loadingLottie.pauseAnimation();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                errorTv.setText(e.getLocalizedMessage());
//            }
//        });
//    }

    @Override
    public void onLoginSuccess() {
        startActivity(intent);
        getActivity().finish();
        loadingLottie.setVisibility(View.GONE);
        loadingLottie.pauseAnimation();
    }

    @Override
    public void onLoginFailed(String errorMessage) {
        errorTv.setText(errorMessage);
        loadingLottie.setVisibility(View.GONE);
        loadingLottie.pauseAnimation();
    }
}