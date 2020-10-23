package com.shopping_point.vendor_shopping_point.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shopping_point.vendor_shopping_point.R;

import com.shopping_point.vendor_shopping_point.databinding.ProductListBinding;
import com.shopping_point.vendor_shopping_point.model.MyProduct;


import java.util.List;

import static com.shopping_point.vendor_shopping_point.utils.Constant.LOCALHOST;


public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyProductViewHolder>{

    private Context mContext;
    private List<MyProduct> myProductList;

    public MyProductAdapter(Context mContext, List<MyProduct> myProductList) {
        this.mContext = mContext;
        this.myProductList = myProductList;
    }

    @NonNull
    @Override
    public MyProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
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
