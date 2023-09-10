package com.example.cameraandgalery.adapters

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.net.Uri
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraandgalery.databinding.ItemBinding
import com.example.cameraandgalery.models.MyImage

class MyPagerAdapter(val list:ArrayList<MyImage> = ArrayList()):RecyclerView.Adapter<MyPagerAdapter.Vh>() {

    inner class Vh(val itemRvBinding : ItemBinding):RecyclerView.ViewHolder(itemRvBinding.root) {


        @SuppressLint("ClickableViewAccessibility")
        fun onBind(myImage: MyImage) {
            val video = itemRvBinding.imageItem
            itemRvBinding.tvItem.text = myImage.name
            video.setVideoURI(Uri.parse(myImage.imageUri))
            video.start()

            video.setOnTouchListener { v, event ->
                when(event.action){

                    MotionEvent.ACTION_DOWN ->{
                        video.pause()
                        true
                    }
                    MotionEvent.ACTION_UP ->{
                        video.start()
                        true
                    }
                    else -> {
                        false
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
       holder.onBind(list[position])
    }
}