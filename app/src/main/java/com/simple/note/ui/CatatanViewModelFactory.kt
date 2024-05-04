package com.simple.note.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.simple.note.data.CatatanRepository

class CatatanViewModelFactory(private val repository: CatatanRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(CatatanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatatanViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
