package com.app.posaboda.services;


import com.app.posaboda.cart_models.CartDataModel;
import com.app.posaboda.models.ClientDataModel;
import com.app.posaboda.models.DeptTypeBrandDataModel;
import com.app.posaboda.models.ProductDataModel;
import com.app.posaboda.models.SaleOrderDataModel;
import com.app.posaboda.models.SingleClientModel;
import com.app.posaboda.models.StatusResponse;
import com.app.posaboda.models.StockDataModel;
import com.app.posaboda.models.UserModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {


    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(@Field("user_name") String user_name,
                          @Field("password") String password

    );

    @GET("api/allSalesOrders")
    Call<SaleOrderDataModel> getSaleOrder(@Header("Authorization") String user_token

    );

    @GET("api/getProfileData")
    Call<UserModel> getUserData(@Header("Authorization") String user_token

    );

    @GET("api/categories")
    Call<DeptTypeBrandDataModel> getCategory();

    @GET("api/types")
    Call<DeptTypeBrandDataModel> getType();

    @GET("api/brands")
    Call<DeptTypeBrandDataModel> getBrand();

    @GET("api/warehouses")
    Call<StockDataModel> getStock();

    @FormUrlEncoded
    @POST("api/filterItems")
    Call<ProductDataModel> getProducts(@Header("Authorization") String user_token,
                                       @Field("warehouse_id") int warehouse_id,
                                       @Field("category_id") String category_id,
                                       @Field("type_id") String type_id,
                                       @Field("brand_id") String brand_id,
                                       @Field("search_word") String search_word
    );

    @FormUrlEncoded
    @POST("api/addNewClientApi")
    Call<SingleClientModel> addClient(@Header("Authorization") String user_token,
                                      @Field("name") String name,
                                      @Field("phone") String phone
    );

    @GET("api/clients")
    Call<ClientDataModel> getClients();

    @POST("api/addNewSalesOrder")
    Call<StatusResponse> addOrder(@Header("Authorization") String user_token,
                                  @Body CartDataModel cartDataModel
                                  );


}

