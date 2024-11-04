package com.example.u_4_0.sqlite_ex_3

import android.content.ContentValues
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class TaskRepo(private val dbHelper: TaskDBHelper )  {

    fun insertTask( title : String, bitmap: Bitmap  ) {

        val db = dbHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put( TaskDBHelper.TITLE_COL, title  )
        contentValues.put( TaskDBHelper.IMAGE_COL, bitMapToByteArray( bitmap ))
        db.insert( TaskDBHelper.TABLE_NAME, null, contentValues )
    }

    private fun bitMapToByteArray( bitmap: Bitmap ) : ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress( Bitmap.CompressFormat.JPEG, 20, stream )
        return stream.toByteArray()
    }

    private fun byteArrayToBitMap( byteArray: ByteArray ) : Bitmap {
        return BitmapFactory.decodeByteArray( byteArray, 0, byteArray.size )
    }

    fun getAllTasks( ) : List<TaskModel> {
        val db = dbHelper.readableDatabase
        val listOfTasks = mutableListOf<TaskModel>()
        val cursor : Cursor = db.query(
            TaskDBHelper.TABLE_NAME,
            arrayOf( TaskDBHelper.TITLE_COL, TaskDBHelper.IMAGE_COL ),
            null, null, null, null, null
        )
        while( cursor.moveToNext() ) {
            val n = cursor.getString( cursor.getColumnIndexOrThrow( TaskDBHelper.TITLE_COL ) )
            val i = cursor.getBlob( cursor.getColumnIndexOrThrow( TaskDBHelper.IMAGE_COL ) )
            val bitMap = byteArrayToBitMap( i )
            listOfTasks.add( TaskModel( n, bitMap ))
        }
        cursor.close()
        return listOfTasks
    }

    fun deleteTask( title: String ) {
        val db = dbHelper.writableDatabase
        db.delete(TaskDBHelper.TABLE_NAME, "${TaskDBHelper.TITLE_COL} = ?", arrayOf(title))
    }

}