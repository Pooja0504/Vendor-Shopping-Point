package com.shopping_point.vendor_shopping_point.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.ActivityPasswordAssistantBinding;
import com.shopping_point.vendor_shopping_point.viewModel.OtpViewModel;

import static com.shopping_point.vendor_shopping_point.utils.Constant.EMAIL;
import static com.shopping_point.vendor_shopping_point.utils.Constant.OTP;


public class PasswordAssistantActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PasswordAssistantActivi";
    private ActivityPasswordAssistantBinding binding;
    private OtpViewModel otpViewModel;
    private String userEmail;
    private String otpCode;
    public static String emailEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_assistant);

        otpViewModel = ViewModelProviders.of(this).get(OtpViewModel.class);

        binding.proceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            checkUserEmail();
        }
    }

    private void checkUserEmail() {
        emailEntered = binding.emailAddress.getText().toString();

        otpViewModel.getOtpCode(emailEntered).observe(this, responseBody -> {
            if (!responseBody.isError()) {

                userEmail = responseBody.getEmail();
                Toast.makeText(this, userEmail, Toast.LENGTH_SHORT).show();
                otpCode = responseBody.getOtp();
                goToAuthenticationActivity();
            } else {
                binding.emailAddress.setError(responseBody.getMessage());
            }
        });
    }

    private void goToAuthenticationActivity() {
        Intent intent = new Intent(this, AuthenticationActivity.class);

        intent.putExtra(EMAIL, userEmail);
        intent.putExtra(OTP, otpCode);
        startActivity(intent);
    }
}
