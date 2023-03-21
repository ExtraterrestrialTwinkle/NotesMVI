package com.siuzannasmolianinova.notesmvi.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.siuzannasmolianinova.notesmvi.databinding.ListItemBinding
import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.ui.main.NotesListAdapter.NotesListHolder
import java.time.format.DateTimeFormatter

class NotesListAdapter : ListAdapter<NoteModel, NotesListHolder>(DiffCallback()) {

    var clickListener: ((id: Long) -> Unit?)? = null
    var onDeleteClickListener: ((id: Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesListHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesListHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class NotesListHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteModel) {
            binding.run {
                root.setOnClickListener {
                    note.id.let { clickListener?.invoke(it) }
                }
                title.text = note.title
                noteText.text = note.text
                date.text = note.date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                deleteButton.setOnClickListener {
                    onDeleteClickListener?.invoke(note.id)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return newItem.title == oldItem.title && newItem.text == oldItem.text && newItem.date == oldItem.date
        }
    }
}
