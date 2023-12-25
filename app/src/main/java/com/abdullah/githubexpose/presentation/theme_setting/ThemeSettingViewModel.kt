package com.abdullah.githubexpose.presentation.theme_setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abdullah.core.domain.usecase.AppSetting.AppSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeSettingViewModel @Inject constructor(private val appSettingUseCase: AppSettingUseCase) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> = appSettingUseCase.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) = appSettingUseCase.saveThemeSetting(isDarkModeActive).asLiveData()
}