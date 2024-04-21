package com.karunesh.imageloaderx.core.memoryCache

import com.karunesh.imageloaderx.core.Result


interface MemoryCache {

    fun get(key: String): Result?

    fun put(key: String, result: Result): Result?

    fun remove(key: String): Result?

}
