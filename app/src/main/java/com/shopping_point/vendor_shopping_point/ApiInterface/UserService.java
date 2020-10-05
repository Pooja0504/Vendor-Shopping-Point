package com.shopping_point.vendor_shopping_point.ApiInterface;

import com.shopping_point.vendor_shopping_point.requestbyapp.UserRequest;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
    @FormUrlEncoded
    @POST("simpleregister.php")
    Call<UserRequest> saveUsers(
            @Field("vendor_name") String vendor_name,
            @Field("vendor_email") String vendor_email,
            @Field("vendor_phone") String vendor_phone,
            @Field("vendor_password") String vendor_password
    );
    // Call<UserResponse> saveUsers(@Body UserRequest userRequest);
}
