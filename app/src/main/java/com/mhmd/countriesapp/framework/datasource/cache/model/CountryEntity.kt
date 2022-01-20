package com.mhmd.countriesapp.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(

    // Value from API
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    // Value from API
    @ColumnInfo(name = "enName")
    var enName: String?,

    // Value from API
    @ColumnInfo(name = "arName")
    var arName: String?,

    // Value from API
    @ColumnInfo(name = "countryCallingCode")
    var countryCallingCode: String?,

    // Value from API
    @ColumnInfo(name = "countryCode")
    var countryCode: String?,

    // Value from API
    @ColumnInfo(name = "countryFlag")
    var countryFlag: String?,

    // Value from API
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Int?,

    )
