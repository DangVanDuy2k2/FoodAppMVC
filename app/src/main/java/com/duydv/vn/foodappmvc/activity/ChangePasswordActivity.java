package com.duydv.vn.foodappmvc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.databinding.ActivityChangePasswordBinding;
import com.duydv.vn.foodappmvc.model.User;
import com.duydv.vn.foodappmvc.pref.DataStoreManager;
import com.duydv.vn.foodappmvc.utils.StringUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    private ActivityChangePasswordBinding mActivityChangePasswordBinding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityChangePasswordBinding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(mActivityChangePasswordBinding.getRoot());

        progressDialog = new ProgressDialog(this);

        mActivityChangePasswordBinding.imgBack.setOnClickListener(view -> onBackPressed());
        mActivityChangePasswordBinding.btnChangePassword.setOnClickListener(view -> onClickChangePassword());
    }

    private void onClickChangePassword() {
        String oldPassword = mActivityChangePasswordBinding.edtOldPassword.getText().toString().trim();
        String newPassword = mActivityChangePasswordBinding.edtNewPassword.getText().toString().trim();
        String confirmPassword = mActivityChangePasswordBinding.edtConfirmNewPassword.getText().toString().trim();

        if(StringUtils.isEmty(oldPassword)){
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.msg_old_password_required), Toast.LENGTH_SHORT).show();
        } else if (StringUtils.isEmty(newPassword)) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.msg_new_password_required), Toast.LENGTH_SHORT).show();
        }else if (StringUtils.isEmty(confirmPassword)) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.msg_confirm_new_password_required), Toast.LENGTH_SHORT).show();
        } else if (!StringUtils.isValidEmail(oldPassword) && !oldPassword.equals(DataStoreManager.getUser().getPassword())) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show();
        }else if (newPassword.length() < 6) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.msg_new_password_invalid), Toast.LENGTH_SHORT).show();
        }else if (!confirmPassword.equals(newPassword)) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.msg_confirm_new_password_invalid), Toast.LENGTH_SHORT).show();
        }else {
            changePassword(newPassword);
        }
    }

    private void changePassword(String newPassword){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        progressDialog.show();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(ChangePasswordActivity.this,getString(R.string.msg_change_password_successfuly),
                                    Toast.LENGTH_SHORT).show();
                            User userLogin = DataStoreManager.getUser();
                            userLogin.setPassword(newPassword);
                            DataStoreManager.setUser(userLogin);
                            mActivityChangePasswordBinding.edtOldPassword.setText("");
                            mActivityChangePasswordBinding.edtNewPassword.setText("");
                            mActivityChangePasswordBinding.edtConfirmNewPassword.setText("");
                        }
                    }
                });
    }
}