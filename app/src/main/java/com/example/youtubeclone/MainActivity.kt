package com.example.youtubeclone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    private lateinit var adaptervideos: VideoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rc = findViewById<RecyclerView>(R.id.rc_video)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.motion_container)
        val listaVideo = mutableListOf<Data>()
        adaptervideos = VideoAdapter(listaVideo) { video ->
            println(video)
        }
        rc.layoutManager = LinearLayoutManager(this)
        rc.adapter = adaptervideos


        CoroutineScope(Dispatchers.IO).launch {
            val res = async { getVideos() }
            val listvideo = res.await()
            // agora mando para a principal
            withContext(Dispatchers.Main) {
                listvideo?.let {
                    listaVideo.clear()
                    listaVideo.addAll(it.data)
                    adaptervideos.notifyDataSetChanged()
                    constraintLayout.removeView(findViewById(R.id.progress_recycler))
                }
            }
        }

    }

    private fun getVideos(): ListVideo? {
        val client = OkHttpClient.Builder().build()
        val request =
            Request.Builder().get().url("https://tiagoaguiar.co/api/youtube-videos").build()
        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                GsonBuilder().create().fromJson(response.body()?.string(), ListVideo::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}