package com.abdullah.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface IAppSettingRepository {
    fun getThemeSetting(): Flow<Boolean>

    fun saveThemeSetting(isDarkModeActive: Boolean): Flow<Boolean>
}