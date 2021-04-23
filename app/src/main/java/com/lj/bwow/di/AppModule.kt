package com.lj.bwow.di

import android.content.Context
import androidx.room.Room
import com.lj.bwow.api.HealthAPI
import com.lj.bwow.data.room.HealthDao
import com.lj.bwow.data.room.HealthDatabase
import com.lj.bwow.repositories.DefaultHealthRepository
import com.lj.bwow.repositories.HealthRepository
import com.lj.bwow.util.Constants.BASE_URL
import com.lj.bwow.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideDefaultHealthRepository(
        dao: HealthDao,
        api: HealthAPI
    ) = DefaultHealthRepository(dao, api) as HealthRepository


    @Singleton
    @Provides
    fun provideHealthDatabase(
        @ApplicationContext context : Context
    ) = Room.databaseBuilder(context, HealthDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideHealthDao(
        database: HealthDatabase
    ) = database.healthDao()

    @Singleton
    @Provides
    fun provideHealthAPI() : HealthAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(HealthAPI::class.java)
    }

}