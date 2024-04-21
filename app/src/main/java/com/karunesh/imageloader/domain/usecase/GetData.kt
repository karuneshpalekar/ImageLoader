package com.karunesh.imageloader.domain.usecase

import androidx.paging.PagingData
import com.karunesh.imageloader.domain.model.DataItem
import com.karunesh.imageloader.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow

class GetData(private val repository: DataRepository) {

    operator fun invoke(pageSize: Int): Flow<PagingData<DataItem>> {
        return repository.getData(pageSize = pageSize)
    }

}