package com.karunesh.imageloader.data.remote.api

import com.karunesh.imageloader.data.remote.dto.DataDtoItem
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("content/misc/media-coverages")
    suspend fun getData(
        @Query("limit") limit: Int
    ): ArrayList<DataDtoItem>

}