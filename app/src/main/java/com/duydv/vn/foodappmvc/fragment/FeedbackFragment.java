package com.duydv.vn.foodappmvc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.duydv.vn.foodappmvc.MyApplication;
import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.activity.MainActivity;
import com.duydv.vn.foodappmvc.constant.GlobalFunction;
import com.duydv.vn.foodappmvc.databinding.FragmentFeedbackBinding;
import com.duydv.vn.foodappmvc.model.Feedback;
import com.duydv.vn.foodappmvc.pref.DataStoreManager;
import com.duydv.vn.foodappmvc.utils.StringUtils;

public class FeedbackFragment extends BaseFragment {
    private FragmentFeedbackBinding mFragmentFeedbackBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentFeedbackBinding = FragmentFeedbackBinding.inflate(inflater,container,false);

        mFragmentFeedbackBinding.txtSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSendFeedback();
            }
        });
        return mFragmentFeedbackBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        if(getActivity() != null){
            ((MainActivity) getActivity()).showToolbar(false,getString(R.string.nav_feedback));
        }
    }

    private void onClickSendFeedback(){
        if(getActivity() == null){
            return;
        }
        String strFeedback = mFragmentFeedbackBinding.edtFeedBack.getText().toString().trim();
        if(StringUtils.isEmty(strFeedback)){
            GlobalFunction.showToastMessage(getActivity(),getString(R.string.msg_feedback_required));
        }else {
            String email = DataStoreManager.getUser().getEmail();

            Feedback feedback = new Feedback(email,strFeedback);

            MyApplication.get(getActivity()).getFeedbackReference()
                    .child(String.valueOf(System.currentTimeMillis()))
                    .setValue(feedback, (error, ref) -> {
                        GlobalFunction.showToastMessage(getActivity(),getString(R.string.msg_send_feedback_successfully));
                        GlobalFunction.hideSoftKeyboard(getActivity());
                        mFragmentFeedbackBinding.edtFeedBack.setText("");
                    });
        }

    }
}
