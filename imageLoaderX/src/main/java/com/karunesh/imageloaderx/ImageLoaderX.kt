package com.karunesh.imageloaderx

import android.widget.ImageView
import com.karunesh.imageloaderx.core.Handlers
import com.karunesh.imageloaderx.core.ImageLoader
import com.karunesh.imageloaderx.util.centerCrop
import com.karunesh.imageloaderx.util.centerInside
import com.karunesh.imageloaderx.util.fetch
import com.karunesh.imageloaderx.util.fitCenter
import com.karunesh.imageloaderx.util.whenError
import com.karunesh.imageloaderx.util.withPlaceholder

class ImageLoaderX private constructor(
    private val imageView: ImageView,
    private val imageLoader: ImageLoader,
    private val url: String?,
    private val handlers: Handlers<ImageView>
) {
    class Builder(private val imageView: ImageView, private val imageLoader: ImageLoader) {
        private var url: String? = null
        private var handlers = Handlers<ImageView>()

        fun url(url: String?): Builder {
            this.url = url
            return this
        }

        fun centerCrop(): Builder {
            handlers.centerCrop()
            return this
        }

        fun fitCenter(): Builder {
            handlers.fitCenter()
            return this
        }

        fun centerInside(): Builder {
            handlers.centerInside()
            return this
        }

        fun withPlaceholder(drawableRes: Int): Builder {
            handlers.withPlaceholder(drawableRes)
            return this
        }

        fun withPlaceholder(drawableRes: Int, tintColor: Int): Builder {
            handlers.withPlaceholder(drawableRes, tintColor)
            return this
        }

        fun whenError(drawableRes: Int, tintColor: Int): Builder {
            handlers.whenError(drawableRes, tintColor)
            return this
        }

        fun build(): ImageLoaderX {
            return ImageLoaderX(imageView, imageLoader, url, handlers)
        }
    }

    fun load() {
        imageView.fetch(imageLoader, url) {
            handlers
        }
    }
}
