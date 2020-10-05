package com.shopping_point.vendor_shopping_point;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import com.shopping_point.vendor_shopping_point.ApiClient.ApiClient;
import com.shopping_point.vendor_shopping_point.requestbyapp.UserRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    EditText sname, semail, sphone, spassword;
    public String v_name, v_email, v_phone, v_password;
    Button button;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        sname = findViewById(R.id.name);
        semail = findViewById(R.id.email);
        sphone = findViewById(R.id.phone);
        spassword = findViewById(R.id.password);
        button = findViewById(R.id.button);
        linearLayout = findViewById(R.id.linlat);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v_name = sname.getText().toString().trim();
                v_email = semail.getText().toString().trim();
                v_phone = sphone.getText().toString().trim();
                v_password = spassword.getText().toString().trim();
                if (validinput()) {
                    final String vendor_name = v_name;
                    final String vendor_email = v_email;
                    final String vendor_phone = v_phone;
                    final String vendor_password = v_password;

                    Call<UserRequest> userRequestCall = ApiClient.getUserService().saveUsers(vendor_name, vendor_email, vendor_phone, vendor_password);
                    userRequestCall.enqueue(new Callback<UserRequest>() {
                        @Override
                        public void onResponse(@NonNull Call<UserRequest> call, @NonNull Response<UserRequest> response) {
                            if (response.isSuccessful()) {
                                String stat= null;
                                if (response.body() != null) {
                                    Toast.makeText(Registration.this, "Success", Toast.LENGTH_SHORT).show();
                                    stat = response.body().getStatus().toUpperCase();
                                }
                                String msg= null;
                                if (response.body() != null) {
                                    msg = response.body().getMessage().toUpperCase();
                                }
                                showMsg(stat, msg);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<UserRequest> call, @NonNull Throwable t) {
                            showMsg("Fail", t.getMessage());
                            System.out.println("Fail" + t.getMessage());
                        }
                    });

                }
            }
        });

    }


    private boolean validinput() {
        if (v_name.isEmpty() || v_email.isEmpty() || v_phone.isEmpty() || v_password.isEmpty()|| v_password.length()>6) {
            showMsg("Error", "Kindly Fill Details Properly");
            return false;
        } else {
            return true;
        }
    }

    private void showMsg(String titel, String message) {
        Snackbar snackbar = Snackbar.make(linearLayout, titel + "\n" + message, Snackbar.LENGTH_SHORT);
        snackbar.setAction("Re-Try", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearText();
            }
        });
        snackbar.setTextColor(Color.RED);
        snackbar.setBackgroundTint(Color.TRANSPARENT);
        snackbar.setActionTextColor(Color.BLUE);
        snackbar.show();
    }

    private void clearText() {
        sname.setText("");
        semail.setText("");
        sphone.setText("");
        spassword.setText("");
    }
}