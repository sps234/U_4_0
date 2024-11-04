package com.example.u_4_0.sqlite_ex_3

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class TaskDBHelper ( c : Context ) : SQLiteOpenHelper( c, "TaskDB1", null , 1 ) {

    companion object {
        val TABLE_NAME = "Tasks1"
        val TITLE_COL = "Title"
        val IMAGE_COL = "Image"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val q1 = "create table if not exists $TABLE_NAME ( $TITLE_COL text, $IMAGE_COL BLOB )"
        p0?.execSQL( q1 )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val q2 = "drop table if exists $TABLE_NAME"
        p0?.execSQL( q2 )
    }
}