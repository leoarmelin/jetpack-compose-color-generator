package com.example.colorgenerator.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
class ColorLockRoom(
    @PrimaryKey
    @ColumnInfo(name = "colorName")
    var colorName: String,
    var value: Int,
    var isLocked: Boolean
)