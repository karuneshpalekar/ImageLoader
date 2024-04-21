package com.karunesh.imageloaderx.util

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.karunesh.imageloaderx.core.Result

class BitmapResult(private val bitmap: Bitmap) : Result {

    override fun getByteCount() = bitmap.byteCount

    override fun isRecycled() = bitmap.isRecycled

    override fun getDrawable(): Drawable {
        return BitmapDrawable(null, bitmap)
    }

}
