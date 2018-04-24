package technologies.setnumd.com.setnumdtech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    private EditText editUsername,editPassword;
    private TextView changeSignupModeTextView;
    private ImageView LogoImageView;
    private RelativeLayout relativeLayout;
    private Button btnSignUp;
    boolean isSignUpActive = true;

    public void showUserList(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);

    }


    public void signuClick(View view) {
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();
        if (username.matches("") || password.matches("")){
            Toast.makeText(LoginActivity.this,"A username and password are Required",Toast.LENGTH_SHORT).show();
        }
        else {
            if (isSignUpActive) {
                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(LoginActivity.this,"Signup Successful",Toast.LENGTH_SHORT).show();
                            showUserList();
                        } else {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null && user != null){
                            Toast.makeText(LoginActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                            showUserList();

                        }else{
                            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editPassword = findViewById(R.id.passwordTextView);
        editUsername = findViewById(R.id.userNameTextView);
        btnSignUp = findViewById(R.id.signUpButton);
        changeSignupModeTextView = findViewById(R.id.changeSignuModeTexView);
        changeSignupModeTextView.setOnClickListener(this);
        editPassword.setOnKeyListener(this);
        LogoImageView = findViewById(R.id.imageview);
        relativeLayout = findViewById(R.id.relativeLayout);
        LogoImageView.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);

        //set default text for username & password
        editUsername.setText("admin");
        editPassword.setText("admin");



        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.changeSignuModeTexView){
            if (isSignUpActive){
                isSignUpActive = false;
                btnSignUp.setText("Login");
                changeSignupModeTextView.setText("Or, Signup");
                editPassword.setText("");
                editUsername.setText("");
            }else{
                isSignUpActive = true;
                btnSignUp.setText("SignUp");
                changeSignupModeTextView.setText("Or, Login");
                editPassword.setText("");
                editUsername.setText("");
            }
            //Log.i("AppInfo ", "Change Sigup Mode!!");
        } else if(view.getId() == R.id.relativeLayout || view.getId() == R.id.imageview){

            InputMethodManager inputMethodManager = (InputMethodManager)this.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }
    }



    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==keyEvent.ACTION_DOWN){
            signuClick(view);
        }
        return false;
    }
}