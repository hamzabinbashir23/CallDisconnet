package com.example.myapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.GetUserReponse
import com.example.myapplication.model.VerifictionNumberModel
import com.example.myapplication.network.ApiEndPoint
import com.example.myapplication.usecase.GetUserUseCase
import com.example.myapplication.usecase.GetVerificationNumberUseCase
import com.example.myapplication.usecase.UpdateUserUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.log

class GetVerificationNumberRepository @Inject constructor(private val backEndApi: ApiEndPoint): GetVerificationNumberUseCase {

    override fun getVerificationNumber(liveDataList: MutableLiveData<VerifictionNumberModel>) {
        val call : Call<VerifictionNumberModel>? = backEndApi.getVerificationNumbers()
        call?.enqueue(object : Callback<VerifictionNumberModel> {
            override fun onResponse(call: Call<VerifictionNumberModel>, response: Response<VerifictionNumberModel>) {
                try {

                    if (response.isSuccessful) {
                        liveDataList.value = response.body()

                    }else {

                    }

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

            override fun onFailure(call: Call<VerifictionNumberModel>, t: Throwable) {
                liveDataList.postValue(null)
            }

        })
    }


}