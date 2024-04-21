package com.karunesh.imageloader.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.karunesh.imageloader.BaseApplication
import com.karunesh.imageloader.data.local.database.Database
import com.karunesh.imageloader.data.remote.api.Api
import com.karunesh.imageloader.data.repository.DataRepositoryImpl
import com.karunesh.imageloader.domain.repository.DataRepository
import com.karunesh.imageloader.domain.usecase.DataUseCases
import com.karunesh.imageloader.domain.usecase.GetData
import com.karunesh.imageloader.domain.usecase.GetThumbnail
import com.karunesh.imageloader.util.ApiBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesApplication(
        @ApplicationContext app: Context
    ): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun providesApi(apiBuilder: ApiBuilder): Api {
        return apiBuilder.builder(api = Api::class.java)
    }


    @Singleton
    @Provides
    fun providesRepository(api: Api, database: Database): DataRepository {
        return DataRepositoryImpl(
            api = api,
            database = database
        )
    }


    @Singleton
    @Provides
    fun providesShowUseCases(repository: DataRepository): DataUseCases {
        return DataUseCases(
            getData = GetData(repository = repository),
            getThumbnail = GetThumbnail(repository = repository)
        )
    }


    @Singleton
    @Provides
    fun providesUserDatabase(application: Application): Database {
        return Room.databaseBuilder(
            application,
            Database::class.java,
            "Database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}