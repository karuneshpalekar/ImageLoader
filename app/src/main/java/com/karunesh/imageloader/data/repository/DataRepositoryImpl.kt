package com.karunesh.imageloader.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.karunesh.imageloader.data.local.database.Database
import com.karunesh.imageloader.data.remote.api.Api
import com.karunesh.imageloader.data.repository.paging.DataRemoteMediator
import com.karunesh.imageloader.domain.model.DataItem
import com.karunesh.imageloader.domain.model.Thumbnail
import com.karunesh.imageloader.domain.repository.DataRepository
import com.karunesh.imageloader.util.DATA_LIMIT
import com.karunesh.imageloader.util.RepositoryHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataRepositoryImpl(
    private val api: Api,
    private val database: Database
) : DataRepository, RepositoryHelper() {

    @OptIn(ExperimentalPagingApi::class)
    override fun getData(pageSize: Int): Flow<PagingData<DataItem>> {
        val pagingSourceFactory = { database.dataDao().getData() }

        return Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = DataRemoteMediator(api, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { user ->
                user.toData()
            }
        }

    }

    override suspend fun getThumbnail(): Flow<List<Thumbnail?>> {

        val response = api.getData(limit = DATA_LIMIT)
        val dataList = response.map {
            it.toDataItem()
        }
        database.withTransaction {
            database.dataDao().insertAll(dataList.map {
                it.toDataEntity()
            })
        }
        return database.dataDao().getAllThumbnails()
    }

}