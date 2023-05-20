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
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import pojo.AuthModel;


public class SignUpFragment extends Fragment {

    ImageButton backBtn;
    NavController navController;
    AppCompatButton doneBtn;
    TextInputEditText displayNameEt;
    TextInputEditText emailEt;
    TextInputEditText passwordEt;
    TextInputEditText confirmPasswordEt;
    TextView resultTv;
    AuthModel authModel = new AuthModel();

    public SignUpFragment() {
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
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        backBtn = view.findViewById(R.id.backButton);
        doneBtn = view.findViewById(R.id.doneButton);
        displayNameEt = view.findViewById(R.id.displayNameEt);
        emailEt = view.findViewById(R.id.emailEt);
        passwordEt = view.findViewById(R.id.passwordEt);
        confirmPasswordEt = view.findViewById(R.id.confirmPasswordEt);
        resultTv = view.findViewById(R.id.resultTv);



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = validate();
                if (!result.isEmpty())
                    resultTv.setText(result);
                else {
                    //signup using firebase
                }
            }
        });
    }

    private String validate() {
        String resultMessage = "";

        String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailRegex);

        authModel.setName(displayNameEt.getText().toString());
        authModel.setEmail(emailEt.getText().toString());
        authModel.setPassword(passwordEt.getText().toString());
        if(authModel.getEmail().isEmpty()){
            resultMessage = "Email can't be empty.";
        }else if (!pattern.matcher(authModel.getEmail()).matches()){
            resultMessage = "Enter valid email please.";
        }
        if (authModel.getName().isEmpty()){
            resultMessage = "Display Name can't be empty.";
        }
        if (authModel.getPassword().isEmpty()){
            resultMessage = "Password field can't be empty.";
        }
        if (confirmPasswordEt.getText().toString().isEmpty()){
            resultMessage = "Confirm Password field can't be empty.";
        }
        if (!authModel.getPassword().equals(confirmPasswordEt.getText().toString())){
            resultMessage = "Confirm Password is not identical";
        }

        return resultMessage;
    }
}