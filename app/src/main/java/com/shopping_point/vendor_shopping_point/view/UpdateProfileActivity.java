package com.shopping_point.vendor_shopping_point.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.ActivityUpdateProfileBinding;
import com.shopping_point.vendor_shopping_point.model.Update;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;
import com.shopping_point.vendor_shopping_point.utils.Validation;
import com.shopping_point.vendor_shopping_point.viewModel.UpdateProfileViewModel;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "UpdateProfileActivity";
    private UpdateProfileViewModel updateProfileViewModel;
    private ActivityUpdateProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
         binding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile);
        binding.name.setText(LoginUtils.getInstance(this).getVendorInfo().getOrganisation_name());
        binding.email.setText(LoginUtils.getInstance(this).getVendorInfo().getEmail());
        binding.contact.setText(LoginUtils.getInstance(this).getVendorInfo().getPhone_no());



        binding.proceed.setOnClickListener(this);
        binding.cancleUpdate.setOnClickListener(this);


        updateProfileViewModel = ViewModelProviders.of(this).get(UpdateProfileViewModel.class);
        // setBoldStyle();
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
                // LoginUtils.getInstance(this).saveUserInfo(updateApiResponse.getUser());
                progressDialog.dismiss();
                // goToLoginActivity();
            } else {
                progressDialog.cancel();
                //Toast.makeText(this, registerApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
        }
    }

}