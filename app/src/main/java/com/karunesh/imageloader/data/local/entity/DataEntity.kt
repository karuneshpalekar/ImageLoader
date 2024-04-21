package com.karunesh.imageloader.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.karunesh.imageloader.domain.model.BackupDetails
import com.karunesh.imageloader.domain.model.DataItem
import com.karunesh.imageloader.domain.model.Thumbnail

@Entity(tableName = "data")
data class DataEntity(
    @PrimaryKey
    val id: String,
    val backupDetails: BackupDetails? = null,
    val coverageURL: String? = null,
    val language: String? = null,
    val mediaType: Int? = null,
    val publishedAt: String? = null,
    val publishedBy: String? = null,
    val thumbnail: Thumbnail? = null,
    val title: String? = null
) {

    fun toData(): DataItem {
        return DataItem(
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