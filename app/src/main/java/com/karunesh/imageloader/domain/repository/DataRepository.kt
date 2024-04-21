package com.karunesh.imageloader.domain.repository

import androidx.paging.PagingData
import com.karunesh.imageloader.domain.model.DataItem
import com.karunesh.imageloader.domain.model.Thumbnail
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    fun getData(pageSize: Int): Flow<PagingData<DataItem>>

    suspend fun getThumbnail(): Flow<List<Thumbnail?>>

}