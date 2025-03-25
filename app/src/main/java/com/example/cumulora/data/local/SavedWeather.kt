package com.example.cumulora.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cumulora.utils.FAVORITE_TABLE_NAME

@Entity(tableName = FAVORITE_TABLE_NAME)
data class SavedWeather(
    @PrimaryKey
    val id: Int,
)
