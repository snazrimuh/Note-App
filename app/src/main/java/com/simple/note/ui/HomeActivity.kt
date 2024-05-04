package com.simple.note.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.simple.note.data.Catatan
import com.simple.note.databinding.ActivityHomeBinding
import com.simple.note.databinding.DialogTambahCatatanBinding
import androidx.lifecycle.ViewModelProvider
import com.simple.note.data.CatatanDatabase
import com.simple.note.data.CatatanRepository
import androidx.lifecycle.Observer
import com.simple.note.R

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var noteViewModel: CatatanViewModel
    private lateinit var noteRepository: CatatanRepository
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        val loggedInUser = sharedPreferences.getString("loggedInUser", "")
        binding.textViewWelcome.text = "Welcome, $loggedInUser!"

        val catatanDao = CatatanDatabase.getDatabase(application).catatanDao()
        noteRepository = CatatanRepository(catatanDao)

        val adapter = CatatanAdapter(emptyList(), ::onDeleteClick, ::onUpdateClick)
        binding.recyclerViewCatatan.adapter = adapter
        binding.recyclerViewCatatan.layoutManager = LinearLayoutManager(this)

        val viewModelFactory = CatatanViewModelFactory(noteRepository)
        noteViewModel = ViewModelProvider(this, viewModelFactory).get(CatatanViewModel::class.java)

        noteViewModel.semuaCatatan.observe(this, Observer { catatan ->
            adapter.setCatatan(catatan) // Pastikan menggunakan fungsi setCatatan
        })

        binding.buttonTambah.setOnClickListener {
            tampilkanDialogTambahCatatan()
        }

        binding.buttonLogout.setOnClickListener {
            sharedPreferences.edit().remove("isLoggedIn").apply()
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    private fun tampilkanDialogTambahCatatan() {
        val dialogBinding = DialogTambahCatatanBinding.inflate(layoutInflater)
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Tambah Catatan Baru")
            .setView(dialogBinding.root)
            .setPositiveButton("Tambah") { dialog, _ ->
                val judul = dialogBinding.editTextJudul.text.toString()
                val isi = dialogBinding.editTextIsi.text.toString()
                if (judul.isNotEmpty() && isi.isNotEmpty()) {
                    noteViewModel.tambahCatatan(Catatan(judul = judul, isi = isi))
                    Toast.makeText(this, "Catatan berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Harap lengkapi judul dan isi catatan", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .create()
        dialog.show()
    }

    private fun tampilkanDialogUpdateCatatan(catatan: Catatan) {
        val dialogBinding = DialogTambahCatatanBinding.inflate(layoutInflater)
        dialogBinding.editTextJudul.setText(catatan.judul)
        dialogBinding.editTextIsi.setText(catatan.isi)

        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Update Catatan")
            .setView(dialogBinding.root)
            .setPositiveButton("Update") { dialog, _ ->
                val judul = dialogBinding.editTextJudul.text.toString()
                val isi = dialogBinding.editTextIsi.text.toString()
                if (judul.isNotEmpty() && isi.isNotEmpty()) {
                    val catatanBaru = catatan.copy(judul = judul, isi = isi)
                    noteViewModel.updateCatatan(catatanBaru)
                    Toast.makeText(this, "Catatan berhasil diupdate", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Harap lengkapi judul dan isi catatan", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .create()

        dialog.show()
    }


    private fun tampilkanDialogKonfirmasiHapus(catatan: Catatan) {
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Konfirmasi Hapus Catatan")
            .setMessage("Apakah Anda yakin ingin menghapus catatan ini?")
            .setPositiveButton("Ya") { dialog, _ ->
                noteViewModel.hapusCatatan(catatan)
                Toast.makeText(this, "Catatan berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Tidak", null)
            .create()

        dialog.show()
    }


    private fun onDeleteClick(catatan: Catatan) {
        tampilkanDialogKonfirmasiHapus(catatan)
    }

    private fun onUpdateClick(catatan: Catatan) {
        tampilkanDialogUpdateCatatan(catatan)
    }

}
