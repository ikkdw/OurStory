package com.dicoding.ourstory.data.remote.auth

import androidx.room.PrimaryKey
import androidx.room.Entity


@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)