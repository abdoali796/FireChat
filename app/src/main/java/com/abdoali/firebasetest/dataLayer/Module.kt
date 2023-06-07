package com.abdoali.firebasetest.dataLayer

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun retrofit(): NotificationApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(ConstrinFCM.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NotificationApi::class.java)
    }


    @Provides
    @Singleton
    fun fcm()= FirebaseMessaging.getInstance()


    @Provides
    @Singleton
    fun repositoryChat(
        firebaseAuth: FirebaseAuth ,
        storage: FirebaseStorage ,
        database: FirebaseDatabase ,
        api: NotificationApi ,
        fcm:FirebaseMessaging,
        @ApplicationContext context: Context
    ):
            RepositoryChat = RepositoryChatImp(firebaseAuth , database , storage , api , fcm ,context)


}