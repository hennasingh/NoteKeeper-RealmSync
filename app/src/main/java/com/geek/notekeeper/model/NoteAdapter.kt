package com.geek.notekeeper.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geek.notekeeper.R
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class NoteAdapter(notes: OrderedRealmCollection<Note>) :
    RealmRecyclerViewAdapter<Note, NoteHolder>(notes, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_item_view, parent, false)
        return NoteHolder(view)

    }


    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = getItem(position)
        holder.bindValues(note)
    }
}