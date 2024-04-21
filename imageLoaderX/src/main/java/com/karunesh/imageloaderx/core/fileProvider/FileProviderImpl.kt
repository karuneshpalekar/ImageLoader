package com.karunesh.imageloaderx.core.fileProvider

import android.net.Uri
import com.karunesh.imageloaderx.core.GenericLoader
import com.karunesh.imageloaderx.core.diskCache.cache.DiskCache
import java.io.File


class FileProviderImpl(
    private val cacheDir: File,
    private val diskCache: DiskCache,
    vararg loaders: GenericLoader
) : FileProvider {

    private val loaders = HashMap<String, GenericLoader>()

    init {
        loaders.forEach { loader ->
            loader.schemes.forEach { this.loaders[it] = loader }
        }
    }

    override fun getFile(uri: Uri): File? {
        return diskCache.get(uri.toString()) ?: loadIntoCache(uri)
    }

    private fun loadIntoCache(uri: Uri): File? {
        var tempFile: File? = null
        try {
            tempFile = File.createTempFile("file", ".tmp", cacheDir)

            val loader = loaders[uri.scheme] ?: return null

            val uriString = uri.toString()

            return loader.load(uriString, tempFile)
                .takeIf { true }
                ?.let {
                    diskCache.put(uriString, tempFile)
                }
        } catch (ex: Throwable) {
            ex.printStackTrace()
        } finally {
            tempFile?.delete()
        }
        return null
    }

}