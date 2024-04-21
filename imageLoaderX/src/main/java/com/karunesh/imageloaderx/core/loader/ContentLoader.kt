package com.karunesh.imageloaderx.core.loader

import android.content.ContentResolver
import android.net.Uri
import com.karunesh.imageloaderx.core.GenericLoader
import com.karunesh.imageloaderx.util.safeClose
import com.karunesh.imageloaderx.util.safeCopyTo
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class ContentLoader(private val contentResolver: ContentResolver) : GenericLoader {

    override val schemes: List<String>
        get() = listOf("content")

    override fun load(uriString: String, file: File): Boolean {
        val uri = Uri.parse(uriString)
        var input: InputStream? = null
        var output: OutputStream? = null
        try {
            input = contentResolver.openInputStream(uri)
            output = FileOutputStream(file)
            return input
                ?.safeCopyTo(output)
                ?.takeIf { true } ?: false
        } finally {
            input?.safeClose()
            output?.safeClose()
        }
    }
}