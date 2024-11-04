package com.example.u_4_0.sqlite_ex_1

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class UserRepoEx1( private val dbHelper : DBHelperEx1 ) {

    fun insertUser( name : String, img : Bitmap ): Long {
        val db : SQLiteDatabase = dbHelper.writableDatabase
        db.setMaximumSize(100 * 1024 * 1024)
        val contentValues = ContentValues()
        contentValues.put( DBHelperEx1.NAME_COL, name )
        contentValues.put( DBHelperEx1.IMAGE_COL, bitmapToByteArray( img ) )
        return db.insert( DBHelperEx1.TABLE_NAME, null, contentValues )
    }

    fun getUsers(): List<ModelEx1> {

        val userlist = mutableListOf<ModelEx1>()
        val db : SQLiteDatabase = dbHelper.readableDatabase

        val cursor : Cursor = db.query(
            DBHelperEx1.TABLE_NAME,
            arrayOf( DBHelperEx1.NAME_COL, DBHelperEx1.IMAGE_COL ),
            null, null, null, null, null
        )
        while ( cursor.moveToNext() ) {
            val n = cursor.getString( cursor.getColumnIndexOrThrow( DBHelperEx1.NAME_COL ) )
            val i = cursor.getBlob( cursor.getColumnIndexOrThrow( DBHelperEx1.IMAGE_COL ) )
            val bitMap = byteArrayToBitmap( i )
            userlist.add( ModelEx1( n, bitMap ) )
        }

        cursor.close()
        return userlist
    }

    private fun bitmapToByteArray( bitmap: Bitmap  ) : ByteArray {
        val stream = ByteArrayOutputStream()
        val bitmap2  = resizeBitmap( bitmap, 300, 300 )

        val compressed = bitmap2.compress( Bitmap.CompressFormat.JPEG, 30, stream )
        if ( ! compressed ) {
            throw IllegalStateException("Failed to compress bitmap")
        }
        return stream.toByteArray()
    }

    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val scaleRatio = minOf(maxWidth.toFloat() / width, maxHeight.toFloat() / height)

        val scaledWidth = (width * scaleRatio).toInt()
        val scaledHeight = (height * scaleRatio).toInt()

        return Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)
    }

    private fun byteArrayToBitmap( byteArray: ByteArray ) : Bitmap {
        return BitmapFactory.decodeByteArray( byteArray, 0, byteArray.size )
    }

}