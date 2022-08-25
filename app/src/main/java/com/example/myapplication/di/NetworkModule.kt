package com.example.myapplication.di

import com.example.myapplication.network.ApiEndPoint
import com.example.myapplication.repository.GetUserRepository
import com.example.myapplication.repository.GetVerificationNumberRepository
import com.example.myapplication.usecase.GetUserUseCase
import com.example.myapplication.usecase.GetVerificationNumberUseCase
import com.example.myapplication.usecase.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //http://192.168.1.71:3000 live
    //http://172.16.10.66:3000 Localhost
    var BASE_URL: String? = "http://192.168.1.71:3000/"

    @Provides
    @Singleton
    fun  getRetroServiceInterface(retrofit: Retrofit): ApiEndPoint {

        return retrofit.create(ApiEndPoint::class.java)

    }

    @Provides
    @Singleton
    fun provideUser
                (userRepository: GetUserRepository) :
            GetUserUseCase
            = userRepository


    @Provides
    @Singleton
    fun provideUserStatusUpdate
                (userRepository: GetUserRepository) :
            UpdateUserUseCase
            = userRepository



    @Provides
    @Singleton
    fun provideVerficationNumber
                (finjaCustomerAccountRepository: GetVerificationNumberRepository) :
            GetVerificationNumberUseCase
            = finjaCustomerAccountRepository

    @Provides
    @Singleton
    fun getRetroInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}