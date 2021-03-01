package com.example.contributors

import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request


class HTTP(
    user : String,
    passwd : String) {

    private val _client : OkHttpClient

    init {
        _client = OkHttpClient.Builder().apply {
            addInterceptor { chain -> chain.proceed(
                chain.request().newBuilder().apply {
                    header("Authorization", Credentials.basic(user, passwd))
                }.build()
            )}
        }.build()
    }

    fun get(url: String) : String? {
        return _client.newCall(
            Request.Builder().apply {
                url(url)
            }.build()
        ).execute().body()?.string()
    }

}