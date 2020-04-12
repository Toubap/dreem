package com.baptisterssl.dreemtest.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.baptisterssl.dreemtest.R
import com.baptisterssl.dreemtest.data.entity.AudioFile
import com.baptisterssl.dreemtest.service.PlayerService
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_carousel_item.view.*

class CarouselAdapter :
    RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val dataSet = arrayListOf<AudioFile>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_carousel_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            val audioFile = dataSet[position]

            Glide.with(context).load(audioFile.image).into(imageViewCard)

            textViewCarouselTitle.text = audioFile.title
            textViewCarouselDescription.text = audioFile.description

            fabCarouselPlay.setOnClickListener {
                audioFile.preview?.let {
                    PlayerService.play(context, it)
                }
            }

            fabCarouselPlay.isVisible = audioFile.audio != null
        }
    }

    fun update(dataSet: List<AudioFile>) {
        this.dataSet.clear()
        this.dataSet.addAll(dataSet)
        notifyDataSetChanged()
    }
}