package com.example.fitnesstime.connection

import com.example.fitnesstime.ui.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    // Sign Up And Sign In end points

    @POST("User/signup.php")
    suspend fun createAccount(@Body createAccountDTO: CreateAccountDTO): Response<ServiceResponse<String>?>

    @POST("User/preferences.php")
    suspend fun addPreferences(@Body createAccountPreferencesDTO: CreateAccountPreferencesDTO): Response<ServiceResponse<String?>>

    @GET("User/login.php")
    suspend fun signIn(@Query("email") email:String, @Query("password") password:String) : Response<ServiceResponse<String?>>

    //Inside section of the program
    @GET("User/get_base_goal.php")
    suspend fun getBaseGoal(@Query("email") email:String) : Response<ServiceResponse<Int>>

    @GET("User/Unpaied_get_height_weight_gender.php")
    suspend fun getBodyMassIndexInfo(@Query("email") email:String) : Response<ServiceResponse<BodyMassIndex>>

    @GET("meal/getProducts.php")
    suspend fun getMeals(@Query("meal_type") meal_type: String,@Query("email") email: String, @Query("date") date: String): Response<ServiceResponse<List<AllMealsInformationAndQuantity>>>

    @GET("diary/getHistory.php")
    suspend fun getHistory(@Query("email") email:String): Response<ServiceResponse<List<Diary>>>

    @GET("search/all_product.php")
    suspend fun getAllProduct(): Response<ServiceResponse<List<SingleProductNutritionInformation>>>

    @GET("search/product.php")
    suspend fun getOneProduct(@Query("product_name") productName: String): Response<ServiceResponse<SingleProductNutritionInformation>>

    @GET("User/profile.php")
    suspend fun getProfileInfo(@Query("email") email:String) : Response<ServiceResponse<UserProfile>>

    @GET("meal/addProduct.php")
    suspend fun addProduct(@Query("meal_type") mealType : String, @Query("email") email : String, @Query("quantity") quantity : Int,@Query("product_id") productId: Int, @Query("date") date: String) : Response<ServiceResponse<String>>




}