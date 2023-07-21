package com.example.adityagrowigh.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.adityagrowigh.R
import com.example.adityagrowigh.data.VideoItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.lang.Exception


class ViewPager2Adapter(val context: Context, val videoItem: List<VideoItem>) : RecyclerView.Adapter<ViewPager2Adapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.video_item,parent,false))
    }

    override fun getItemCount(): Int {
        return videoItem.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {

        holder.setVideoData(videoItem[position])
        var flag = 0
        holder.likeBtn.setOnClickListener {
            if(flag == 0){
                holder.likeBtn.setMaxProgress(0.7f)
                holder.likeBtn.playAnimation()
                flag = 1
            }
            else{
                holder.likeBtn.setMaxProgress(0f)
                holder.likeBtn.playAnimation()
                flag = 0
            }
        }
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val videoView: VideoView = itemView.findViewById(R.id.videoView)
        val likeBtn: LottieAnimationView = itemView.findViewById(R.id.like_video_iv)

        init {
            videoView.setOnClickListener {
                if (videoView.isPlaying) {
                    videoView.pause()
                } else {
                    videoView.start()
                }
            }
        }


        @OptIn(DelicateCoroutinesApi::class)
        fun setVideoData(videoItem: VideoItem) {
                videoView.setVideoPath(videoItem.videoURL)
            GlobalScope.launch (Dispatchers.IO){
                val videoLoaded = withTimeoutOrNull(4000){
                    try{
                        videoView.setOnPreparedListener { mediaPlayer ->
                            mediaPlayer.start()
                            val videoWidth = mediaPlayer.videoWidth
                            val videoHeight = mediaPlayer.videoHeight

                            if (videoWidth != 0 && videoHeight != 0) {
                                val videoRatio: Float = videoWidth.toFloat() / videoHeight.toFloat()
                                val screenRatio: Float =
                                    videoView.width.toFloat() / videoView.height.toFloat()

                                val scale: Float = if (videoRatio >= screenRatio) {
                                    videoRatio / screenRatio
                                } else {
                                    screenRatio / videoRatio
                                }

                                if (videoRatio >= screenRatio) {
                                    videoView.scaleX = scale
                                    videoView.scaleY = 1f
                                } else {
                                    videoView.scaleX = 1f
                                    videoView.scaleY = scale
                                }
                            }
                        }
                        videoView.setOnCompletionListener { mediaPlayer ->
                            // Loop the video playback when it completes
                            mediaPlayer.start()
                        }
                        true
                    }
                    catch (e:Exception){
                        false
                    }
                }
            }



        }
    }
}
