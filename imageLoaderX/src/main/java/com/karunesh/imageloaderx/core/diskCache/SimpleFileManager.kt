package com.karunesh.imageloaderx.core.diskCache

import java.io.File
import java.io.IOException


class SimpleFileManager(private val dir: File) : FileManager {
    override fun journal(): File = File(dir, "journal.bin")


    @Throws(IOException::class)
    override fun prepare() {
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw IOException("Unable to create specified cache directory")
            }
        }
    }

    override fun get(name: String?): File? = name?.let { File(dir, it) }


    @Throws(IOException::class)
    override fun accept(extFile: File?, name: String?): File {
        val newFile = get(name)
        return if ((dir.exists() || dir.mkdirs())
            or (newFile!!.exists() && newFile.delete())
            or extFile!!.renameTo(newFile)
        ) {
            newFile
        } else {
            throw formatException("Unable to accept file %s", extFile)
        }
    }

    override fun exists(name: String?): Boolean? = name?.let { File(dir, it).exists() }


    @Throws(IOException::class)
    override fun delete(name: String) {
        val file = File(dir, name)
        if (file.exists() && !file.delete()) {
            throw formatException("Unable to delete file %s", file)
        }
    }

    private fun formatException(format: String, file: File?): IOException {
        val message = String.format(format, file!!.name)
        return IOException(message)
    }


}