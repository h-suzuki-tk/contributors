package com.example.contributors

import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request

class HTTP {

    private val client = OkHttpClient()

    companion object {
        const val USER = "h-suzuki-tk"
        const val PASSWD = "passwd"

        fun get(url: String) : String? {
            return OkHttpClient().newCall(
                Request
                .Builder()
                .url(url)
                .header("Authorization", Credentials.basic(USER, PASSWD))
                .build()
            ).execute().body()?.string()
        }
    }
}