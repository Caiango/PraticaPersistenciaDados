package com.example.persistenciaandroidpratica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fileName = "nome_do_arq"
        val fileContentInterno = "conteudo_do_arq"
        val fileContentExterno = "conteudo_do_arq_externo"

        //referencia do arquivo

        //diretorio interno
        val file = File(filesDir, fileName)
        file.createNewFile()

        //diretorio externo
        val extFile = File(getExternalFilesDir(null), fileName)
        //val extFile = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
        extFile.createNewFile()

        //salvar no diretorio
        file.outputStream().use {
            it.write(fileContentInterno.toByteArray())
        }

        extFile.outputStream().use {
            it.write(fileContentExterno.toByteArray())
        }

        //ler arquivo
        file.inputStream().use {
            Toast.makeText(applicationContext, it.readBytes().decodeToString(), Toast.LENGTH_LONG).show()
        }

        extFile.inputStream().use {
            Toast.makeText(applicationContext, it.readBytes().decodeToString(), Toast.LENGTH_LONG).show()
        }

    }
}