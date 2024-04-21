package com.karunesh.imageloader.domain.model

import com.karunesh.imageloader.data.local.entity.DataEntity

data class DataItem(
    val backupDetails: BackupDetails? = null,
    val coverageURL: String? = null,
    val id: String,
    val language: String? = null,
    val mediaType: Int? = null,
    val publishedAt: String? = null,
    val publishedBy: String? = null,
    val thumbnail: Thumbnail? = null,
    val title: String? = null
) {
    fun toDataEntity(): DataEntity {
        return DataEntity(
            id = id,
            backupDetails = backupDetails,
            coverageURL = coverageURL,
            language = language,
            mediaType = mediaType,
            publishedAt = publishedAt,
            publishedBy = publishedBy,
            thumbnail = thumbnail,
            title = title
        )
    }
}