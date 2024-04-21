package com.karunesh.imageloaderx.core.fileProvider

import android.net.Uri
import com.karunesh.imageloaderx.core.diskCache.cache.DiskCache
import java.io.File

interface FileProvider {

    fun getFile(url: String): File? = getFile(Uri.parse(url))

    fun getFile(uri: Uri): File?

}
