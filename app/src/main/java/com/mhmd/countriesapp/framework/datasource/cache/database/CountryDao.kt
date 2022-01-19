package com.mhmd.countriesapp.framework.datasource.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mhmd.countriesapp.framework.datasource.cache.model.CountryEntity

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountry(country: CountryEntity): Long

    @Query("SELECT * FROM countries WHERE id = :id")
    suspend fun getCountry(id: Int): CountryEntity?

    @Query(
        """
        SELECT * FROM countries 
        WHERE enName LIKE '%' || :query || '%'
        OR arName LIKE '%' || :query || '%'  
        ORDER BY id
        """
    )
    suspend fun searchCountries(
        query: String,
    ): List<CountryEntity>

    @Query(
        """
        SELECT * FROM countries 
        ORDER BY id
    """
    )
    suspend fun getAllCountries(
    ): List<CountryEntity>

    @Query(
        """
        SELECT * FROM countries 
        WHERE isFavorite     
        ORDER BY id
    """
    )
    suspend fun getFavouriteCountries(
    ): List<CountryEntity>

}
