package com.shopping_point.vendor_shopping_point.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shopping_point.vendor_shopping_point.R;

import com.shopping_point.vendor_shopping_point.databinding.ProductListBinding;
import com.shopping_point.vendor_shopping_point.model.MyProduct;
import com.shopping_point.vendor_shopping_point.view.MyProductActivity;
import com.shopping_point.vendor_shopping_point.viewModel.ActivateProductViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.DeactivateProductViewModel;


import java.io.IOException;
import java.util.List;



public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyProductViewHolder>{

    private Context mContext;
    private List<MyProduct> myProductList;
    private ActivateProductViewModel activateProductViewModel;
    private DeactivateProductViewModel deactivateProductViewModel;
    private MyProductActivity myProductActivity;


    public MyProductAdapter(Context mContext, List<MyProduct> myProductList,MyProductActivity myProductActivity) {
        this.mContext = mContext;
        this.myProductList = myProductList;
        this.myProductActivity=myProductActivity;
    }

    @NonNull
    @Override
    public MyProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        activateProductViewModel = ViewModelProviders.of(myProductActivity).get(ActivateProductViewModel.class);
        deactivateProductViewModel = ViewModelProviders.of(myProductActivity).get(DeactivateProductViewModel.class);
        ProductListBinding productListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_list,parent,false);
        return new MyProductViewHolder(productListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductViewHolder holder, int position) {
        MyProduct myProduct = myProductList.get(position);

        if (myProduct != null) {
            String imageUrl =  myProduct.getImage().replaceAll("\\\\", "/");
           //Toast.makeText(mContext, myProduct.getTitle() + "  IN PRODUCT ADAPTER ", Toast.LENGTH_SHORT).show();


            Glide.with(mContext)
                    .load(imageUrl)
                    .into(holder.binding.imageView);

            String Title = myProduct.getTitle();
            holder.binding.title.setText(Title);
            String ShortDesc = myProduct.getShortDesc();
            holder.binding.ShortDesc.setText(ShortDesc);
            String Rating = myProduct.getRating();
            holder.binding.Rating.setText(Rating + " ★ ");
            String Price = myProduct.getPrice();
            holder.binding.Price.setText(Price + " ₹ ");


            if(myProduct.getIsActive().equals("1")) {

                holder.binding.status.setText(R.string.active);
                holder.binding.status.setChecked(true);

            }else {
                holder.binding.status.setText(R.string.inactive);
                holder.binding.status.setChecked(false);
            }






            holder.binding.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                    {
                        activateProductViewModel.activateVendor(myProduct.getProduct_id()).observe( myProductActivity, responseBody -> {
                            if(responseBody!= null){

                                holder.binding.progressBarProductlist.setVisibility(View.INVISIBLE);

                                holder.binding.status.setText(R.string.active);

                                // Toast.makeText(mContext, responseBody.string(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }else {
                        deactivateProductViewModel.deactivateVendor(myProduct.getProduct_id()).observe( myProductActivity, responseBody -> {
                            if(responseBody!= null){

                                holder.binding.progressBarProductlist.setVisibility(View.INVISIBLE);

                                holder.binding.status.setText(R.string.inactive);

                                //Toast.makeText(mContext, responseBody.string(), Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                }
            });
        }
    }





    @Override
    public int getItemCount() {
        if (myProductList == null) {
            return 0;
        }
        return myProductList.size();
    }

    class MyProductViewHolder extends RecyclerView.ViewHolder {

        private final ProductListBinding binding;

        private MyProductViewHolder(ProductListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
