package com.example.myapplication.usecase

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.GetUserReponse
import com.example.myapplication.model.VerifictionNumberModel

interface GetVerificationNumberUseCase {

    fun getVerificationNumber(liveDataList : MutableLiveData<VerifictionNumberModel>)

}