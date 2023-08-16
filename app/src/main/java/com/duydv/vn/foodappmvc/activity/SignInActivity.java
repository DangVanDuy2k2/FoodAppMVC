package com.duydv.vn.foodappmvc.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.constant.GlobalFunction;
import com.duydv.vn.foodappmvc.databinding.ActivitySignInBinding;
import com.duydv.vn.foodappmvc.model.User;
import com.duydv.vn.foodappmvc.pref.DataStoreManager;
import com.duydv.vn.foodappmvc.utils.StringUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends BaseActivity {
    private ActivitySignInBinding mActivitySignInBinding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySignInBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(mActivitySignInBinding.getRoot());

        progressDialog = new ProgressDialog(this);

        mActivitySignInBinding.radUser.setChecked(true);

        mActivitySignInBinding.layoutSignUp.setOnClickListener(view -> GlobalFunction.startActivity(SignInActivity.this,
                SignUpActivity.class));

        mActivitySignInBinding.btnSignIn.setOnClickListener(view -> onClickSignIn());
        mActivitySignInBinding.txtForgotPassword.setOnClickListener(view -> onClickForgotPassword());
    }

    private void onClickSignIn() {
        String email = mActivitySignInBinding.edtEmailSignIn.getText().toString().trim();
        String password = mActivitySignInBinding.edtPasswordSignIn.getText().toString().trim();

        if(StringUtils.isEmty(email)){
            Toast.makeText(SignInActivity.this, getString(R.string.msg_email_required), Toast.LENGTH_SHORT).show();
        }else if (StringUtils.isEmty(password)) {
            Toast.makeText(SignInActivity.this, getString(R.string.msg_password_required), Toast.LENGTH_SHORT).show();
        }else if (!StringUtils.isValidEmail(email)) {
            Toast.makeText(SignInActivity.this, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show();
        }else if (password.length() < 6) {
            Toast.makeText(SignInActivity.this, getString(R.string.msg_password_invalid), Toast.LENGTH_SHORT).show();
        }else {
            //Kiem tra dang nhap admin
            if(mActivitySignInBinding.radAdmin.isChecked()){
                if(!email.contains(Constant.EMAIL_ADMIN_FORM)){
                    Toast.makeText(SignInActivity.this, getString(R.string.msg_email_admin_invalid), Toast.LENGTH_SHORT).show();
                }else{
                    signInUser(email,password);
                }
            }else {
                //Kiem tra dung nhap nguoi dung
                if(email.contains(Constant.EMAIL_ADMIN_FORM)){
                    Toast.makeText(SignInActivity.this, getString(R.string.msg_email_user_invalid), Toast.LENGTH_SHORT).show();
                }else {
                    signInUser(email,password);
                }
            }
        }
    }

    public void signInUser(String email, String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null){
                                User objectUser = new User(user.getEmail(),password);
                                if(objectUser.getEmail().contains(Constant.EMAIL_ADMIN_FORM)){
                                    objectUser.setAdmin(true);
                                }
                                DataStoreManager.setUser(objectUser);
                                GlobalFunction.startMainActivity(SignInActivity.this);
                                //dong cac activity phia trc lai
                                finishAffinity();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, getString(R.string.msg_sign_in_fails),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onClickForgotPassword(){
        GlobalFunction.startActivity(this,ForgotPasswordActivity.class);
    }
}