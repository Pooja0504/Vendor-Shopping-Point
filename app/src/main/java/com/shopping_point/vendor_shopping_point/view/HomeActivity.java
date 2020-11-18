package com.shopping_point.vendor_shopping_point.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.shopping_point.vendor_shopping_point.model.NewsFeed;
import com.shopping_point.vendor_shopping_point.model.UploadPhoto;
import com.shopping_point.vendor_shopping_point.receiver.NetworkChangeReceiver;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;
import com.shopping_point.vendor_shopping_point.utils.OnNetworkListener;
import com.shopping_point.vendor_shopping_point.viewModel.NewsFeedViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.UploadPhotoViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.VendorImageViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.vendor_shopping_point.utils.Constant.CAMERA_PERMISSION_CODE;
import static com.shopping_point.vendor_shopping_point.utils.Constant.CAMERA_REQUEST;
import static com.shopping_point.vendor_shopping_point.utils.Constant.GALLERY_REQUEST;
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
Bitmap bitmap;
    String encode_image;
    public  ArrayList<String> poster;
    private NewsFeedViewModel newsFeedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        poster=new ArrayList<>();
        binding.included.content.addproduct.setOnClickListener(this);
        binding.included.content.activateproduct.setOnClickListener(this);
        binding.included.content.promotions.setOnClickListener(this);
        uploadPhotoViewModel = ViewModelProviders.of(this).get(UploadPhotoViewModel.class);
        newsFeedViewModel = ViewModelProviders.of(this).get(NewsFeedViewModel.class);
        vendorImageViewModel = ViewModelProviders.of(this).get(VendorImageViewModel.class);
        snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);
        setUpViews();
        flipImages(poster);
        getVendorImage();
        getPosters();
        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver.setOnNetworkListener(this);


    }

    private void getPosters() {
      newsFeedViewModel.getPosters().observe(this, NewsFeedResponse -> {

            List<NewsFeed> newsFeeds = NewsFeedResponse.getPosters();
            for(int i=0;i<newsFeeds.size();i++)
            {
                NewsFeed currentNewsFeed = newsFeeds.get(i);

                String posterUrl =  currentNewsFeed.getImage().replaceAll("\\\\", "/");
            //   poster.add(posterUrl);


                ImageView imageView = new ImageView(this);

                Glide.with(this)
                        .load(posterUrl)
                        .into(imageView);


                binding.included.content.imageSlider.addView(imageView);
            }




        });}


    private void flipImages(ArrayList<String> images) {
        for (String image : images) {
            ImageView imageView = new ImageView(this);
            Glide.with(this)
                    .load(image)
                    .into(imageView);
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

            case R.id.addproduct:
                Intent addproduct = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(addproduct);
                break;
            case R.id.activateproduct:
                Intent activateproduct = new Intent(HomeActivity.this, MyProductActivity.class);
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

        int id = LoginUtils.getInstance(this).getVendorInfo().getId();
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null ) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), path);
                circleImageView.setImageBitmap(bitmap);
               circleImageView.setVisibility(View.VISIBLE);

                encode_image= imageToString(bitmap);
uploadImage(encode_image,id);

            } catch (IOException e) {
                e.printStackTrace();
            }



        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            circleImageView.setImageBitmap(photo);

            String encodePhoto = imageToString(photo);
            Toast.makeText(this, encodePhoto, Toast.LENGTH_SHORT).show();
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

    private void uploadImage(String encodephoto, int id) {

        Toast.makeText(this, encodephoto, Toast.LENGTH_SHORT).show();

        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        progressDialog.setMessage("Profile Uploading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Toast.makeText(this, "Before upload", Toast.LENGTH_SHORT).show();
        uploadPhotoViewModel.getUploadPhotoResponseLiveData(new UploadPhoto(encodephoto,id)).observe(this, uploadPhotoApiResponse -> {
            Toast.makeText(this, "In api response", Toast.LENGTH_SHORT).show();
            if (!uploadPhotoApiResponse.isError()) {
                Toast.makeText(this, uploadPhotoApiResponse.getMessage(), Toast.LENGTH_LONG).show();
                //LoginUtils.getInstance(this).saveUserInfo(addBannerApiResponse.getUser());
                progressDialog.dismiss();
            }else
            {
                progressDialog.cancel();
                Toast.makeText(this, uploadPhotoApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
       }else if(id==R.id.nav_signOut){
           LoginUtils.getInstance(this).clearAll();
           Intent intent = new Intent(this, LoginActivity.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
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