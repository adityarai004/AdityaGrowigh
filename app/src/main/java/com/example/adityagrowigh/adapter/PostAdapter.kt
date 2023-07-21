package com.example.adityagrowigh.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.adityagrowigh.R
import com.example.adityagrowigh.data.PostItem

class PostAdapter(val mContext: Context, private val mList:List<PostItem>): RecyclerView.Adapter<PostAdapter.ViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.images_recycler_view,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postItem = mList[position]
        val imgUrl =  postItem.icon.toString()
        Glide.with(mContext).load(imgUrl).into(holder.imageIV)
        holder.likes.text = "${postItem.likes.toString() } Likes"
        holder.comments.text =  "${postItem.comments.toString()} Comments"
        var flag = 0
        holder.likeIV.setOnClickListener {
            if(flag == 0){
                holder.likeIV.setMaxProgress(0.7f)
                holder.likeIV.playAnimation()
                holder.likes.text =  "${postItem.likes?.plus(1)} Likes"
                flag = 1
            }
            else{
                holder.likeIV.setMaxProgress(0f)
                holder.likeIV.playAnimation()
                holder.likes.text =  "${postItem.likes} Likes"
                flag = 0
            }
        }

        holder.shareIV.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, postItem.icon)

            // Check if there's an activity to handle the share intent
            if (shareIntent.resolveActivity(mContext.packageManager) != null) {
                mContext.startActivity(Intent.createChooser(shareIntent, "Share Image"))
            }
        }
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageIV: ImageView = itemView.findViewById(R.id.image_iv)
        var likes: TextView = itemView.findViewById(R.id.like_tv)
        val comments: TextView = itemView.findViewById(R.id.comment_tv)
        val likeIV: LottieAnimationView = itemView.findViewById(R.id.like_iv)
        val shareIV: ImageView = itemView.findViewById(R.id.share_iv)
    }

    companion object {
        private const val SWIPE_THRESHOLD = 100
    }
}