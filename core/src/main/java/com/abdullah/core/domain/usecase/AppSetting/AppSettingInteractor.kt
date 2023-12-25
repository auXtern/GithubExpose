package com.abdullah.core.domain.usecase.AppSetting

import com.abdullah.core.domain.repository.IAppSettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppSettingInteractor @Inject constructor(private val appSettingRepository: IAppSettingRepository):
    AppSettingUseCase {
    override fun getThemeSetting(): Flow<Boolean> = appSettingRepository.getThemeSetting()

    override fun saveThemeSetting(isDarkModeActive: Boolean): Flow<Boolean> = appSettingRepository.saveThemeSetting(isDarkModeActive)
}