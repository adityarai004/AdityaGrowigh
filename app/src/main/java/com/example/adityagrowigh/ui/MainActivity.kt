package com.example.adityagrowigh.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.adityagrowigh.adapter.OnBoardingAdapter
import com.example.adityagrowigh.data.OnBoardingItem
import com.example.adityagrowigh.R


class MainActivity : AppCompatActivity() {
    private lateinit var onBoardingItemsAdapter: OnBoardingAdapter
    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences("onboarding", Context.MODE_PRIVATE)
        val onBoardingCompleted = sharedPreferences.getBoolean("completed", false)
        progressBar = findViewById(R.id.next_btn)
        if (!onBoardingCompleted) {
            setOnBoardingItems()
        } else {
            navigateToHomeActivity()
        }

    }


    private fun navigateToHomeActivity(){
        markOnboardingCompleted()
        startActivity(Intent(applicationContext, WelcomeActivity::class.java))
        finish()
    }
    private fun setOnBoardingItems(){
        onBoardingItemsAdapter = OnBoardingAdapter(
            listOf(
                OnBoardingItem(onBoardingImage = R.drawable.first_slide_image,title = "About Us",
                desc = "we are a dynamic and innovative tech company passionate about creating cutting-edge solutions to tackle real-world challenges, our mission is to empower businesses and individuals with advanced technologies that drive growth, efficiency, and transformation."),
                OnBoardingItem(onBoardingImage = R.drawable.second_slide_image,title = "Our Mission",
                    desc = "we offer a wide range of tech solutions designed to address various industry needs. From web and mobile applications to artificial intelligence, IoT, and blockchain technologies, we stay ahead of the curve, leveraging the latest trends to provide unmatched value to our clients."),
                OnBoardingItem(onBoardingImage = R.drawable.third_slide_image,title = "Our Vision",
                    desc = "Our vision is to be a leading player in the tech industry, recognized for our relentless pursuit of excellence and commitment to delivering high-quality products and services. We envision a world where technology simplifies complex problems, improves lives, and connects people like never before")
            )
        )
        val onBoardingViewPager = findViewById<ViewPager2>(R.id.on_boarding_view_pager)
        onBoardingViewPager.adapter = onBoardingItemsAdapter
        onBoardingViewPager.registerOnPageChangeCallback(object:
        ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
        (onBoardingViewPager.getChildAt(0)as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        findViewById<ProgressBar>(R.id.next_btn).setOnClickListener {
            if(onBoardingViewPager.currentItem+1 < onBoardingItemsAdapter.itemCount){
                progressBar.progress += 3
                onBoardingViewPager.currentItem += 1
            }
            else{
                markOnboardingCompleted()

                navigateToHomeActivity()
            }
        }
        findViewById<TextView>(R.id.textSkip).setOnClickListener {
            markOnboardingCompleted()

            navigateToHomeActivity()
        }
    }
    private fun markOnboardingCompleted() {
        // Save the onboarding completion status in SharedPreferences
        val sharedPreferences = getSharedPreferences("onboarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("completed", true)
        editor.apply()
    }
}