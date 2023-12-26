package com.example.youtubeclone

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    private lateinit var adaptervideos: VideoAdapter
    private lateinit var view: View
    private lateinit var conteiner: MotionLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        val rc = findViewById<RecyclerView>(R.id.rc_video)
        val listaVideo = mutableListOf<Data>()
        adaptervideos = VideoAdapter(listaVideo) { video ->
            showOverlayView(video)
        }
        rc.layoutManager = LinearLayoutManager(this)
        rc.adapter = adaptervideos
        // sombra
        view = findViewById(R.id.view_layer)
        // deixando transparente
        view.alpha = 0f


        CoroutineScope(Dispatchers.IO).launch {
            val res = async { getVideos() }
            val listvideo = res.await()
            // agora mando para a principal
            withContext(Dispatchers.Main) {
                listvideo?.let {
                    listaVideo.clear()
                    listaVideo.addAll(it.data)
                    adaptervideos.notifyDataSetChanged()
                    conteiner = findViewById(R.id.motion_container)
                    conteiner.removeView(findViewById<FrameLayout>(R.id.progress_recycler))
                }
            }
        }

    }
    // quando clica no video o alfa sobre para 0.5


    private fun showOverlayView(video: Data) {
        view.animate().apply {
            duration = 400
            alpha(0.5f)
        }
        conteiner.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                if (progress > 0.5f) {
                    view.alpha = 1.0f - progress
                } else {
                    view.alpha = 0.5f
                }

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {

            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }
        })
        val detailAdapter = VideoDetail(videos())
        val recyclerview = findViewById<RecyclerView>(R.id.rv_similar)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = detailAdapter
        detailAdapter.notifyDataSetChanged()
        val imgIcon = findViewById<ImageView>(R.id.image_chanel)
        val title = findViewById<TextView>(R.id.text_video)
        val textsub = findViewById<TextView>(R.id.title_chanel)
        title.text = video.title
        textsub.text=video.publisher.name

        Picasso.get().load(video.publisher.pictureProfileUrl).into(imgIcon)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
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