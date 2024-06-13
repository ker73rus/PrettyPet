package com.example.prettypet

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBConnection(val context:Context, val factory:SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context,"app",factory,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        var query = "CREATE TABLE users(id INT PRIMARY KEY, phone TEXT, password TEXT); CREATE TABLE pets(id INT PRIMARY KEY, name TEXT, owner INT))"
        db!!.execSQL(query)
        query = ""
        db.execSQL(query)
        addUser(User("",""))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun addUser(user: User){
        val values = ContentValues()
        values.put("phone", user.phone)
        values.put("password", user.password)
        val db = this.writableDatabase
        db.insert("users",null,values)
        db.close()
    }
    fun getUser(phone:String):User{
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM users WHERE phone = '$phone'",null)
        if(result.moveToFirst())
            return User(result.getString(1),result.getString(2))
        return User("","")
    }

    fun addPet(pet:Pet){
        val values = ContentValues()
        values.put("name", pet.name)
        values.put("icon", pet.icon)
        values.put("owner", pet.owner)
        val db = this.writableDatabase
        db.insert("pets",null,values)
        db.close()
    }
}