package com.shopping_point.vendor_shopping_point.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.NewsfeedListItemBinding;
import com.shopping_point.vendor_shopping_point.databinding.NotificationItemBinding;
import com.shopping_point.vendor_shopping_point.databinding.ProductListBinding;
import com.shopping_point.vendor_shopping_point.model.MyProduct;
import com.shopping_point.vendor_shopping_point.model.NewsFeed;
import com.shopping_point.vendor_shopping_point.model.Notification;
import com.shopping_point.vendor_shopping_point.view.NavNotification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    private Context mContext;
    private List<Notification> notifications;

    public NotificationAdapter(Context applicationContext, List<Notification> notifications, NavNotification navNotification) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationItemBinding notificationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.notification_item,parent,false);
        return new NotificationAdapter.NotificationViewHolder(notificationItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);

        if (notification != null) {
            String imageUrl = notification.getImage().replaceAll("\\\\", "/");
            //Toast.makeText(mContext, myProduct.getTitle() + "  IN PRODUCT ADAPTER ", Toast.LENGTH_SHORT).show();


            Glide.with(mContext)
                    .load(imageUrl)
                    .into(holder.binding.imageView);

            String title = notification.getTitle();
            holder.binding.title.setText(title);
            String body = notification.getBody();
            holder.binding.body.setText(body);

        }
    }

    @Override
    public int getItemCount() {
        if (notifications == null) {
            return 0;
        }
        return notifications.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        private final NotificationItemBinding binding;

        private NotificationViewHolder(NotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

