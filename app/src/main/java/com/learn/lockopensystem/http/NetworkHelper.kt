package com.learn.lockopensystem.http

import Api
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkHelper {
    companion object {
        fun getRetrofit(): Api {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConstant.BASE_URL)
                .build()
            return retrofit.create(Api::class.java)
        }
    }
}