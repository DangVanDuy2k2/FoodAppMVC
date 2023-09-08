package com.duydv.vn.foodappmvc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.duydv.vn.foodappmvc.MyApplication;
import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.activity.MainActivity;
import com.duydv.vn.foodappmvc.adapter.FoodInCartAdapter;
import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.constant.GlobalFunction;
import com.duydv.vn.foodappmvc.database.FoodDatabase;
import com.duydv.vn.foodappmvc.databinding.FragmentCartBinding;
import com.duydv.vn.foodappmvc.listener.IManagerFoodListener;
import com.duydv.vn.foodappmvc.model.Order;
import com.duydv.vn.foodappmvc.model.Food;
import com.duydv.vn.foodappmvc.pref.DataStoreManager;
import com.duydv.vn.foodappmvc.utils.StringUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends BaseFragment {
    private FragmentCartBinding mFragmentCartBinding;
    private List<Food> mListFoodInCart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentCartBinding = FragmentCartBinding.inflate(inflater,container,false);

        getListFoodInCart();
        mFragmentCartBinding.txtOrder.setOnClickListener(view -> onClickOrder());

        return mFragmentCartBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        if(getActivity() != null){
            ((MainActivity) getActivity()).showToolbar(false,getString(R.string.nav_cart));
        }
    }

    private void getListFoodInCart(){
        mListFoodInCart = new ArrayList<>();
        mListFoodInCart = FoodDatabase.getInstance(getContext()).foodDAO().getListFood();

        int sumPrice = FoodDatabase.getInstance(getContext()).foodDAO().getSumPrice();
        String strSumPrice = sumPrice + Constant.VND;
        mFragmentCartBinding.txtSumPriceInCart.setText(strSumPrice);

        displayListFoodInCart();
    }

    private void displayListFoodInCart(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mFragmentCartBinding.rcvFoodCart.setLayoutManager(linearLayoutManager);

        FoodInCartAdapter mFoodInCartAdapter = new FoodInCartAdapter(mListFoodInCart, new IManagerFoodListener() {
            @Override
            public void onClickUpdateFood(Food food) {
                int sumPrice = FoodDatabase.getInstance(getContext()).foodDAO().getSumPrice();
                String strSumPrice = sumPrice + Constant.VND;
                mFragmentCartBinding.txtSumPriceInCart.setText(strSumPrice);
                FoodDatabase.getInstance(getContext()).foodDAO().updateFood(food);
            }

            @Override
            public void onClickDeleteFood(Food food) {
                FoodDatabase.getInstance(getContext()).foodDAO().deleteFood(food);
                getListFoodInCart();
            }
        });
        mFragmentCartBinding.rcvFoodCart.setAdapter(mFoodInCartAdapter);
    }

    private void onClickOrder(){
        if(getActivity() == null){
            return;
        }

        if(mListFoodInCart == null || mListFoodInCart.isEmpty()){
            return;
        }

        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_order,null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(viewDialog);

        //init ui
        TextView txt_sum_price_order = viewDialog.<TextView>findViewById(R.id.txt_sum_price_order);
        TextView txt_list_food_order = viewDialog.<TextView>findViewById(R.id.txt_list_food_order);
        EditText edt_fullName = viewDialog.<EditText>findViewById(R.id.edt_fullname);
        EditText edt_phone_number = viewDialog.<EditText>findViewById(R.id.edt_phone_number);
        EditText edt_address = viewDialog.<EditText>findViewById(R.id.edt_address);
        TextView txt_cancel = viewDialog.<TextView>findViewById(R.id.txt_cancel);
        TextView txt_order = viewDialog.<TextView>findViewById(R.id.txt_order);

        //show infor
        int sumPrice = FoodDatabase.getInstance(getContext()).foodDAO().getSumPrice();
        String strSumPrice = sumPrice + Constant.VND;
        txt_sum_price_order.setText(strSumPrice);

        String strListFoodOrder = getStringListFoodOrder();
        txt_list_food_order.setText(strListFoodOrder);

        bottomSheetDialog.show();

        //listener
        txt_cancel.setOnClickListener(view -> bottomSheetDialog.dismiss());
        txt_order.setOnClickListener(view -> {
            String fullName = edt_fullName.getText().toString().trim();
            String phone = edt_phone_number.getText().toString().trim();
            String address = edt_address.getText().toString().trim();

            if(StringUtils.isEmty(fullName) || StringUtils.isEmty(phone) || StringUtils.isEmty(address)){
                GlobalFunction.showToastMessage(getActivity(),getString(R.string.msg_order_required));
            }else {
                String email = DataStoreManager.getUser().getEmail();
                long id = System.currentTimeMillis();

                Order order = new Order(id,fullName,email,phone,address,strListFoodOrder,sumPrice
                        ,Constant.CART_PAYMENT_METHOD,false);

                if(getActivity() == null){
                    return;
                }
                MyApplication.get(getActivity()).getBookingReference()
                        .child(String.valueOf(id))
                        .setValue(order, (error, ref) -> {
                            GlobalFunction.showToastMessage(getActivity(),getString(R.string.msg_order_successfully));
                            bottomSheetDialog.dismiss();

                            FoodDatabase.getInstance(getActivity()).foodDAO().deleteAllFood();
                            getListFoodInCart();
                        });
            }
        });
    }

    private String getStringListFoodOrder(){
        List<Food> mListFoodOrder = FoodDatabase.getInstance(getContext()).foodDAO().getListFood();
        String strListFoodOrder = "";
        for(Food food : mListFoodOrder){
            strListFoodOrder = strListFoodOrder + food.getName() + " (" + food.getRealPrice() + Constant.VND + ") - Số lượng: " + food.getCount() + "\n";
        }
        return strListFoodOrder;
    }

    @Override
    public void onStart() {
        super.onStart();
        getListFoodInCart();
    }
}
