package com.shopping_point.vendor_shopping_point;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import static com.shopping_point.vendor_shopping_point.SplashScreen.appPreferences;

public class Login extends AppCompatActivity {
    TextInputEditText edt_email_login, edt_pass_login;
    Button btn_login;
    LinearLayout login_Llayout;
    static final String URL_LOGIN_VENDOR = "http://myleader.sparsematrix.co.in/vrshop/temp/vendor/vendor_login.php";
    private ProgressBar progressBar;
    static String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]{2,}+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email_login = findViewById(R.id.edt_email_login);
        edt_pass_login = findViewById(R.id.edt_pass_login);
        btn_login = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progress_bar_login);
        login_Llayout = findViewById(R.id.vendorLogin_Llayout);
        edt_email_login.setFilters(new InputFilter[]{new Registration.EmojiExcludeFilter()});
        edt_pass_login.setFilters(new InputFilter[]{new Registration.EmojiExcludeFilter()});
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_ac_vendor();
            }
        });
    }
    private void login_ac_vendor() {
        final String s_email = edt_email_login.getText().toString().trim();
        final String s_password = edt_pass_login.getText().toString().trim();

        if (s_email.isEmpty() || !s_email.matches(emailPattern)) {
            edt_email_login.setError("Please enter right email Address");
            edt_email_login.requestFocus();
            return;
        } else if (s_password.isEmpty()) {

            edt_pass_login.setError("Please enter  Password");
            edt_pass_login.requestFocus();
            return;

        } else {
            progressBar.setVisibility(View.VISIBLE);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN_VENDOR, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("login");
                        if (success.equals("1")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String name = object.getString("name").trim();
                                String email = object.getString("email").trim();
                                appPreferences.setLoginStatus(true);
                                appPreferences.setDiaplayName(name);
                                appPreferences.setDiaplayEmail(email);
                                //Toast.makeText(Login.this, "Success \n Welcome : " + name, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this,MainActivity.class).putExtra("name",name).putExtra("email",email);
                                clearText();
                                startActivity(intent);

                                progressBar.setVisibility(View.GONE);

                            }
                        }else if(success.equals("0"))
                        { progressBar.setVisibility(View.GONE);
                            showSnackbar(" Account is not activated ");
                            //  Toast.makeText(Login.this, " Account is not activated ", Toast.LENGTH_SHORT).show();
                        }else if(success.equals("not_registred"))
                        {
                            progressBar.setVisibility(View.GONE);

                            edt_email_login.setError("email id is not registred yet");
                            edt_email_login.requestFocus();

                        }else
                        if(success.equals("wrong_password"))
                        {
                            progressBar.setVisibility(View.GONE);
                            edt_pass_login.setError("wrong password");
                            edt_pass_login.requestFocus();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        btn_login.setEnabled(true);
                        //Toast.makeText(Login.this, "ERROR!! \n Please Try Again" , Toast.LENGTH_SHORT).show();
                        showSnackbar("ERROR!!   Please Try Again");
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.GONE);
                            btn_login.setEnabled(true);
                            Toast.makeText(Login.this, "ERROR " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", s_email);
                    params.put("password", s_password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    public void forgotpassword(View view) {
        clearText();
        Intent intent= new Intent(Login.this,Forgot_Password.class);
        startActivity(intent);
    }
    public void clearText()
    {
        edt_email_login.setText("");
        edt_pass_login.setText("");
        edt_email_login.requestFocus();
    }

    public void showSnackbar(String text)
    {
        Snackbar snackbar=Snackbar.make(login_Llayout,text,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public void register(View view) {
        Intent intent= new Intent(Login.this,Registration.class);
        startActivity(intent);
    }
}