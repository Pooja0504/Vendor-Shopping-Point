package com.shopping_point.vendor_shopping_point.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.adapter.CategoryAdapter;
import com.shopping_point.vendor_shopping_point.viewModel.CategoryViewModel;
import com.shopping_point.vendor_shopping_point.databinding.ActivityAddProductBinding;

import com.shopping_point.vendor_shopping_point.model.Category;
import com.shopping_point.vendor_shopping_point.model.Product;
import com.shopping_point.vendor_shopping_point.model.CategoryResponse.*;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;
import com.shopping_point.vendor_shopping_point.viewModel.AddProductViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.vendor_shopping_point.utils.Constant.GALLERY_REQUEST;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddProduct";
    private ActivityAddProductBinding binding;
    private AddProductViewModel addProductViewModel;
    private CategoryViewModel categoryViewModel;
    Bitmap bitmap;
    ArrayAdapter<String> stringArrayAdapter;
    ArrayList<String> category = new ArrayList<>();
    String category_name;
    String encode_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        binding.browse.setOnClickListener(this);
        binding.upload.setOnClickListener(this);


        Toast.makeText(this, "BEFORE", Toast.LENGTH_SHORT).show();
        categoryViewModel.getCategory().observe(this, CategoryResponse -> {
            List<Category> list=  CategoryResponse.getCategory();

            for(Category value : list){
                category.add(value.getCategory_name());
            }
            stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,category);
            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.categorySpinner.setAdapter(stringArrayAdapter);

        });







        binding.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                category_name = category.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddProductActivity.this, "SELECT CATEGORY", Toast.LENGTH_SHORT).show();
            }
        });
        addProductViewModel = ViewModelProviders.of(this).get(AddProductViewModel.class);


    }


    private void uplodVendor() {
        int seller_id = LoginUtils.getInstance(this).getVendorInfo().getId();
        String product_name = binding.productname.getText().toString();
        String price = binding.price.getText().toString();
        String description = binding.description.getText().toString();
        //String category = binding.category.getText().toString();
        String brand = binding.brand.getText().toString();
        String quantity = binding.quantity.getText().toString();


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

//        if (category.isEmpty()) {
//            binding.category.setError("Mention Category");
//            binding.category.requestFocus();
//            return;
//        }
        if (brand.isEmpty()) {
            binding.brand.setError("Mention Brand Name");
            binding.brand.requestFocus();
            return;
        }
        if (quantity.isEmpty()) {
            binding.quantity.setError("Mention Quantity");
            binding.quantity.requestFocus();
            return;
        }


        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        progressDialog.setMessage("Add Product");
        progressDialog.setCancelable(false);
        progressDialog.show();

        addProductViewModel.getAddProductResponseLiveData(new Product(seller_id,product_name, price, description,category_name,brand,quantity,encode_image)).observe(this, addProductApiResponse -> {
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
//                case R.id.category_spinner:
//                selectCategory();
//                break;
        }
    }

//    private void selectCategory() {
//        Toast.makeText(this, "In select category function ", Toast.LENGTH_SHORT).show();
//        categoryViewModel.getCategory().observe(this, CategoryResponse -> {
//           List<Category> list=  CategoryResponse.getCategory();
//
//           for(Category value : list){
//               category.add(String.valueOf(value));
//           }
//            stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,category);
//           stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            binding.categorySpinner.setAdapter(stringArrayAdapter);
//
//        });
//
//
//
//       //Intent intent=new Intent(getApplicationContext(), CategoryActivity.class);
//       // startActivity(intent);
//    }

    private void browseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null ) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), path);
                binding.img.setImageBitmap(bitmap);
                binding.img.setVisibility(View.VISIBLE);

                encode_image= imageToString(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }



    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
