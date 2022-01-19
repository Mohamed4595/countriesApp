package com.mhmd.countriesapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mhmd.countriesapp.business.data.cache.abstraction.CountryCacheDatasource
import com.mhmd.countriesapp.business.data.cache.implementation.CountryCacheDatasourceImp
import com.mhmd.countriesapp.framework.datasource.cache.abstraction.CountryDaoService
import com.mhmd.countriesapp.framework.datasource.cache.database.AppDatabase
import com.mhmd.countriesapp.framework.datasource.cache.database.CountryDao
import com.mhmd.countriesapp.framework.datasource.cache.implementation.CountryDaoServiceImp
import com.mhmd.countriesapp.framework.datasource.cache.mappers.CountryEntityMapper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.Executors
import org.koin.android.ext.koin.androidContext

val CacheModule = module {
    single { provideDb(androidContext()) }
    single { provideCountryDao(get()) }
    single { provideEntityCountryMapper() }
    single { provideCountryDaoService(get(),get()) }
    single { provideCountryDatasource(get()) }

}

fun provideDb(app: Context): AppDatabase {
    return Room
        .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .setQueryCallback(
            RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
                println("SQL Query: $sqlQuery SQL Args: $bindArgs")
            },
            Executors.newSingleThreadExecutor()
        )
        .build()
}

fun provideCountryDao(db: AppDatabase): CountryDao {
    return db.countryDao()
}

fun provideEntityCountryMapper(): CountryEntityMapper {
    return CountryEntityMapper()
}


fun provideCountryDaoService(
    countryDao: CountryDao,
    countryEntityMapper: CountryEntityMapper
): CountryDaoService {
    return CountryDaoServiceImp(countryDao, countryEntityMapper)
}

fun provideCountryDatasource(countryDaoService: CountryDaoService): CountryCacheDatasource {
    return CountryCacheDatasourceImp(countryDaoService)
}


