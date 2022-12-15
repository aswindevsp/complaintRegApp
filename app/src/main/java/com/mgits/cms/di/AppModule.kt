package com.mgits.cms.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.*
import com.google.firebase.firestore.Query.Direction.*
import com.mgits.cms.utils.Constants.PRODUCTS_COLLECTION
import com.mgits.cms.utils.Constants.TIME_PROPERTY
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
    fun provideQueryProductsByName() = FirebaseFirestore.getInstance()
        .collection(PRODUCTS_COLLECTION)
        .orderBy(TIME_PROPERTY, ASCENDING)
}