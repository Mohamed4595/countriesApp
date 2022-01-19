package com.mhmd.countriesapp.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhmd.countriesapp.framework.datasource.cache.model.CountryEntity

@Database(entities = [CountryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    companion object {
        const val DATABASE_NAME: String = "countries_db"
    }
}
