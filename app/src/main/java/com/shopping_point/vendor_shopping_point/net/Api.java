package com.shopping_point.vendor_shopping_point.net;


import com.shopping_point.vendor_shopping_point.model.AddBannerApiResponse;
import com.shopping_point.vendor_shopping_point.model.AddProductApiResponse;
import com.shopping_point.vendor_shopping_point.model.Banner;
import com.shopping_point.vendor_shopping_point.model.CategoryResponse;
import com.shopping_point.vendor_shopping_point.model.Image;
import com.shopping_point.vendor_shopping_point.model.LoginApiResponse;
import com.shopping_point.vendor_shopping_point.model.MyProductResponse;
import com.shopping_point.vendor_shopping_point.model.NewsFeedResponse;
import com.shopping_point.vendor_shopping_point.model.GetBannersApiResponse;
import com.shopping_point.vendor_shopping_point.model.NotificationResponse;
import com.shopping_point.vendor_shopping_point.model.OrderApiResponse;
import com.shopping_point.vendor_shopping_point.model.Otp;
import com.shopping_point.vendor_shopping_point.model.Product;
import com.shopping_point.vendor_shopping_point.model.RegisterApiResponse;
import com.shopping_point.vendor_shopping_point.model.Update;
import com.shopping_point.vendor_shopping_point.model.UpdateApiResponse;
import com.shopping_point.vendor_shopping_point.model.Upload;
import com.shopping_point.vendor_shopping_point.model.UploadProfileApiResponse;
import com.shopping_point.vendor_shopping_point.model.Vendor;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @DELETE("history/remove.php")
    Call<ResponseBody> removeAllFromHistory();

    @GET("vendor/getImage.php")
    Call<Image> getUserImage(@Query("id") int vendorId);

    @GET("vendor/info.php")
    Call<ResponseBody> updatePassword(@Query("password") String newPassword, @Query("email") String email);

    @GET("vendor/otp.php")
    Call<Otp> getOtp(@Query("email") String email);

    @GET("posters/getPostersAdmin.php")
    Call<NewsFeedResponse> getPosters();

    @GET("vendor/getNotification.php")
    Call<NotificationResponse> getNotification(@Query("id")int seller_id);

    @POST("vendor/addProduct.php")
    Call<AddProductApiResponse> createProduct(@Body Product product);

    @GET("vendor/get_product.php")
    Call<MyProductResponse> getMyproduct(@Query("id")int seller_id);


    @GET("admin/get_category.php")
    Call<CategoryResponse> getCategory();

    @POST("vendor/update_profile.php")
    Call<UpdateApiResponse> updateProfile(@Body Update update);

    @POST("vendor/addBanner.php")
    Call<AddBannerApiResponse> createBanner(@Body Banner banner);

    @POST("vendor/upload.php")
    Call<UploadProfileApiResponse> upload_profile_image(@Body Upload upload);

    @GET("vendor/deactivate_product.php")
    Call<ResponseBody> deactivateProduct(@Query("product_id") int product_id);

    @GET("vendor/activate_product.php")
    Call<ResponseBody> activateProduct(@Query("product_id") int product_id);

    @GET("posters/getPosters.php")
    Call<GetBannersApiResponse> getBanners();

    @GET("admin/remove_banner_vendor.php")
    Call<ResponseBody> removePoster(@Query("poster_id") int poster_id);


    @GET("vendor/ordering_details.php")
    Call<OrderApiResponse> getOrdersDetails(@Query("id")int seller_id);
}
