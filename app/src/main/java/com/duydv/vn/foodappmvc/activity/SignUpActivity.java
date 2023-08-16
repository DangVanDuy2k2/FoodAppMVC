package com.duydv.vn.foodappmvc.activity;

import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.constant.GlobalFunction;
import com.duydv.vn.foodappmvc.databinding.ActivitySignUpBinding;
import com.duydv.vn.foodappmvc.model.User;
import com.duydv.vn.foodappmvc.pref.DataStoreManager;
import com.duydv.vn.foodappmvc.utils.StringUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends BaseActivity {
    private ActivitySignUpBinding mActivitySignUpBinding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(mActivitySignUpBinding.getRoot());

        progressDialog = new ProgressDialog(this);
        mActivitySignUpBinding.radUser.setChecked(true);

        mActivitySignUpBinding.imgBack.setOnClickListener(view -> onBackPressed());
        mActivitySignUpBinding.btnSignUp.setOnClickListener(view -> onClickSignUp());
    }

    private void onClickSignUp() {
        String email = mActivitySignUpBinding.edtEmailSignUp.getText().toString().trim();
        String password = mActivitySignUpBinding.edtPasswordSignUp.getText().toString().trim();
        String confirPassword = mActivitySignUpBinding.edtConfirmPasswordSignUp.getText().toString().trim();

        if(StringUtils.isEmty(email)){
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_email_required), Toast.LENGTH_SHORT).show();
        } else if (StringUtils.isEmty(password)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_password_required), Toast.LENGTH_SHORT).show();
        }else if (StringUtils.isEmty(confirPassword)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_confirm_password_required), Toast.LENGTH_SHORT).show();
        } else if (!StringUtils.isValidEmail(email)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show();
        }else if (password.length() < 6) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_password_invalid), Toast.LENGTH_SHORT).show();
        }else if (!confirPassword.equals(password)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_confirm_password_invalid), Toast.LENGTH_SHORT).show();
        }else {
            //Kiem tra dang nhap admin
            if(mActivitySignUpBinding.radAdmin.isChecked()){
                if(!email.contains(Constant.EMAIL_ADMIN_FORM)){
                    Toast.makeText(SignUpActivity.this, getString(R.string.msg_email_admin_invalid), Toast.LENGTH_SHORT).show();
                }else{
                    signUpUser(email,password);
                }
            }else {
                //Kiem tra dung nhap nguoi dung
                if(email.contains(Constant.EMAIL_ADMIN_FORM)){
                    Toast.makeText(SignUpActivity.this, getString(R.string.msg_email_user_invalid), Toast.LENGTH_SHORT).show();
                }else {
                    signUpUser(email,password);
                }
            }
        }
    }

    private void signUpUser(String email, String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null){
                                User objectUser = new User(user.getEmail(), password);
                                if(objectUser.getEmail().contains(Constant.EMAIL_ADMIN_FORM)){
                                    objectUser.setAdmin(true);
                                }
                                DataStoreManager.setUser(objectUser);
                                GlobalFunction.startMainActivity(SignUpActivity.this);

                                //Dong tat ca cac sctivity truoc do
                                finishAffinity();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, getString(R.string.msg_sign_up_fails),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}