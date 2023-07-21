package com.example.adityagrowigh.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.adityagrowigh.R
import com.example.adityagrowigh.adapter.ViewPager2Adapter
import com.example.adityagrowigh.data.VideoItem

class VideosFragment : Fragment() {
    private lateinit var viewPager2: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager2 = view.findViewById(R.id.view_pager)

        val videos = mutableListOf<VideoItem>()
        videos.add(VideoItem("https://packaged-media.redd.it/zvq5etw85zcb1/pb/m2-res_480p.mp4?m=DASHPlaylist.mpd&v=1&e=1689840000&s=ebc90adc7b276b4dbb2d9ea2d7a7444dc42dcfa6#t=0"))
        videos.add(VideoItem("https://packaged-media.redd.it/jdz1yv4h3vcb1/pb/m2-res_480p.mp4?m=DASHPlaylist.mpd&v=1&e=1689850800&s=ebe2eaf491c46cc9d082b9de534ea0157e9f282e#t=0"))
        videos.add(VideoItem("https://packaged-media.redd.it/ldpqlpsy2vcb1/pb/m2-res_480p.mp4?m=DASHPlaylist.mpd&v=1&e=1689847200&s=f0cfdabc67e0347a1dde1651a71afda9720388fa#t=0"))
        videos.add(VideoItem("https://packaged-media.redd.it/r4xl45mduxcb1/pb/m2-res_480p.mp4?m=DASHPlaylist.mpd&v=1&e=1689854400&s=ed0e79e9c331b66a896ba99d662aa246b995d8e0#t=0"))
        viewPager2.adapter =ViewPager2Adapter(requireContext(),videos)
    }

}