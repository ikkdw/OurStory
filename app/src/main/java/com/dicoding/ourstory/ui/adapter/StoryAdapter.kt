package com.dicoding.ourstory.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ourstory.R
import com.dicoding.ourstory.data.remote.story.ListStoryItem
import com.dicoding.ourstory.databinding.ItemStoryRowBinding

class StoryAdapter(private val stories: List<ListStoryItem>) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding =
            ItemStoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(stories[position])
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    inner class StoryViewHolder(private val binding: ItemStoryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(story)
            }
            binding.apply {
                Glide.with(binding.root)
                    .load(story.photoUrl)
                    .error(R.drawable.ic_place_holder)
                    .into(imgItemPhoto)
                tvItemName.text = story.name
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }
}
