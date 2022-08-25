package com.example.myapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.GetUserReponse
import com.example.myapplication.network.ApiEndPoint
import com.example.myapplication.usecase.GetUserUseCase
import com.example.myapplication.usecase.UpdateUserUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetUserRepository  @Inject constructor(private val backEndApi: ApiEndPoint): GetUserUseCase, UpdateUserUseCase {


    override fun getUser(userPhoneNumber: String, liveDataList: MutableLiveData<GetUserReponse>) {

        val call : Call<GetUserReponse>? = backEndApi.getUser(userPhoneNumber)
        call?.enqueue(object : Callback<GetUserReponse> {
            override fun onResponse(call: Call<GetUserReponse>, response: Response<GetUserReponse>) {
                try {

                    if (response.isSuccessful) {
                        liveDataList.value = response.body()

                    }else {

                    }

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

            override fun onFailure(call: Call<GetUserReponse>, t: Throwable) {
                liveDataList.postValue(null)
            }

        })

    }

    override fun updateUserStatus(mobileNumber: String) {
        val call : Call<GetUserReponse>? = backEndApi.setUserStatus(mobileNumber)
        call?.enqueue(object : Callback<GetUserReponse> {
            override fun onResponse(call: Call<GetUserReponse>, response: Response<GetUserReponse>) {
                try {

                    if (response.isSuccessful) {
                        //  liveDataList.value = response.body()
                        Log.d("",response.body().toString())
                    }else {
                        Log.d("",response.body().toString())
                    }

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

            override fun onFailure(call: Call<GetUserReponse>, t: Throwable) {
                //  liveDataList.postValue(null)
                Log.d("", t.message!!)
            }

        })
    }
}