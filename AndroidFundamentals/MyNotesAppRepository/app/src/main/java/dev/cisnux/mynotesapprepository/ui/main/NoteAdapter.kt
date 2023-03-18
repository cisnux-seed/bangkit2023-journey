package dev.cisnux.mynotesapprepository.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.cisnux.mynotesapprepository.database.Note
import dev.cisnux.mynotesapprepository.databinding.ItemNoteBinding
import dev.cisnux.mynotesapprepository.helper.NoteDiffCallback
import dev.cisnux.mynotesapprepository.ui.insert.NoteAddUpdateActivity

// with diffutils we doesn't need to use smoothScrollToPosition()
// to scroll to last last position when note is deleted
class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val listNotes = ArrayList<Note>()

    /**
     * Kode di atas memanggil kelas NoteDiffCallback
     * untuk memeriksa perubahan yang ada pada listNotes.
     * Jadi jika ada perubahan pada listNotes,
     * maka akan memperbarui secara otomatis.
     * NoteDiffCallback digunakan sebagai pengganti
     * notifyDataSetChanged, yang fungsinya sama-sama
     * untuk melakukan pembaharuan item pada RecyclerView.
     * */
    fun setListNotes(listNotes: List<Note>) {
        val diffCallback = NoteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvItemTitle.text = note.title
                tvItemDate.text = note.date
                tvItemDescription.text = note.description
                cvItemNote.setOnClickListener {
                    val intent = Intent(it.context, NoteAddUpdateActivity::class.java)
                    intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}