package com.itech.androidassessment.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.itech.androidassessment.R
import com.itech.androidassessment.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding:ActivityBaseBinding
    private lateinit var appBarConfiguration : AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //initialize the view binding with a layoutInflater
        binding = ActivityBaseBinding.inflate(layoutInflater)

        //setting the root layout as contentView
        setContentView(binding.root)

        // Set up Action Bar
        //setSupportActionBar(binding.toolbar)
        val host: NavHostFragment = supportFragmentManager .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return
        val navController =host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBar(navController, appBarConfiguration)


    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {

        setupActionBarWithNavController(navController, appBarConfig)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}