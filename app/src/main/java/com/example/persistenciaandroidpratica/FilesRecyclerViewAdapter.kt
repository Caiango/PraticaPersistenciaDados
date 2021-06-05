package com.example.persistenciaandroidpratica

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.persistenciaandroidpratica.databinding.RecyclerItemBinding
import java.io.File

class FilesRecyclerViewAdapter (private val files : ArrayList<File>, private val callback : (Int) -> Unit) : RecyclerView.Adapter<FilesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        val binding = RecyclerItemBinding.bind(view)
        val viewHolder = ViewHolder(binding)

        viewHolder.deleteFile.setOnClickListener {
            val position = viewHolder.adapterPosition
            callback(position)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileName = files[position].name

        holder.fileName.text = fileName

    }

    override fun getItemCount(): Int = files.size

    class ViewHolder (view: RecyclerItemBinding) : RecyclerView.ViewHolder(view.root) {
        val fileName = view.txtName
        val deleteFile = view.imageButton

    }
}