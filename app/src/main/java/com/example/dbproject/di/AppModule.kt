package com.example.dbproject.di

import android.app.Application
import androidx.room.Room
import com.example.dbproject.model.ContactsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): ContactsDatabase{
        val database = Room.databaseBuilder(
            app,
            ContactsDatabase::class.java,
            "contacts_db"
        ).build()
        return database
    }

}