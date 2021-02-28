package com.example.contributors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.eclipsesource.json.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        const val CONTRIBUTORS_URL = "https://api.github.com/repositories/90792131/contributors"
        const val URL = "url"
        const val CONTRIBUTIONS = "contributions"
    }

    private val _default_fragment = ContributorsListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.tool_bar)
        toolbar.apply {
            title = ""
        }
        setSupportActionBar(toolbar)

        //val appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        //val apikey = appInfo.metaData.getString("TOKEN")
        //Log.d("確認", apikey.toString())

        supportFragmentManager.beginTransaction()
            .add(R.id.content, _default_fragment)
            .commit()

        readContributors()
    }

    private fun readContributors() = GlobalScope.launch {

        async(Dispatchers.Default) { HTTP.get(CONTRIBUTORS_URL) }.await().let { buf ->

            for ( data in  Json.parse(buf).asArray()) {
                data.asObject().let { obj ->
                    _default_fragment.addContributor(
                            User.read(obj.getString(URL, null)),
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
