package com.shopping_point.vendor_shopping_point.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.BannerListItemBinding;
import com.shopping_point.vendor_shopping_point.model.NewsFeed1;


import java.util.List;


public class NewsFeedAdapter1 extends RecyclerView.Adapter<NewsFeedAdapter1.NewsFeedViewHolder>{

    private Context mContext;
    private List<NewsFeed1> newsFeedList;

    public NewsFeedAdapter1(Context mContext, List<NewsFeed1> newsFeedList) {
        this.mContext = mContext;
        this.newsFeedList = newsFeedList;
    }

    @NonNull
    @Override
    public NewsFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        BannerListItemBinding newsfeedListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.banner_list_item,parent,false);
        return new NewsFeedViewHolder(newsfeedListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedViewHolder holder, int position) {
        NewsFeed1 currentNewsFeed = newsFeedList.get(position);

        // Load poster into ImageView
        String posterUrl =   currentNewsFeed.getImage().replaceAll("\\\\", "/");
        Glide.with(mContext)
                .load(posterUrl)
                .into(holder.binding.poster);
    }

    @Override
    public int getItemCount() {
        if (newsFeedList == null) {
            return 0;
        }
        return newsFeedList.size();
    }

    class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        private final BannerListItemBinding binding;

        private NewsFeedViewHolder(BannerListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
