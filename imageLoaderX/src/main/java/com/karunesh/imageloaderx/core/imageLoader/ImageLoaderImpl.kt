package com.karunesh.imageloaderx.core.imageLoader

import android.util.Log
import com.karunesh.imageloaderx.core.Decoder
import com.karunesh.imageloaderx.core.Handlers
import com.karunesh.imageloaderx.core.ImageLoader
import com.karunesh.imageloaderx.core.ViewHolder
import com.karunesh.imageloaderx.core.ViewSize
import com.karunesh.imageloaderx.core.fileProvider.FileProvider
import com.karunesh.imageloaderx.core.memoryCache.MemoryCache
import java.lang.ref.WeakReference
import java.security.MessageDigest
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future


class ImageLoaderImpl(
    private val fileProvider: FileProvider,
    private val decoders: List<Decoder>,
    private val memoryCache: MemoryCache,
    private val mainExecutor: Executor,
    private val backgroundExecutor: ExecutorService,
) : ImageLoader {

    private val futures: MutableMap<String, Future<*>> = HashMap()

    private fun <T> waitSizeAsync(
        view: ViewHolder<T>,
        uriString: String,
        handlers: Handlers<T>
    ) {
        backgroundExecutor.submit {
            view.getSize()
            mainExecutor.execute {
                load(view, uriString, handlers)
            }
        }
    }

    private fun <T> loadAsync(
        view: ViewHolder<T>,
        size: ViewSize,
        uriString: String,
        key: String,
        handlers: Handlers<T>
    ) {
        val weakImageView = WeakReference(view)
        handlers.placeholder.invoke(view)
        backgroundExecutor.submit {
            fileProvider.getFile(uriString)
                ?.let { file ->
                    decoders.find { decoder -> decoder.probe(file) }
                        ?.run { decode(file, size.width, size.height) }
                }
                ?.let { result ->
                    memoryCache.put(key, result)
                    mainExecutor.execute {
                        weakImageView.get()?.apply {
                            if (tag == key) {
                                handlers.success.invoke(view, result)
                            }
                        }
                        futures.remove(key)
                    }
                } ?: handlers.error.invoke(view)
        }.let { future ->
            futures[uriString] = future
        }
    }


    override fun <T> load(view: ViewHolder<T>, uriString: String?, handlers: Handlers<T>) {

        val size = view.optSize() ?: run {
            if (uriString != null) {
                waitSizeAsync(view, uriString, handlers)
            }; return
        }
        val key = generateKey(uriString, size.width, size.height)
        val prevTag = view.tag
        view.tag = key
        val isLoading = prevTag
            ?.takeIf { it is String }
            ?.let { prevKey ->
                val future = futures[prevKey]
                if (prevKey == key && future?.isDone == false) {
                    true
                } else {
                    future?.cancel(true)
                    false
                }
            }
        if (isLoading == true) return

        memoryCache.get(key)
            ?.takeUnless { it.isRecycled() }
            ?.run { handlers.success.invoke(view, this) }
            ?: uriString?.let { loadAsync(view, size, it, key, handlers) }
    }

    override fun preDownloadIntoCache(uris: List<String?>, callback: (Boolean) -> Unit) {
        backgroundExecutor.submit {
            val lastUri = uris[uris.size - 1]
            uris.forEach { uri ->
                if (uri != null) {
                    Log.d("Uri", "Uri is $uri")
                    fileProvider.getFile(uri)
                    if (uri == lastUri) {
                        Log.d("Uri", "Done")
                        callback(true)
                    }
                }
            }

        }
    }

    private fun generateKey(url: String?, width: Int, height: Int): String {
        return url?.toSHA1() + "_" + width + "_" + height
    }

    private fun String.toSHA1(): String {
        val bytes = MessageDigest.getInstance("SHA-1").digest(this.toByteArray())
        return bytes.toHex()
    }

    private fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }

}
