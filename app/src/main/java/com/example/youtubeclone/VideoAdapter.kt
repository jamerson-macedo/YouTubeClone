package com.example.youtubeclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class VideoAdapter(private val listVideo: List<Data>, val onClick: (Data) -> Unit) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    inner class VideoViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind(video: Data) {
            with(itemView) {
                setOnClickListener {
                    onClick.invoke(video)
                }
                Picasso.get().load(video.thumbnailUrl)
                    .into(itemView.findViewById<ImageView>(R.id.video_thumb))
                Picasso.get().load(video.publisher.pictureProfileUrl)
                    .into(itemView.findViewById<ImageView>(R.id.video_autor))
                itemView.findViewById<TextView>(R.id.title_video).text = video.title
                // concatenaçao com todos os dados
                itemView.findViewById<TextView>(R.id.subtitle_video).text = context.getString(
                    R.string.info,
                    video.publisher.name,
                    video.viewsCountLabel,
                    video.publishedAt
                )
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rc, parent, false)
        )
    }

    override fun getItemCount() = listVideo.size


    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(listVideo[position])
    }
}