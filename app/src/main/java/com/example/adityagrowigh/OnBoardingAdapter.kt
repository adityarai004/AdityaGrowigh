package com.example.adityagrowigh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class OnBoardingAdapter(private val onBoardingItems: List<OnBoardingItem>) :
    RecyclerView.Adapter<OnBoardingAdapter.OnBoardingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.slider_layout,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    inner class OnBoardingItemViewHolder(view : View):RecyclerView.ViewHolder(view){
        private val imageOnBoarding = view.findViewById<ImageView>(R.id.slider_iv)
        private val title = view.findViewById<TextView>(R.id.slider_title_tv)
        private val desc = view.findViewById<TextView>(R.id.slider_desc_tv)
        fun bind(onBoardingItem: OnBoardingItem){
            imageOnBoarding.setImageResource(onBoardingItem.onBoardingImage)
            title.text = onBoardingItem.title
            desc.text = onBoardingItem.desc
        }
    }
}