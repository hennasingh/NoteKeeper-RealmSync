package com.geek.notekeeper.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

class Note(
    var noteName: String,
    var date: Date = Date(),
    var _partition: String = "Public"
) : RealmObject() {

    @PrimaryKey
    var _id: ObjectId = ObjectId()

}

