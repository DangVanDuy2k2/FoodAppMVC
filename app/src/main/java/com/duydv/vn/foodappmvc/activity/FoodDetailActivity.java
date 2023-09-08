package com.duydv.vn.foodappmvc.activity;

import static com.duydv.vn.foodappmvc.R.drawable.bg_shape_gray_conner_6;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.database.FoodDatabase;
import com.duydv.vn.foodappmvc.databinding.ActivityFoodDetailBinding;
import com.duydv.vn.foodappmvc.model.Food;
import com.duydv.vn.foodappmvc.utils.GlideUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class FoodDetailActivity extends BaseActivity {
    private ActivityFoodDetailBinding mActivityFoodDetailBinding;
    private Food mFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFoodDetailBinding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(mActivityFoodDetailBinding.getRoot());

        showToolbar();
        getDataIntent();
        showInforFood();
        setStatusTextViewAddToCart();
        initListener();
    }

    private void showToolbar(){
        mActivityFoodDetailBinding.toolbar.layoutToolbar.setVisibility(View.VISIBLE);
        mActivityFoodDetailBinding.toolbar.txtTitle.setText(getString(R.string.food_detail));
        mActivityFoodDetailBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        mActivityFoodDetailBinding.toolbar.imgCart.setVisibility(View.VISIBLE);
    }

    private void getDataIntent(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        mFood = (Food) bundle.get(Constant.KEY_OBJECT_INTENT);
    }

    private void showInforFood(){
        if(mFood == null){
            return;
        }

        GlideUtils.loadUrl(mFood.getImage(),mActivityFoodDetailBinding.imgFoodDetail);
        mActivityFoodDetailBinding.txtFoodNameDetail.setText(mFood.getName());
        if(mFood.getSale() <= 0){
            mActivityFoodDetailBinding.txtSaleDetail.setVisibility(View.GONE);
            mActivityFoodDetailBinding.txtOldPriceDetail.setVisibility(View.GONE);
        }else{
            mActivityFoodDetailBinding.txtSaleDetail.setVisibility(View.VISIBLE);
            String strSale = Constant.SALE + mFood.getSale() + Constant.PERCENT;
            mActivityFoodDetailBinding.txtSaleDetail.setText(strSale);

            String oldPrice = mFood.getPrice() + Constant.VND;
            mActivityFoodDetailBinding.txtOldPriceDetail.setText(oldPrice);
            mActivityFoodDetailBinding.txtOldPriceDetail.setPaintFlags(mActivityFoodDetailBinding.txtOldPriceDetail.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        String strRealPrice = mFood.getRealPrice() + Constant.VND;
        mActivityFoodDetailBinding.txtRealPriceDetail.setText(strRealPrice);
        mActivityFoodDetailBinding.txtDescription.setText(mFood.getDescription());
    }

    private void initListener(){
        mActivityFoodDetailBinding.toolbar.imgBack.setOnClickListener(view -> onBackPressed());
        mActivityFoodDetailBinding.txtAddToCart.setOnClickListener(view -> onClickAddToCart());
        mActivityFoodDetailBinding.toolbar.imgCart.setOnClickListener(view -> onClickAddToCart());
    }

    private void onClickAddToCart(){
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_add_to_cart,null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);

        //init ui
        ImageView img_food_add_to_cart = viewDialog.<ImageView>findViewById(R.id.img_food_add_to_cart);
        TextView txt_name_name_food_add_to_cart = viewDialog.<TextView>findViewById(R.id.txt_name_food_add_to_cart);
        TextView txt_sum_price = viewDialog.<TextView>findViewById(R.id.txt_sum_price);
        TextView txt_minus = viewDialog.<TextView>findViewById(R.id.txt_minus);
        TextView txt_plus = viewDialog.<TextView>findViewById(R.id.txt_plus);
        TextView txt_count = viewDialog.<TextView>findViewById(R.id.txt_count);
        TextView txt_cancel = viewDialog.<TextView>findViewById(R.id.txt_cancel);
        TextView txt_add_to_cart = viewDialog.<TextView>findViewById(R.id.txt_add_to_cart);

        //show infor
        GlideUtils.loadUrl(mFood.getImage(),img_food_add_to_cart);
        txt_name_name_food_add_to_cart.setText(mFood.getName());

        int sumPrice = mFood.getRealPrice();
        String strSumPrice = sumPrice + Constant.VND;
        txt_sum_price.setText(strSumPrice);

        mFood.setCount(1);
        mFood.setSum_price(sumPrice);

        bottomSheetDialog.show();

        //listener
        txt_minus.setOnClickListener(view -> {
            int count = Integer.parseInt(txt_count.getText().toString());
            if(count <= 1){
                return;
            }

            int newCount = count - 1;
            txt_count.setText(String.valueOf(newCount));

            int newSumPrice = newCount * mFood.getRealPrice();
            String strSumPrice1 = newSumPrice + Constant.VND;
            txt_sum_price.setText(strSumPrice1);

            mFood.setCount(newCount);
            mFood.setSum_price(newSumPrice);
        });

        txt_plus.setOnClickListener(view -> {
            int count = Integer.parseInt(txt_count.getText().toString());
            int newCount = count + 1;
            txt_count.setText(String.valueOf(newCount));

            int newSumPrice = newCount * mFood.getRealPrice();
            String strSumPrice1 = newSumPrice + Constant.VND;
            txt_sum_price.setText(strSumPrice1);

            mFood.setCount(newCount);
            mFood.setSum_price(newSumPrice);
        });

        txt_cancel.setOnClickListener(view -> bottomSheetDialog.dismiss());
        txt_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodDatabase.getInstance(FoodDetailActivity.this).foodDAO().insertFood(mFood);
                bottomSheetDialog.dismiss();
                setStatusTextViewAddToCart();
            }
        });
    }

    private void setStatusTextViewAddToCart(){
        if(isFoodInCart()){
            mActivityFoodDetailBinding.txtAddToCart.setText(getString(R.string.added_to_cart));
            mActivityFoodDetailBinding.txtAddToCart.setBackgroundResource(bg_shape_gray_conner_6);
            mActivityFoodDetailBinding.toolbar.imgCart.setVisibility(View.GONE);
        }else{
            mActivityFoodDetailBinding.txtAddToCart.setText(getString(R.string.add_to_cart));
            mActivityFoodDetailBinding.txtAddToCart.setBackgroundResource(R.drawable.bg_shape_green_conner_6);
            mActivityFoodDetailBinding.toolbar.imgCart.setVisibility(View.VISIBLE);
        }
    }

    private boolean isFoodInCart(){
        List<Food> list = FoodDatabase.getInstance(FoodDetailActivity.this).foodDAO().checkFoodInCart(mFood.getId());
        return list != null && !list.isEmpty();
    }
}