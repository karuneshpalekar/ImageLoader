package com.karunesh.imageloaderx.util

import android.widget.ImageView
import com.karunesh.imageloaderx.core.Handlers
import com.karunesh.imageloaderx.core.ImageLoader


fun ImageView.fetch(
    imageLoader: ImageLoader,
    url: String?,
    params: Handlers<ImageView>.() -> Unit = {}
) {
    val handlers = Handlers<ImageView>()
        .apply {
            successHandler { viewHolder, result ->
                viewHolder.get().setImageDrawable(result.getDrawable())
            }
        }
        .apply(params)
    val viewHolder = ImageViewHolder(this)
    imageLoader.load(viewHolder, url, handlers)
}
