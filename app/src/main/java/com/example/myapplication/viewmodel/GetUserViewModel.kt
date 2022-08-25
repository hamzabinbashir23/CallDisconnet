package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.GetUserReponse
import com.example.myapplication.usecase.GetUserUseCase
import com.example.myapplication.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetUserViewModel  @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase

): ViewModel() {


    var getUserMLD : MutableLiveData<GetUserReponse> = MutableLiveData()


    init {
        getUserMLD = MutableLiveData()
    }

    fun getUser(userPhoneNumber: String) {
        getUserUseCase.getUser(userPhoneNumber,getUserMLD)
    }

    fun setUserStatus(phoneNumber: String){
        updateUserUseCase.updateUserStatus(phoneNumber)
    }
}