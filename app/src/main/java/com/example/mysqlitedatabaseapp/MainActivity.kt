package com.example.mysqlitedatabaseapp

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    var edit_name:EditText ?=null
    var edit_email:EditText ?=null
    var edit_ID:EditText ?=null
    var view:Button ?=null
    var save:Button ?=null
    var delete:Button ?=null
    var db:SQLiteDatabase ?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edit_name =findViewById(R.id.edit_name)
        edit_email = findViewById(R.id.edit_email)
        edit_ID=findViewById(R.id.edit_ID)
        view =findViewById(R.id.mBtnView)
        save = findViewById(R.id.mBtnSave)
        delete= findViewById(R.id.mBtnDelete)
        db=openOrCreateDatabase("safari_system", MODE_PRIVATE,null)
        db!!.execSQL("CREATE TABLE IF NOT EXISTS users(jina VARCHAR, arafa VARCHAR, kitambulisho VARCHAR)")
        save!!.setOnClickListener {
            var name = edit_name!!.text.toString()
            var email = edit_email!!.text.toString()
            var idNumber = edit_ID!!.text.toString()
            if (name.isEmpty()){
                edit_name!!.setError("Please fill this input")
                edit_name!!.requestFocus()
            }else if (email.isEmpty()){
                edit_email!!.setError("Please fill this input")
                edit_email!!.requestFocus()

            }else if (idNumber.isEmpty()){
                edit_ID!!.setError("Please fill this input")
                edit_ID!!.requestFocus()
            }else{
                db!!.execSQL("INSERT INTO users VALUES('"+name+"','"+email+"','"+idNumber+"')")
                display("SUCCES","User saved successfully")
            }
        }
        //Check if gthe ther is any record in the db
        view!!.setOnClickListener {
            var cursor=db!!.rawQuery("SELECT * FROM users",
            null)
            if (cursor.count ==0){
                display("NO DATA FOUND","Sorry,your db is empty")
            }else {
                //User buffer to append the records
                var buffer = StringBuffer()
                while (cursor.moveToNext()) {
                    buffer.append(cursor.getString(0) + "\n")
                    buffer.append(cursor.getString(1) + "\n")
                    buffer.append(cursor.getString(2) + "\n\n")

                }
                display("USERS", buffer.toString())
            }
        }
        delete!!.setOnClickListener {
            //get the id from user
            var idNumber=edit_ID!!. text.toString().trim()
            if (idNumber.isEmpty()){
                edit_ID!!.setError("please fill this input")
                edit_ID!!.requestFocus()
            }else {
                //Use the cursor to select the user with the given ID
                var cursor = db!!.rawQuery(
                    "SELECT *FROM user WHERE kitambulisho='" + idNumber + "'",
                    null
                )
                //Check if the record is available in the db
                if (cursor.count == 0) {
                    display("NO RECORD", "Sorry we didn't find a user with the ID")
                } else {
                    //DELETE THE RECORD
                    db!!.execSQL("DELETE FROM user WHERE kitambulisho='" + idNumber + "'")
                    display("SUCCESS", "User delete successfully")
                }
            }
        }
    }

    fun display(kichwa:String, ujumbe:String){
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(kichwa)
        alertDialog.setTitle(ujumbe)
        alertDialog.create().show()
    }
}