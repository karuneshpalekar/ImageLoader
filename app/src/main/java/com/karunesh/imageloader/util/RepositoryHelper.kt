package com.karunesh.imageloader.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class RepositoryHelper {

    suspend fun <T> invokeApi(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (t: Throwable) {
                Resource.Error(error = t)
            }
        }
    }

}