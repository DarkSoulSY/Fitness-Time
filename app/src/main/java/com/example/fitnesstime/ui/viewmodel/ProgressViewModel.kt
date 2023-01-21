package com.example.fitnesstime.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.ui.model.Diary
import com.example.fitnesstime.ui.repositories.DiaryRepository
import kotlinx.coroutines.*


class ProgressViewModel: ViewModel() {

    private val api = RetrofitInstance.retrofit

    private val diaryRepository: DiaryRepository = DiaryRepository(api)

    val diaryRecords = MutableLiveData<List<Diary>?>()





    fun setDiaryRecords(context: Context, email : String){
         GlobalScope.launch(Dispatchers.IO){
            val response = diaryRepository.getHistory(email)
            withContext(Dispatchers.Main){
                if (response.isSuccessful) {
                    if (response.body()!!.Success!!)
                        diaryRecords.value = response.body()!!.Data!!
                    else
                        diaryRecords.value = null
                }
                    //Toast.makeText(context, response.body()!!.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*@OptIn(DelicateCoroutinesApi::class)
    fun setDiaryRecords(context: Context, email: String){
        GlobalScope.launch(Dispatchers.IO) {
            val response = diaryRepository.getHistory(email)
            withContext(Dispatchers.Main) {
                if (response.body()!!.Success) {
                    //diaryRecords.value = response.body()!
                    Toast.makeText(context, response.body()!!.Message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }*/
}