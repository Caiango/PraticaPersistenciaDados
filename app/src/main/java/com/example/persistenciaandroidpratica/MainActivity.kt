package com.example.persistenciaandroidpratica

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.persistenciaandroidpratica.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var storageType = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()

//        //ler arquivo
//        file.inputStream().use {
//            Toast.makeText(applicationContext, it.readBytes().decodeToString(), Toast.LENGTH_LONG)
//                .show()
//        }
//
//        extFile.inputStream().use {
//            Toast.makeText(applicationContext, it.readBytes().decodeToString(), Toast.LENGTH_LONG)
//                .show()
//        }

    }

    fun setupUI() {
        binding.button.setOnClickListener {
            val fileName = binding.etName.text.toString()
            val fileContent = binding.etContent.text.toString()

            if (storageType == "internal") {
                val file = File(filesDir, fileName)
                file.createNewFile()

                file.outputStream().use {
                    it.write(fileContent.toByteArray())
                    Toast.makeText(
                        applicationContext,
                        "CRIADO COM SUCESSO",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if (storageType == "external") {
                val extFile = File(getExternalFilesDir(null), fileName)
                extFile.createNewFile()

                extFile.outputStream().use {
                    it.write(fileContent.toByteArray())
                }
                Toast.makeText(
                    applicationContext,
                    "CRIADO COM SUCESSO",
                    Toast.LENGTH_LONG
                ).show()
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