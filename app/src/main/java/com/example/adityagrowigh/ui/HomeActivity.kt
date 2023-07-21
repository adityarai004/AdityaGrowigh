package com.example.adityagrowigh.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.adityagrowigh.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: FragmentContainerView
    private    lateinit var fab:FloatingActionButton
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            Toast.makeText(this, "Selected: $uri", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navHostFragment = findViewById(R.id.nav_host_fragment)
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)
fab = findViewById(R.id.fab)

        fab.setOnClickListener{
            openGallery()
        }
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mapFragment -> {
                    showMapFragment()
                    bottomNavigationView.visibility = View.GONE
                    fab.visibility = View.GONE
                    true
                }
                R.id.feedsFragment -> {
                    showFeedsFragment()
                    true
                }
                R.id.videosFragment -> {
                    showVideosFragment()
                    true
                }
                else -> false
            }
        }
    }


    private fun showVideosFragment() {
        val navController = navHostFragment.findNavController()
        navController.navigate(R.id.videosFragment)
    }

    private fun showMapFragment() {
        val navController = navHostFragment.findNavController()
        navController.navigate(R.id.mapFragment)
    }


    private fun showFeedsFragment() {
        val navController = navHostFragment.findNavController()
        navController.navigate(R.id.feedsFragment)
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        bottomNavigationView.visibility = View.VISIBLE
        fab.visibility =  View.VISIBLE
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }
    companion object {
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
    }
    fun setFabVisibility(isVisible: Boolean) {
        if (isVisible) {
            fab.show()
        } else {
            fab.hide()
        }
    }
}



