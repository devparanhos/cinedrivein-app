package br.com.renovatiu.cinedrivein.di.modules.database

import androidx.room.Room
import br.com.renovatiu.cinedrivein.data.local.database.AppDatabase
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room
            .databaseBuilder(androidApplication(), AppDatabase::class.java, "cinedrivein.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().sessionDao()
    }

    single {
        Firebase.firestore
    }
}