package com.example.roomdatabaseexample

import androidx.room.*

@Dao
interface ContactDAO {

    // OnConflictStrategy is optional, but good idea to specify
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: ContactEntity)

    @Update
    fun updateContact(contact: ContactEntity)

    @Delete
    fun deleteContact(contact: ContactEntity)

    @Query("SELECT * FROM contact_table ")
    fun viewAllContacts() : List<ContactEntity>

    @Query("SELECT * FROM contact_table WHERE id LIKE :contactId")
    fun findContact(contactId: Int) : ContactEntity

}