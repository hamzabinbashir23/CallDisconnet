package com.example.myapplication.usecase

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.GetUserReponse
import com.example.myapplication.model.UserResponseModel

interface GetUserUseCase {

    fun getUser(userPhoneNumber: String, liveDataList: MutableLiveData<GetUserReponse>)

}