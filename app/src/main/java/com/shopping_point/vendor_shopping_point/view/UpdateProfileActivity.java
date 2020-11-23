package com.shopping_point.vendor_shopping_point.view;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.ActivityUpdateProfileBinding;
import com.shopping_point.vendor_shopping_point.model.Update;
import com.shopping_point.vendor_shopping_point.model.UploadPhoto;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;
import com.shopping_point.vendor_shopping_point.utils.Validation;
import com.shopping_point.vendor_shopping_point.viewModel.UpdateProfileViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.UploadPhotoViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.VendorImageViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.vendor_shopping_point.utils.Constant.CAMERA_PERMISSION_CODE;
import static com.shopping_point.vendor_shopping_point.utils.Constant.CAMERA_REQUEST;
import static com.shopping_point.vendor_shopping_point.utils.Constant.GALLERY_REQUEST;
import static com.shopping_point.vendor_shopping_point.utils.Constant.READ_EXTERNAL_STORAGE_CODE;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "UpdateProfileActivity";
    private UpdateProfileViewModel updateProfileViewModel;
    private ActivityUpdateProfileBinding binding;
    private VendorImageViewModel vendorImageViewModel;
    private UpdateProfileActivity updateProfileActivity ;
    private UploadPhotoViewModel uploadPhotoViewModel;
    private Uri selectedImage;
    String encode_image;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        vendorImageViewModel = ViewModelProviders.of(this).get(VendorImageViewModel.class);
        uploadPhotoViewModel = ViewModelProviders.of(this).get(UploadPhotoViewModel.class);
        updateProfileActivity=new UpdateProfileActivity();
         binding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile);
        binding.name.setText(LoginUtils.getInstance(this).getVendorInfo().getOrganisation_name());
        binding.email.setText(LoginUtils.getInstance(this).getVendorInfo().getEmail());
        binding.contact.setText(LoginUtils.getInstance(this).getVendorInfo().getPhone_no());

        binding.profilePicim.setOnClickListener(this);
        getVendorImage();

        binding.proceed.setOnClickListener(this);
        binding.cancleUpdate.setOnClickListener(this);


        updateProfileViewModel = ViewModelProviders.of(this).get(UpdateProfileViewModel.class);
        // setBoldStyle();
    }
    private void getVendorImage() {
        vendorImageViewModel.getVendorImage(LoginUtils.getInstance(this).getVendorInfo().getId()).observe(this, response -> {
            if (response != null) {

                String imageUrl =response.getImage().replaceAll("\\\\", "/");

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.profile_picture)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                        .dontAnimate()
                        .dontTransform();

                Glide.with(getApplicationContext())
                        .load(imageUrl)
                        .apply(options)
                        .into(binding.profilePicim);
            }
        });
    }
    private void updateAdmin() {

        String email = LoginUtils.getInstance(this).getVendorInfo().getEmail();
        String name =  binding.name.getText().toString().trim();
        String phone_no = binding.contact.getText().toString().trim();

        Toast.makeText(this, email + name + phone_no , Toast.LENGTH_SHORT).show();
        if (name.isEmpty()) {
            binding.name.setError(getString(R.string.Name_Required));
            binding.name.requestFocus();
            return;
        }

        if (!Validation.isValidName(name)) {
            binding.name.setError(getString(R.string.enter_at_least_3_characters));
            binding.name.requestFocus();
            return;
        }

        if (phone_no.isEmpty()) {
            binding.contact.setError(getString(R.string.Phone_Required));
            binding.contact.requestFocus();
            return;
        }


        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        progressDialog.setMessage("Profile Updating...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        updateProfileViewModel.getUpdateResponseLiveData(new Update(email, name, phone_no)).observe(this, updateApiResponse -> {
            if (!updateApiResponse.isError()) {
                Toast.makeText(this, updateApiResponse.getMessage(), Toast.LENGTH_LONG).show();
                LoginUtils.getInstance(this).saveVendorInfo(updateApiResponse.getName(),updateApiResponse.getEmail(),updateApiResponse.getPhone_no());
                goToAccountActivity();
                // LoginUtils.getInstance(this).saveUserInfo(updateApiResponse.getUser());
                progressDialog.dismiss();
                // goToLoginActivity();
            } else {
                progressDialog.cancel();
                //Toast.makeText(this, registerApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void goToAccountActivity() {
        startActivity(new Intent(UpdateProfileActivity.this,AccountActivity.class));
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.proceed:
                updateAdmin();
                break;
            case R.id.cancle_update:
                finish();
                break;
            case R.id.profilePicim:
                showCustomAlertDialog();
                break;
        }
    }

    private void showCustomAlertDialog() {
        final Dialog dialog = new Dialog(UpdateProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_image_dialog);

        Button takePicture = dialog.findViewById(R.id.takePicture);
        Button useGallery = dialog.findViewById(R.id.useGallery);

        takePicture.setEnabled(true);
        useGallery.setEnabled(true);

        takePicture.setOnClickListener(v -> {
            launchCamera();
            dialog.cancel();
        });

        useGallery.setOnClickListener(v -> {
            getImageFromGallery();
            dialog.cancel();
        });

        dialog.show();
    }

    private void getImageFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (UpdateProfileActivity.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_CODE);
            } else {
                try {
                    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    getIntent.setType("image/*");

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                    startActivityForResult(chooserIntent, GALLERY_REQUEST);
                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
            }
        }
    }


    private void launchCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            selectedImage = data.getData();
            binding.profilePicim.setImageURI(selectedImage);
            encode_image=imageToString(bitmap);

            uploadPhoto(encode_image);

        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            binding.profilePicim.setImageBitmap(photo);


            encode_image=imageToString(photo);
            uploadPhoto(encode_image);


        }
    }
    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
    private void uploadPhoto(String encode_image) {
        Toast.makeText(this, "IN UPLOADPHOTO FUNC", Toast.LENGTH_SHORT).show();
        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        int adminId= LoginUtils.getInstance(this).getVendorInfo().getId();
        uploadPhotoViewModel.getUploadPhotoResponseLiveData(new UploadPhoto(encode_image,adminId)).observe(this, uploadProfileApiResponse -> {
            if (!uploadProfileApiResponse.isError()) {
                Toast.makeText(this, uploadProfileApiResponse.getMessage(), Toast.LENGTH_LONG).show();
                //LoginUtils.getInstance(this).saveUserInfo(addBannerApiResponse.getUser());
                progressDialog.dismiss();
            }else
            {
                progressDialog.cancel();
                Toast.makeText(this, uploadProfileApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}