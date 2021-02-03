package com.geek.notekeeper.model

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.geek.notekeeper.model.Note
import kotlinx.android.synthetic.main.task_item_view.view.*

class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val noteName = itemView.tv_note
    private val date = itemView.tv_date

    fun bindValues(note: Note) {
        noteName.text = note.noteName
        date.text = note.date.toString()
    }

}