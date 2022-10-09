package com.example.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class number extends AppCompatActivity {

    EditText enternum;
    Button getotpbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        EditText enternum = findViewById(R.id.input_mobile_number);
        Button getotpbutton = findViewById(R.id.buttongetotp);
        ProgressBar pb = findViewById(R.id.progressBar2);

        getotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!enternum.getText().toString().trim().isEmpty()) {
                    if ((enternum.getText().toString().trim()).length() == 10) {

                        pb.setVisibility(View.VISIBLE);
                        getotpbutton.setVisibility(View.INVISIBLE);


                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enternum.getText().toString(),
                                60, TimeUnit.SECONDS,
                                number.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        pb.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        pb.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        Toast.makeText(number.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(s, forceResendingToken);
                                        pb.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        Intent i = new Intent(getApplicationContext(), otp.class);
                                        i.putExtra("mobile", enternum.getText().toString());
                                        startActivity(i);
                                    }
                                }
                        );


                    } else {
                        Toast.makeText(number.this, "Enter Correct Number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(number.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}