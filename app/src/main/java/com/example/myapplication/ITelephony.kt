package com.example.myapplication

interface ITelephony {
    fun endCall(): Boolean
    fun answerRingingCall()
    fun silenceRinger()
}