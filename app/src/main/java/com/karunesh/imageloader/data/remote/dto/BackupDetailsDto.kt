package com.karunesh.imageloader.data.remote.dto

import com.karunesh.imageloader.domain.model.BackupDetails

data class BackupDetailsDto(
    val pdfLink: String? = null,
    val screenshotURL: String? = null
) {

    fun toBackupDetails(): BackupDetails {
        return BackupDetails(
            pdfLink = pdfLink,
            screenshotURL = screenshotURL
        )
    }

}