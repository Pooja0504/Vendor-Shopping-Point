package com.shopping_point.vendor_shopping_point;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.shopping_point.vendor_shopping_point.SplashScreen.appPreferences;

public class MainActivity extends AppCompatActivity {
    EditText product_name, price, description, rating;
    Button browse, upload;
    ImageView img;
    TextView tv_name,tv_email;
    Bitmap bitmap;
    String encodeImageString;
    private ProgressBar progressBar;

    private static final String url = "https://ar-application.000webhostapp.com/AR_Shopping/upload_product.php";


    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        img = findViewById(R.id.img);
        upload = findViewById(R.id.upload);
        browse = findViewById(R.id.browse);
        progressBar = findViewById(R.id.progress_bar_login);

        nav=findViewById(R.id.navmenu);
        drawerLayout=findViewById(R.id.drawer);

        View navView =  nav.inflateHeaderView(R.layout.navheader);
        tv_name=navView.findViewById(R.id.drawer_name);
        tv_email=navView.findViewById(R.id.drawer_email);
        Bundle extras = getIntent().getExtras();

        String name = extras.getString("name");
        String email = extras.getString("email");

        tv_name.setText(name +"");
        tv_email.setText(email + "");

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(MainActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);
                            }
                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                            }
                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                upload.setVisibility(View.GONE);
                browse.setEnabled(false);
                uploaddatatodb();
            }
        });


customnavdrawer();


    }

    private void customnavdrawer() {

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav=findViewById(R.id.navmenu);
        drawerLayout=findViewById(R.id.drawer);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_home :

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_call :
                        Toast.makeText(getApplicationContext(),"Contact us Panel is Open",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_setting :
                        Toast.makeText(getApplicationContext(),"Setting Panel is Open", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_logout :
                        appPreferences.setLoginStatus(false);
                        startActivity(new Intent(getApplicationContext(),Login.class));

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {

                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            } catch (Exception ex) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void uploaddatatodb() {
        product_name = findViewById(R.id.product_name);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        rating = findViewById(R.id.rating);
        final String name = product_name.getText().toString().trim();
        final String price = this.price.getText().toString().trim();
        final String desc = description.getText().toString().trim();
        final String rating = this.rating.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                product_name.setText("");
                MainActivity.this.price.setText("");
                description.setText("");
                MainActivity.this.rating.setText("");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("response");
                    img.setImageResource(R.drawable.ic_launcher_foreground);

                    if(success.equals("success")) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        upload.setVisibility(View.VISIBLE);
                        browse.setEnabled(true);
                        product_name.requestFocus();
                    }else
                    {
                        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        upload.setVisibility(View.VISIBLE);
                        browse.setEnabled(true);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed\n Image size too large", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                upload.setVisibility(View.VISIBLE);
                browse.setEnabled(true);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", name);
                map.put("price", price);
                map.put("rating", rating);
                map.put("description", desc);
                map.put("upload", encodeImageString);
                return map;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}