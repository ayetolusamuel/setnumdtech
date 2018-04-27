package technologies.setnumd.com.setnumdtech;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    private EditText editEmail,editPassword;
   private TextView txtSignUp;
    private ImageView LogoImageView;
    private LinearLayout linearLayout;
    private Button btnLogin;
    boolean isSignUpActive = true;

   private void showUserList(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);


    }

    public void login(View view){


        if (!validate()) {
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(technologies.setnumd.com.setnumdtech.LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);







    }

    public void onLoginSuccess() {

        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        btnLogin.setEnabled(true);
        ParseUser.logInInBackground(email, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (e == null && user != null){
                    //  hideProgressBar();
                    Toast.makeText(LoginActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                    showUserList();

                }else{
                    //e.printStackTrace();
                  Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
       // finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("enter a valid email address");
            valid = false;
        } else {
            editEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            editEmail.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            editPassword.setError(null);
        }

        return valid;
    }

   /* public void signuClick(View view) {

        String username = editemail.getText().toString();
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
                        showProgressbar();
                        if (e == null && user != null){
                           hideProgressBar();
                            Toast.makeText(LoginActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                            showUserList();

                        }else{
                            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editPassword = findViewById(R.id.input_password);
        editEmail= findViewById(R.id.input_email);
        txtSignUp = findViewById(R.id.link_signup);
        btnLogin = findViewById(R.id.btn_login);

        editPassword.setOnKeyListener(this);
        LogoImageView = findViewById(R.id.imageview);
      linearLayout = findViewById(R.id.linear_Layout);
        LogoImageView.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);

        editEmail.setText("ayetolusamuel@gmail.com");
        editPassword.setText("admin");
        setTitle("Login|Signup Portal");

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


    @Override
    public void onClick(View view) {
      if (view.getId() == R.id.link_signup){
         Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
         startActivity(intent);

        } else if(view.getId() == R.id.linear_Layout || view.getId() == R.id.imageview){

            InputMethodManager inputMethodManager = (InputMethodManager)this.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }
    }



    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==keyEvent.ACTION_DOWN){
          login(view);
        }
        return false;
    }
}