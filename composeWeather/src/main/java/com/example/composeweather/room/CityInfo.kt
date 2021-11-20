package com.example.composeweather.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_info")
data class CityInfo(
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    @ColumnInfo(name = "location") val location: String = "",
    @ColumnInfo(name = "location_id") val locationId: String = "",
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "province") val province: String = "",
    @ColumnInfo(name = "city") val city: String = "",
    @ColumnInfo(name = "is_location") val isLocation: Int = 0,
    @ColumnInfo(name = "is_index") var isIndex: Int = 0,
)
