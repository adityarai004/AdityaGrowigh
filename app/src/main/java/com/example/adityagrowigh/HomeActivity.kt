package com.example.adityagrowigh

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.properties.Delegates

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: FragmentContainerView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var currentLocation: Location? = null
    private lateinit var locationRequest: LocationRequest
    private var selectedFragmentIndex by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

//        navHostFragment = findViewById(R.id.nav_host_fragment)
//        bottomNavigationView = findViewById(R.id.bottom_nav_view)
//        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

        navHostFragment = findViewById(R.id.nav_host_fragment)
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)


        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            selectedFragmentIndex = menuItem.itemId
            when (menuItem.itemId) {
                R.id.mapFragment -> {
                    showMapFragment()
                    checkLocationPermission()
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

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fetchCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION_PERMISSION
            )
        }
    }
    private fun fetchCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000)
                .setWaitForAccurateLocation(false)
                .build()
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    currentLocation = locationResult.lastLocation
                        MapFragment().currentLocation =currentLocation
                }
            }

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION_PERMISSION
            )
        }
    }

    fun getCurrentLocation(): Location? {
        return currentLocation
    }


    private fun showFeedsFragment() {
        val navController = navHostFragment.findNavController()
        navController.navigate(R.id.feedsFragment)
    }
    companion object {
        private const val REQUEST_CODE_LOCATION_PERMISSION = 1001
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (currentFragment is VideosFragment || currentFragment is MapFragment) {
            // If the current fragment is VideosFragment or SettingFragment, navigate back to HomeFragment
            selectedFragmentIndex = R.id.feedsFragment
            val homeFragment = FeedsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, homeFragment)
                .commit()
            bottomNavigationView.selectedItemId = selectedFragmentIndex
        } else {
            // If the current fragment is HomeFragment, navigate back to Activity1
            super.onBackPressed()
        }
    }
}



