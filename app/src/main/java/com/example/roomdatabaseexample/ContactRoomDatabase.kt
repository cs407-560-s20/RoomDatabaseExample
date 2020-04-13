package com.example.roomdatabaseexample

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ContactEntity::class), version = 1)
abstract class ContactRoomDatabase : RoomDatabase() {

    abstract fun contactDAO() : ContactDAO
}