package com.karunesh.imageloader.domain.usecase

import com.karunesh.imageloader.domain.model.Thumbnail
import com.karunesh.imageloader.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow

class GetThumbnail(private val repository: DataRepository) {

    suspend operator fun invoke(): Flow<List<Thumbnail?>> {
        return repository.getThumbnail()
    }

}