package dev.cisnux.mynotesapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dev.cisnux.mynotesapp.databinding.ItemNoteBinding

private typealias OnItemClick = (selectedNote: Note?, position: Int) -> Unit

class NoteAdapter(inline val onItemClick: OnItemClick) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    var listNotes = ArrayList<Note>()
        @SuppressLint("NotifyDataSetChanged")
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)
            notifyDataSetChanged()
        }

    fun addItem(note: Note) {
        listNotes.add(note)
        notifyItemInserted(this.listNotes.size - 1)
    }

    fun updateItem(position: Int, note: Note) {
        listNotes[position] = note
        notifyItemChanged(position, note)
    }

    fun removeItem(position: Int) {
        listNotes.removeAt(position)
        notifyItemRemoved(position)
        // Notify any registered observers that the itemCount
        // items starting at position positionStart have changed.
        notifyItemRangeChanged(position, listNotes.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = listNotes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) =
        holder.bind(listNotes[position])

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvItemTitle.text = note.title
                tvItemDate.text = note.date
                tvItemDescription.text = note.description
                cvItemNote.setOnClickListener {
                    onItemClick(note, adapterPosition)
                }
            }
        }
    }
}
