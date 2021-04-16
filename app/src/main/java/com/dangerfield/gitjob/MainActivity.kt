package com.dangerfield.gitjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.dangerfield.gitjob.databinding.ActivityMainBinding
import com.dangerfield.gitjob.presentation.util.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupBottomNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
    }

    private fun setupBottomNavigation() {
        val navGraphs = listOf(
            R.navigation.nav_graph_jobs_feed,
            R.navigation.nav_graph_saved_jobs,
        )

        binding.bottomNav.inflateMenu(R.menu.bottom_nav_menu)

        binding.bottomNav.setupWithNavController(
            navGraphIds = navGraphs,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )
    }
}