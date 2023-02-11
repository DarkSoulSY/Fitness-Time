package com.example.fitnesstime.connection

import androidx.annotation.Nullable
import com.example.fitnesstime.ui.fragments.AddConsumedCalories
import com.example.fitnesstime.ui.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    // Sign Up And Sign In end points
    @POST("User/addAccountNPreferences.php")
    suspend fun createAccountNPreferences(@Body createAccountNPreferences: CreateAccountNPreferences): Response<ServiceResponse<String>>

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

    @POST("diary/add_daily_weight.php")
    suspend fun addDailyWeight(@Body addDailyWeight : AddDailyWeight) : Response<ServiceResponse<String?>>

    @POST("diary/add_consumed_calories.php")
    suspend fun addConsumedCalories(@Body addConsumedCalories: AddConsumedCalories) : Response<ServiceResponse<String?>>

    @GET("User/getMacros.php")
    suspend fun getMacros(@Query("email") email: String) : Response<ServiceResponse<Macros>>

    @GET("profile/EditProfile.php")
    suspend fun editProfile(@Query("email") email: String, @Query("password") password: String, @Nullable @Query("first_new") firstName: String?, @Nullable @Query("last_new") lastName: String?) : Response<ServiceResponse<String>>

    @GET("meal/deleteProduct.php")
    suspend fun deleteProduct(@Query("email") email: String, @Query("date") date: String, @Query("product_name") productName: String, @Query("meal_type") mealType: String) : Response<ServiceResponse<String?>>

    @POST("profile/EditPassword.php")
    suspend fun changePassword(@Body changePassword: ChangePassword) : Response<ServiceResponse<String?>>

}