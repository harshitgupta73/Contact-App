package com.example.phoneapp.data_layer.diModule

import android.app.Application
import androidx.room.Room
import com.example.phoneapp.data_layer.appDatabase.AppDatabase
import com.example.phoneapp.data_layer.repo.AppRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {
    @Provides
    @Singleton
    fun provideDatabaseInstance(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "app_database").fallbackToDestructiveMigration().build()
    }
    @Provides
    @Singleton
    fun provideRepo(database: AppDatabase): AppRepo{
        return AppRepo(database)
    }

}