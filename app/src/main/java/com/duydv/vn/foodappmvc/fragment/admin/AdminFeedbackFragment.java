package com.duydv.vn.foodappmvc.fragment.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.duydv.vn.foodappmvc.MyApplication;
import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.activity.AdminActivity;
import com.duydv.vn.foodappmvc.adapter.FeedBackAdapter;
import com.duydv.vn.foodappmvc.databinding.FragmentAdminFeedbackBinding;
import com.duydv.vn.foodappmvc.fragment.BaseFragment;
import com.duydv.vn.foodappmvc.model.Feedback;
import com.google.firebase.auth.FederatedAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminFeedbackFragment extends BaseFragment {
    private FragmentAdminFeedbackBinding mFragmentAdminFeedbackBinding;
    private List<Feedback> mListFeedback;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentAdminFeedbackBinding = FragmentAdminFeedbackBinding.inflate(inflater,container,false);

        getListFeedbackFromFirebase();

        return mFragmentAdminFeedbackBinding.getRoot();
    }

    private void getListFeedbackFromFirebase() {
        if(getActivity() == null){
            return;
        }

        MyApplication.get(getActivity()).getFeedbackReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListFeedback = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Feedback feedback = dataSnapshot.getValue(Feedback.class);
                    if(feedback != null){
                        mListFeedback.add(0,feedback);
                    }
                }
                displayListFeedback(mListFeedback);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), R.string.msg_onCancelled, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayListFeedback(List<Feedback> mListFeedback){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mFragmentAdminFeedbackBinding.rcvFeedback.setLayoutManager(linearLayoutManager);

        FeedBackAdapter feedBackAdapter = new FeedBackAdapter(mListFeedback);
        mFragmentAdminFeedbackBinding.rcvFeedback.setAdapter(feedBackAdapter);
    }

    @Override
    protected void initToolbar() {
        if(getActivity() != null){
            ((AdminActivity) getActivity()).showToolbar(getString(R.string.nav_feedback));
        }
    }
}