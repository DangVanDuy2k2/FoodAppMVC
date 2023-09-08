package com.duydv.vn.foodappmvc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.duydv.vn.foodappmvc.MyApplication;
import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.databinding.ActivityAddFoodBinding;
import com.duydv.vn.foodappmvc.model.Food;
import com.duydv.vn.foodappmvc.model.FoodObject;
import com.duydv.vn.foodappmvc.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class AddFoodActivity extends AppCompatActivity {
    private ActivityAddFoodBinding mActivityAddFoodBinding;
    private Food mFood;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddFoodBinding = ActivityAddFoodBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityAddFoodBinding.getRoot());

        getDataIntent();
        setStatusActivity();
        initListener();
    }

    private void getDataIntent(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Food food = (Food) bundle.get(Constant.KEY_OBJECT_INTENT);
            mFood = food;
            if(food != null){
                isUpdate = true;
                mActivityAddFoodBinding.edtName.setText(food.getName());
                mActivityAddFoodBinding.edtDescription.setText(food.getDescription());
                mActivityAddFoodBinding.edtPrice.setText(String.valueOf(food.getPrice()));
                mActivityAddFoodBinding.edtSale.setText(String.valueOf(food.getSale()));
                mActivityAddFoodBinding.edtImage.setText(food.getImage());
                mActivityAddFoodBinding.chkPopular.setChecked(food.isPopular());
            }
        }
    }

    private void setStatusActivity(){
        mActivityAddFoodBinding.toolbar.layoutToolbar.setVisibility(View.VISIBLE);
        mActivityAddFoodBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        mActivityAddFoodBinding.toolbar.imgCart.setVisibility(View.GONE);
        if(isUpdate){
            mActivityAddFoodBinding.txtAddOrEdit.setText(R.string.update);
            mActivityAddFoodBinding.toolbar.txtTitle.setText(R.string.update_food);
        }else{
            mActivityAddFoodBinding.txtAddOrEdit.setText(R.string.add);
            mActivityAddFoodBinding.toolbar.txtTitle.setText(R.string.add_food);
        }
    }

    private void initListener(){
        mActivityAddFoodBinding.toolbar.imgBack.setOnClickListener(view -> onBackPressed());
        mActivityAddFoodBinding.txtAddOrEdit.setOnClickListener(view -> {
            if(isUpdate){
                onClickUpdateFood();
            }else{
                onClickAddFood();
            }
        });
    }

    private void onClickUpdateFood(){
        String newName = mActivityAddFoodBinding.edtName.getText().toString().trim();
        String newDescription = mActivityAddFoodBinding.edtDescription.getText().toString().trim();
        String newPrice = mActivityAddFoodBinding.edtPrice.getText().toString().trim();
        String newSale = mActivityAddFoodBinding.edtSale.getText().toString().trim();
        String newImage = mActivityAddFoodBinding.edtImage.getText().toString().trim();
        boolean newPopular = mActivityAddFoodBinding.chkPopular.isChecked();

        if(StringUtils.isEmty(newName) || StringUtils.isEmty(newDescription) || StringUtils.isEmty(newPrice)
                || StringUtils.isEmty(newSale) || StringUtils.isEmty(newImage)){
            Toast.makeText(AddFoodActivity.this,R.string.msg_edt_required,Toast.LENGTH_SHORT).show();
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("name",newName);
            map.put("description",newDescription);
            map.put("price",Integer.parseInt(newPrice));
            map.put("sale",Integer.parseInt(newSale));
            map.put("image",newImage);
            map.put("popular",newPopular);

            MyApplication.get(getApplicationContext()).getFoodReference().child(String.valueOf(mFood.getId()))
                    .updateChildren(map, (error, ref) ->
                            Toast.makeText(AddFoodActivity.this, R.string.msg_update_food_succesfuly, Toast.LENGTH_SHORT).show());
        }
    }

    private void onClickAddFood(){
        String name = mActivityAddFoodBinding.edtName.getText().toString().trim();
        String description = mActivityAddFoodBinding.edtDescription.getText().toString().trim();
        String price = mActivityAddFoodBinding.edtPrice.getText().toString().trim();
        String sale = mActivityAddFoodBinding.edtSale.getText().toString().trim();
        String image = mActivityAddFoodBinding.edtImage.getText().toString().trim();
        boolean popular = mActivityAddFoodBinding.chkPopular.isChecked();

        if(StringUtils.isEmty(name) || StringUtils.isEmty(description) || StringUtils.isEmty(price) || StringUtils.isEmty(sale) || StringUtils.isEmty(image)){
            Toast.makeText(AddFoodActivity.this,R.string.msg_edt_required,Toast.LENGTH_SHORT).show();
        }else{
            long id = System.currentTimeMillis();
            FoodObject foodObject = new FoodObject(id,name,image,Integer.parseInt(price),popular,Integer.parseInt(sale),description);

            MyApplication.get(getApplicationContext()).getFoodReference().child(String.valueOf(id)).setValue(foodObject,
                    (error, ref) -> {
                Toast.makeText(AddFoodActivity.this, R.string.msg_add_food_succesfuly, Toast.LENGTH_SHORT).show();
                mActivityAddFoodBinding.edtName.setText("");
                mActivityAddFoodBinding.edtDescription.setText("");
                mActivityAddFoodBinding.edtPrice.setText("");
                mActivityAddFoodBinding.edtSale.setText("");
                mActivityAddFoodBinding.edtImage.setText("");
                mActivityAddFoodBinding.chkPopular.setChecked(false);
            });
        }
    }
}