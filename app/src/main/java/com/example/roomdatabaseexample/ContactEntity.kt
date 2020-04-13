package com.example.roomdatabaseexample

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class ContactEntity(

    @PrimaryKey(autoGenerate = true)
    var id : Int,

    var name : String,

    var email: String
)