package com.example.u_4_0.sqlite_ex_2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.u_4_0.R

class DBAdapterEx2 (private var mctx : Context, private var resources: Int, private var items : List<ModelUserEx2> )
:ArrayAdapter<ModelUserEx2>( mctx, resources, items ){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from( mctx )
        val view : View = layoutInflater.inflate( resources, null )
        val n : TextView = view.findViewById(R.id.tv1 )
        val a : TextView = view.findViewById(R.id.tv2 )

        val i = items[position]
        n.text = i.Name
        a.text = i.Age.toString()

        return view
    }
}