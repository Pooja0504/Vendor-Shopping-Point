package com.shopping_point.vendor_shopping_point.net;


import com.shopping_point.vendor_shopping_point.model.AddProductApiResponse;
import com.shopping_point.vendor_shopping_point.model.Image;
import com.shopping_point.vendor_shopping_point.model.LoginApiResponse;
import com.shopping_point.vendor_shopping_point.model.MyProductResponse;
import com.shopping_point.vendor_shopping_point.model.NewsFeedResponse;
import com.shopping_point.vendor_shopping_point.model.Otp;
import com.shopping_point.vendor_shopping_point.model.Product;
import com.shopping_point.vendor_shopping_point.model.RegisterApiResponse;
import com.shopping_point.vendor_shopping_point.model.Update;
import com.shopping_point.vendor_shopping_point.model.UpdateApiResponse;
import com.shopping_point.vendor_shopping_point.model.Vendor;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @POST("vendor/vendor_register.php")
    Call<RegisterApiResponse> createVendor(@Body Vendor vendor);

    @GET("vendor/vendor_login.php")
    Call<LoginApiResponse> logInVendor(@Query("email") String email, @Query("password") String password);

    @GET("vendor/deleteuser.php")
    Call<ResponseBody> deleteAccount(@Query("userId") int userId);

    @Multipart
    @POST("vendor/upload.php")
    Call<ResponseBody> uploadPhoto(@Part MultipartBody.Part userPhoto, @Part("id") RequestBody userId);

    @DELETE("history/remove.php")
    Call<ResponseBody> removeAllFromHistory();

    @GET("vendor/getImage.php")
    Call<Image> getUserImage(@Query("id") int vendorId);

    @GET("vendor/info.php")
    Call<ResponseBody> updatePassword(@Query("password") String newPassword, @Query("email") String email);

    @GET("users/otp.php")
    Call<Otp> getOtp(@Query("email") String email);

    @GET("posters/getPosters.php")
    Call<NewsFeedResponse> getPosters();

    @POST("vendor/addProduct.php")
    Call<AddProductApiResponse> createProduct(@Body Product product);

    @GET("vendor/get_product.php")
    Call<MyProductResponse> getMyproduct(@Query("id")int seller_id);

    @POST("vendor/update_profile.php")
    Call<UpdateApiResponse> updateProfile(@Body Update update);

}
