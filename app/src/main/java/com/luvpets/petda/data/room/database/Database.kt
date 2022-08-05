package com.luvpets.petda.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.luvpets.petda.data.model.PetInfoEntity
import com.luvpets.petda.room.dao.PetInfoDao

@Database(entities = [PetInfoEntity::class], version = 1)
abstract class PetInfoDB: RoomDatabase() {
  abstract fun dao(): PetInfoDao
  companion object {
    fun getInstance(context: Context): PetInfoDB {
      return Room.databaseBuilder(context.applicationContext, PetInfoDB::class.java, "petInfo.db").fallbackToDestructiveMigration().build()
    }
  }
}