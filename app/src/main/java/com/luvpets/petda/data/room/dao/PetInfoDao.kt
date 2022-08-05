package com.luvpets.petda.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.luvpets.petda.data.model.PetInfoEntity

@Dao
interface PetInfoDao {
  @Query("SELECT * FROM petInfo_table")
  fun getPetInfo(): PetInfoEntity
  
  @Insert
  fun addPetInfo(petInfo: PetInfoEntity)
}