package com.example.rickandmortywiki.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfig = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.listOfCharacterFragment,
                R.id.allEpisodesFragment
            ),
            drawerLayout = binding.drawerLayout
        )

        setupActionBarWithNavController(
            navController = navController,
            configuration = appBarConfig
        )

        binding.navView.setupWithNavController(navController)
        binding.navView.setCheckedItem(navController.graph.startDestinationId)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}