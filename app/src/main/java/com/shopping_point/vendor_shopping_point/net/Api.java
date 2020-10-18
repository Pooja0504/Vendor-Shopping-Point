package com.shopping_point.vendor_shopping_point.net;


import com.shopping_point.vendor_shopping_point.model.LoginApiResponse;
import com.shopping_point.vendor_shopping_point.model.RegisterApiResponse;
import com.shopping_point.vendor_shopping_point.model.Vendor;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("vendor/vendor_register.php")
    Call<RegisterApiResponse> createVendor(@Body Vendor vendor);

    @GET("vendor/vendor_login.php")
    Call<LoginApiResponse> logInVendor(@Query("email") String email, @Query("password") String password);

    @GET("vendor/deleteuser.php")
    Call<ResponseBody> deleteAccount(@Query("userId") int userId);


}
