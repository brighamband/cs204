package com.example.awscognitoauth;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;

public class MainActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private EditText givenNameField;
    private EditText emailField;
    private EditText confirmationCodeField;

    private Button signUpButton;
    private Button signInButton;
    private Button confirmButton;

    private Context context;

    //TODO Explain these Variables
    private String username;
    private String password;
    private String givenName;
    private String email;
    private String confirmationCode;

    private AppHelper appHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        {
            usernameField = (EditText) findViewById(R.id.usernameEditText);
            passwordField = (EditText) findViewById(R.id.passwordEditText);
            givenNameField = (EditText) findViewById(R.id.givenNameEditText);
            emailField = (EditText) findViewById(R.id.emailEditText);
            confirmationCodeField = (EditText) findViewById(R.id.confirmCodeEditText);

            usernameField.addTextChangedListener(textWatcher);
            passwordField.addTextChangedListener(textWatcher);
            givenNameField.addTextChangedListener(textWatcher);
            emailField.addTextChangedListener(textWatcher);
            confirmationCodeField.addTextChangedListener(textWatcher);

            signUpButton = (Button) findViewById(R.id.signUpButton);
            signInButton = (Button) findViewById(R.id.signInButton);
            confirmButton = (Button) findViewById(R.id.confirmButton);
        }

        appHelper = AppHelper.getInstance();
        appHelper.init(getApplicationContext());
        context = this;

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Create a CognitoUserAttributes object
                CognitoUserAttributes userAttributes = new CognitoUserAttributes();
                //TODO: Add user attributes to the object created
                userAttributes.addAttribute("given_name", givenName);
                userAttributes.addAttribute("email", email);
                //TODO: Call respective method on the user pool (from AppHelper.java)
                appHelper.getUserPool().signUpInBackground(username, password, userAttributes, null, signupCallback);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean forcedAliasCreation = false;
                //TODO: Call respective method on the user pool (from AppHelper.java)
                CognitoUser myUser = appHelper.getUserPool().getUser(username);
                //TODO: Call respective method on the CognitoUser Object
                myUser.confirmSignUpInBackground(confirmationCode, false, confHandler);
            }
        });


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Call respective method on the user pool (from AppHelper.java)
                CognitoUser myUser = appHelper.getUserPool().getUser(username);
                myUser.getSessionInBackground(authenticationHandler);
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            username = usernameField.getText().toString();
            password = passwordField.getText().toString();
            givenName = givenNameField.getText().toString();
            email = emailField.getText().toString();
            confirmationCode = confirmationCodeField.getText().toString();
        }
    };

    //TODO: Call this method once the login was successful
    public void startLoggedInActivity() {
        Intent intent = new Intent(this, LoggedInActivity.class);
        startActivity(intent);
    }

    //TODO: Import these handlers from AWS and then allow it to generate the necessary methods

    // Call Back after Signup button is pressed
    SignUpHandler signupCallback = new SignUpHandler() {
        @Override
        public void onSuccess(CognitoUser user, SignUpResult signUpResult) {
            String theToast = "Successful Signup";
            Toast.makeText(getApplicationContext(),theToast,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(getApplicationContext(),exception.toString(),Toast.LENGTH_LONG).show();
        }
    };

    // Call Back after Confirm is pressed
    GenericHandler confHandler = new GenericHandler() {
        @Override
        public void onSuccess() {
            String theToast = "Successful Confirmation";
            Toast.makeText(getApplicationContext(),theToast,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(getApplicationContext(),exception.toString(),Toast.LENGTH_LONG).show();
        }
    };

    // Call back after Login is pressed
    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            startLoggedInActivity();
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, password, null);
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);
            authenticationContinuation.continueTask();
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {

        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(getApplicationContext(),exception.toString(),Toast.LENGTH_LONG).show();
        }
    };
}
