package com.example.colorgenerator.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
class ColorLockRoom(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "colorName")
    var colorName: String,
    var value: Int,
    var isLocked: Boolean
)