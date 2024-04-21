package com.karunesh.imageloader.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.karunesh.imageloader.data.local.converter.DataTypeConverter
import com.karunesh.imageloader.data.local.dao.DataDao
import com.karunesh.imageloader.data.local.entity.DataEntity

@Database(entities = [DataEntity::class], version = 1)
@TypeConverters(
    DataTypeConverter::class
)
abstract class Database : RoomDatabase() {

    abstract fun dataDao(): DataDao
}