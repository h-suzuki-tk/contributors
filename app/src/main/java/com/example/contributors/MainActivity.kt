package com.example.contributors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.tool_bar)
        toolbar.apply {
            title = ""
        }
        setSupportActionBar(toolbar)

        replaceFragment(ContributorsListFragment())
    }

    public fun replaceFragment(fragment : Fragment) {
        val ft = supportFragmentManager.beginTransaction().apply {
            replace(R.id.content, fragment)
            addToBackStack(null)
        }
        ft.commit()
    }

}