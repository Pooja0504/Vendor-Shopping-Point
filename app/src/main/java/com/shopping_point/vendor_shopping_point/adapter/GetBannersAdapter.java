package com.shopping_point.vendor_shopping_point.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.BannerListItemBinding;
import com.shopping_point.vendor_shopping_point.model.GetBanners;
import com.shopping_point.vendor_shopping_point.utils.RequestCallback;
import com.shopping_point.vendor_shopping_point.viewModel.GetBannersViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.RemovePosterViewModel;


import java.util.List;


public class GetBannersAdapter extends RecyclerView.Adapter<GetBannersAdapter.NewsFeedViewHolder>{

    private Context mContext;
    private List<GetBanners> newsFeedList;

    private RemovePosterViewModel removePosterViewModel;
    private GetBannersViewModel newsFeedViewModel;
    private GetBanners newsFeed;

    public GetBannersAdapter(Context mContext, List<GetBanners> newsFeedList, FragmentActivity activity) {
        this.mContext = mContext;
        this.newsFeedList = newsFeedList;
        newsFeedViewModel = ViewModelProviders.of(activity).get(GetBannersViewModel.class);
        removePosterViewModel = ViewModelProviders.of(activity).get(RemovePosterViewModel.class);

    }

    @NonNull
    @Override
    public NewsFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        BannerListItemBinding newsfeedListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.banner_list_item,parent,false);
        return new NewsFeedViewHolder(newsfeedListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedViewHolder holder, int position) {
        GetBanners currentNewsFeed = newsFeedList.get(position);

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

    class NewsFeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final BannerListItemBinding binding;

        private NewsFeedViewHolder(BannerListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.close.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            // Get position of the product
            newsFeed = newsFeedList.get(position);

            switch (view.getId()) {
                case R.id.close:
                    deletePoster();
                    break;

            }
        }


        private void deletePoster() {
            deleteFromCategory(() -> {

                notifyDataSetChanged();
            });
            newsFeedList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), newsFeedList.size());
            showSnackBar("Poster Removed");
        }

        private void showSnackBar(String text) {
            Snackbar.make(itemView, text, Snackbar.LENGTH_SHORT).show();
        }

        private void deleteFromCategory(RequestCallback callback) {
            removePosterViewModel.removePoster(newsFeed.getPosterId(), callback);
        }


    }




}
