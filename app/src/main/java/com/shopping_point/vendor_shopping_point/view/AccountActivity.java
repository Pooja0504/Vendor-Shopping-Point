package com.shopping_point.vendor_shopping_point.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.ActivityAccountBinding;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;
import com.shopping_point.vendor_shopping_point.viewModel.DeleteUserViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.FromHistoryViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.VendorImageViewModel;


import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.getEnglishState;
import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.setEnglishState;
import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.setLocale;
import static com.shopping_point.vendor_shopping_point.utils.Constant.LOCALHOST;


public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AccountActivity";
    private DeleteUserViewModel deleteUserViewModel;
    private FromHistoryViewModel fromHistoryViewModel;
    public static boolean historyIsDeleted = false;
    private VendorImageViewModel vendorImageViewModel;
    private CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        ActivityAccountBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_account);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.my_account));
        binding.updateProfile.setOnClickListener(this);

        deleteUserViewModel = ViewModelProviders.of(this).get(DeleteUserViewModel.class);
        fromHistoryViewModel = ViewModelProviders.of(this).get(FromHistoryViewModel.class);
        vendorImageViewModel = ViewModelProviders.of(this).get(VendorImageViewModel.class);

        binding.nameOfVendor.setText(LoginUtils.getInstance(this).getVendorInfo().getOrganisation_name());
        binding.emailOfVendor.setText(LoginUtils.getInstance(this).getVendorInfo().getEmail());
        getVendorImage();
        View headerContainer = binding.profileImageAccount.getRootView();
        circleImageView = headerContainer.findViewById(R.id.profile_image_account);

        binding.languages.setOnClickListener(this);
        binding.helpCenter.setOnClickListener(this);

        binding.changePassword.setOnClickListener(this);
        binding.deleteAccount.setOnClickListener(this);
    }

    
    private void getVendorImage() {
        vendorImageViewModel.getVendorImage(LoginUtils.getInstance(this).getVendorInfo().getId()).observe(this, response -> {
            if (response != null) {
                String imageUrl =  response.getImage().replaceAll("\\\\", "/");

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_signOut) {
            signOut();
            deleteAllProductsInHistory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        LoginUtils.getInstance(this).clearAll();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteAllProductsInHistory() {
        fromHistoryViewModel.removeAllFromHistory().observe(this, responseBody -> {
            Log.d(TAG,getString(R.string.all_removed));
        });
        historyIsDeleted = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_profile:
                Intent updateprofileIntent = new Intent(this, UpdateProfileActivity.class);
                startActivity(updateprofileIntent);
                break;

            case R.id.languages:
                showCustomAlertDialog();
                break;
            case R.id.helpCenter:
                Intent helpCenterIntent = new Intent(this, HelpActivity.class);
                startActivity(helpCenterIntent);
                break;
            case R.id.changePassword:
                Intent passwordIntent = new Intent(this, PasswordActivity.class);
                startActivity(passwordIntent);
                break;
            case R.id.deleteAccount:
                deleteAccount();
                break;
        }
    }

    private void deleteAccount() {
        deleteUserViewModel.deleteUser(LoginUtils.getInstance(this).getVendorInfo().getId()).observe(this, responseBody -> {
            if(responseBody!= null){
                LoginUtils.getInstance(getApplicationContext()).clearAll();
                try {
                  //  Toast.makeText(AccountActivity.this, responseBody.string() + "", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: delete account" + responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                goToLoginActivity();
            }
        });
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showCustomAlertDialog() {
        final Dialog dialog = new Dialog(AccountActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_language_dialog);

        Button english = dialog.findViewById(R.id.txtEnglish);
        Button hindi = dialog.findViewById(R.id.txtHindi);

        if(getEnglishState(this)){
            english.setEnabled(false);
            english.setAlpha(.5f);
            hindi.setEnabled(true);
        }else {
            hindi.setEnabled(false);
            hindi.setAlpha(.5f);
            english.setEnabled(true);
        }

        english.setOnClickListener(v -> {
            english.setEnabled(true);
            chooseEnglish();
            dialog.cancel();
        });

        hindi.setOnClickListener(v -> {
            hindi.setEnabled(true);
            chooseHindi();
            dialog.cancel();
        });

        dialog.show();
    }

    private void chooseEnglish() {
        setLocale(this,"en");
        recreate();
       // Toast.makeText(this, "English", Toast.LENGTH_SHORT).show();
        setEnglishState(this, true);
    }

    private void chooseHindi() {
        setLocale(this,"hi");
        recreate();
       // Toast.makeText(this, "Hindi", Toast.LENGTH_SHORT).show();
        setEnglishState(this, false);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
