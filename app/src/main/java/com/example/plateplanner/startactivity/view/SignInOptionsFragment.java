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
import com.example.plateplanner.R;
import com.example.plateplanner.homeactivity.HomeActivity;
import com.example.plateplanner.startactivity.model.AuthSharedPreferences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class SignInOptionsFragment extends Fragment {

    private final int GOOGLE_REQUEST = 0;
    private final String TAG = "SignInOptionsFragment";
    Intent intent;

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

        initUi(view);

        setListeners();

        intent = new Intent(getActivity(), HomeActivity.class);

    }

    private void initUi(View view) {
        signInWithEmailBtn = view.findViewById(R.id.emailSignBtn);
        googleBtn = view.findViewById(R.id.googleBtn);
        skipTv = view.findViewById(R.id.skipTv);
        signInTv = view.findViewById(R.id.signInTv);
        signUp = view.findViewById(R.id.emailSignBtn);
        navController = Navigation.findNavController(view);
    }

    private void setListeners() {
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

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });
    }

    private void signInWithGoogle() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);
        Intent googleSignIn = googleSignInClient.getSignInIntent();
        startActivityForResult(googleSignIn, GOOGLE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_REQUEST) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(googleSignInAccount);
            } catch (ApiException ex) {
                Log.e(TAG, "Google Sign in failed", ex);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount googleSignInAccount) {
        AuthCredential credential = GoogleAuthProvider
                .getCredential(googleSignInAccount.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "google sign in success");
                    AuthSharedPreferences authSharedPreferences = AuthSharedPreferences.getInstance(getContext());
                    authSharedPreferences.setLoginStatus(true);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Log.i(TAG, "google sign in failed");
                }
            }
        });
    }
}