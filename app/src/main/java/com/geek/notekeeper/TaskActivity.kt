package com.geek.notekeeper

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.geek.notekeeper.model.Note
import com.geek.notekeeper.model.NoteAdapter
import kotlinx.android.synthetic.main.activity_task.*

class TaskActivity : AppCompatActivity() {

    private lateinit var adapter: NoteAdapter
    private var noteList = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        rv_tasks.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter(ArrayList())
        rv_tasks.adapter = adapter

        //   // create a dialog to enter a task name when the floating action button is clicked
        fab.setOnClickListener {
            val input = EditText(this);
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Enter your Note: ")
                .setCancelable(true)
                .setPositiveButton("Create") { dialog, _ ->
                    dialog.dismiss()
                    noteList.add(Note(input.text.toString()))
                    adapter.updateData(noteList)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
            val dialog = dialogBuilder.create()
            dialog.setView(input)
            dialog.setTitle("Create New Note")
            dialog.show()
        }
    }
}
