package com.geek.notekeeper

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.geek.notekeeper.model.Note
import com.geek.notekeeper.model.NoteAdapter
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.activity_task.*

class TaskActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private var user: User? = null
    private lateinit var adapter: NoteAdapter

    override fun onStart() {
        super.onStart()

        user = noteApp.currentUser()
        //configure realm and use the current user and the public partition
        val config = SyncConfiguration.Builder(user, "public")
            .waitForInitialRemoteData()
            .build()

        //It is recommended to get a Realm Instance asynchronously
        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@TaskActivity.realm = realm
                //Set up Recycler View
                setUpRecyclerView(realm)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        //create a dialog to enter a task name when the floating action button is clicked
        fab.setOnClickListener {
            val input = EditText(this);
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Enter your Note: ")
                .setCancelable(true)
                .setPositiveButton("Create") { dialog, _ ->
                    dialog.dismiss()
                    //1
                    val note = Note(input.text.toString())

                    //all realm writes needs to occur inside of a transaction
                    //2
                    realm.executeTransactionAsync { realm ->
                        realm.insert(note)
                    }
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

    private fun setUpRecyclerView(realm: Realm) {

        rv_tasks.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter(realm.where<Note>().sort("date").findAll())
        rv_tasks.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        // its recommended to close realm references even if the user does not logout
        realm.close()
    }
}
