package com.shopping_point.vendor_shopping_point;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forgot_Password extends AppCompatActivity
{
    String str_email;
    EditText edt_forgot_email;
    Button btn_forgot;
    EditText edt_forgot_OTP;
    ProgressBar progressBar;
    ConstraintLayout forgot_layout;

    static final String URL_FORGOT = "https://ar-application.000webhostapp.com/AR_Shopping/forgot_password_send_email_vendor.php";
    static final String URL_RESET= "https://ar-application.000webhostapp.com/AR_Shopping/reset_vendor_password.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        edt_forgot_email=findViewById(R.id.edt_forgot_email);
        btn_forgot=findViewById(R.id.btn_forgot);
        edt_forgot_OTP=findViewById(R.id.edt_forgot_otp);
        progressBar=findViewById(R.id.progress_bar_forgot_otp);
        forgot_layout=findViewById(R.id.forgot_layout);
       // edt_forgot_email.setFilters(new InputFilter[]{new Registration.EmojiExcludeFilter()});
        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_forgot.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                final String  email=edt_forgot_email.getText().toString().trim();
                str_email=email;
                //Toast.makeText(Forgot_Password.this, ""+str_email, Toast.LENGTH_SHORT).show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FORGOT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("response");
                            final String otp_server = jsonObject.getString("otp");
                          //  Toast.makeText(Forgot_Password.this, otp_server, Toast.LENGTH_SHORT).show();
                            // Toast.makeText(RegisterPage.this, success, Toast.LENGTH_SHORT).show();
                            if(success.equals("success")) {
                                progressBar.setVisibility(View.GONE);
                                btn_forgot.setVisibility(View.VISIBLE);
                                edt_forgot_OTP.setVisibility(View.VISIBLE);
                                showSnackbar("EMAIL SEND SUCESSFULLY");
                               // Toast.makeText(Forgot_Password.this, "EMAIL SEND SUCESSFULLY", Toast.LENGTH_SHORT).show();

                                btn_forgot.setText("verify");
                                btn_forgot.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String otp=edt_forgot_OTP.getText().toString().trim();
                                        if(otp.equals(otp_server.trim()))
                                        {
                                            showSnackbar("Verified Successfully");
                                            //Toast.makeText(Forgot_Password.this, "Verified Successfully", Toast.LENGTH_SHORT).show();
                                            customDialog();
                                        }else
                                        {
                                            showSnackbar("Wrong OTP");
                                            //Toast.makeText(Forgot_Password.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }else if(success.equals("fail"))
                            {
                                progressBar.setVisibility(View.GONE);
                                btn_forgot.setVisibility(View.VISIBLE);
                                showSnackbar("Please Try Again");
                                //Toast.makeText(Forgot_Password.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                            else if(success.equals("error"))
                            {
                                progressBar.setVisibility(View.GONE);
                                btn_forgot.setVisibility(View.VISIBLE);
                                // showMessage();
                                showSnackbar("Email id Not Found");
                                //Toast.makeText(Forgot_Password.this, "Email id Not Found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Forgot_Password.this, "ERROR " + e.toString(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            btn_forgot.setVisibility(View.VISIBLE);

                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Forgot_Password.this, "Error ! " + error.toString(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btn_forgot.setVisibility(View.VISIBLE);
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);

            }
        });
    }


    private void customDialog() {

        AlertDialog.Builder mydialog = new AlertDialog.Builder(Forgot_Password.this);
        LayoutInflater inflater = LayoutInflater.from(Forgot_Password.this);
        View myview = inflater.inflate(R.layout.forgot_password_custom_dialog, null);
        final AlertDialog dialog = mydialog.create();
        dialog.setView(myview);
        dialog.setCancelable(false);
        final EditText password = myview.findViewById(R.id.edt_pass_forgot);
        final EditText conf_password = myview.findViewById(R.id.edt_confpass_forgot);
        Button btnReset = myview.findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String str_password = password.getText().toString().trim();
                String str_conf_password = conf_password.getText().toString().trim();
                if (str_password.isEmpty()) {
                    password.setError("please enter password");
                } else if (str_conf_password.isEmpty())
                {
                    conf_password.setError("please enter conform password");
                }else if(!str_password.equals(str_conf_password))
                {
                    conf_password.setError("password not matched");
                }else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_RESET, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("response");


                                // Toast.makeText(RegisterPage.this, success, Toast.LENGTH_SHORT).show();
                                if(success.equals("success")) {
                                    edt_forgot_OTP.setVisibility(View.GONE);
                                    edt_forgot_email.setText("");
                                    //Toast.makeText(Forgot_Password.this, "PASSWORD RESET SUCESSFULLY", Toast.LENGTH_SHORT).show();
                                        showSnackbar("PASSWORD RESET SUCESSFULLY");
                                    dialog.setCancelable(true);
                                }else if(success.equals("fail"))
                                {
                                    edt_forgot_OTP.setVisibility(View.GONE);
                                    edt_forgot_email.setText("");
                                    edt_forgot_email.requestFocus();
                                    showSnackbar("Please Try Again");
                                    //Toast.makeText(Forgot_Password.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                                }else if(success.equals("error"))
                                {
                                    edt_forgot_OTP.setVisibility(View.GONE);
                                    edt_forgot_email.setText("");
                                    edt_forgot_email.requestFocus();
                                    showSnackbar("Email id is not registred ");
                                   // Toast.makeText(Forgot_Password.this, "Email id is not registred ", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                edt_forgot_OTP.setVisibility(View.GONE);
                                edt_forgot_email.setText("");
                                edt_forgot_email.requestFocus();
                                e.printStackTrace();
                                showSnackbar("ERROR " );
                                //Toast.makeText(Forgot_Password.this, "ERROR " + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    edt_forgot_OTP.setVisibility(View.GONE);
                                    edt_forgot_email.setText("");
                                    edt_forgot_email.requestFocus();
                                   showSnackbar("Error ! ");
                                    //Toast.makeText(Forgot_Password.this, "Error ! " + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", str_email);
                            params.put("password",str_password);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                }

                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void showSnackbar(String text)
    {
        Snackbar snackbar=Snackbar.make(forgot_layout,text,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}