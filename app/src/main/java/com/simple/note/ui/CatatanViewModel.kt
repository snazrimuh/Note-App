package com.simple.note.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simple.note.data.Catatan
import com.simple.note.data.CatatanRepository
import kotlinx.coroutines.launch

class CatatanViewModel(private val repository: CatatanRepository) : ViewModel() {

    val semuaCatatan: LiveData<List<Catatan>> = repository.semuaCatatan

    fun tambahCatatan(catatan: Catatan) = viewModelScope.launch {
        repository.tambahCatatan(catatan)
    }

    fun hapusCatatan(catatan: Catatan) = viewModelScope.launch {
        repository.hapusCatatan(catatan)
    }

    fun updateCatatan(catatan: Catatan) = viewModelScope.launch {
        repository.updateCatatan(catatan)
    }
}
