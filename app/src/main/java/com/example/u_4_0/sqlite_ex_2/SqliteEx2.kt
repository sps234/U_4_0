package com.example.u_4_0.sqlite_ex_2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.u_4_0.R

class SqliteEx2 : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sqlite_ex2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = findViewById<EditText>( R.id.textView1 )
        val age = findViewById<EditText>( R.id.textView2 )
        val add = findViewById<Button>( R.id.addBt )
        val show = findViewById<Button>( R.id.showBt )

        add.setOnClickListener {

            val db = DBHelperEx2( this, null )
            val n = name.text.toString()
            val a = age.text.toString().toInt()
            db.addDetails( n, a )
            Toast.makeText( applicationContext, "Name $n and Age $a added", Toast.LENGTH_LONG ).show()
            name.text.clear()
            age.text.clear()
        }

        show.setOnClickListener {
            val db = DBHelperEx2( this, null )
            val cursor = db.getDetails()
            val lv : ListView = findViewById(R.id.lv1 )
            val list = mutableListOf<ModelUserEx2>()
            while ( cursor.moveToNext() ) {
                list.add( ModelUserEx2( cursor.getString( cursor.getColumnIndex( db.NAME )),
                    cursor.getString( cursor.getColumnIndex( db.AGE ) ).toInt() ))
            }
            lv.adapter = DBAdapterEx2( this, R.layout.custom_ex_2, list )
        }
    }
}