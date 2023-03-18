package dev.cisnux.mydatastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    fun getThemeSetting(): Flow<Boolean> =
        dataStore.data.map { preference ->
            preference[THEME_KEY] ?: false
        }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preference ->
            preference[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        // @Volatile tidak menyimpan didalam cache
        // dan melakukan perubahan dibanyak thread
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        // singleton
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences =
            // Synchronized method is a method which can be used by only one thread at a time.
            // Other threads will be waiting until the method will be released.
            // You should have only serious reasons to declare method as synchronized
            // because such method decreases the productivity.
            INSTANCE ?: synchronized(this) {
                val instances = SettingPreferences(dataStore)
                INSTANCE = instances
                instances
            }

        private val THEME_KEY = booleanPreferencesKey("theme_setting")
    }
}