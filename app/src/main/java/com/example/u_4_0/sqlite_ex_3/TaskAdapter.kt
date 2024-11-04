package com.example.u_4_0.sqlite_ex_3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.u_4_0.R

class TaskAdapter(private val mctx : Context, private val resources: Int, private val tasks : MutableList<TaskModel>, private val taskRepo: TaskRepo )
    : ArrayAdapter<TaskModel> ( mctx, resources, tasks ){



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from( mctx )
        val view : View = layoutInflater.inflate( resources, null )

        val title = view.findViewById<TextView>( R.id.tV39 )
        val image = view.findViewById<ImageView>( R.id.imgV39 )

        val i : TaskModel = tasks[position]
        title.text = i.title
        image.setImageBitmap( i.img )


        val delete  = view.findViewById<ImageButton>( R.id.iC39 )

        delete.setOnClickListener {
            taskRepo.deleteTask( i.title )
            tasks.removeAt(position)
            notifyDataSetChanged()
            Toast.makeText(mctx, "Task deleted", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}