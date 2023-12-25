package com.abdullah.core.data

import com.abdullah.core.data.source.pref.SettingPreference
import com.abdullah.core.domain.repository.IAppSettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettingRepository @Inject constructor(
    private val settingPreference: SettingPreference
) : IAppSettingRepository {

    override fun getThemeSetting(): Flow<Boolean> {
        return settingPreference.getThemeSetting()
    }

    override fun saveThemeSetting(isDarkModeActive: Boolean): Flow<Boolean> = flow {
        settingPreference.saveThemeSetting(isDarkModeActive)
    }
}