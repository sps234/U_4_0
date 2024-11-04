package com.example.u_4_0.sqlite_ex_2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf


class DBHelperEx2(context: Context, factory: SQLiteDatabase.CursorFactory? ) : SQLiteOpenHelper( context, "SQLite-1", factory, 1 ) {

    val TABLE_NAME = "Users"
    val NAME = "Name"
    val AGE = "Age"

    override fun onCreate(p0: SQLiteDatabase?) {
        val q1 = "create table $TABLE_NAME ( $NAME TEXT, $AGE INTEGER )"
        p0?.execSQL( q1 )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val q2 = "drop table if exists $TABLE_NAME"
        p0?.execSQL( q2 )
        onCreate( p0 )
    }

    fun addDetails(  Name : String,  Age:Int ) {
        val v = ContentValues()
        v.put( NAME, Name )
        v.put( AGE, Age )
        val db = this.writableDatabase
        db.insert( TABLE_NAME, null, v )
        db.close()
    }

    fun getDetails( ) : Cursor {
        val db = this.readableDatabase
        return db.rawQuery( "select * from $TABLE_NAME", null )
    }



}