package com.duydv.vn.foodappmvc.fragment.admin;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.duydv.vn.foodappmvc.MyApplication;
import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.activity.AddFoodActivity;
import com.duydv.vn.foodappmvc.activity.AdminActivity;
import com.duydv.vn.foodappmvc.adapter.AdminFoodAdapter;
import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.constant.GlobalFunction;
import com.duydv.vn.foodappmvc.databinding.FragmentAdminFoodBinding;
import com.duydv.vn.foodappmvc.fragment.BaseFragment;
import com.duydv.vn.foodappmvc.listener.IManagerFoodListener;
import com.duydv.vn.foodappmvc.model.Food;
import com.duydv.vn.foodappmvc.utils.StringUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminFoodFragment extends BaseFragment {
    private FragmentAdminFoodBinding mFragmentAdminFoodBinding;
    private List<Food> mListFoods;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentAdminFoodBinding = FragmentAdminFoodBinding.inflate(inflater,container,false);

        setHintSearchFood();
        getListFoodFromFirebase("");
        mFragmentAdminFoodBinding.imgSearch.setOnClickListener(view -> onClickSearch());
        mFragmentAdminFoodBinding.btnAddFood.setOnClickListener(view -> GlobalFunction.startActivity(getActivity(),AddFoodActivity.class));

        return mFragmentAdminFoodBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        if(getActivity() != null){
            ((AdminActivity) getActivity()).showToolbar(getString(R.string.nav_food));
        }
    }

    private void getListFoodFromFirebase(String key) {
        if(getActivity() == null){
            return;
        }
        MyApplication.get(getActivity()).getFoodReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListFoods = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Food food = dataSnapshot.getValue(Food.class);
                    if(food == null){
                        return;
                    }

                    if(StringUtils.isEmty(key)){
                        mListFoods.add(0,food);
                    }else{
                        if(!StringUtils.isEmty(food.getName()) && food.getName().toLowerCase().trim().contains(key.toLowerCase().trim())){
                            mListFoods.add(0,food);
                        }
                    }
                }
                displayListFoods();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), getString(R.string.msg_onCancelled), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayListFoods(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mFragmentAdminFoodBinding.rcvFood.setLayoutManager(linearLayoutManager);

        AdminFoodAdapter adminFoodAdapter = new AdminFoodAdapter(mListFoods, new IManagerFoodListener() {
            @Override
            public void onClickUpdateFood(Food food) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY_OBJECT_INTENT,food);
                GlobalFunction.startActivity(getActivity(),AddFoodActivity.class,bundle);
            }

            @Override
            public void onClickDeleteFood(Food food) {
                showDialogDeleteFood(food);
            }
        });
        mFragmentAdminFoodBinding.rcvFood.setAdapter(adminFoodAdapter);
    }

    private void onClickSearch(){
        String strSearch = mFragmentAdminFoodBinding.edtSearchFood.getText().toString().trim();
        searchFood(strSearch);
        GlobalFunction.hideSoftKeyboard(getActivity());
    }

    private void searchFood(String key){
        if(mListFoods != null){
            mListFoods.clear();
        }
        getListFoodFromFirebase(key);
    }

    private void setHintSearchFood(){
        mFragmentAdminFoodBinding.edtSearchFood.setHint(getString(R.string.hint_search_food));

        mFragmentAdminFoodBinding.edtSearchFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String strSearch = editable.toString();
                if(StringUtils.isEmty(strSearch)){
                    searchFood("");
                    mFragmentAdminFoodBinding.edtSearchFood.setHint(getString(R.string.hint_search_food));
                }
            }
        });
    }

    private void showDialogDeleteFood(Food food){
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.title_dialog_delete_food)
                .setMessage(R.string.message_dialog_confirm_delete_food)
                .setNegativeButton(R.string.confirm,(dialogInterface, i) -> {
                    if(getActivity() != null){
                        MyApplication.get(getActivity()).getFoodReference().child(String.valueOf(food.getId())).removeValue();
                        Toast.makeText(getActivity(), R.string.msg_delete_food_succesfuly, Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(R.string.cancel,null)
                .show();
    }
}