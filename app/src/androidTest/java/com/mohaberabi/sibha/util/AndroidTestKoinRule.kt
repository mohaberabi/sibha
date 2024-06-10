package com.mohaberabi.sibha.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.platform.app.InstrumentationRegistry
import com.mohaberabi.sibha.core.database.db.TasbeehDb
import com.mohaberabi.sibha.core.di.appModule
import com.mohaberabi.sibha.sibha_app.dataStore
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject


open class AndroidTestKoinRule(
    private val modules: List<Module> = listOf(),
) : TestWatcher() {
    protected lateinit var context: Context
    override fun starting(
        description: Description?,
    ) {
        val instrumentedContext =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        context = instrumentedContext
        stopKoin()
        startKoin {
            androidContext(context)
            modules(
                databaseTestModule + modules
            )
        }
    }

    override fun finished(description: Description?) {
        stopKoin()
        clearDataStore()
        clearRoom()
    }


    private fun clearRoom() {
        val database = Room.inMemoryDatabaseBuilder(
            context,
            TasbeehDb::class.java
        ).build()
        database.clearAllTables()
        database.close()
    }


    private fun clearDataStore() = runTest {
        context.dataStore.edit {
            it.clear()
        }
    }
}