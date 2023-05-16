package com.abdoali.firebasetest.dataLayer

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun firebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun currentUser(firebaseAuth: FirebaseAuth) = firebaseAuth.currentUser


    @Provides
    @Singleton
    fun firebaseStorage() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun firebaseDatabase() = FirebaseDatabase.getInstance()


    @Provides
    @Singleton
    fun repositoryChat(
        firebaseAuth: FirebaseAuth ,
        storage: FirebaseStorage ,
        database: FirebaseDatabase ,
        @ApplicationContext context: Context
    ):
            RepositoryChat = RepositoryChatImp(firebaseAuth , database , storage , context)


}