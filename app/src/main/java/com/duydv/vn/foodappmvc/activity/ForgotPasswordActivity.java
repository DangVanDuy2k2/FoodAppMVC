package com.duydv.vn.foodappmvc.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.databinding.ActivityForgotPasswordBinding;
import com.duydv.vn.foodappmvc.utils.StringUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends BaseActivity {
    private ActivityForgotPasswordBinding mActivity_forgot_password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity_forgot_password = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(mActivity_forgot_password.getRoot());

        progressDialog = new ProgressDialog(this);

        mActivity_forgot_password.imgBack.setOnClickListener(view -> onBackPressed());
        mActivity_forgot_password.btnResetPassword.setOnClickListener(view -> onClickResetPassword());
    }

    private void onClickResetPassword() {
        String email = mActivity_forgot_password.edtEmailForgotPassword.getText().toString().trim();

        if(StringUtils.isEmty(email)){
            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.msg_email_required), Toast.LENGTH_SHORT).show();
        }else if (!StringUtils.isValidEmail(email)) {
            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show();
        }else {
            resetPassword(email);
        }
    }

    private void resetPassword(String email){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, getString(R.string.msg_reset_password_successfully),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}