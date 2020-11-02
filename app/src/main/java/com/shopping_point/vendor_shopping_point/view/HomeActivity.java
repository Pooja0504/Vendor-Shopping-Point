package com.shopping_point.vendor_shopping_point.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.ActivityHomeBinding;
import com.shopping_point.vendor_shopping_point.receiver.NetworkChangeReceiver;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;
import com.shopping_point.vendor_shopping_point.utils.OnNetworkListener;
import com.shopping_point.vendor_shopping_point.utils.Slide;
import com.shopping_point.vendor_shopping_point.viewModel.UploadPhotoViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.VendorImageViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.vendor_shopping_point.utils.Constant.CAMERA_PERMISSION_CODE;
import static com.shopping_point.vendor_shopping_point.utils.Constant.CAMERA_REQUEST;
import static com.shopping_point.vendor_shopping_point.utils.Constant.GALLERY_REQUEST;
import static com.shopping_point.vendor_shopping_point.utils.Constant.LOCALHOST;
import static com.shopping_point.vendor_shopping_point.utils.Constant.READ_EXTERNAL_STORAGE_CODE;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, OnNetworkListener {

    private static final String TAG = "HomeActivity";
    private ActivityHomeBinding binding;
    private Snackbar snack;
    private NetworkChangeReceiver mNetworkReceiver;
    private CircleImageView circleImageView,circleImageView2;
    private Uri selectedImage;
    private UploadPhotoViewModel uploadPhotoViewModel;
    private VendorImageViewModel vendorImageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.included.content.myproduct.setOnClickListener(this);
        binding.included.content.addproduct.setOnClickListener(this);
        binding.included.content.activateproduct.setOnClickListener(this);
        binding.included.content.promotions.setOnClickListener(this);
        uploadPhotoViewModel = ViewModelProviders.of(this).get(UploadPhotoViewModel.class);
        vendorImageViewModel = ViewModelProviders.of(this).get(VendorImageViewModel.class);
        snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);
        setUpViews();
        flipImages(Slide.getSlides());
        getVendorImage();
        
        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver.setOnNetworkListener(this);


    }



    private void flipImages(ArrayList<Integer> images) {
        for (int image : images) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(image);
            binding.included.content.imageSlider.addView(imageView);

        }

        binding.included.content.imageSlider.setFlipInterval(2000);
        binding.included.content.imageSlider.setAutoStart(true);

        // Set the animation for the view that enters the screen
        binding.included.content.imageSlider.setInAnimation(this, R.anim.slide_in_right);
        // Set the animation for the view leaving th screen
        binding.included.content.imageSlider.setOutAnimation(this, R.anim.slide_out_left);
    }

    private void setUpViews() {
        Toolbar toolbar = binding.included.toolbar;
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);



        View headerContainer = binding.navView.getHeaderView(0);
        circleImageView = headerContainer.findViewById(R.id.profilePic);
        circleImageView2 = headerContainer.findViewById(R.id.plus);
        circleImageView2.setOnClickListener(this);

        TextView organisation_name = headerContainer.findViewById(R.id.nameOfVendor);
        organisation_name.setText(LoginUtils.getInstance(this).getVendorInfo().getOrganisation_name());
        TextView seller_mail = headerContainer.findViewById(R.id.email_of_vendor);
        seller_mail.setText(LoginUtils.getInstance(this).getVendorInfo().getEmail());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plus:
                showCustomAlertDialog();
                break;
            case R.id.myproduct:
                Intent myproduct = new Intent(getApplicationContext(), MyProductActivity.class);
                startActivity(myproduct);
                break;
            case R.id.addproduct:
                Intent addproduct = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(addproduct);
                break;
            case R.id.activateproduct:
                Intent activateproduct = new Intent(HomeActivity.this, ActivateProductActivity.class);
                startActivity(activateproduct);
                break;
            case R.id.promotions:
                Intent promotions = new Intent(HomeActivity.this, AddBannerActivity.class);
                startActivity(promotions);
                break;
        }

    }

    private void showCustomAlertDialog() {
        final Dialog dialog = new Dialog(HomeActivity.this);
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
            if (HomeActivity.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_CODE);
            } else {
                try {
                    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    getIntent.setType("image/*");

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri path = data.getData();
        Bitmap bitmap = null;
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            selectedImage = data.getData();
            circleImageView.setImageURI(selectedImage);

            int id = LoginUtils.getInstance(this).getVendorInfo().getId();
            String encodephoto = imageToString(bitmap);

          //  Toast.makeText(this, encodephoto, Toast.LENGTH_SHORT).show();
            uploadImage(encodephoto,id);

        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            circleImageView.setImageBitmap(photo);
            int id = LoginUtils.getInstance(this).getVendorInfo().getId();
            String encodePhoto = imageToString(photo);
          //  Toast.makeText(this, encodePhoto, Toast.LENGTH_SHORT).show();
            uploadImage(encodePhoto,id);

        }
    }
    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    private void uploadImage(String photo,int id) {
        uploadPhotoViewModel.uploadPhoto(photo,id).observe(this, response -> {
            //Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();

            if(response!= null){
                Log.d(TAG, "onResponse: upload profile picture" + response);
                Toast.makeText(this, "NOt Null", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(this, "NULL", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getVendorImage() {
        vendorImageViewModel.getVendorImage(LoginUtils.getInstance(this).getVendorInfo().getId()).observe(this, response -> {
            if (response != null) {
                String imageUrl = response.getImage().replaceAll("\\\\", "/");
                //Toast.makeText(this, imageUrl, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "GET USER IMAGE : " + imageUrl);
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
                        .into(circleImageView);
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

       if (id == R.id.nav_myAccount) {
            Intent accountIntent = new Intent(this, AccountActivity.class);
            startActivity(accountIntent);
        } else if (id == R.id.nav_newsFeed) {
            Intent newsFeedIntent = new Intent(this, NewsFeedActivity.class);
            startActivity(newsFeedIntent);
        }else if (id == R.id.nav_settings) {
           Intent navsetting = new Intent(this, NavSettings.class);
           startActivity(navsetting);
       }else if (id == R.id.nav_notification) {
           Intent NavNotification = new Intent(this, NavNotification.class);
           startActivity(NavNotification);
       }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showSnackBar() {
        snack.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snack.show();
    }

    public void hideSnackBar() {
        snack.dismiss();
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerNetworkBroadcastForNougat();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    public void onNetworkConnected() {
        hideSnackBar();
    }

    @Override
    public void onNetworkDisconnected() {
        showSnackBar();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            closeApplication();
        }
    }
    private void closeApplication() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.want_to_exit)
                .setPositiveButton(R.string.ok, (dialog, which) -> finish())
                .setNegativeButton(R.string.cancel, null)
                .show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.darkGreen));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.darkGreen));
    }
}