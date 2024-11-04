package com.example.u_4_0.sqlite_ex_1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.u_4_0.R

class DBAdapterEx1(private val mctx : Context, private val resources: Int, private val users : List<ModelEx1>)
    : ArrayAdapter<ModelEx1> ( mctx, resources, users ){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from( mctx )
        val view : View = layoutInflater.inflate( resources, null )
        val name : TextView = view.findViewById(R.id.text12 )
        val imageView : ImageView = view.findViewById(R.id.img12 )

        val user = users[position]
        name.text = user.Name
        imageView.setImageBitmap( user.img )

        return view
    }
}