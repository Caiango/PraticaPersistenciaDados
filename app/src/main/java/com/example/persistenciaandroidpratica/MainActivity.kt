package com.example.persistenciaandroidpratica

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.persistenciaandroidpratica.databinding.ActivityMainBinding
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var file: File
    lateinit var extFile: File
    private lateinit var internalFiles: MutableList<File>
    private lateinit var externalFiles: MutableList<File>
    private var finalList: ArrayList<File> = ArrayList()
    private var storageType = ""
    private lateinit var adapter: FilesRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        internalFiles = File(filesDir.toURI()).listFiles().toMutableList()
        externalFiles = File(getExternalFilesDir(null)?.toURI()).listFiles().toMutableList()
        finalList.addAll(internalFiles)
        finalList.addAll(externalFiles)

        adapter = FilesRecyclerViewAdapter(finalList,this::removeFile)

        setupUI()
    }

    private fun removeFile (position: Int) {
        val file = finalList[position]
        file.delete()
        finalList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    fun setupUI() {
        binding.rv.layoutManager = LinearLayoutManager(applicationContext)
        binding.rv.setHasFixedSize(true)
        binding.rv.adapter = adapter
        binding.button.setOnClickListener {
            val fileName = binding.etName.text.toString()
            val fileContent = binding.etContent.text.toString()

            if (storageType == "internal") {
                file = File(filesDir, fileName)
                file.createNewFile()

                file.outputStream().use {
                    it.write(fileContent.toByteArray())
                    Toast.makeText(
                        applicationContext,
                        "CRIADO COM SUCESSO",
                        Toast.LENGTH_LONG
                    ).show()
                    finalList.add(file)
                    adapter.notifyItemInserted(finalList.indexOf(file))
                }
            } else if (storageType == "external") {
                extFile = File(getExternalFilesDir(null), fileName)
                extFile.createNewFile()

                extFile.outputStream().use {
                    it.write(fileContent.toByteArray())
                }
                Toast.makeText(
                    applicationContext,
                    "CRIADO COM SUCESSO",
                    Toast.LENGTH_LONG
                ).show()
                finalList.add(extFile)
                adapter.notifyItemInserted(finalList.indexOf(extFile))
            } else {
                Toast.makeText(
                    applicationContext,
                    "SELECIONE UMA OPÇÃO INTERNA/EXTERNA",
                    Toast.LENGTH_LONG
                ).show()

            }
        }

    }

    fun checkRadio(view: View) {
        when (view.id) {
            R.id.radio1 -> {
                storageType = "internal"
            }
            R.id.radio2 -> {
                storageType = "external"
            }
        }
    }
}