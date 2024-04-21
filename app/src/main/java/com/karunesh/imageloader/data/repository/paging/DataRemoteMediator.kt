package com.karunesh.imageloader.data.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.karunesh.imageloader.data.local.database.Database
import com.karunesh.imageloader.data.local.entity.DataEntity
import com.karunesh.imageloader.data.remote.api.Api
import com.karunesh.imageloader.util.DATA_LIMIT

@OptIn(ExperimentalPagingApi::class)
class DataRemoteMediator(
    private val api: Api,
    private val database: Database
) : RemoteMediator<Int, DataEntity>() {

    private var currentPage: Int? = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DataEntity>
    ): MediatorResult {

        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val lastPage = currentPage ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    lastPage + 1
                }
            }

            val response = api.getData(DATA_LIMIT)
            val dataList = response.map {
                it.toDataItem()
            }

            currentPage = page

            database.withTransaction {
                database.dataDao().insertAll(dataList.map {
                    it.toDataEntity()
                })
            }
            return MediatorResult.Success(endOfPaginationReached = false)

        } catch (exp: Exception) {
            return MediatorResult.Error(exp)
        }
    }
}