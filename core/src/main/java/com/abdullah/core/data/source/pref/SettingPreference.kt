package com.abdullah.core.data.source.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingPreference @Inject constructor(private val context: Context) {

    private val THEME_KEY = booleanPreferencesKey(SettingPreference.THEME_KEY)

    fun getThemeSetting(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean): Flow<Boolean> {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
        return getThemeSetting()
    }

    companion object {
        val THEME_KEY = "theme_setting"
    }
}