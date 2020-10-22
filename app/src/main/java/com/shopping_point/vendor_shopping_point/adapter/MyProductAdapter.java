package com.shopping_point.vendor_shopping_point.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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
    private List<MyProduct> productlist;


    public MyProductAdapter(Context mContext, List<MyProduct> productlist) {
        this.mContext = mContext;
        this.productlist = productlist;
    }

    @NonNull
    @Override
    public MyProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ProductListBinding productListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_list,parent,false);
        return new MyProductViewHolder(productListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductViewHolder holder, int position) {
        MyProduct myProduct = productlist.get(position);
        if(myProduct!=null){
            String productUrl = LOCALHOST + myProduct.getImage().replaceAll("\\\\", "/");
            Glide.with(mContext)
                    .load(productUrl)
                    .into(holder.binding.imageView);
            String productname = myProduct.getTitle();
            holder.binding.title.setText(productname);
            String description= myProduct.getShortDesc();
            holder.binding.ShortDesc.setText(description);
            String rating= myProduct.getRating();
            holder.binding.Rating.setText(rating);
            String price= myProduct.getPrice();
            holder.binding.Price.setText(price);
        }


    }

    @Override
    public int getItemCount() {
        if (productlist == null) {
            return 0;
        }
        return productlist.size();
    }

    class MyProductViewHolder extends RecyclerView.ViewHolder {

        private final ProductListBinding binding;

        private MyProductViewHolder(ProductListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}