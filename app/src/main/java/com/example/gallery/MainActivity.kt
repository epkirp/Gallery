package com.example.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.example.gallery.fragments.NewFragment
import com.example.gallery.fragments.PopularFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : MvpAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            val newFragment = NewFragment()
            val popularFragment = PopularFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container, newFragment)
                .add(R.id.container, popularFragment)
                .hide(popularFragment)
                .commit()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            val newFragment = supportFragmentManager.fragments.find { it is NewFragment }!!
            val popularFragment = supportFragmentManager.fragments.find { it is PopularFragment }!!

            when (it.itemId) {
                R.id.menu_new -> {
                    if (newFragment.isHidden) {
                        supportFragmentManager.beginTransaction()
                            swapFragments(newFragment, popularFragment)
                    }
                }
                R.id.menu_popular -> {
                    if (popularFragment.isHidden) {
                        supportFragmentManager.beginTransaction()
                            swapFragments(popularFragment, newFragment)
                    }
                }
            }
            true
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun swapFragments(showFragment: Fragment, hideFragment: Fragment)
    {
        supportFragmentManager.beginTransaction()
            .hide(hideFragment)
            .show(showFragment)
            .commit()
    }
}
