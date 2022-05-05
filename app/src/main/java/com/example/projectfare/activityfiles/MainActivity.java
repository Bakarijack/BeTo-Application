package com.example.projectfare.activityfiles;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectfare.R;
import com.example.projectfare.modalclasses.UserModalClass;
import com.example.projectfare.databasefiles.UserDatabaseHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button verifyB,enterButton;
    private EditText phoneNumber,codeNumber;
    private  LinearLayout linearLayoutP,linearLayoutL,linearLayoutV;
    private String mVerificationId;
    // variable for FirebaseAuth class
    private FirebaseAuth mAuth;
    private UserDatabaseHandler userDatabaseHandler;
    private UserModalClass userModalClass;

    Handler handler = new Handler();
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            linearLayoutP.setVisibility(View.INVISIBLE);
            linearLayoutL.setVisibility(View.VISIBLE);
        }
    };


    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            linearLayoutL.setVisibility(View.INVISIBLE);
            linearLayoutV.setVisibility(View.VISIBLE);
        }
    };

    private String phone,otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        //create a full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        //create a listener for the verify button
        verifyB = (Button) findViewById(R.id.verifyB);
        enterButton = (Button) findViewById(R.id.enterB);
        phoneNumber = (EditText) findViewById(R.id.phoneN);
        codeNumber = (EditText) findViewById(R.id.codeN);

        userDatabaseHandler =  new UserDatabaseHandler(MainActivity.this);


        if(userDatabaseHandler.isAnyUserExist() == true){
            Intent intent = new Intent(MainActivity.this, Menu.class);
            startActivity(intent);                    finish();
            finish(); // prevent coming back to this activity
        }

        //action for the enter button
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(!phoneNumber.getText().toString().isEmpty()){
                    phone = phoneNumber.getText().toString();
                    sendOTP(phone);
                    enterButtonOnaction();

                }else {
                    return;
                }*/
                if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(MainActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    // if the text field is not empty we are calling our
                    // send OTP method for getting OTP from Firebase.
                    String phone = "+254" + phoneNumber.getText().toString();
                    //sendVerificationCode(phone);
                    enterButtonOnaction();
                    sendOTP(phone);
                    //for testing feature
                    //Intent intent = new Intent(MainActivity.this,Menu.class);
                    //startActivity(intent);
                }
            }
        });

        verifyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(codeNumber.getText().toString())) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(MainActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(codeNumber.getText().toString());
                }
            }
        });
    }

    public void sendOTP(String num){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(num)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            //Log.d(TAG, "onVerificationCompleted:" + credential);

           // signInWithPhoneAuthCredential(credential);
            final String code = credential.getSmsCode();

            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                codeNumber.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }



        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            //Log.w(TAG, "onVerificationFailed", e);

           /* if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }*/

            // Show a message and update the UI
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
           // Log.d(TAG, "onCodeSent:" + verificationId);
            super.onCodeSent(verificationId, token);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            //mResendToken = token;
        }
    };

    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    String phone = "+254"+phoneNumber.getText().toString();
                    userModalClass = new UserModalClass("Unknown",phone);
                    if (userDatabaseHandler.insertUserData(userModalClass.getName(),userModalClass.getPhoneNumber()) == false){
                        return;
                    }
                    Intent i = new Intent(MainActivity.this, Menu.class);
                    startActivity(i);
                    finish();
                } else {
                    // if the code is not correct then we are
                    // displaying an error message to the user.
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void enterButtonOnaction(){
        linearLayoutP = (LinearLayout) findViewById(R.id.linPhone);
        linearLayoutL = (LinearLayout) findViewById(R.id.linloading);
        linearLayoutV = (LinearLayout) findViewById(R.id.linCode);

        handler.postDelayed(runnable1,0);
        handler.postDelayed(runnable2,2000);

    }

   /* public void verifyListener(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }*/

}