package com.example.adityagrowigh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class HomeActivity : AppCompatActivity() {
    lateinit var feedsButton: Button
    lateinit var uploadButton: Button
    lateinit var mainContainer: ConstraintLayout
    lateinit var welcomeBuddyTV: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        feedsButton = findViewById<Button>(R.id.feeds_button)
        uploadButton = findViewById<Button>(R.id.upload_image_button)
        mainContainer = findViewById(R.id.main_container)
        welcomeBuddyTV = findViewById(R.id.welcome_buddy_tv)
        feedsButton.setOnClickListener {
            showFragment(FeedsFragment())
        }
        uploadButton.setOnClickListener {
            showFragment(UploadImageFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(null) // Add the fragment to the back stack, so pressing the back button navigates back to the previous fragment
            .commit()
        feedsButton.visibility = View.GONE
        uploadButton.visibility = View.GONE
        welcomeBuddyTV.text = "Loading...."
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        feedsButton.visibility = View.VISIBLE
        uploadButton.visibility = View.VISIBLE
        welcomeBuddyTV.text = "Welcome Buddy"
    }
}