package com.dicoding.ourstory

import com.dicoding.ourstory.data.remote.story.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                photoUrl = "photo : $i",
                createdAt = "date : $i",
                name = "Dicoding $i",
                description = "IT Course  $i",
                i.toDouble(),
                id = "id $i",
                i.toDouble(),
            )
            items.add(story)
        }
        return items
    }
}