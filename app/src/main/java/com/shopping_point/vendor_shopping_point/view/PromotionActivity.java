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
import android.widget.Toast;

import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.ActivityPromotionBinding;
import com.shopping_point.vendor_shopping_point.viewModel.AddBannerViewModel;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.vendor_shopping_point.utils.Constant.GALLERY_REQUEST;

public class PromotionActivity extends AppCompatActivity implements View.OnClickListener {
    private  ActivityPromotionBinding binding;
    private Bitmap bitmap;
    String encode_image;
    private AddBannerViewModel addBannerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_promotion);

        binding.btnSelectBanner.setOnClickListener(this);
        binding.uploadBanner.setOnClickListener(this);
        addBannerViewModel = ViewModelProviders.of(this).get(AddBannerViewModel.class);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSelectBanner:
                browseImage();
                break;
            case R.id.upload_banner:
                uplodBanner(encode_image);
                break;

        }
    }

    private void uplodBanner(String encode_image) {



        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        progressDialog.setMessage("Adding Poster");
        progressDialog.setCancelable(false);
        progressDialog.show();

        addBannerViewModel.getAddBannerResponseLiveData(encode_image).observe(this, addBannerApiResponse ->  {
            Toast.makeText(this, "MEOWWWWWWWWWW", Toast.LENGTH_SHORT).show();
            if (!addBannerApiResponse.isError()) {
                Toast.makeText(this, addBannerApiResponse.getMessage(), Toast.LENGTH_LONG).show();

                progressDialog.dismiss();
            }else
            {
                progressDialog.cancel();
                Toast.makeText(this, addBannerApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
                Toast.makeText(this, encode_image, Toast.LENGTH_SHORT).show();

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

}