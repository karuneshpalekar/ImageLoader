package com.karunesh.imageloaderx.core.diskCache.cache

import com.karunesh.imageloaderx.core.diskCache.DiskLruCache
import java.io.File

interface DiskCache {

    fun get(key: String): File?

    fun put(key: String, file: File): File?

    fun remove(key: String)

}
