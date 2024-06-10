package com.dicoding.ourstory

import com.dicoding.ourstory.data.remote.story.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                photoUrl = "https://dicoding-assets.sgp1.cdn.digitaloceanspaces.com/blog/wp-content/uploads/2015/12/IMG_0223.jpg",
                createdAt = "2024-05-07T05:43:15.759Z",
                name = "Dicoding $i",
                description = "IT Course  $i",
                lon = -6.89549696359841,
                id = "id $i",
                lat = 107.63382340858696,
            )
            items.add(story)
        }
        return items
    }
}