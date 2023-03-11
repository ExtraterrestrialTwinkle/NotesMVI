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

class NotesListAdapter(
    private val onClick: (Long) -> Unit
) : ListAdapter<NoteModel, NotesListHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesListHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: NotesListHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class NotesListHolder(
        private val binding: ListItemBinding,
        onClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentItemId: Long? = null

        init {
            binding.root.setOnClickListener {
                this.currentItemId?.let { onClick(it) }
            }
        }

        fun bind(note: NoteModel) {
            binding.run {
                currentItemId = note.id
                title.text = note.title
                noteText.text = note.text
                date.text = note.date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                deleteButton.setOnClickListener {
                    currentList.remove(note)
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
