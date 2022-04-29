package br.dev.mavpf.autocontrol.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CarDatabaseModule {

    @Provides
    @Singleton
    fun provideCarDatabase(
        @ApplicationContext app: Context
    ): CarDatabase = Room.databaseBuilder(
        app.applicationContext,
        CarDatabase::class.java,
        "CarDatabase"
    ).build()

    @Provides
    @Singleton
    fun provideCarDatabaseDao(db: CarDatabase) = db.carDatabaseDao()
}