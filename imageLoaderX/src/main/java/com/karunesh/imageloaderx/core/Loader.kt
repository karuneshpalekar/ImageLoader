package com.karunesh.imageloaderx.core

import android.content.Context
import com.karunesh.imageloaderx.core.diskCache.cache.DiskCacheImpl
import com.karunesh.imageloaderx.core.fileProvider.FileProvider
import com.karunesh.imageloaderx.core.memoryCache.MemoryCache
import com.karunesh.imageloaderx.core.diskCache.DiskLruCache
import com.karunesh.imageloaderx.core.fileProvider.FileProviderImpl
import com.karunesh.imageloaderx.core.imageLoader.ImageLoaderImpl
import com.karunesh.imageloaderx.core.memoryCache.MemoryCacheImpl
import com.karunesh.imageloaderx.core.loader.ContentLoader
import com.karunesh.imageloaderx.core.loader.FileLoader
import com.karunesh.imageloaderx.core.loader.UrlLoader
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


object Loader {

    private var imageLoader: ImageLoader? = null

    fun Context.imageLoader(): ImageLoader {
        return imageLoader ?: initImageLoader()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun Context.initImageLoader(
        decoders: List<Decoder> = listOf(BitmapDecoder()),
        fileProvider: FileProvider = FileProviderImpl(
            cacheDir,
            DiskCacheImpl(DiskLruCache.create(cacheDir, 15728640L)),
            UrlLoader(),
            FileLoader(assets),
            ContentLoader(contentResolver)
        ),
        memoryCache: MemoryCache = MemoryCacheImpl(),
        mainExecutor: Executor = MainExecutorImpl(),
        backgroundExecutor: ExecutorService = Executors.newFixedThreadPool(10)
    ): ImageLoader {
        val loader = ImageLoaderImpl(
            fileProvider,
            decoders,
            memoryCache,
            mainExecutor,
            backgroundExecutor
        )
        imageLoader = loader
        return loader
    }

}