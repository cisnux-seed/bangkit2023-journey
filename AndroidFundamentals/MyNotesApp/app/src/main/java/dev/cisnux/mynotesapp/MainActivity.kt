package dev.cisnux.mynotesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.cisnux.mynotesapp.databinding.ActivityMainBinding
import dev.cisnux.mynotesapp.db.NoteHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter

    @Suppress("DEPRECATION")
    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result.data?.let { resultData ->
            when (result.resultCode) {
                NoteAddUpdateActivity.RESULT_ADD -> {
                    val note =
                        resultData.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
                    adapter.addItem(note)
                    // this one is used to scroll to last item in recyclerview
                    // when new item is added
                    binding.rvNotes.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                NoteAddUpdateActivity.RESULT_UPDATE -> {
                    val note =
                        result.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
                    val position =
                        result?.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0) as Int
                    adapter.updateItem(position, note)
                    // in update item it doesn't really useful
                    binding.rvNotes.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }
                NoteAddUpdateActivity.RESULT_DELETE -> {
                    val position =
                        result?.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0) as Int
                    adapter.removeItem(position)
                    showSnackbarMessage("Satu item berhasil dihapus")
                }
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar
            .make(binding.rvNotes, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Notes"

        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.setHasFixedSize(true)

        adapter = NoteAdapter { selectedNote, position ->
            val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
            intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, selectedNote)
            intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
            resultLauncher.launch(intent)
        }


        binding.rvNotes.adapter = adapter
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
            resultLauncher.launch(intent)
        }

        @Suppress("DEPRECATION")
        if (savedInstanceState == null) {
            // proses ambil data
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Note>(EXTRA_STATE)
            list?.let {
                adapter.listNotes = list
            }
        }
    }

    private fun loadNotesAsync() = lifecycleScope.launch {
        binding.progressbar.visibility = View.VISIBLE
        val noteHelper = NoteHelper.getInstance(applicationContext)
        noteHelper.open()
        val deferredNotes = async(Dispatchers.IO) {
            val cursor = noteHelper.queryAll()
            MappingHelper.mapCursorToArrayList(cursor)
        }
        val notes = deferredNotes.await()
        binding.progressbar.visibility = View.INVISIBLE
        if (notes.size > 0) {
            adapter.listNotes = notes
            Log.d("MainActivity", "I hate my life 2")
        } else {
            adapter.listNotes = ArrayList()
            showSnackbarMessage("Tidak ada data saat ini")
        }
        noteHelper.close()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotes)
    }

    companion object {
        private const val EXTRA_STATE = "extra_state"
    }
}