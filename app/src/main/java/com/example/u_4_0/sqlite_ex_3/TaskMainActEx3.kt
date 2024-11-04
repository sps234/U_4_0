package com.example.u_4_0.sqlite_ex_3

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
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.u_4_0.R

//  TaskMainActivityExample3

class TaskMainActEx3 : AppCompatActivity() {

    private lateinit var title : EditText
    private lateinit var img : ImageButton
    private lateinit var save : Button
    private lateinit var show : Button
    private lateinit var listView: ListView

    private val pickImageRequest = 1

    private var selectedBitmap : Bitmap? = null
    private  lateinit var taskRepo : TaskRepo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_main_act_ex3)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = findViewById(R.id.edT31 )
        save = findViewById(R.id.saveBtn31)
        show = findViewById(R.id.showBtn32 )
        img = findViewById(R.id.imgV31 )

        img.setOnClickListener {
            pickImage()
        }

        val db : TaskDBHelper = TaskDBHelper( this )
        taskRepo = TaskRepo( db )
        save.setOnClickListener {
            insertData()
            Toast.makeText( this, "Task Inserted", Toast.LENGTH_LONG ).show()
        }

        listView = findViewById(R.id.lV31 )
        show.setOnClickListener {
            displayTasks()
        }

    }

    private fun pickImage() {
        val intent = Intent( Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
        startActivityForResult( intent, pickImageRequest )
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == pickImageRequest && resultCode == Activity.RESULT_OK && data?.data != null ) {
            val imgUri : Uri = data.data!!
            if ( imgUri != null ) {
                selectedBitmap = if ( Build.VERSION.SDK_INT < 28 ) {
                    MediaStore.Images.Media.getBitmap( this.contentResolver, imgUri )
                }
                else {
                    val source = ImageDecoder.createSource( this.contentResolver, imgUri )
                    ImageDecoder.decodeBitmap( source )
                }
                img.setImageBitmap( selectedBitmap )
            }
            Toast.makeText( this, "Image picked", Toast.LENGTH_LONG ).show()
        }
    }

    private fun insertData( ) {
        val ttl = title.text.toString()

        if ( ttl.isNotEmpty() && selectedBitmap != null ) {
            taskRepo.insertTask( ttl, selectedBitmap!!)
        }
        else {
            Toast.makeText( this, "Please pick image and enter title both", Toast.LENGTH_LONG ).show()
        }
    }

    private fun displayTasks( ) {
        val listOfTasks = taskRepo.getAllTasks().toMutableList()
        val adptr = TaskAdapter( this, R.layout.custom_ex_3, listOfTasks, taskRepo )
        listView.adapter = adptr
    }
}