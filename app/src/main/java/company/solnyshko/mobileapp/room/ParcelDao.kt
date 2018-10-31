package company.solnyshko.mobileapp.room

import android.arch.persistence.room.*

@Dao
interface ParcelDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertParcel(parcelEntity: ParcelEntity)

    @Update
    fun updateGender(parcelEntity: ParcelEntity)

    @Delete
    fun deleteGender(parcelEntity: ParcelEntity)

    @Query("SELECT * FROM ParcelEntity")
    fun getGenders(): List<ParcelEntity>
}