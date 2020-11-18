package com.shopping_point.vendor_shopping_point.view;

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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.ActivityAddBannerBinding;
import com.shopping_point.vendor_shopping_point.databinding.ActivityAddProductBinding;
import com.shopping_point.vendor_shopping_point.model.Banner;
import com.shopping_point.vendor_shopping_point.model.Category;
import com.shopping_point.vendor_shopping_point.model.Product;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;
import com.shopping_point.vendor_shopping_point.viewModel.AddBannerViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.AddProductViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.CategoryViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.vendor_shopping_point.utils.Constant.GALLERY_REQUEST;

public class AddBannerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddBanner";
    private ActivityAddBannerBinding binding;
    private AddBannerViewModel addBannerViewModel;
    Bitmap bitmap;
    String encode_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_banner);
        binding.btnSelectBanner.setOnClickListener(this);
        binding.uploadBanner.setOnClickListener(this);










        addBannerViewModel = ViewModelProviders.of(this).get(AddBannerViewModel.class);


    }


    private void uplodBanner() {



        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        progressDialog.setMessage("Add Banner");
        progressDialog.setCancelable(false);
        progressDialog.show();
int Vendor_id = LoginUtils.getInstance(this).getVendorInfo().getId();
        addBannerViewModel.getAddBannerResponseLiveData(new Banner(encode_image,Vendor_id)).observe(this, addBannerApiResponse -> {
            if (!addBannerApiResponse.isError()) {
                Toast.makeText(this, addBannerApiResponse.getMessage(), Toast.LENGTH_LONG).show();
                //LoginUtils.getInstance(this).saveUserInfo(addBannerApiResponse.getUser());
                progressDialog.dismiss();
            }else
            {
                progressDialog.cancel();
               Toast.makeText(this, addBannerApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSelectBanner:
                browseImage();
                break;
            case R.id.upload_banner:
                uplodBanner();
                break;

        }
    }


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
                binding.imageOfBanner.setImageBitmap(bitmap);
                binding.imageOfBanner.setVisibility(View.VISIBLE);

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
