package com.shopping_point.vendor_shopping_point.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.CategoryListBinding;
import com.shopping_point.vendor_shopping_point.model.Category;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<com.shopping_point.vendor_shopping_point.adapter.CategoryAdapter.CategoryViewHolder>{

    private Context mContext;
    private List<Category> categorylist;


    public CategoryAdapter(Context mContext, List<Category> categorylist) {
        this.mContext = mContext;
        this.categorylist = categorylist;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CategoryListBinding activityAddCategoryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.category_list,parent,false);
        return new CategoryViewHolder(activityAddCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categorylist.get(position);

if(category!=null){
    String categoryName = category

            .getCategory_name();
    //Toast.makeText(mContext, ""+categoryName, Toast.LENGTH_SHORT).show();
    holder.binding.categoryName.setText((CharSequence) categoryName);

}


    }

    @Override
    public int getItemCount() {
        if (categorylist == null) {
            return 0;
        }
        return categorylist.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private final CategoryListBinding binding;

        private CategoryViewHolder(CategoryListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
