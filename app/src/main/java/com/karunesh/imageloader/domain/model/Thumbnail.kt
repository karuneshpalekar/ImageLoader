package com.karunesh.imageloader.domain.model

data class Thumbnail(
    val aspectRatio: Int? = null,
    val basePath: String? = null,
    val domain: String? = null,
    val id: String? = null,
    val key: String? = null,
    val qualities: List<Int>? = null,
    val version: Int? = null
)