package com.example.adityagrowigh

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

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
        holder.likes.setOnClickListener {
            if(flag == 0){

                holder.likes.text =  "${postItem.likes?.plus(1)} Likes"
                flag = 1
            }
            else{
                holder.likes.text =  "${postItem.likes} Likes"
                flag = 0
            }
        }
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageIV: ImageView = itemView.findViewById(R.id.image_iv)
        var likes: TextView = itemView.findViewById(R.id.like_tv)
        val comments: TextView = itemView.findViewById(R.id.comment_tv)
    }
}