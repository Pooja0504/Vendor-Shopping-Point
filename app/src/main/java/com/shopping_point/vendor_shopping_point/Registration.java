package com.shopping_point.vendor_shopping_point;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Registration extends AppCompatActivity {

    private ProgressBar progressBar;
    TextView link_to_login;
    int RC_SIGN_IN = 0;
    static final String URL_SELLER_REG = "http://myleader.sparsematrix.co.in/vrshop/vendor_register.php";
    TextInputEditText edt_name, edt_email, edt_pass, edt_conf_pass,edt_contact;
    Button btn_reg;
    public static String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]{2,}+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        btn_reg = findViewById(R.id.btn_register);
        edt_name = findViewById(R.id.edt_name);
        edt_contact=findViewById(R.id.edt_phone_no);
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        edt_conf_pass = findViewById(R.id.edt_confpass);
        progressBar = findViewById(R.id.progress_bar);
        link_to_login = findViewById(R.id.link_to_login);

        edt_name.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        edt_contact.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        edt_email.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        edt_pass.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        edt_conf_pass.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sname = edt_name.getText().toString().toUpperCase().trim();
                final String semail = edt_email.getText().toString().toLowerCase().trim();
                final String scontact = edt_contact.getText().toString().toLowerCase().trim();
                final String spass = edt_pass.getText().toString().trim();
                final String conf_pass=edt_conf_pass.getText().toString().trim();


                if (sname.isEmpty()) {
                    edt_name.setError("Please Enter Organisation Name");
                    edt_name.requestFocus();
                    return;
                } else if (semail.isEmpty() || !semail.matches(emailPattern)) {

                    edt_email.setError("Please enter right email Address");
                    edt_email.requestFocus();
                    return;


                } else if (spass.isEmpty() || conf_pass.isEmpty()) {
                    edt_pass.setError("Please Enter Password");
                    edt_pass.requestFocus();

                } else if(!spass.equals(conf_pass))
                {
                    edt_pass.setError("Password and conform password not matched");
                    edt_pass.requestFocus();

                }else if(scontact.isEmpty()){
                    edt_contact.setError("Please Enter Organisation Name");
                    edt_contact.requestFocus();
                    return;
                }

                else


                {
                    progressBar.setVisibility(View.VISIBLE);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SELLER_REG, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                // Toast.makeText(AdminPanel.this, success, Toast.LENGTH_SHORT).show();
                                if (success.equals("1")) {


                                    Toast.makeText(Registration.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                    clearText();
                                    startActivity(new Intent(Registration.this, Login.class));
                                    progressBar.setVisibility(View.GONE);


                                } else if(success.equals("already_registred")) {
                                    edt_email.setError("email id already registred");
                                    edt_email.requestFocus();
                                    progressBar.setVisibility(View.GONE);

                                }else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Registration.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                                    edt_name.requestFocus();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Registration.this, "Error ! " + e.toString(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);


                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Registration.this, "Error ! " + error.toString(), Toast.LENGTH_SHORT).show();
                                    System.out.println(error.toString());
                                    progressBar.setVisibility(View.GONE);
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("organisation", sname);
                            params.put("email", semail);
                            params.put("contact", scontact);
                            params.put("password", spass);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);


                }
            }
        });
    }

    public void vendorlogin(View view) {
        clearText();
        Intent intent = new Intent(Registration.this, Login.class);
        startActivity(intent);
    }
    public static class EmojiExcludeFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }

            return null;
        }
    }

    public void clearText()
    {
        edt_name.setText("");
        edt_email.setText("");
        edt_contact.setText("");
        edt_pass.setText("");
        edt_conf_pass.setText("");
    }
    @Override
    public void onBackPressed() {
        return;
    }
}