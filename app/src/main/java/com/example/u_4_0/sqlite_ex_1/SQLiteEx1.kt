package com.example.u_4_0.sqlite_ex_1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.u_4_0.R

class SQLiteEx1 : AppCompatActivity() {

    private lateinit var name : EditText
    private lateinit var image : ImageView
    private lateinit var pick : Button
    private lateinit var save : Button
    private lateinit var show : Button
    private lateinit var listView: ListView

    private var selectedBitmap: Bitmap? = null
    private val PICK_IMAGE_REQUEST = 1

    private  lateinit var  userRepo : UserRepoEx1

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sqlite_ex1)

        name = findViewById(R.id.edt1 )
        image = findViewById(R.id.img1 )
        pick = findViewById(R.id.pickBt1 )
        save = findViewById(R.id.saveBt1 )
        show = findViewById(R.id.showBt1 )
        listView = findViewById( R.id.lv1 )

        pick.setOnClickListener {
            pickImage()
        }

        val dbHelper = DBHelperEx1( this )
        userRepo = UserRepoEx1( dbHelper )

        save.setOnClickListener {
            val n = name.text.toString()
            if ( n.isNotEmpty() && selectedBitmap != null) {
                userRepo.insertUser( n, selectedBitmap!! )
                Toast.makeText(this, "User saved", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Enter name and select an image", Toast.LENGTH_SHORT).show()
            }
        }

        show.setOnClickListener {
            showUsers()
        }

    }

    private fun pickImage () {
        val intent = Intent( Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
        startActivityForResult( intent, PICK_IMAGE_REQUEST )

//        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
//            type = "image/*"
//        }
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null ) {
            val imageUri : Uri? = data.data
            if ( imageUri != null ) {
                selectedBitmap = if ( Build.VERSION.SDK_INT < 28 ) {
                    MediaStore.Images.Media.getBitmap( this.contentResolver, imageUri )
                }
                else {
                    val source = ImageDecoder.createSource( this.contentResolver, imageUri )
                    ImageDecoder.decodeBitmap( source )
                }
                image.setImageBitmap( selectedBitmap )
            }
        }
    }

    private  fun showUsers() {
        val userlist = userRepo.getUsers()
        val adptr = DBAdapterEx1( this, R.layout.custom_ex_1, userlist )
        listView.adapter = adptr
    }
}


/*

without image -


class SQLiteEx1 : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sqlite_ex1)

        val name : EditText = findViewById(R.id.edt1)
        val age : EditText = findViewById(R.id.edt2)
        val save : Button = findViewById(R.id.bt1)
        val show : Button = findViewById(R.id.bt2)

        val txt1 : TextView = findViewById(R.id.txt1 )
        val txt2 : TextView = findViewById(R.id.txt2 )

        save.setOnClickListener{

            val db = DBHelperEx1( this, null )
            val n = name.text.toString()
            val a = age.text.toString().toInt()
            db.addDetails( n, a )
            Toast.makeText( this, "$n $a Data Saved ", Toast.LENGTH_LONG ).show()
            name.text.clear()
            age.text.clear()

        }

        show.setOnClickListener{

            val db = DBHelperEx1( this, null )
            val cursor = db.getDetails()

            cursor.moveToFirst()
            txt1.append( cursor.getString( cursor.getColumnIndex( DBHelperEx1.NAME_COL ) ) + "\n" )
            txt2.append( cursor.getString( cursor.getColumnIndex( DBHelperEx1.AGE_COL ) ) + "\n" )

            while ( cursor.moveToNext() ) {
                txt1.append( cursor.getString( cursor.getColumnIndex( DBHelperEx1.NAME_COL ) ) + "\n" )
                txt1.append( cursor.getString( cursor.getColumnIndex( DBHelperEx1.NAME_COL ) ) + "\n" )
            }
            cursor.close()
        }


    }
}
 */