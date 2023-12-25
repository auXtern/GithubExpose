package com.abdullah.core.domain.usecase.AppSetting

import kotlinx.coroutines.flow.Flow

interface AppSettingUseCase {
    fun getThemeSetting(): Flow<Boolean>

    fun saveThemeSetting(isDarkModeActive: Boolean): Flow<Boolean>
}