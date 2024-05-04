package com.simple.note.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CatatanDao {
    @Query("SELECT * FROM catatan")
    fun getAllCatatan(): LiveData<List<Catatan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun tambahCatatan(catatan: Catatan)

    @Delete
    suspend fun hapusCatatan(catatan: Catatan)

    @Update
    suspend fun updateCatatan(catatan: Catatan)
}
