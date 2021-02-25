package com.example.contributors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.eclipsesource.json.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    private val _contributors = mutableListOf<Contributor>()
    private val _default_fragment = ContributorsListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readContributors()
        replaceFragment(_default_fragment)
    }

    private fun readContributors() = GlobalScope.launch {

        // 非同期処理
        async(Dispatchers.Default) {

            // 通信・データの取得
            OkHttpClient().newCall(Request
                .Builder()
                .url(CONTRIBUTORS_URL)
                .addHeader("H-API-KEY", "AUTH_API_KEY_20181203")
                .build()
            ).execute().body()?.string()

        }.await().let {

            // 取得データの解析
            for ( data in  Json.parse(it).asArray()) {
                _contributors += Contributor(data.asObject())
                _default_fragment.addContributor(_contributors.last())
            }

        }

    }

    public fun replaceFragment(fragment : Fragment) {
        val ft = supportFragmentManager.beginTransaction().apply {
            replace(R.id.content, fragment)
            addToBackStack(null)
        }
        ft.commit()
    }

}
