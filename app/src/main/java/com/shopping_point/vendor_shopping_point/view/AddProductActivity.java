package com.shopping_point.vendor_shopping_point.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.ActivityAddProductBinding;
import com.shopping_point.vendor_shopping_point.model.Vendor;
import com.shopping_point.vendor_shopping_point.viewModel.AddProductViewModel;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "AddProduct";
    private ActivityAddProductBinding binding;
    private AddProductViewModel addProductViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product);

        binding.browse.setOnClickListener(this);
        binding.upload.setOnClickListener(this);

        addProductViewModel = ViewModelProviders.of(this).get(AddProductViewModel.class);


    }


    private void uplodVendor() {
        String product_name = binding.productname.getText().toString();
        String price = binding.price.getText().toString();
        String description = binding.description.getText().toString();
        String category = binding.category.getText().toString();


        if (product_name.isEmpty()) {
            binding.productname.setError("Product Name Required");
            binding.productname.requestFocus();
            return;
        }



        if (price.isEmpty()) {
            binding.price.setError("Price Required");
            binding.price.requestFocus();
        }

        if(description.isEmpty())
        {
            binding.description.setError("Description Required");
            binding.description.requestFocus();
            return;
        }

        if (category.isEmpty()) {
            binding.category.setError("Mention Category");
            binding.category.requestFocus();
            return;
        }



        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        progressDialog.setMessage("Add Product");
        progressDialog.setCancelable(false);
        progressDialog.show();

        addProductViewModel.getAddProductResponseLiveData(new Vendor(product_name, price, description,category)).observe(this, addProductApiResponse -> {
            if (!addProductApiResponse.isError()) {
                Toast.makeText(this, addProductApiResponse.getMessage(), Toast.LENGTH_LONG).show();
                //LoginUtils.getInstance(this).saveUserInfo(addProductApiResponse.getUser());
                progressDialog.dismiss();
            }else
            {
                progressDialog.cancel();
                Toast.makeText(this, addProductApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.browse:
                browseImage();
                break;
            case R.id.upload:
                uplodVendor();
                break;
        }
    }

    private void browseImage() {
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
    }


}
