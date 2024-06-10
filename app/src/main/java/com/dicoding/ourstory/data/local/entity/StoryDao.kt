package com.dicoding.ourstory.data.local.entity

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.ourstory.data.remote.story.ListStoryItem

@Dao
interface StoryDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertStory(quote: List<ListStoryItem>)
//
//    @Query("SELECT * FROM story")
//    fun getAllStory(): PagingSource<Int, ListStoryItem>
//
//    @Query("DELETE FROM story")
//    suspend fun deleteAll()
}