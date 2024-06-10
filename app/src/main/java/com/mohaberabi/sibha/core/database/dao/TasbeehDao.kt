package com.mohaberabi.sibha.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohaberabi.sibha.core.database.entity.TasbeehEntity
import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.util.DataError
import kotlinx.coroutines.flow.Flow


@Dao
interface TasbeehDao {

    @Upsert
    suspend fun addTasbeeh(tasbeeh: TasbeehEntity)

    @Query("DELETE FROM tasbeeh WHERE id =:id")
    suspend fun deleteTasbeeh(id: Long)


    @Query("UPDATE tasbeeh SET count =:count WHERE id =:tasbeehId")

    suspend fun updateTasbeehCount(tasbeehId: Long, count: Int)

    @Query("SELECT * FROM tasbeeh")
    fun getAllTasbeeh(): Flow<List<TasbeehEntity>>


    @Query("SELECT COUNT(*) FROM tasbeeh")
    suspend fun getTasbeehsCount(): Int

    @Upsert
    suspend fun insertDefaultTasbeehs(tasbeehs: List<TasbeehEntity>)
}