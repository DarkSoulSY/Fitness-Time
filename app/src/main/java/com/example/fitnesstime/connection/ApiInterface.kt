package com.example.fitnesstime.connection

import com.example.fitnesstime.ui.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST("User/signup.php")
    suspend fun createAccount(@Body createAccountDTO: CreateAccountDTO): Response<ServiceResponse<String>>

    @POST("User/preferences.php")
    suspend fun addPreferences(@Body createAccountPreferencesDTO: CreateAccountPreferencesDTO): Response<ServiceResponse<String>>

    @GET("User/login.php")
    suspend fun signInAccount(@Query("email") email:String, @Query("password") password:String) : Response<ServiceResponse<LoginAccountDTO>>

    @GET("get_base_goal.php")
    fun getBaseGoal(@Query("email") email:String) : Response<ServiceResponse<Int>>

    @GET("search/product.php")
    fun getProductInformation(@Query("product_name") email:String) : Response<ServiceResponse<ProductInformation>>

    //@GET("get_productName_qantity.php")

    //@GET("diary/get_all_info.php")

}