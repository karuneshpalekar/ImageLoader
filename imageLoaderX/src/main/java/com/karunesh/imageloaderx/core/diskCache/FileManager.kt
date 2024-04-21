package com.karunesh.imageloaderx.core.diskCache

import java.io.File
import java.io.IOException


interface FileManager {
    fun journal(): File?

    @Throws(IOException::class)
    fun prepare()

    operator fun get(name: String?): File?

    @Throws(IOException::class)
    fun accept(extFile: File?, name: String?): File?

    fun exists(name: String?): Boolean?

    @Throws(IOException::class)
    fun delete(name: String)
}