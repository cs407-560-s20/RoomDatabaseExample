package com.example.roomdatabaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    lateinit var db : ContactRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Build a db instance
        db = Room.databaseBuilder(
            applicationContext,
            ContactRoomDatabase::class.java, "contacts.db"
        ).build()

    }


   fun addButton(view: View) {

       Thread{
           // Insert methods treat 0 as not-set while inserting the item.
           // This allows autoincrement primary key to get autoincremented values
           val contact  = ContactEntity(0,text_name.text.toString(), text_email.text.toString())
           Log.d(TAG, "Inserting a contact...")
           db.contactDAO().insertContact(contact)
           clearEditTexts()
       }.start()

    }

    fun viewAllDataButton(view: View) {

        Thread{
            // Read all the records
            val contacts = db.contactDAO().viewAllContacts()

            val buffer = StringBuffer()
            for (item in contacts){
                Log.d(TAG, "contact: ${item.id}, ${item.name}, ${item.email}")

                buffer.append("ID : ${item.id}" + "\n")
                buffer.append("NAME : ${item.name}" + "\n")
                buffer.append("EMAIL :  ${item.email}" + "\n\n")
            }


            // We cannot call showDialog from a non-UI thread, instead we can call it from a runOnUiThread to access our views
            runOnUiThread {
                // Do you UI operations
                showDialog("Data Listing", buffer.toString())
            }

        }.start()

    }


    fun deleteButton(view: View) {
        Thread{
            // Delete a contact using id
            val id = text_id.text.toString()
            if(id.isNotEmpty())
            {
                // First find the contact and then delete
                val contact  = db.contactDAO().findContact(id.toInt())
                Log.d(TAG, "${contact.id}, ${contact.name}, ${contact.email}")

                // Delete the contact
                db.contactDAO().deleteContact(contact)

                clearEditTexts()
            }
            else{
                // We cannot call showDialog from a non-UI thread, instead we can call it from a runOnUiThread to access our views
                runOnUiThread {
                    // Do you UI operations
                    showToast("Enter an id")
                }
            }
        }.start()

    }


    fun updateButton(view: View) {

        Thread{
            // Update a contact using id
            val id = text_id.text.toString()
            if(id.isNotEmpty())
            {
                // First find the contact and then update
                val contact  = db.contactDAO().findContact(id.toInt())
                Log.d(TAG, "${contact.id}, ${contact.name}, ${contact.email}")


                // Update the contact
                contact.name = text_name.text.toString()
                contact.email = text_email.text.toString()

                db.contactDAO().updateContact(contact)

                clearEditTexts()
            }
            else{
                // We cannot call showDialog from a non-UI thread, instead we can call it from a runOnUiThread to access our views
                runOnUiThread {
                    // Do you UI operations
                    showToast("Enter an id")
                }
            }
        }.start()

    }

/**
* A helper function to show Toast message
*/
private fun showToast(text: String){
   Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

/**
* show an alert dialog with data dialog.
*/
private fun showDialog(title : String,Message : String){
   val builder = AlertDialog.Builder(this)
   builder.setCancelable(true)
   builder.setTitle(title)
   builder.setMessage(Message)
   builder.show()
}


/**
* A helper function to clear our edittexts
*/
private fun clearEditTexts(){
   text_email.text.clear()
   text_id.text.clear()
   text_name.text.clear()
}



}
