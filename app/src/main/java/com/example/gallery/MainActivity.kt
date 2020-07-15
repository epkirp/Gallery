package com.example.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.example.gallery.fragments.NewFragment
import com.example.gallery.fragments.PopularFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

/*class MainActivity : AppCompatActivity() {

    private var savedStateSparseArray = SparseArray<Fragment.SavedState>()
    private var currentSelectItemId = R.id.menu_new
    companion object {
        const val SAVED_STATE_CONTAINER_KEY = "ContainerKey"
        const val SAVED_STATE_CURRENT_TAB_KEY = "CurrentTabKey"
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.menu_new -> {
                swapFragments(item.itemId, "New")
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_popular -> {
                swapFragments(item.itemId, "Popular")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            savedStateSparseArray = savedInstanceState.getSparseParcelableArray(SAVED_STATE_CONTAINER_KEY)!!
            currentSelectItemId = savedInstanceState.getInt(SAVED_STATE_CURRENT_TAB_KEY)
        }
        else {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, NewFragment())
                .commit()
        }

        setContentView(R.layout.activity_main)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

     override fun onSaveInstanceState(outState: Bundle) {
        if (outState != null) {
            super.onSaveInstanceState(outState)
        }
        outState?.putSparseParcelableArray(SAVED_STATE_CONTAINER_KEY, savedStateSparseArray)
        outState?.putInt(SAVED_STATE_CURRENT_TAB_KEY, currentSelectItemId)
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment != null && fragment.isVisible) {
                with(fragment.childFragmentManager) {
                    if (backStackEntryCount > 0) {
                        popBackStack()
                        return
                    }
                }
            }
        }
        super.onBackPressed()
    }

    private fun swapFragments(@IdRes actionId: Int, key: String) {
        if (supportFragmentManager.findFragmentByTag(key) == null) {
            savedFragmentState(actionId)
            createFragment(key, actionId)
        }
    }

    private fun createFragment(key: String, actionId: Int) {
        if (key == "New") {
            val fragment = NewFragment.newInstance(key)

            fragment.setInitialSavedState(savedStateSparseArray[actionId])
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, key)
                .commit()
        }
        else {
            val fragment = PopularFragment.popularInstance(key)

            fragment.setInitialSavedState(savedStateSparseArray[actionId])
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, key)
                .commit()
        }
    }

    private fun savedFragmentState(actionId: Int) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (currentFragment != null) {
            savedStateSparseArray.put(currentSelectItemId,
                supportFragmentManager.saveFragmentInstanceState(currentFragment)
            )
        }
        currentSelectItemId = actionId
    }
}*/


class MainActivity : AppCompatActivity() {
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
                            .hide(popularFragment)
                            .show(newFragment)
                            .commit()
                    }
                }
                R.id.menu_popular -> {
                    if (popularFragment.isHidden) {
                        supportFragmentManager.beginTransaction()
                            .hide(newFragment)
                            .show(popularFragment)
                            .commit()
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
