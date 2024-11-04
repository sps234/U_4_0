package com.example.u_4_0.sqlite_ex_1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class DBHelperEx1( c : Context ) : SQLiteOpenHelper( c, "SQLite-13", null, 1 ) {

    companion object {

        val TABLE_NAME = "Userdetails_2"
        val ID = "Id"
        val NAME_COL = "Name"
//        val AGE_COL = "Age"
        val IMAGE_COL  = "Image"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val q1 = "create table if not exists $TABLE_NAME (  $NAME_COL text, $IMAGE_COL BLOB )"
        p0?.execSQL( q1 )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL( "drop table if exists $TABLE_NAME" )
        onCreate( p0 )
    }

//    fun addDetails( name : String, age : Int ) {
//        val v = ContentValues()
//        v.put( NAME_COL, name )
//        v.put( AGE_COL, age )
//        val db = this.writableDatabase
//        db.insert( TABLE_NAME, null, v )
//        db.close()
//    }
//
//    fun getDetails()  : Cursor {
//        val db = this.readableDatabase
//        return db.rawQuery("select * from $TABLE_NAME", null )
//    }


}