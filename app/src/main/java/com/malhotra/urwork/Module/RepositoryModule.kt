package com.malhotra.urwork.Module

import com.google.firebase.firestore.FirebaseFirestore
import com.malhotra.urwork.Repository.PersonalRepository
import com.malhotra.urwork.Repository.Personalmp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun personalRepository(database : FirebaseFirestore) : PersonalRepository {
        return Personalmp(database)
    }
}