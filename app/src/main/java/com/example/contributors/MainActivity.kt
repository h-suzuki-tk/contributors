package com.example.contributors

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.eclipsesource.json.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var _http_client : HTTP

    companion object {
        const val CONTRIBUTORS_URL = "https://api.github.com/repositories/90792131/contributors"
        const val USER = "h-suzuki-tk"
        const val PASSWD = "TOKEN"
        const val URL = "url"
        const val CONTRIBUTIONS = "contributions"
    }

    private val _default_fragment = ContributorsListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _http_client = HTTP(
            user = USER,
            passwd = packageManager.getApplicationInfo(
                packageName, PackageManager.GET_META_DATA).metaData.getString(PASSWD) ?: "")

        val toolbar = findViewById<Toolbar>(R.id.tool_bar)
        toolbar.apply {
            title = ""
        }
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .add(R.id.content, _default_fragment)
            .commit()

        readContributors()
    }

    private fun readContributors() = GlobalScope.launch {

        async(Dispatchers.Default) { _http_client.get(CONTRIBUTORS_URL) }.await().let { buf ->

            for ( data in Json.parse(buf).asArray() ) {
                data.asObject().let { obj ->
                    _default_fragment.addContributor(
                        User.read(_http_client, obj.getString(URL, null)),
                        obj.get(CONTRIBUTIONS).asInt())
                }
            }

        }
      
    }

    fun replaceFragment(fragment : Fragment) {

        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.anim.open_enter,
                R.anim.close_enter,
                R.anim.open_exit,
                R.anim.close_exit)
            replace(R.id.content, fragment)
            addToBackStack(null)
        }.commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> supportFragmentManager.popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }

}
