package com.karunesh.imageloader.data.remote.dto

import com.karunesh.imageloader.domain.model.Thumbnail

data class ThumbnailDto(
    val aspectRatio: Int? = null,
    val basePath: String? = null,
    val domain: String? = null,
    val id: String? = null,
    val key: String? = null,
    val qualities: List<Int>? = null,
    val version: Int? = null
) {

    fun toThumbnail(): Thumbnail {
        return Thumbnail(
            aspectRatio = aspectRatio,
            basePath = basePath,
            domain = domain,
            id = id,
            key = key,
            qualities = qualities,
            version = version
        )
    }

}