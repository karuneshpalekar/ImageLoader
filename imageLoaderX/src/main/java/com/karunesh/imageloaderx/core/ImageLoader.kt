package com.karunesh.imageloaderx.core

interface ImageLoader {

    fun <T> load(
        view: ViewHolder<T>,
        uriString: String?,
        handlers: Handlers<T>
    )

    fun preDownloadIntoCache(
        uris: List<String?>,
        callback: (Boolean) -> Unit
    )

}
