package com.shopping_point.vendor_shopping_point.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.OrderListBinding;

import com.shopping_point.vendor_shopping_point.model.Order;
import com.shopping_point.vendor_shopping_point.view.OrdersActivity;

import java.util.List;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyOrderViewHolder>{

    private Context mContext;
    private List<Order> orderList;

    private OrdersActivity ordersActivity;


    public OrdersAdapter(Context mContext, List<Order> orderList, OrdersActivity ordersActivity) {
        this.mContext = mContext;
        this.orderList = orderList;
        this.ordersActivity =ordersActivity;
    }

    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        OrderListBinding productListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_list,parent,false);
        return new MyOrderViewHolder(productListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        if (order != null) {
            String imageUrl =  order.getProductImage().replaceAll("\\\\", "/");

            Glide.with(mContext)
                    .load(imageUrl)
                    .into(holder.binding.imageView);

            String Title = order.getProductName();
            holder.binding.title.setText(Title);
            String ShortDesc = order.getProductDescription();
            holder.binding.ShortDesc.setText(ShortDesc);

            holder.binding.Rating.setText(order.getProductRating() + " ★");

            double rating = Double.parseDouble(String.valueOf(order.getProductRating()));
            if(rating<=3 && rating >=2)
            {
                holder.binding.Rating.setBackgroundColor(Color.parseColor("#FFA22C"));
            }else if(rating<2)
            {
                holder.binding.Rating.setBackgroundColor(Color.parseColor("#FE0000"));

            }



            Integer Price = order.getProductPrice();
            holder.binding.Price.setText(Price + " ₹ ");


     holder.binding.ordercount.setText(" total Product selled : " + order.getOrderCount() + " ");
     holder.binding.productRemaining.setText("Remaining Products : "+ order.getProductQuantity() + " ");






        }
    }





    @Override
    public int getItemCount() {
        if (orderList == null) {
            return 0;
        }
        return orderList.size();
    }

    class MyOrderViewHolder extends RecyclerView.ViewHolder {

        private final OrderListBinding binding;

        private MyOrderViewHolder(OrderListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
