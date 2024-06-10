package com.mohaberabi.sibha.core.data.source

import android.database.sqlite.SQLiteFullException
import com.mohaberabi.sibha.core.database.dao.TasbeehDao
import com.mohaberabi.sibha.core.database.entity.toTasbeehEntity
import com.mohaberabi.sibha.core.database.entity.toTasbeehModel
import com.mohaberabi.sibha.core.domain.datasource.TasbeehLocalDataSource
import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.util.AppError
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class RoomTasbeehDataSource(
    private val tasbeehDao: TasbeehDao,
) : TasbeehLocalDataSource {


    override suspend fun addTasbeeh(tasbeeh: TasbeehModel): EmptyDataResult<DataError.Local> {
        return try {
            tasbeehDao.addTasbeeh(tasbeeh.toTasbeehEntity())
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)

        }
    }

    override suspend fun deleteTasbeeh(id: Long) = tasbeehDao.deleteTasbeeh(id)

    override suspend fun updateTasbeehCount(
        tasbeehId: Long,
        count: Int
    ): EmptyDataResult<DataError.Local> {
        return try {
            tasbeehDao.updateTasbeehCount(tasbeehId, count)
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }

    override fun getAllTasbeeh(): Flow<List<TasbeehModel>> =
        tasbeehDao.getAllTasbeeh().map { list -> list.map { it.toTasbeehModel() } }


    override suspend fun getTasbeehsCount(): Int = tasbeehDao.getTasbeehsCount()
    override suspend fun insertDefaultTasbeehs(tasbeehs: List<TasbeehModel>):
            EmptyDataResult<DataError.Local> {
        return try {
            tasbeehDao.insertDefaultTasbeehs(tasbeehs.map { it.toTasbeehEntity() })
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        }
    }

}