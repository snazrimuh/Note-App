package com.simple.note.data

import androidx.lifecycle.LiveData

class CatatanRepository(private val catatanDao: CatatanDao) {

    val semuaCatatan: LiveData<List<Catatan>> = catatanDao.getAllCatatan()

    suspend fun tambahCatatan(catatan: Catatan) {
        catatanDao.tambahCatatan(catatan)
    }

    suspend fun hapusCatatan(catatan: Catatan) {
        catatanDao.hapusCatatan(catatan)
    }
    suspend fun updateCatatan(catatan: Catatan) {
        catatanDao.updateCatatan(catatan)
    }

}

