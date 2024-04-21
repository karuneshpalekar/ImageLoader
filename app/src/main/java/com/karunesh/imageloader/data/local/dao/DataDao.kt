package com.karunesh.imageloader.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karunesh.imageloader.data.local.entity.DataEntity
import com.karunesh.imageloader.domain.model.Thumbnail
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao {

    @Query("SELECT * FROM data")
    fun getData(): PagingSource<Int, DataEntity>

    @Query("SELECT thumbnail FROM data")
    fun getAllThumbnails(): Flow<List<Thumbnail?>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(data: List<DataEntity>)

    @Query("DELETE FROM data")
    suspend fun clearData()
}