package com.example.adityagrowigh

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject


class FeedsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var requestQueue: RequestQueue
    private var postList = mutableListOf<PostItem>()
    lateinit var swipeLayout:SwipeRefreshLayout

    private var topicList = listOf<String>("Artificial Intelligence",
        "Data Science",
        "Virtual Reality",
        "Augmented Reality",
        "Cybersecurity",
        "Blockchain",
        "Internet of Things",
        "Machine Learning",
        "Mobile App Development",
        "Cloud Computing",
        "Robotics",
        "Big Data",
        "Gaming",
        "Web Development",
        "Biotechnology",
        "Cryptocurrency",
        "Fintech",
        "Software Engineering",
        "Network Security",
        "Space Exploration",
        "Digital Marketing",
        "3D Printing",
        "E-commerce",
        "Renewable Energy",
        "Smart Home Technology",
        "Quantum Computing",
        "Nanotechnology",
        "Bioinformatics",
        "Genetic Engineering",
        "Drone Technology",
        "Artificial Neural Networks",
        "Autonomous Vehicles",
        "Wireless Communication",
        "Mobile Payment",
        "HealthTech",
        "EdTech",
        "AR in Education",
        "VR in Training",
        "Gesture Control Technology",
        "Internet Privacy",
        "Gesture Recognition",
        "Natural Language Processing",
        "Smart Cities",
        "Smart Grids",
        "Telemedicine",
        "Biometric Technology",
        "Cloud Gaming",
        "Edge Computing",
        "Computer Vision",
        "Gesture-based Interfaces",
        "Brain-Computer Interfaces",
        "Quantum Cryptography",
        "Quantum Teleportation",
        "Hybrid Cloud",
        "5G Technology",
        "AI-powered Assistants",
        "Blockchain in Supply Chain",
        "Cryptocurrency Mining",
        "Data Privacy",
        "Edge AI",
        "Neuromorphic Computing",
        "Robotic Process Automation",
        "AI in Healthcare",
        "AI in Finance",
        "AR Navigation",
        "Quantum Internet",
        "Neurotechnology",
        "AI Ethics",
        "Cryptocurrency Regulations",
        "Space Technology",
        "Deep Learning",
        "IoT Security",
        "Smart Wearables",
        "Self-driving Cars",
        "AI-generated Art",
        "Cloud-based Gaming",
        "Quantum Sensors",
        "Renewable Energy Tech",
        "Smart Farming",
        "AI in Robotics",
        "Internet Censorship",
        "Green Technology",
        "Futuristic Gadgets",
        "Digital Twin Technology",
        "Distributed Ledger Technology",
        "Edge Analytics",
        "Cloud-based AI",
        "Genomics Technology",
        "IoT in Healthcare",
        "AI-powered Translation",
        "Biometric Authentication",
        "Robot-assisted Surgery",
        "Cryptocurrency Trading",
        "AR Advertising",
        "Quantum Computing Applications",
        "Cybersecurity Frameworks",
        "Smart Grid Technology",
        "Biocomputers",
        "AI in Customer Service",
        "Blockchain in Finance",
        "Brain-Computer Interface Gaming",
        "Space Tourism",
        "AI in Agriculture",
        "AR Shopping",
        "VR Therapy",
        "Gesture-controlled Drones",
        "Internet of Nanothings",
        "AI in Music Composition",
        "Cryptocurrency Wallets",
        "Blockchain in Healthcare",
        "AI in Transportation",
        "VR Concerts",
        "Biometric Payment",
        "Smart Homes of the Future",
        "Cybersecurity Training",
        "Quantum Machine Learning",
        "Blockchain in Supply Chain",
        "AI-driven Personalization",
        "Cybersecurity in IoT")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_feeds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.photo_rv)
        recyclerView.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
        // Instantiate the RequestQueue with the cache and network. Start the queue.
        recyclerView.isNestedScrollingEnabled = true
        val cache = DiskBasedCache(requireContext().cacheDir, 2048 * 2048) // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

        // Instantiate the RequestQueue with the cache and network. Start the queue.
        requestQueue = RequestQueue(cache, network).apply {
            start()
        }
        swipeLayout = view.findViewById(R.id.swipe_container) as SwipeRefreshLayout
        swipeLayout.setOnRefreshListener{
            postList.clear()
            val random1 = (0..100).shuffled().last()
            fetchData(random1)
            Handler(Looper.getMainLooper()).postDelayed({
                swipeLayout.isRefreshing = false
            },2000)

        }
        val random1 = (0..100).shuffled().last()
        fetchData(random1)
    }
    private fun fetchData(topicIndex:Int) {
        val url = "https://pixabay.com/api/?key=38074738-bfe4988920be278e204dd16c9&q=${topicList[topicIndex]}&image_type=photo&pretty=true"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val allImages = mutableListOf<JSONObject>()
                val hits = response.getJSONArray("hits")
                if(hits.length() >=10){


                    for (i in 9 downTo 0){
                        allImages.add(hits.getJSONObject(i))
                        Log.e("TAG",hits.getJSONObject(i).getInt("likes").toString())
                        postList.add(
                            PostItem(
                                hits.getJSONObject(i).getInt("id"),
                                hits.getJSONObject(i).getString("webformatURL"),
                                hits.getJSONObject(i).getInt("likes"),
                                hits.getJSONObject(i).getInt("comments")
                            )
                        )
                    }
                }
                else if (hits.length() == 0){
                    val random1 = (0..100).shuffled().last()
                    fetchData(random1)
                }
                else{
                    for (i in hits.length()-1 downTo 0){
                        allImages.add(hits.getJSONObject(i))
                        Log.e("TAG",hits.getJSONObject(i).getInt("likes").toString())
                        postList.add(
                            PostItem(
                                hits.getJSONObject(i).getInt("id"),
                                hits.getJSONObject(i).getString("webformatURL"),
                                hits.getJSONObject(i).getInt("likes"),
                                hits.getJSONObject(i).getInt("comments")
                            )
                        )
                    }
                }


                    val adapter = PostAdapter(this.requireContext(),postList)
                    recyclerView.adapter = adapter

            },
            { error ->
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

}