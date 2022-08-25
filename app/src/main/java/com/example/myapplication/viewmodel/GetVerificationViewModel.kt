package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.GetUserReponse
import com.example.myapplication.model.VerifictionNumberModel
import com.example.myapplication.usecase.GetUserUseCase
import com.example.myapplication.usecase.GetVerificationNumberUseCase
import com.example.myapplication.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetVerificationViewModel @Inject constructor(
    private val getUserUseCase: GetVerificationNumberUseCase
    ): ViewModel() {


    var getUserMLD : MutableLiveData<VerifictionNumberModel> = MutableLiveData()


    init {
        getUserMLD = MutableLiveData()
    }

    fun getVerificationNumber(){
        getUserUseCase.getVerificationNumber(getUserMLD)
    }


}