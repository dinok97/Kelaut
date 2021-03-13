package com.kelaut.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

private val fragmentHome = HomeFragment.newInstance()
private val fragmentTransaction = TransactionFragment.newInstance()
private val fragmentProfile = ProfileFragment.newInstance()
private var active: Fragment = fragmentHome

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val menu: Menu = findViewById<BottomNavigationView>(R.id.bottom_navigation).menu
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

//         initialize first fragment that appear
        selectedMenu(menu.getItem(0))

        bottomNavigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            selectedMenu(item)
            false
        }

        supportFragmentManager.beginTransaction().add(R.id.frame_container, fragmentProfile)
            .hide(fragmentProfile).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame_container, fragmentTransaction)
            .hide(fragmentTransaction).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame_container, fragmentHome).commit()
    }

    private fun selectedMenu(menuItem: MenuItem) {
        menuItem.isChecked = true
        when (menuItem.itemId) {
            R.id.navigation_home -> {
                showFragment(fragmentHome)
                active = fragmentHome
            }
            R.id.navigation_transaction -> {
                showFragment(fragmentTransaction)
                active = fragmentTransaction
            }
            R.id.navigation_profile -> {
                showFragment(fragmentProfile)
                active = fragmentProfile
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().hide(active).show(fragment).commit()
    }

}