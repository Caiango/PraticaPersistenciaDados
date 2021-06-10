package com.example.persistenciaandroidpratica

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.File

class Utils {

    fun setupEncrypt(context: Context, file: File): EncryptedFile {
        val mainKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedFile.Builder(
            context,
            file,
            mainKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()
    }

    companion object {
        fun writeAndEncrypt( context: Context, file: File, content: String) : Boolean{

            if (file.exists()) {
                return false
            }

            val encryptedFile = Utils().setupEncrypt(context, file)

            encryptedFile.openFileOutput().use { writer ->
                writer.write(content.toByteArray())
                return true
            }
        }

        fun decryptAndRead(context: Context, file: File) : String {
            val encryptedFile = Utils().setupEncrypt(context, file)

            var result = ""

            encryptedFile.openFileInput().use { reader ->
                result = reader.readBytes().toString()
            }

            return result
        }
    }
}