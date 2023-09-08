package com.duydv.vn.foodappmvc.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.duydv.vn.foodappmvc.MyApplication;
import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.activity.FoodDetailActivity;
import com.duydv.vn.foodappmvc.activity.MainActivity;
import com.duydv.vn.foodappmvc.activity.OrderHistoryActivity;
import com.duydv.vn.foodappmvc.adapter.FoodAdapter;
import com.duydv.vn.foodappmvc.adapter.FoodPopularAdapter;
import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.constant.GlobalFunction;
import com.duydv.vn.foodappmvc.databinding.FragmentHomeBinding;
import com.duydv.vn.foodappmvc.listener.IItemFoodListener;
import com.duydv.vn.foodappmvc.model.Food;
import com.duydv.vn.foodappmvc.utils.StringUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private FragmentHomeBinding mFragmentHomeBinding;
    private List<Food> mListFoods;
    private List<Food> mListFoodPopulars;
    private final Handler mHandler = new Handler();
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mListFoodPopulars == null || mListFoodPopulars.isEmpty()){
                return;
            }
            if(mFragmentHomeBinding.viewpager.getCurrentItem() == mListFoodPopulars.size()-1){
                mFragmentHomeBinding.viewpager.setCurrentItem(0);
                return;
            }
            mFragmentHomeBinding.viewpager.setCurrentItem(mFragmentHomeBinding.viewpager.getCurrentItem() + 1);
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false);

        getListFoodFromFirebase("");
        mFragmentHomeBinding.imgSearch.setOnClickListener(view -> onClickSearch());
        setHintSearchFood();
        
        return mFragmentHomeBinding.getRoot();
    }

    public void getListFoodFromFirebase(String key){
        if(getActivity() == null){
            return;
        }
        MyApplication.get(getActivity()).getFoodReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mFragmentHomeBinding.layoutContent.setVisibility(View.VISIBLE);
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
                displayListFoodPopulars();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), getString(R.string.msg_onCancelled), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayListFoods(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        mFragmentHomeBinding.rcvFood.setLayoutManager(gridLayoutManager);

        FoodAdapter foodAdapter = new FoodAdapter(mListFoods, this::goToFoodDetail);
        mFragmentHomeBinding.rcvFood.setAdapter(foodAdapter);
    }

    private List<Food> getListFoodPopulars(){
        mListFoodPopulars = new ArrayList<>();
        if(mListFoods == null || mListFoods.isEmpty()){
            return mListFoodPopulars;
        }

        for(Food food : mListFoods){
            if(food.isPopular()){
                mListFoodPopulars.add(0,food);
            }
        }
        return mListFoodPopulars;
    }

    private void displayListFoodPopulars(){
        FoodPopularAdapter foodPopularAdapter = new FoodPopularAdapter(getListFoodPopulars(), this::goToFoodDetail);
        mFragmentHomeBinding.viewpager.setAdapter(foodPopularAdapter);

        mFragmentHomeBinding.indicator3.setViewPager(mFragmentHomeBinding.viewpager);
        mFragmentHomeBinding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable,3000);
            }
        });
    }

    private void onClickSearch(){
        String strSearch = mFragmentHomeBinding.edtSearch.getText().toString().trim();
        searchFood(strSearch);
        if(StringUtils.isEmty(strSearch)){
            mFragmentHomeBinding.layoutFoodPopular.setVisibility(View.VISIBLE);
        }else{
            mFragmentHomeBinding.layoutFoodPopular.setVisibility(View.GONE);
        }
        GlobalFunction.hideSoftKeyboard(getActivity());
    }

    private void searchFood(String key){
        if(mListFoods != null){
            mListFoods.clear();
        }
        getListFoodFromFirebase(key);
    }

    private void setHintSearchFood(){
        mFragmentHomeBinding.edtSearch.setHint(getString(R.string.hint_search_food));

        mFragmentHomeBinding.edtSearch.addTextChangedListener(new TextWatcher() {
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
                    mFragmentHomeBinding.layoutFoodPopular.setVisibility(View.VISIBLE);
                    mFragmentHomeBinding.edtSearch.setHint(getString(R.string.hint_search_food));
                }
            }
        });
    }

    @Override
    protected void initToolbar() {
        if(getActivity() != null){
            ((MainActivity) getActivity()).showToolbar(true,getString(R.string.nav_home));
        }
    }

    private void goToFoodDetail(Food food){
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_OBJECT_INTENT,food);
        GlobalFunction.startActivity(getActivity(), FoodDetailActivity.class,bundle);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable,3000);
    }
}
