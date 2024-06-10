package com.mohaberabi.sibha.core.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.mohaberabi.sibha.core.util.const.BackgroundShareType
import com.mohaberabi.sibha.core.util.const.SibhaBackgrounds
import com.mohaberabi.sibha.core.util.const.SibhaSound
import com.mohaberabi.sibha.core.domain.datasource.UserLocalDataSource
import com.mohaberabi.sibha.core.domain.model.UserPrefsModel
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import com.mohaberabi.sibha.core.util.SibhaCoroutineDispatchers
import com.mohaberabi.sibha.core.util.toEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

class UserDataStore(
    private val dataStore: DataStore<Preferences>,
    private val coroutineDispatchers: SibhaCoroutineDispatchers
) : UserLocalDataSource {
    companion object {


        private val FONT_SIZE_KEY = intPreferencesKey("fontSize")
        private val BG_KEY = stringPreferencesKey("background")
        private val NOTI_CHKPNT_KEY = stringSetPreferencesKey("notificationsCheckpointKey")
        private val SOUND_KEY = stringPreferencesKey("soundKey")
        private val BG_SHARE_TYPE_KEY = stringPreferencesKey("bbShareTypeKey")
        private val BG_PATH_KEY = stringPreferencesKey("bgPathKey")

    }

    override fun getUserData(): Flow<UserPrefsModel> {

        return dataStore.data.map { prefs ->
            val fontSize = prefs[FONT_SIZE_KEY] ?: 16

            val bg = prefs[BG_KEY]?.toEnum<SibhaBackgrounds>() ?: SibhaBackgrounds.LANDSCAPE_1

            val notiCheckpoint = prefs[NOTI_CHKPNT_KEY] ?: emptySet()
            val sound = prefs[SOUND_KEY]?.toEnum<SibhaSound>() ?: SibhaSound.SOUND_1
            val bgShareType = prefs[BG_SHARE_TYPE_KEY]?.toEnum<BackgroundShareType>()
                ?: BackgroundShareType.NORMAL

            val bgPath = prefs[BG_PATH_KEY]
            UserPrefsModel(
                fontSize = fontSize,
                background = bg,
                notifyCheckpoints = notiCheckpoint.map { it.toInt() }.toSet(),
                sound = sound,
                bgShareType = bgShareType,
                customBgPath = bgPath,
            )
        }

    }

    override suspend fun updateFontSize(size: Int) {

        withContext(coroutineDispatchers.ioDispatcher) {
            dataStore.edit { prefs ->
                prefs[FONT_SIZE_KEY] = size
            }
        }
    }

    override suspend fun updateBackground(newBg: SibhaBackgrounds) {
        withContext(coroutineDispatchers.ioDispatcher) {
            dataStore.edit { prefs ->
                prefs[BG_KEY] = newBg.name.lowercase()
            }
        }

    }

    override suspend fun updateSound(sound: SibhaSound) {
        withContext(coroutineDispatchers.ioDispatcher) {
            dataStore.edit { prefs ->
                prefs[SOUND_KEY] = sound.name.lowercase()
            }
        }
    }

    override suspend fun updateNotifyCheckpoint(notifyCheckPoint: Set<Int>) {
        withContext(coroutineDispatchers.ioDispatcher) {
            dataStore.edit { prefs ->
                prefs[NOTI_CHKPNT_KEY] = notifyCheckPoint.map { it.toString() }.toSet()
            }
        }
    }


    override suspend fun updateBackgroundShareType(backgroundShareType: BackgroundShareType) {

        withContext(coroutineDispatchers.ioDispatcher) {
            dataStore.edit { prefs ->
                prefs[BG_SHARE_TYPE_KEY] = backgroundShareType.name.lowercase()
            }
        }
    }

    override suspend fun updateDefaultBackground(background: String?) {

        withContext(coroutineDispatchers.ioDispatcher) {
            dataStore.edit { prefs ->
                if (background == null) prefs.remove(BG_PATH_KEY)
                else prefs[BG_PATH_KEY] = background

            }
        }

    }
}