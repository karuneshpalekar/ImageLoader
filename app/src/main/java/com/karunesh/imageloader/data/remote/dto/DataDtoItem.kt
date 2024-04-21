package com.karunesh.imageloader.data.remote.dto

import com.karunesh.imageloader.domain.model.DataItem

data class DataDtoItem(
    val backupDetails: BackupDetailsDto? = null,
    val coverageURL: String? = null,
    val id: String,
    val language: String? = null,
    val mediaType: Int? = null,
    val publishedAt: String? = null,
    val publishedBy: String? = null,
    val thumbnail: ThumbnailDto? = null,
    val title: String? = null
) {

    fun toDataItem(): DataItem {
        return DataItem(
            backupDetails = backupDetails?.toBackupDetails(),
            coverageURL = coverageURL,
            id = id,
            language = language,
            mediaType = mediaType,
            publishedAt = publishedAt,
            publishedBy = publishedBy,
            thumbnail = thumbnail?.toThumbnail(),
            title = title
        )
    }
}