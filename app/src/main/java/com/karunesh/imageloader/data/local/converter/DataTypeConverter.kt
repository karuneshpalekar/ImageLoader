package com.karunesh.imageloader.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.karunesh.imageloader.domain.model.BackupDetails
import com.karunesh.imageloader.domain.model.Thumbnail

class DataTypeConverter {

    @TypeConverter
    fun backupDetailsDataToString(value: BackupDetails?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToBackupDetailsData(value: String?): BackupDetails? {
        return Gson().fromJson(value, BackupDetails::class.java)
    }

    @TypeConverter
    fun thumbnailDataToString(value: Thumbnail?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToThumbnailData(value: String?): Thumbnail? {
        return Gson().fromJson(value, Thumbnail::class.java)
    }

}
