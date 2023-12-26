package com.example.youtubeclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class VideoDetail(private val listVideo: List<Data>) : RecyclerView.Adapter<VideoDetail.VideoViewHolder>() {
    inner class VideoViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind(video: Data) {
            with(itemView) {
                Picasso.get().load(video.thumbnailUrl)
                    .into(itemView.findViewById<ImageView>(R.id.video_thumb_detail))
                itemView.findViewById<TextView>(R.id.title_video_content).text = video.title
                // concatenaçao com todos os dados
                itemView.findViewById<TextView>(R.id.title_chanel_content).text = context.getString(
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_video_content, parent, false)
        )
    }

    override fun getItemCount() = listVideo.size


    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(listVideo[position])
    }
}