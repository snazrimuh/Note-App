package com.simple.note.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catatan")
data class Catatan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val judul: String,
    val isi: String
)
