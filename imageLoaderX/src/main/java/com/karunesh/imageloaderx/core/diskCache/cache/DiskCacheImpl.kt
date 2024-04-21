package com.karunesh.imageloaderx.core.diskCache.cache

import com.karunesh.imageloaderx.core.diskCache.DiskLruCache
import java.io.File


class DiskCacheImpl(private val diskLruCache: DiskLruCache) : DiskCache {

    override fun get(key: String): File? {
        return diskLruCache[key]
    }

    override fun put(key: String, file: File): File? {
        return diskLruCache.put(key, file)
    }

    override fun remove(key: String) {
        diskLruCache.delete(key)
    }

}